package fuk.plugintest;

import java.util.ArrayList;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fuk.plugintest.items.itemManager;
import net.md_5.bungee.api.ChatColor;

public class playerDamage implements Listener {
	
	private Main plugin;
	
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
		int maxHealth = playerHealth.maxHealthActual;
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
		ItemStack chestplate = player.getInventory().getChestplate();
		if (chestplate != null){
			if (chestplate.getItemMeta().equals(itemManager.cowChestplateT2.getItemMeta())){
				if (Math.random() < 0.1){
					damageTaken *= -1.25;
				}
			}
		}
		
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
			int maxHealth = fileSave.maxHealth.get(playername);
			double damage = event.getDamage();
			damage = maxHealth * (damage / (20d + (double) playerHealth.maxHealthActual / 100d));
			updatePlayerHealth(player, health, maxHealth, damage);
		}
		else if (event.getCause() == DamageCause.FIRE){
			Player player = (Player) event.getEntity();
			String playername = player.getName();
			int health = fileSave.health.get(playername);
			int maxHealth = fileSave.maxHealth.get(playername);
			updatePlayerHealth(player, health, maxHealth, 5d);
		}
	}
	
	
	private void updatePlayerHealth(Player player, int health, int maxHealth, double damageTaken){
		String playername = player.getName();
		health = (int) Math.min(playerHealth.maxHealthActual, health - damageTaken);
		if (health <= 0){
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
			health = maxHealth;
		}
		fileSave.health.put(playername, health);
		player.setHealth(Math.min(20d, 20d * (double) health / (double) playerHealth.maxHealthActual)); 
	}
	
}
