package fuk.plugintest;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.Inventory;

public class InventoryManager{
	
	private Main plugin;
	public static HashMap<String, ArrayList<Inventory>> enchantMenu = new HashMap<String, ArrayList<Inventory>>();
	
	public InventoryManager(Main plugin){
		this.plugin = plugin;
		init();
	}
	
	public void init(){
		new EnchantManager(plugin);
	}
	
}
