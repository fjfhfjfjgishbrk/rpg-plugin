package fuk.plugintest.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fuk.plugintest.Main;

public class ItemsGems {
	
private static Main plugin;
	
	public ItemsGems(Main plugin){
		this.plugin = plugin;
	}
	
	private static void createFireGem(){
		ItemStack item = new ItemStack(Material.REDSTONE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Fire gem");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "On weapons:");
		lore.add(ChatColor.RED + "Increases fire damage by 1");
		lore.add(ChatColor.RED + "Also increases fire damage by 0.1%");
		lore.add("");
		lore.add(ChatColor.DARK_RED + "" + ChatColor.ITALIC + "The heat from this gem melts every");
		lore.add(ChatColor.DARK_RED + "" + ChatColor.ITALIC + "item that gets into contact with it.");
		lore.add(ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Except for your inventory and a lot of");
		lore.add(ChatColor.DARK_RED + "" + ChatColor.ITALIC + "other stuff.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.fireGem = item;
		itemManager.noRightClick.add(itemManager.fireGem);
		itemManager.gems.add(itemManager.fireGem);
	}
	
	private static void createWaterGem(){
		ItemStack item = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Water gem");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "On weapons:");
		lore.add(ChatColor.AQUA + "Increases water damage by 1");
		lore.add(ChatColor.AQUA + "Also increases water damage by 0.1%");
		lore.add("");
		lore.add(ChatColor.BLUE + "" + ChatColor.ITALIC + "It looks like sapphire but only made out of ");
		lore.add(ChatColor.BLUE + "" + ChatColor.ITALIC + "water. Though, since it's a gem, the water is");
		lore.add(ChatColor.BLUE + "" + ChatColor.ITALIC + "as hard as a rock so it might be very hard to put");
		lore.add(ChatColor.BLUE + "" + ChatColor.ITALIC + "you hand right through it.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.waterGem = item;
		itemManager.gems.add(itemManager.waterGem);
	}
	
	private static void createIceGem(){
		ItemStack item = new ItemStack(Material.BLUE_ICE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Ice gem");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "On weapons:");
		lore.add(ChatColor.BLUE + "Increases ice damage by 1");
		lore.add(ChatColor.BLUE + "Also increases ice damage by 0.1%");
		lore.add("");
		lore.add(ChatColor.BLUE + "" + ChatColor.ITALIC + "It's just cold and freezing.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.iceGem = item;
		itemManager.noRightClick.add(itemManager.iceGem);
		itemManager.gems.add(itemManager.iceGem);
	}
	
	private static void createEarthGem(){
		ItemStack item = new ItemStack(Material.EMERALD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Earth gem");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "On weapons:");
		lore.add(ChatColor.GREEN + "Increases earth damage by 1");
		lore.add(ChatColor.GREEN + "Also increases earth damage by 0.1%");
		lore.add("");
		lore.add(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "Looks and feels like dirt but in fact");
		lore.add(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "it has lots of power hidden inside it.");
		lore.add(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "Hidden using magic I guess, since it really");
		lore.add(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "looks so normal.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.earthGem = item;
		itemManager.gems.add(itemManager.earthGem);
	}
	
	private static void createThunderGem(){
		ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Thunder gem");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "On weapons:");
		lore.add(ChatColor.YELLOW + "Increases thunder damage by 1");
		lore.add(ChatColor.YELLOW + "Also increases thunder damage by 0.1%");
		lore.add("");
		lore.add(ChatColor.RED + "" + ChatColor.ITALIC + "Legends say that if you collect all six gems");
		lore.add(ChatColor.RED + "" + ChatColor.ITALIC + "you can become immortal. I was thinking of adding");
		lore.add(ChatColor.RED + "" + ChatColor.ITALIC + "it but honestly I think it would be too unbalanced");
		lore.add(ChatColor.RED + "" + ChatColor.ITALIC + "so don't actually try using the six gems on a");
		lore.add(ChatColor.RED + "" + ChatColor.ITALIC + "glove that has six holes on it. It won't do anything.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.thunderGem = item;
		itemManager.gems.add(itemManager.thunderGem);
	}
	
	private static void createMagicGem(){
		ItemStack item = new ItemStack(Material.BLAZE_POWDER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Magic gem");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "On weapons:");
		lore.add(ChatColor.LIGHT_PURPLE + "Increases magic damage by 1");
		lore.add(ChatColor.LIGHT_PURPLE + "Also increases magic damage by 0.1%");
		lore.add("");
		lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Magic looks cool, but it isn't. It just adds some");
		lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "power to your attack. That's it.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.magicGem = item;
		itemManager.gems.add(itemManager.magicGem);
	}
	
	public static void init(){
		createFireGem();
		createWaterGem();
		createIceGem();
		createEarthGem();
		createThunderGem();
		createMagicGem();
	}
	
}
