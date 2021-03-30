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

public class CowItems {
	
	public static UUID cowchestID;
	public static UUID cowchest2ID;
	public static UUID cowswordID;
	
	private static Main plugin;
	
	public CowItems(Main plugin){
		this.plugin = plugin;
	}
	
	//cow sword
		private static void createCowSword(){
			ItemStack item = new ItemStack(Material.STONE_SWORD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_RED + "Cow Eliminator");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Damage: " + ChatColor.GREEN + "+30♦");
			lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+20");
			lore.add("");
			lore.add(ChatColor.YELLOW + "Ability: Cowpocalypse");
			lore.add(ChatColor.GRAY + "Gives " + ChatColor.RED + "450 strength" + ChatColor.GRAY + " and " + ChatColor.RED + "350 damage" + ChatColor.GRAY + " against cows.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(cowswordID, "generic.attackSpeed", 100, AttributeModifier.Operation.ADD_NUMBER));
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDamage"), PersistentDataType.INTEGER_ARRAY, new int[]{0, 0, 0, 30, 0, 0});
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "strength"), PersistentDataType.INTEGER, 20);
			item.setItemMeta(meta);
			itemManager.cowSword = item;
		}
		
		//stack cow meat
		private static void createStackBeef(){
			ItemStack item = new ItemStack(Material.BEEF, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Condensed beef");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "Heals you for 25♥");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 25);
			item.setItemMeta(meta);
			itemManager.stackBeef = item;
		}
		
		//very stack cow meat
		private static void createCookedStackBeef(){
			ItemStack item = new ItemStack(Material.COOKED_BEEF, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_RED + "Highly Condensed beef");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "Heals you for 175♥ Health");
			lore.add(ChatColor.GREEN + "Gives you for 60※ Defense for 1 minute");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 175);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defenseBuff"), PersistentDataType.INTEGER_ARRAY, new int[]{60, 60});
			item.setItemMeta(meta);
			itemManager.stackCookedBeef = item;
		}
		
		//pure milk
		private static void createGodMilk(){
			ItemStack item = new ItemStack(Material.MILK_BUCKET, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Pure milk");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "Heals you for 21♥ Health");
			lore.add(ChatColor.GREEN + "Gives you for 4※ Defense for 7 seconds");
			lore.add("");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "This milk is too pure to do anything. Perhaps only when");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "combined with something can we see it's full potential.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "recover"), PersistentDataType.INTEGER, 21);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defenseBuff"), PersistentDataType.INTEGER_ARRAY, new int[]{4, 7});
			item.setItemMeta(meta);
			itemManager.godMilk = item;
		}
		
		//cow chestplate t1
		private static void createCowChestplate(){
			ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Milky chestplate");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+2650");
			lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+350");
			lore.add("");
			lore.add(ChatColor.YELLOW + "Ability: Purify force");
			lore.add(ChatColor.GRAY + "Heals you for " + ChatColor.RED + "1%♥ Health " + ChatColor.GRAY + "per second");
			lore.add("");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "Combined with pure milk this chestplate has became one");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "of the hardest substance know on Earth.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.setUnbreakable(true);
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(cowchestID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 3760);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 450);
			item.setItemMeta(meta);
			itemManager.cowChestplate = item;
		}
		
		// milk catalyst
		private static void createMilkCata(){
			ItemStack item = new ItemStack(Material.AZURE_BLUET, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_PURPLE + "Milk catalyst");
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "This catalyst from the outer worlds can probably");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "increase the power of pure milk.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			itemManager.milkCatalyst = item;
			itemManager.noRightClick.add(itemManager.milkCatalyst);
		}
		
		//cow chestplate t2
		private static void createCowChestplateT2(){
			ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_PURPLE + "Catalyzed milky chestplate");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+6230");
			lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+1220");
			lore.add(ChatColor.GRAY + "Dodge: " + ChatColor.RED + "+450");
			lore.add("");
			lore.add(ChatColor.YELLOW + "Ability: Catalyzed purification");
			lore.add(ChatColor.GRAY + "Heals you for " + ChatColor.RED + "3%♥ Health " + ChatColor.GRAY + "per second, and also");
			lore.add(ChatColor.GRAY + "has a chance to recover " + ChatColor.RED + "125% damage " + ChatColor.GRAY + "taken on hit");
			lore.add("");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "The purification force of the chestplate has now been");
			lore.add(ChatColor.ITALIC.toString() + ChatColor.DARK_GRAY + "catalyzed which makes it stronger than ever.");
			meta.setLore(lore);
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.setUnbreakable(true);
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(cowchest2ID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 6230);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 1220);
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "dodge"), PersistentDataType.INTEGER, 450);
			item.setItemMeta(meta);
			itemManager.cowChestplateT2 = item;
		}
		
		static void initCow(){
			createCowSword();
			createStackBeef();
			createCookedStackBeef();
			createGodMilk();
			createCowChestplate();
			createMilkCata();
			createCowChestplateT2();
		}
	
}
