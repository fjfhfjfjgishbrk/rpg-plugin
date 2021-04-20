package fuk.plugintest;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class playerDamage implements Listener {
	
	private Main plugin;
	
	private static Integer a = 0;
	
	public playerDamage(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void playerGetHitDamage(EntityDamageByEntityEvent event){
		if (!(event.getEntity() instanceof Player)){
			return;
		}
		if (!(event.getDamager() instanceof LivingEntity)){
			return;
		}
		if (event.getDamager() instanceof Player){
			Player player = (Player) event.getDamager();
			Player hitPlayer = (Player) event.getEntity();
			player.sendMessage(ChatColor.DARK_RED + "WHY YOU HRUT PEOPLE OMG WTF??!??!?!??!");
			hitPlayer.sendMessage(ChatColor.YELLOW + "Ha you get hit noob lol");
			event.setCancelled(true);
			return;
		}
		LivingEntity attackEntity = (LivingEntity) event.getDamager();
		Player player = (Player) event.getEntity();
		String playername = player.getName();
		ArrayList<Integer> elementDefense = fileSave.elementdefense.get(playername);
		ArrayList<Integer> elementAttack = EntityElementDefense.elementAttack.get(attackEntity.getType());
		if (EntityElementDefense.customElementAttack.containsKey(attackEntity.getUniqueId())){
			elementAttack = EntityElementDefense.customElementAttack.get(attackEntity.getUniqueId());
		}
		int dodgeLvl = fileSave.dodge.get(playername);
		if (fileSave.dodge.containsKey(playername)){
			dodgeLvl += fileSave.dodge.get(playername);
		}
		double dodgeRate = (double) dodgeLvl / (double) (dodgeLvl + 1000);
		if (Math.random() < dodgeRate){
			event.setCancelled(true);
			return;
		}
		int health = fileSave.health.get(playername);
		int maxHealth = playerHealth.maxHealthActual.get(playername);
		int attackLvl = HealthBar.mobLevel.get(attackEntity.getUniqueId());
		int defense = playerHealth.defenseActual;
		double baseAtk = attackEntity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
		double damage = baseAtk;
		for (int i = 0; i < 6; i++){
			int def = elementDefense.get(i);
			int atk = elementAttack.get(i);
			if (def < 0){
				damage += baseAtk * ((double) atk / 150d) * (1d + Math.abs(def / 125d));
			}
			else if (def > 0){
				damage += baseAtk * ((double) atk / 150d) * (1d - def / (double) (175 + def));
			}
			else {
				damage += baseAtk * ((double) atk / 150d);
			}
		}
		
		double damageTaken = damage * (double) attackLvl + Math.pow(2d, Math.pow(attackLvl, 1/3.3));
		damageTaken *= (1d - (double) defense / (double) (100d + defense));
		
		
		//--------------------------------
		//item abilities
		NamespacedKey nameTag = new NamespacedKey(plugin, "name");
		HashMap<String, Integer> sets = new HashMap<String, Integer>();
		
		ItemStack helmet = player.getInventory().getHelmet();
		if (helmet != null){
			if (helmet.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
				String name = helmet.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
				switch (name) {
					case "goldhelmet":
						if (attackEntity.getType() == EntityType.SKELETON) {
							damageTaken *= 0.9;
							putSet(sets, "gold");
						}
						break;
					case "goldhelmet2":
						if (attackEntity.getType() == EntityType.SKELETON) {
							damageTaken *= 0.8;
							putSet(sets, "gold");
						}
						break;
					default:
						
				}
			}
		}
		
		
		ItemStack chestplate = player.getInventory().getChestplate();
		if (chestplate != null){
			if (chestplate.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
				String name = chestplate.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
				switch (name) {
					case "cowchest2":
						if (Math.random() < 0.08) {
							damageTaken *= -1.25;
						}
						break;
					case "goldchest":
						putSet(sets, "gold");
						break;
					default:
						
				}
			}
		}
		
		ItemStack leggings = player.getInventory().getLeggings();
		if (leggings != null){
			if (leggings.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
				String name = leggings.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
				switch (name) {
					case "goldleggings":
						putSet(sets, "gold");
						break;
					default:
						
				}
			}
		}
		
		ItemStack boots = player.getInventory().getBoots();
		if (boots != null){
			if (boots.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
				String name = boots.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
				switch (name) {
					case "goldboots":
						putSet(sets, "gold");
						damageTaken *= (1d - 0.02 * sets.get("gold"));
						break;
					case "goldboots2":
						putSet(sets, "gold");
						damageTaken *= (1d - 0.04 * sets.get("gold"));
						break;
					default:
						
				}
			}
		}
		damageTaken *= 0.9 + Math.random() * 0.2;
		updatePlayerHealth(player, health, maxHealth, damageTaken);
		
		player.setNoDamageTicks(1);
	}
	
	@EventHandler
	public void playerGetDamage(EntityDamageEvent event){
		if (!(event.getEntity() instanceof Player)){
			return;
		} 
		
		//fall damage
		if (event.getCause() == DamageCause.FALL){
			Player player = (Player) event.getEntity();
			String playername = player.getName();
			int health = fileSave.health.get(playername);
			int maxHealth = playerHealth.maxHealthActual.get(playername);
			double damage = event.getDamage();
			damage = maxHealth * (damage / (20d + (double) playerHealth.maxHealthActual.get(playername) / 600d));
			updatePlayerHealth(player, health, maxHealth, damage);
		}
		else if (event.getCause() == DamageCause.FIRE){
			Player player = (Player) event.getEntity();
			String playername = player.getName();
			int health = fileSave.health.get(playername);
			int maxHealth = playerHealth.maxHealthActual.get(playername);
			updatePlayerHealth(player, health, maxHealth, 5d);
		}
	}
	
	
	public static void updatePlayerHealth(Player player, int health, int maxHealth, double damageTaken){
		String playername = player.getName();
		System.out.println(player.getName().equalsIgnoreCase("_fjfhfjfjgishbrk"));
		Boolean testing = true;
		if (testing) {
			player.sendMessage(ChatColor.GRAY + Integer.toString((int) damageTaken));
		}
		health = (int) Math.min(playerHealth.maxHealthActual.get(playername), health - damageTaken);
		if (health <= 0){
			if (!testing || !player.getName().equalsIgnoreCase("_fjfhfjfjgishbrk")) {
				player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "YOU DIED", ChatColor.DARK_RED + "you noob lol haha", 5, 45, 15);
				for (ItemStack item: player.getInventory().getContents()){
					if (!(item == null)){
						player.getWorld().dropItemNaturally(player.getLocation(), item);
					}
				}
				player.getInventory().clear();
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 25, 1, false, false));
				if (player.getBedSpawnLocation() == null){
					player.teleport(player.getWorld().getSpawnLocation());
				}
				else {
					player.teleport(player.getBedSpawnLocation());
				}
			}
			else {
				a++;
				player.sendMessage(ChatColor.GRAY + "You died " + Integer.toString(a) + "times");
				player.sendTitle("", ChatColor.DARK_RED + "You died " + Integer.toString(a) + "times", 5, 45, 15);
			}
			health = maxHealth;
		}
		fileSave.health.put(playername, health);
		player.setHealth(Math.min(20d, 20d * (double) health / (double) playerHealth.maxHealthActual.get(playername))); 
	}
	
	private void putSet(HashMap<String, Integer> map, String s) {
		if (map.containsKey(s)) {
			map.put(s, map.get(s) + 1);
		}
		else {
			map.put(s, 1);
		}
	}
	
}
