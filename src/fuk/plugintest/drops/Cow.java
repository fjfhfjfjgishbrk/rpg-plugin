package fuk.plugintest.drops;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fuk.plugintest.items.itemManager;

public class Cow {
	
	public static List<ItemStack> addDrops(List<ItemStack> drops, Player player, Integer luck){
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
	
}
