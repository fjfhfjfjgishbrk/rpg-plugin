package fuk.plugintest.abilities;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import fuk.plugintest.Main;
import fuk.plugintest.items.itemManager;

public class AbilitiesManager implements Listener{
	
	private Main plugin;
	
	public static HashMap<String, Integer> attackSpeedValue = new HashMap<String, Integer>();
	public static HashMap<String, Integer> attackSpeedPercent = new HashMap<String, Integer>();
	
	public AbilitiesManager(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		new AbilitiesGold(plugin);
	}
	
	@EventHandler
	public void useAbility(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			Player player = event.getPlayer();
			String playername = player.getName();
			ItemStack holdingItem = player.getInventory().getItemInMainHand();
			if (holdingItem == null || !holdingItem.hasItemMeta()){
				return;
			}
			
			
			String name = "";
			if (holdingItem.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "name"), PersistentDataType.STRING)) {
				name = holdingItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "name"), PersistentDataType.STRING);
			}
			
			AbilitiesGold.useAbilityGold(name, player);
		}
	}
}
