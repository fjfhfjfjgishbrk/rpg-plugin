package fuk.plugintest;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import fuk.plugintest.items.itemManager;
import net.md_5.bungee.api.ChatColor;

public class playerEat implements Listener {
	
	private Main plugin;
	private HashMap <String, Long> healCooldown = new HashMap<String, Long>();
	private HashMap <String, Long> boostCooldown = new HashMap<String, Long>();
	
	public playerEat(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void playerEatEvent(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			Player player = event.getPlayer();
			String playername = player.getName();
			NamespacedKey healTag = new NamespacedKey(plugin, "recover");
			NamespacedKey defBuffTag = new NamespacedKey(plugin, "defenseBuff");
			NamespacedKey walkBuffTag = new NamespacedKey(plugin, "walkBuff");
			NamespacedKey dodgeBuffTag = new NamespacedKey(plugin, "dodgeBuff");
			NamespacedKey healBuffTag = new NamespacedKey(plugin, "healBuff");
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
					health = Math.min(playerHealth.maxHealthActual, health + healValue);
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
						player.sendMessage(ChatColor.GOLD + "Defense boosting still in cooldown.");
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
					eat = true;
				}
			}
			
			
			//walkspeed boost
			if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(walkBuffTag, PersistentDataType.INTEGER_ARRAY)){
				boolean boost = true;
				if (fileSave.walkBoost.containsKey(playername)){
					player.sendMessage(ChatColor.GOLD + "You already have walk speed buff!");
					boost = false;
				}
				else if (boostCooldown.containsKey(playername)){
					if (boostCooldown.get(playername) > System.currentTimeMillis()){
						player.sendMessage(ChatColor.GOLD + "Walk speed boosting still in cooldown.");
						boost = false;
					}
				}
				if (boost){
					int speed[] = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(walkBuffTag, PersistentDataType.INTEGER_ARRAY);
					int amount = speed[0];
					int duration = speed[1];
					fileSave.walkBoost.put(playername, amount);
					fileSave.walkDuration.put(playername, duration);
					player.sendMessage(ChatColor.WHITE + "[Gained " + Integer.toString(amount) + "༄ walk speed for " + Integer.toString(duration) + " seconds]");
					eat = true;
				}
			}
			
			//dodge boost
			if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(dodgeBuffTag, PersistentDataType.INTEGER_ARRAY)){
				boolean boost = true;
				if (fileSave.dodgeBoost.containsKey(playername)){
					player.sendMessage(ChatColor.GOLD + "You already have dodge buff!");
					boost = false;
				}
				else if (boostCooldown.containsKey(playername)){
					if (boostCooldown.get(playername) > System.currentTimeMillis()){
						player.sendMessage(ChatColor.GOLD + "Dodge boosting still in cooldown.");
						boost = false;
					}
				}
				if (boost){
					int dodge[] = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(dodgeBuffTag, PersistentDataType.INTEGER_ARRAY);
					int amount = dodge[0];
					int duration = dodge[1];
					fileSave.dodgeBoost.put(playername, amount);
					fileSave.dodgeDuration.put(playername, duration);
					player.sendMessage(ChatColor.WHITE + "[Gained " + Integer.toString(amount) + "↺ dodge for " + Integer.toString(duration) + " seconds]");
					eat = true;
				}
			}
			
			//heal boost
			if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(healBuffTag, PersistentDataType.INTEGER_ARRAY)){
				boolean boost = true;
				if (fileSave.dodgeBoost.containsKey(playername)){
					player.sendMessage(ChatColor.GOLD + "You already have dodge buff!");
					boost = false;
				}
				else if (boostCooldown.containsKey(playername)){
					if (boostCooldown.get(playername) > System.currentTimeMillis()){
						player.sendMessage(ChatColor.GOLD + "Dodge boosting still in cooldown.");
						boost = false;
					}
				}
				if (boost){
					int heal[] = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(healBuffTag, PersistentDataType.INTEGER_ARRAY);
					int amount = heal[0];
					int duration = heal[1];
					fileSave.healBoost.put(playername, amount);
					fileSave.healDuration.put(playername, duration);
					player.sendMessage(ChatColor.AQUA + "[Gained " + Double.toString((double) amount / 100d) + "% ❉ healing for " + Integer.toString(duration) + " seconds]");
					eat = true;
				}
			}
			
			if (eat){
				player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
				boostCooldown.put(playername, System.currentTimeMillis() + 1000);
			}
		}
	}
	
}
