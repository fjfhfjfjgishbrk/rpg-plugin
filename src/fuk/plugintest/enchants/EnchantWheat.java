package fuk.plugintest.enchants;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fuk.plugintest.items.itemManager;

public class EnchantWheat {
	
	public static ItemStack wheatUpgrade;
	public static ItemStack hayUpgrade;
	public static ItemStack stackWheatUpgrade;
	public static ItemStack rareDropUpgrade;
	public static ItemStack dodgeUpgrade;
	
	public static HashMap<String, Inventory> wheatInventory = new HashMap<String, Inventory>();
	
	public static ArrayList<String> wheatSkillNames = new ArrayList<String>();
	
	public static void initList(){
		wheatSkillNames.add("Wheat luck");
		wheatSkillNames.add("Hay luck");
		wheatSkillNames.add("Stacked wheat luck");
		wheatSkillNames.add("Rare drops luck");
		wheatSkillNames.add("Dodge upgrade");
	}
	
	
	public static void initLevels(Player player){
		for (String skillName: wheatSkillNames){
			if (!EnchantManager.wheatLevels.get(player.getName()).containsKey(skillName)){
				EnchantManager.wheatLevels.get(player.getName()).put(skillName, 1);
			}
		}
	}
	
	public static void init(Player player){
		initLevels(player);
		String playername = player.getName();
		HashMap<String, HashMap<ItemStack, Inventory>> iconMap = EnchantManager.menus;
		HashMap<String, ArrayList<Inventory>> invenClass = EnchantManager.invenClass;
		Inventory menu = Bukkit.createInventory(null, 54, "Wheat skills");
		
		wheatUpgrade = EnchantManager.createIcon(Material.WHEAT, EnchantManager.wheatLevels.get(playername).get("Wheat luck"), 5, 1d, "Increases the wheat you harvest ", "by ", "Wheat luck", 200, " wheat", true);
		hayUpgrade = EnchantManager.createIcon(Material.HAY_BLOCK, EnchantManager.wheatLevels.get(playername).get("Hay luck"), 3, 1d, "Increases the drop chances of hay ", "bale by ", "Hay luck", 150, " hay bale", true);
		stackWheatUpgrade = EnchantManager.createIcon(Material.WHEAT, EnchantManager.wheatLevels.get(playername).get("Stacked wheat luck"), 2, 1.5d, "Increases the drop chances of stacked ", "wheat by ", "Stacked wheat luck", 150, " extremely stacked wheat", true);
		rareDropUpgrade = EnchantManager.createIcon(Material.DIAMOND, EnchantManager.wheatLevels.get(playername).get("Rare drops luck"), 1, 2.5d, "Increases the drop chances of crystal ", "seed and translucent wheat by ", "Rare drops luck", 80, " extremely condensed hay bale", true);
		dodgeUpgrade = EnchantManager.createIcon(Material.IRON_BOOTS, EnchantManager.wheatLevels.get(playername).get("Dodge upgrade"), 1, 3d, "", "Increase your dodge by ", "Dodge upgrade", 20, " translucent uncultivated sandwich", false);
		
		menu.setItem(10, wheatUpgrade);
		menu.setItem(11, hayUpgrade);
		menu.setItem(12, stackWheatUpgrade);
		menu.setItem(13, rareDropUpgrade);
		menu.setItem(19, dodgeUpgrade);
		
		if (iconMap.containsKey(playername)){
			iconMap.get(playername).put(EnchantManager.icons.get(playername).get("Wheat"), menu);
		} else {
			HashMap<ItemStack, Inventory> temp = new HashMap<ItemStack, Inventory>();
			temp.put(EnchantManager.icons.get(playername).get("Wheat"), menu);
			iconMap.put(playername, temp);
		}
		
		if (invenClass.containsKey(playername)){
			 invenClass.get(playername).add(menu);
		} else {
			ArrayList<Inventory> temp = new ArrayList<Inventory>();
			temp.add(menu);
			invenClass.put(playername, temp);
		}
		
		wheatInventory.put(playername, menu);
	}
	
	public static void clicked(ItemStack item, HumanEntity player){
		ItemMeta meta = item.getItemMeta();
		if (meta.equals(wheatUpgrade.getItemMeta())){
			EnchantManager.clickStuff(player, new ItemStack(Material.WHEAT), "Wheat luck", "wheat", 200, 5);	
		}
		else if (meta.equals(hayUpgrade.getItemMeta())){
			EnchantManager.clickStuff(player, new ItemStack(Material.HAY_BLOCK), "Hay luck", "hay bale", 150, 3);
		}
		else if (meta.equals(stackWheatUpgrade.getItemMeta())){
			EnchantManager.clickStuff(player, itemManager.exStackWheat, "Stacked wheat luck", "extremely stacked wheat", 150, 2);
		}
		else if (meta.equals(rareDropUpgrade.getItemMeta())){
			EnchantManager.clickStuff(player, itemManager.exStackHay, "Rare drops luck", "extremely condensed hay bale", 80, 1);
		}
		else if (meta.equals(dodgeUpgrade.getItemMeta())){
			EnchantManager.clickStuff(player, itemManager.naturalSandwichT2, "Dodge upgrade", "translucent uncultivated sandwich", 30, 1);
		}
		EnchantManager.initMenu((Player) player);
	}
	
}
