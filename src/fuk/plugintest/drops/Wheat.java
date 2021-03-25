package fuk.plugintest.drops;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fuk.plugintest.enchants.EnchantManager;
import fuk.plugintest.items.itemManager;

public class Wheat {
		
	public static List<ItemStack> addDrops(List<ItemStack> drops, Player player, Integer luck){
		String playername = player.getName();
		HashMap<String, Integer> luckMap = EnchantManager.wheatLevels.get(playername);
		int wheatLuck = luckMap.get("Wheat luck");
		int hayLuck = luckMap.get("Hay luck");
		int stackWheatLuck = luckMap.get("Stacked wheat luck");
		int rareLuck = luckMap.get("Rare drops luck");
		drops.add(new ItemStack(Material.WHEAT_SEEDS, 1 + (int) Math.round(Math.random())));
		drops.add(new ItemStack(Material.WHEAT, (int) ((1 + Math.floor(Math.random() * Math.min(luck, 1500d) * 0.006d)) * (1 + (double) wheatLuck * 0.01))));
		if (Math.random() < (0.04 * (1d + (double) hayLuck * 0.01))){
			ItemStack hay = new ItemStack(Material.HAY_BLOCK);
			drops.add(hay);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.YELLOW + "[Uncommon Drop!] Hay bale");
		}
		if (Math.random() < (0.005 * (1d + (double) stackWheatLuck * 0.015))){
			ItemStack wheat = itemManager.stackWheat.clone();
			drops.add(wheat);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.LIGHT_PURPLE + "[Extreme Rare Drop!] Stacked wheat");
		}
		if (Math.random() < (0.0008 * (1d + (double) rareLuck * 0.025)) && luck > 300){
			ItemStack seed = itemManager.crystalSeed.clone();
			drops.add(seed);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.DARK_PURPLE + "[Legendary Drop!] Crystal seed");
		}
		if (Math.random() < (0.0001 * (1d + (double) rareLuck * 0.025)) && luck > 1200){
			ItemStack wheat = itemManager.transWheat.clone();
			drops.add(wheat);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.AQUA + "[Godly Drop!] Translucent wheat");
		}
		return drops;
	}
	
}
