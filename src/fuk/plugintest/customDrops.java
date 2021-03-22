package fuk.plugintest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class customDrops {
	
	private Main plugin;
	
	static int luck;
	
	public customDrops(Main plugin){
		this.plugin = plugin;
	}
	
	
	public static List<ItemStack> getDrops(LivingEntity entity, Player player){
		
		List<ItemStack> drops = new ArrayList<ItemStack>();
		String playername = player.getName();
		luck = fileSave.luck.get(playername);
		
		// cow drops
		if (entity.getType().equals(EntityType.COW)){
			drops = addDropsCow(drops, player);
			
		}
		
		return drops;
	}
	
	public static List<ItemStack> getCropDrops(Material crop, Player player){
		
		List<ItemStack> drops = new ArrayList<ItemStack>();
		String playername = player.getName();
		luck = fileSave.luck.get(playername);
		
		if (crop.equals(Material.WHEAT)){
			drops = addDropsWheat(drops, player);
		}
		
		return drops;
	}
	
	private static List<ItemStack> addDropsCow(List<ItemStack> drops, Player player){
		drops.add(new ItemStack(Material.BEEF, 1 + (int) Math.floor(Math.random() * Math.min(luck, 3000d) * 0.01d)));
		if (Math.random() < 0.05){
			ItemStack stackbeef = itemManager.stackBeef.clone();
			stackbeef.setAmount(1 + (int) Math.floor(Math.random() * Math.min(luck, 4000d) * 0.003d));
			drops.add(stackbeef);
			player.sendMessage(ChatColor.BOLD.toString() + ChatColor.YELLOW + "[Uncommon Drop!] Condensed beef");
		}
		if (Math.random() < 0.009){
			ItemStack cowsword = itemManager.cowSword.clone();
			drops.add(cowsword);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.DARK_RED + "[Rare Drop!] Cow Eiliminator");
		}
		if (Math.random() < 0.004 && luck > 360){
			ItemStack godMilk = itemManager.godMilk.clone();
			drops.add(godMilk);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.LIGHT_PURPLE + "[Extreme Rare Drop!] Pure milk");
		}
		if (Math.random() < 0.001 && luck > 1000){
			ItemStack milkCata = itemManager.milkCatalyst.clone();
			drops.add(milkCata);
			player.sendMessage(ChatColor.BOLD.toString()  + ChatColor.DARK_PURPLE + "[Legendary Drop!] Milk catalyst");
		}
		return drops;
	}
	
	private static List<ItemStack> addDropsWheat(List<ItemStack> drops, Player player){
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
