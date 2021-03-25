package fuk.plugintest.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import fuk.plugintest.Main;

public class ItemsBeetroot {
	
	private static Main plugin;
	
	public ItemsBeetroot(Main plugin){
		this.plugin = plugin;
	}
	
	private static void createStackBeetroot(){
		ItemStack item = new ItemStack(Material.BEETROOT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Stacked beetroot");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "People said stacked items could make");
		lore.add(ChatColor.DARK_GRAY + "storing items easier.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.stackBeetroot = item;
	}
	
	
	private static void createExStackBeetroot(){
		ItemStack item = new ItemStack(Material.BEETROOT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "Extremely stacked beetroot");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "So they stacked a lot of items together, hoping");
		lore.add(ChatColor.DARK_GRAY + "that it would make transporting them cheaper.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.exStackBeetroot = item;
	}
	
	private static void createBeetrootSingu(){
		ItemStack item = new ItemStack(Material.RED_DYE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Beetroot singularity");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "The beetroot stack was too dense and became a");
		lore.add(ChatColor.DARK_GRAY + "singularity, which destroyed everything.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.beetrootSingularity = item;
	}
	
	private static void createRoastedBeetroot(){
		ItemStack item = new ItemStack(Material.BEETROOT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "Roasted Beetroot");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.RED + "Heals you for 250♥");
		lore.add(ChatColor.AQUA + "Increases ❉ healing by 10% for 30 seconds");
		lore.add("");
		lore.add(ChatColor.DARK_GRAY + "After cooking these beetroots they now look");
		lore.add(ChatColor.DARK_GRAY + "edible. Probably still not very tasty though.");
		meta.setLore(lore);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 250);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "healBuff"), PersistentDataType.INTEGER_ARRAY, new int[]{1000, 30});
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.roastedBeetroot = item;
	}
	
	private static void createMoistRoastedBeetroot(){
		ItemStack item = new ItemStack(Material.BEETROOT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Moist roasted beetroot");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.RED + "Heals you for 2650♥");
		lore.add(ChatColor.AQUA + "Increases ❉ healing by 50% for 10 minutes");
		lore.add("");
		lore.add(ChatColor.DARK_GRAY + "And now it became wet. But it's still beetroot,");
		lore.add(ChatColor.DARK_GRAY + "so probably still edible.");
		meta.setLore(lore);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 2650);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "healBuff"), PersistentDataType.INTEGER_ARRAY, new int[]{5000, 600});
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.moistRoastBeetroot = item;
	}
	
	private static void createCrystalBeetroot(){
		ItemStack item = new ItemStack(Material.BEETROOT_SEEDS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Crystal beetroot seeds");
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(ChatColor.DARK_GRAY + "These crystals seem to grow on crops. Since they");
		lore.add(ChatColor.DARK_GRAY + "are not living organisms, they could grow on any type of crops.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.crystalBeetroot = item;
		itemManager.noRightClick.add(itemManager.crystalBeetroot);
	}
	
	private static void createLiquidBeetroot(){
		ItemStack item = new ItemStack(Material.BEETROOT_SOUP, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Liquidfied beetroot");
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(ChatColor.DARK_GRAY + "These beetroots sometimes absorb too much water from the air");
		lore.add(ChatColor.DARK_GRAY + "and dirt which often makes it to moist to become a solid. The");
		lore.add(ChatColor.DARK_GRAY + "reason behind why these items come with a bowl is still a mystery,");
		lore.add(ChatColor.DARK_GRAY + "but it would be very inconvenient if there wasn't one.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.liquidBeetroot = item;
	}
	
	static void initBeet(){
		createStackBeetroot();
		createExStackBeetroot();
		createBeetrootSingu();
		createRoastedBeetroot();
		createCrystalBeetroot();
		createLiquidBeetroot();
		createMoistRoastedBeetroot();
	}
}
