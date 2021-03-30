package fuk.plugintest.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fuk.plugintest.Main;
import fuk.plugintest.giveItems;

public class itemManager {
	
	private Main plugin;
	
	File uuidFile;
	YamlConfiguration uuidConfig;
	
	private static HashMap<String, ItemStack> items = new HashMap<String, ItemStack>();
	
	public static ItemStack testFireSword;
	public static ItemStack testFireSwordt2;
	public static ItemStack testPickaxe;
	
	public static ItemStack cowSword;
	public static ItemStack stackBeef;
	public static ItemStack stackCookedBeef;
	public static ItemStack godMilk;
	public static ItemStack cowChestplate;
	public static ItemStack cowChestplateT2;
	public static ItemStack milkCatalyst;
	
	//farming items
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
	
	public static ItemStack stackCarrot;
	public static ItemStack exStackCarrot;
	public static ItemStack stackGoldCarrot;
	public static ItemStack exStackGoldCarrot;
	
	//mining items
	public static ItemStack stackGold;
	public static ItemStack exStackGold;
	public static ItemStack stackGoldBlock;
	public static ItemStack exStackGoldBlock;
	public static ItemStack goldSword;
	public static ItemStack goldSwordT2;
	public static ItemStack goldAxe;
	public static ItemStack goldHoe;
	public static ItemStack goldPick;
	public static ItemStack goldHelmet;
	public static ItemStack goldChestplate;
	public static ItemStack goldLeggings;
	public static ItemStack goldBoots;
	public static ItemStack goldHelmetT2;
	public static ItemStack goldChestplateT2;
	public static ItemStack goldLeggingsT2;
	public static ItemStack goldBootsT2;
	
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
