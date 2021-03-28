package fuk.plugintest.drops;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fuk.plugintest.enchants.EnchantManager;
import fuk.plugintest.items.itemManager;

public class Beetroot {
	
	public static List<ItemStack> addDrops(List<ItemStack> drops, Player player, Integer luck){
		String playername = player.getName();
		HashMap<String, Integer> luckMap = EnchantManager.beetrootLevels.get(playername);
		int beetrootLuck = luckMap.get("Beetroot luck");
		int stackWheatLuck = luckMap.get("Stacked beetroot luck");
		int rareLuck = luckMap.get("Rare drops luck");
		drops.add(new ItemStack(Material.BEETROOT_SEEDS, 1 + (int) Math.round(Math.random())));
		drops.add(new ItemStack(Material.BEETROOT, (int) ((1 + Math.floor(Math.random() * Math.min(luck, 1500d) * 0.005d)) * (1 + (double) beetrootLuck * 0.006))));
		if (Math.random() < (0.0035 * (1d + (double) stackWheatLuck * 0.012))){
			ItemStack beetroot = itemManager.stackBeetroot.clone();
			drops.add(beetroot);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.LIGHT_PURPLE + "[Extreme Rare Drop!] Stacked beetroot");
		}
		if (Math.random() < (0.0007 * (1d + (double) rareLuck * 0.025)) && luck > 350){
			ItemStack seed = itemManager.crystalBeetroot.clone();
			drops.add(seed);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.DARK_PURPLE + "[Legendary Drop!] Crystal beetroot seeds");
		}
		if (Math.random() < (0.0001 * (1d + (double) rareLuck * 0.025)) && luck > 1150){
			ItemStack liquidBeet = itemManager.liquidBeetroot.clone();
			drops.add(liquidBeet);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.AQUA + "[Godly Drop!] Liquidfied beetroot");
		}
		return drops;
	}
}
