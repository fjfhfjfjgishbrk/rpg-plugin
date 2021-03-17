package fuk.plugintest;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class playerDamage implements Listener {
	
	private Main plugin;
	private HashMap <String, Long> healCooldown = new HashMap<String, Long>();
	private HashMap <String, Long> boostCooldown = new HashMap<String, Long>();
	
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
		LivingEntity attackEntity = (LivingEntity) event.getDamager();
		Player player = (Player) event.getEntity();
		String playername = player.getName();
		int dodgeLvl = fileSave.dodge.get(playername);
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
		double damageTaken = baseAtk * (double) attackLvl + Math.pow(2d, Math.pow(attackLvl, 1/3.3));
		System.out.println(Double.toString(damageTaken));
		damageTaken *= (1d - (double) defense / (double) (100d + defense));
		System.out.println(Double.toString(damageTaken));
		System.out.println(Double.toString(1d - (double) defense / (double) (150d + defense)));
		
		
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
	
	
	@EventHandler
	public void playerEat(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			Player player = event.getPlayer();
			String playername = player.getName();
			NamespacedKey healTag = new NamespacedKey(plugin, "recover");
			NamespacedKey defBuffTag = new NamespacedKey(plugin, "defenseBuff");
			ItemStack holdingItem = player.getInventory().getItemInMainHand();
			if (holdingItem == null || !holdingItem.hasItemMeta()){
				return;
			}
			
			// prevent from right clicking custom items
			if (itemManager.noRightClick.contains(holdingItem)){
				event.setCancelled(true);
				return;
			}
			
			
			boolean eat = false;
			//-------------------
			// eat healing stuff
			if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(healTag, PersistentDataType.INTEGER)){
				int health = fileSave.health.get(playername);
				int maxHealth = fileSave.maxHealth.get(playername);
				int healValue = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(healTag, PersistentDataType.INTEGER);
				boolean heal = true;
				if (healCooldown.containsKey(playername)){
					if (healCooldown.get(playername) > System.currentTimeMillis()){
						player.sendMessage(ChatColor.GOLD + "Healing still in cooldown.");
						heal = false;
					}
				}
				else if (health >= playerHealth.maxHealthActual){
					player.sendMessage(ChatColor.GOLD + "Already full health!");
					heal = false;
				}
				if (heal){
					health = Math.min(maxHealth, health + healValue);
					player.sendMessage(ChatColor.RED + "[Healed for " + Integer.toString(healValue) + "♥ health]");
					fileSave.health.put(playername, health);
					eat = true;
					healCooldown.put(playername, System.currentTimeMillis() + 1000);
				}
			}
			
			//------------------------
			// eat defense boost stuff
			if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(defBuffTag, PersistentDataType.INTEGER_ARRAY)){
				boolean boost = true;
				if (fileSave.defenseBoost.containsKey(playername)){
					player.sendMessage(ChatColor.GOLD + "You already have defense buff!");
					boost = false;
				}
				else if (boostCooldown.containsKey(playername)){
					if (boostCooldown.get(playername) > System.currentTimeMillis()){
						player.sendMessage(ChatColor.GOLD + "Boosting still in cooldown.");
						boost = false;
					}
				}
				if (boost){
					int def[] = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(defBuffTag, PersistentDataType.INTEGER_ARRAY);
					int amount = def[0];
					int duration = def[1];
					fileSave.defenseBoost.put(playername, amount);
					fileSave.defenseDuration.put(playername, duration);
					player.sendMessage(ChatColor.GREEN + "[Gained " + Integer.toString(amount) + "※ defense for " + Integer.toString(duration) + " seconds]");
					boostCooldown.put(playername, System.currentTimeMillis() + 1000);
					eat = true;
				}
				
				
			}
			
			if (eat){
				player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
			}
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
