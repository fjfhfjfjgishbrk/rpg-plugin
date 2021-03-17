package fuk.plugintest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealthBar implements Listener {
	
	public static HashMap <UUID, Integer> mobLevel = new HashMap<UUID, Integer>();
	public static HashMap <UUID, Double> mobMaxHealth = new HashMap<UUID, Double>();
	public static HashMap <UUID, Double> mobHealth = new HashMap<UUID, Double>();
	public static HashMap <UUID, List<Player>> mobAttack = new HashMap<UUID, List<Player>>();
	public static HashMap <String, Long> attackCooldown = new HashMap<String, Long>();
	private Main plugin;
	
	public HealthBar(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void entitySpawn(EntitySpawnEvent event) {
		if (event.getEntity() instanceof ArmorStand) {
			return;
		}
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity mob = (LivingEntity) event.getEntity();
			if (!mob.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
				mob.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000, 6, false, false));
			}
		}
		barUpdate(event.getEntity(), 0, false);
	}
	
	@EventHandler
	public void entityDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity().isDead() || event.getEntity() instanceof ArmorStand || event.getEntity() instanceof Player){
			return;
		}
		double damageDealt = event.getDamage();
		boolean crit = false;
		if (event.getDamager() instanceof Player){
			Player player = (Player) event.getDamager();
			String playername = player.getName();
			if (attackCooldown.containsKey(playername)){
				if (attackCooldown.get(playername) > System.currentTimeMillis()){
					event.setCancelled(true);
					return;
				}
			}
			mobAttack.putIfAbsent(event.getEntity().getUniqueId(), new ArrayList<Player>());
			if (!(mobAttack.get(event.getEntity().getUniqueId()).contains(player))){
				mobAttack.get(event.getEntity().getUniqueId()).add(player);
			}
			damageDealt = damageCalculate.calculate((Player) event.getDamager(), damageDealt, (LivingEntity) event.getEntity());
			Long critRate = fileSave.critRate.get(playername);
			Long critDamage = fileSave.critDamage.get(playername);
			if (Math.random() * 10000 < critRate){
				damageDealt *= 1 + (double) critDamage / 10000d;
				crit = true;
			}
			int attackSpeed = fileSave.attackSpeed.get(playername);
			int cooldown = (int) (500 - 9.6 * Math.pow(attackSpeed, 0.4));
			attackCooldown.put(playername, System.currentTimeMillis() + cooldown);
		}
		barUpdate(event.getEntity(), damageDealt, crit);
	}
	
	@EventHandler
	public void entityRegen(EntityRegainHealthEvent event) {
		barUpdate(event.getEntity(), -event.getAmount(), false);
		event.setCancelled(true);
	}
	
	
	public void barUpdate(Entity entity, double damage, boolean crit) {
		if (entity instanceof ArmorStand || entity instanceof Player) {
			return;
		}
		if (entity instanceof LivingEntity) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
				public void run(){
					LivingEntity spawnedMob = (LivingEntity) entity;
					UUID mobID = spawnedMob.getUniqueId();
					
					
					// init mob level
					if (!(mobLevel.containsKey(mobID)) || !(mobMaxHealth.containsKey(mobID)) || !(mobHealth.containsKey(mobID))){
						int totalLevel = 0;
						int playerCount = 0;
						int distance = 100;
						int defaultLevel = 1;
						int lvl = 0;
						for (Player player : Bukkit.getOnlinePlayers()){
							if (spawnedMob.getWorld() == player.getWorld()){
								if (player.getLocation().distance(spawnedMob.getLocation()) <= distance){
									totalLevel += (int) Math.floor((fileSave.attackLevel.get(player.getName()) + fileSave.defenseLevel.get(player.getName())) / 2);
									playerCount += 1;
								}
							}
						}
						if (playerCount == 0) {
							lvl = defaultLevel;
						}
						else {
							lvl = Math.round(totalLevel / playerCount);
						}
						mobLevel.put(mobID, lvl);
						// health = base * 2^(sqrt(x-1/10))
						double maxHealth = spawnedMob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * Math.pow(2, Math.pow((float) (lvl - 1) / 10f, 0.5));
						mobMaxHealth.put(mobID, maxHealth);
						mobHealth.put(mobID, maxHealth);
					}
					
					//calculate damage
					String level = Integer.toString(mobLevel.get(mobID));
					Integer lvl = mobLevel.get(mobID);
					double health = mobHealth.get(mobID);
					double damageTaken = damage;
					if (damageTaken != 0){
						if (damageTaken < 0){
							// regen = base * 1.6^(sqrt(x-1/8))
							health -= damageTaken * Math.pow(1.6, Math.pow((float) (lvl - 1) / 8f, 0.5));
						}
						else {
							// damage = base * (1 - x / (x+100)) (resistance is x/x+100)
							damageTaken = damage * (1f - (float) lvl / (float) (lvl + 100));
							health -= damageTaken;
						}
						mobHealth.put(mobID, health);
					}
					double maxHealth = mobMaxHealth.get(mobID);
					
					//change health bar
					DecimalFormat df = new DecimalFormat("###.#");
					if (health <= 0){
						spawnedMob.setCustomName(ChatColor.BOLD + "[Lv." + level + "] " + ChatColor.RESET + ChatColor.BLACK + "0 / " + df.format(maxHealth));
						spawnedMob.setCustomNameVisible(true);
						spawnedMob.setHealth(0);
						mobLevel.remove(mobID);
						mobMaxHealth.remove(mobID);
						mobHealth.remove(mobID);
					} 
					else {
						spawnedMob.setCustomName(ChatColor.BOLD + "[Lv." + level + "] " + ChatColor.RESET + getHealthColor(health, maxHealth) + df.format(health) + " / " + df.format(maxHealth));
						spawnedMob.setCustomNameVisible(true);
					}
					
					//damage number display
					if (damageTaken <= 0){
						return;
					}
					double randomAngle = Math.random() * 2 * Math.PI;
					double randomHeight = Math.random() * 1;
					ArmorStand damageNum = (ArmorStand) spawnedMob.getWorld().spawnEntity(spawnedMob.getLocation().add(0.7 * Math.cos(randomAngle), -1 + randomHeight, 0.7 * Math.sin(randomAngle)), EntityType.ARMOR_STAND);
					if (crit){
						damageNum.setCustomName(ChatColor.BOLD.toString() + ChatColor.RED + "✱ " + df.format(damageTaken) + " ✱");
					}
					else {
						damageNum.setCustomName(ChatColor.GOLD + df.format(damageTaken));
					}
					damageNum.setCustomNameVisible(true);
					damageNum.setGravity(false);
					damageNum.setInvulnerable(true);
					damageNum.setInvisible(true);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
						public void run(){
							damageNum.remove();
						}
					}, 40);
				}
			}, 4);
		}
	}
	
	private ChatColor getHealthColor(double health, double maxHealth){
		double percent = health / maxHealth;
		if (percent > 0.75){
			return ChatColor.GREEN;
		}
		else if (percent > 0.5){
			return ChatColor.YELLOW;
		}
		else if (percent > 0.25){
			return ChatColor.RED;
		}
		else {
			return ChatColor.DARK_RED;
		}
		
	}
	
}
