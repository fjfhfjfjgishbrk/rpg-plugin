package fuk.plugintest.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import fuk.plugintest.Main;

public class ItemsTest {
	
	private static Main plugin;
	
	public static UUID testUUID;
	
	public ItemsTest(Main plugin){
		this.plugin = plugin;
	}
	
	private static void createTestFireSword(){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Fire test sword");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "30♦");
		lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+20");
		lore.add("");
		lore.add(ChatColor.DARK_RED + "This item is used for testing");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(testUUID, "generic.attackSpeed", 100, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDamage"), PersistentDataType.INTEGER_ARRAY, new int[]{30, 0, 0, 0, 0, 0});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "strength"), PersistentDataType.INTEGER, 20);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "testfiresword");
		item.setItemMeta(meta);
		itemManager.testFireSword = item;
		itemManager.canUpgrade.add("testfiresword");
		itemManager.sword.add("testfiresword");
	}
	
	private static void createTestFireSword2(){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Fire test sword tier 2");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "120♦");
		lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+60");
		lore.add("");
		lore.add(ChatColor.DARK_RED + "This item is used for testing");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(testUUID, "generic.attackSpeed", 100, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDamage"), PersistentDataType.INTEGER_ARRAY, new int[]{120, 0, 0, 0, 0, 0});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "strength"), PersistentDataType.INTEGER, 60);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "testfiresword2");
		item.setItemMeta(meta);
		itemManager.testFireSwordt2 = item;
		itemManager.canUpgrade.add("testfiresword2");
		itemManager.sword.add("testfiresword2");
	}
	
	private static void createTestPickaxe(){
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Test Pickaxe");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Mining Speed: " + ChatColor.YELLOW + "+2500");
		lore.add("");
		lore.add(ChatColor.DARK_RED + "This item is used for testing");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "miningspeed"), PersistentDataType.INTEGER, 2500);
		item.setItemMeta(meta);
		itemManager.testPickaxe = item;
	}
	
	public static void initTestItems(){
		createTestFireSword();
		createTestFireSword2();
		createTestPickaxe();
	}
}
