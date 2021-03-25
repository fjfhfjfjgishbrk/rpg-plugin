package fuk.plugintest.items;

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

import fuk.plugintest.Main;
import fuk.plugintest.giveItems;

public class itemManager {
	
	private Main plugin;
	
	File uuidFile;
	YamlConfiguration uuidConfig;
	
	public static ItemStack testFireSword;
	
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
	
	public static ItemStack stackBeetroot;
	public static ItemStack exStackBeetroot;
	public static ItemStack beetrootSingularity;
	public static ItemStack roastedBeetroot;
	public static ItemStack crystalBeetroot;
	public static ItemStack liquidBeetroot;
	public static ItemStack moistRoastBeetroot;
	
	public static ArrayList<ItemStack> noRightClick = new ArrayList<ItemStack>();
	
	public itemManager(Main plugin){
		this.plugin = plugin;
		new CowItems(plugin);
		new ItemsWheat(plugin);
		new ItemsTest(plugin);
		new ItemsBeetroot(plugin);
		readUUID();
	}
	
	private void init(){
		CowItems.initCow();
		ItemsWheat.initWheat();
		ItemsTest.initTestItems();
		ItemsBeetroot.initBeet();
		
		giveItems.init();
	}
	
	public void readUUID(){
		uuidFile = new File(plugin.getDataFolder(), "uuidFile.yml");
		uuidConfig = YamlConfiguration.loadConfiguration(uuidFile);
		if (!uuidFile.exists()) {
 	        uuidFile.getParentFile().mkdirs();
 	        plugin.saveResource("players.yml", false);
 	    }
		CowItems.cowchestID = setUUID("cowChest");
		CowItems.cowchest2ID = setUUID("cowChest2ID");
		CowItems.cowswordID = setUUID("cowSword");
		ItemsTest.testUUID = setUUID("testItems");
		
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
	
	
}
