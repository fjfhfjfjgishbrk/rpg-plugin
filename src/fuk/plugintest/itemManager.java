package fuk.plugintest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class itemManager {
	
	private Main plugin;
	
	File uuidFile;
	YamlConfiguration uuidConfig;
	
	private UUID cowchestID;
	private UUID cowchest2ID;
	private UUID cowswordID;
	
	public static ItemStack cowSword;
	public static ItemStack stackBeef;
	public static ItemStack stackCookedBeef;
	public static ItemStack godMilk;
	public static ItemStack cowChestplate;
	public static ItemStack cowChestplateT2;
	public static ItemStack milkCatalyst;
	
	public static ItemStack stackWheat;
	public static ItemStack exStackWheat;
	public static ItemStack stackHay;
	public static ItemStack exStackHay;
	public static ItemStack betterBread;
	public static ItemStack crystalSeed;
	public static ItemStack transWheat;
	
	public static ItemStack sandwich;
	public static ItemStack naturalSandwich;
	public static ItemStack naturalSandwichT2;
	
	public static ArrayList<ItemStack> noRightClick = new ArrayList<ItemStack>();
	
	public itemManager(Main plugin){
		this.plugin = plugin;
		readUUID();
	}
	
	private void init(){
		initCow();
		initWheat();
	}
	
	private void readUUID(){
		uuidFile = new File(plugin.getDataFolder(), "uuidFile.yml");
		uuidConfig = YamlConfiguration.loadConfiguration(uuidFile);
		if (!uuidFile.exists()) {
 	        uuidFile.getParentFile().mkdirs();
 	        plugin.saveResource("players.yml", false);
 	    }
		cowchestID = setUUID("cowChest");
		cowchest2ID = setUUID("cowChest2ID");
		cowswordID = setUUID("cowSword");
		
		try {
 	    	uuidConfig.save(uuidFile);
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		init();
	}
	
	
	private UUID setUUID(String path){
		UUID uuid;
		String readValue = String.valueOf(uuidConfig.get(path, -1));
		try {
			uuid = UUID.fromString(readValue);
		} catch (Exception e) {
			uuid = UUID.randomUUID();
			uuidConfig.set(path, uuid.toString());
		}
		return uuid;
	}
	
	//cow sword
	private void createCowSword(){
		ItemStack item = new ItemStack(Material.STONE_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "Cow Eliminator");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+30");
		lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+20");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Cowpocalypse");
		lore.add(ChatColor.GRAY + "Gives " + ChatColor.RED + "450 strength" + ChatColor.GRAY + " and " + ChatColor.RED + "350 damage" + ChatColor.GRAY + " against cows.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(cowswordID, "generic.attackSpeed", 10, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "damage"), PersistentDataType.INTEGER, 30);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "strength"), PersistentDataType.INTEGER, 20);
		item.setItemMeta(meta);
		cowSword = item;
	}
	
	//stack cow meat
	private void createStackBeef(){
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
		stackBeef = item;
	}
	
	//very stack cow meat
	private void createCookedStackBeef(){
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
		stackCookedBeef = item;
	}
	
	//pure milk
	private void createGodMilk(){
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
		godMilk = item;
	}
	
	//cow chestplate t1
	private void createCowChestplate(){
		ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Milky chestplate");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+2650");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+350");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Purify force");
		lore.add(ChatColor.GRAY + "Heals you for " + ChatColor.RED + "2%♥ Health " + ChatColor.GRAY + "per second");
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
		cowChestplate = item;
	}
	
	// milk catalyst
	private void createMilkCata(){
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
		milkCatalyst = item;
		noRightClick.add(milkCatalyst);
	}
	
	//cow chestplate t2
	private void createCowChestplateT2(){
		ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Catalyzed milky chestplate");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+6230");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+1220");
		lore.add(ChatColor.GRAY + "Dodge: " + ChatColor.RED + "+450");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Catalyzed purification");
		lore.add(ChatColor.GRAY + "Heals you for " + ChatColor.RED + "5%♥ Health " + ChatColor.GRAY + "per second, and also");
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
		cowChestplateT2 = item;
	}
	
	private void initCow(){
		createCowSword();
		createStackBeef();
		createCookedStackBeef();
		createGodMilk();
		createCowChestplate();
		createMilkCata();
		createCowChestplateT2();
	}
	
	//stack wheat
	private void createStackWheat(){
		ItemStack item = new ItemStack(Material.WHEAT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Stacked wheat");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "It's just a lot of wheat.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		stackWheat = item;
	}
	
	//very stack wheat
	private void createVeryStackWheat(){
		ItemStack item = new ItemStack(Material.WHEAT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "Extremely stacked wheat");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "It's more than a lot of wheat.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		exStackWheat = item;
	}
	
	//stack hay
	private void createStackHay(){
		ItemStack item = new ItemStack(Material.HAY_BLOCK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Condensed hay bale");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Wouldn't it be heavy holding this much wheat?");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		stackHay = item;
		noRightClick.add(stackHay);
	}
	
	//very stack hay
	private void createVeryStackHay(){
		ItemStack item = new ItemStack(Material.HAY_BLOCK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Extremely condensed hay bale");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "When will we ever have enough wheat?");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		exStackHay = item;
		noRightClick.add(exStackHay);
	}
	
	//processed bread
	private void createProcessedBread(){
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
		betterBread = item;
	}
	
	//sandwich
	private void createSandwich(){
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
		sandwich = item;
	}
	
	//natural sandwich
	private void createNaturalSandwich(){
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
		naturalSandwich = item;
	}
	
	//transparent sandwich
	private void createNaturalSandwichT2(){
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
		naturalSandwichT2 = item;
	}
	
	//crystal seed
	private void createCrystalSeed(){
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
		crystalSeed = item;
		noRightClick.add(crystalSeed);
	}
	
	//trans wheat
	private void createTransWheat(){
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
		transWheat = item;
	}
	
	private void initWheat(){
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
