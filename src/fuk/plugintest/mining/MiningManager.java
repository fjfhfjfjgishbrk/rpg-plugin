package fuk.plugintest.mining;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.ProtocolManager;

import fuk.plugintest.Main;

public class MiningManager {
	
	private Main plugin;
	
	public static HashMap<String, Boolean> isMining = new HashMap<String, Boolean>();
	public static HashMap<Material, Integer> breakSpeed = new HashMap<Material, Integer>();
	public static HashMap<ItemStack, Integer> pickaxeSpeed = new HashMap<ItemStack, Integer>();
	
	public MiningManager(Main plugin, ProtocolManager protocolManager){
		this.plugin = plugin;
		initSpeed();
		new BlockBreakListener(plugin, protocolManager);
	}
	
	private static void initSpeed(){
		breakSpeed.put(Material.COAL_ORE, 3);
		breakSpeed.put(Material.IRON_ORE, 5);
		breakSpeed.put(Material.GOLD_ORE, 4);
		breakSpeed.put(Material.REDSTONE_ORE, 7);
		breakSpeed.put(Material.LAPIS_BLOCK, 7);
		breakSpeed.put(Material.EMERALD_ORE, 8);
		breakSpeed.put(Material.DIAMOND_ORE, 9);
		breakSpeed.put(Material.OBSIDIAN, 25);
	}
	
	
}
