package fuk.plugintest;

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

public class ItemsWheat {
	
	private static Main plugin;
	
	public ItemsWheat(Main plugin){
		this.plugin = plugin;
	}
	
	//stack wheat
		private static void createStackWheat(){
			ItemStack item = new ItemStack(Material.WHEAT, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Stacked wheat");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "It's just a lot of wheat.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			itemManager.stackWheat = item;
		}
		
		//very stack wheat
		private static void createVeryStackWheat(){
			ItemStack item = new ItemStack(Material.WHEAT, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_RED + "Extremely stacked wheat");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "It's more than a lot of wheat.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			itemManager.exStackWheat = item;
		}
		
		//stack hay
		private static void createStackHay(){
			ItemStack item = new ItemStack(Material.HAY_BLOCK, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Condensed hay bale");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Wouldn't it be heavy holding this much wheat?");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			itemManager.stackHay = item;
			itemManager.noRightClick.add(itemManager.stackHay);
		}
		
		//very stack hay
		private static void createVeryStackHay(){
			ItemStack item = new ItemStack(Material.HAY_BLOCK, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_PURPLE + "Extremely condensed hay bale");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "When will we ever have enough wheat?");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			itemManager.exStackHay = item;
			itemManager.noRightClick.add(itemManager.exStackHay);
		}
		
		//processed bread
		private static void createProcessedBread(){
			ItemStack item = new ItemStack(Material.BREAD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_RED + "Processed bread");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "Heals you for 350♥");
			meta.setLore(lore);
			lore.add("");
			lore.add(ChatColor.GRAY + "Made out of lots of wheat, although this sandwich looks");
			lore.add(ChatColor.GRAY + "very heavy, it is surprising quite healthy.");
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 350);
			item.setItemMeta(meta);
			itemManager.betterBread = item;
		}
		
		//sandwich
		private static void createSandwich(){
			ItemStack item = new ItemStack(Material.BREAD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sandwich");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "Heals you for 900♥");
			lore.add("");
			lore.add(ChatColor.GRAY + "Just a normal sandwich.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 900);
			item.setItemMeta(meta);
			itemManager.sandwich = item;
		}
		
		//natural sandwich
		private static void createNaturalSandwich(){
			ItemStack item = new ItemStack(Material.BREAD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_PURPLE + "Natural sandwich");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "Heals you for 800♥");
			lore.add(ChatColor.WHITE + "Gives you 250 ༄ walk speed for 8 minutes");
			lore.add(ChatColor.GOLD + "Gives you 250 ↺ dodge for 8 minutes");
			lore.add("");
			lore.add(ChatColor.GRAY + "It's a sandwich that is natural. With crystals in it.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 800);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "walkBuff"), PersistentDataType.INTEGER_ARRAY, new int[]{250, 480});
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "dodgeBuff"), PersistentDataType.INTEGER_ARRAY, new int[]{250, 480});
			item.setItemMeta(meta);
			itemManager.naturalSandwich = item;
		}
		
		//transparent sandwich
		private static void createNaturalSandwichT2(){
			ItemStack item = new ItemStack(Material.BREAD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.AQUA + "Translucent uncultivated sandwich");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "Heals you for 3200♥");
			lore.add(ChatColor.WHITE + "Gives you 1200 ༄ walk speed for 35 minutes");
			lore.add(ChatColor.GOLD + "Gives you 650 ↺ dodge for 30 minutes");
			lore.add("");
			lore.add(ChatColor.GRAY + "The sandwich starts to become transparent, but you can still see");
			lore.add(ChatColor.GRAY + "enough of it to be able to take a bite.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 800);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "walkBuff"), PersistentDataType.INTEGER_ARRAY, new int[]{1200, 2100});
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "dodgeBuff"), PersistentDataType.INTEGER_ARRAY, new int[]{650, 1800});
			item.setItemMeta(meta);
			itemManager.naturalSandwichT2 = item;
		}
		
		//crystal seed
		private static void createCrystalSeed(){
			ItemStack item = new ItemStack(Material.WHEAT_SEEDS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_PURPLE + "Crystal seed");
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "It's a seed but somehow has crystal stuck to it. Might");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "be tasty to eat it though.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			itemManager.crystalSeed = item;
			itemManager.noRightClick.add(itemManager.crystalSeed);
		}
		
		//trans wheat
		private static void createTransWheat(){
			ItemStack item = new ItemStack(Material.WHEAT, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.AQUA + "Translucent wheat");
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "Since this wheat is nearly invisible, it is very hard for people");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "to find it while harvesting. Some people have only ever seen one");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "in their whole life, and mostly they only see it for a split second");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "before it disappears again.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			itemManager.transWheat = item;
		}
		
		static void initWheat(){
			createStackWheat();
			createVeryStackWheat();
			createStackHay();
			createVeryStackHay();
			createProcessedBread();
			createSandwich();
			createNaturalSandwich();
			createNaturalSandwichT2();
			createCrystalSeed();
			createTransWheat();
		}
}
