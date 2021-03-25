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

public class EnchantBeetroot {
	
	public static HashMap<String, Inventory> beetrootInventory = new HashMap<String, Inventory>();
	
	public static ArrayList<String> beetrootSkillNames = new ArrayList<String>();
	
	public static ItemStack beetrootUpgrade;
	public static ItemStack stackBeetrootUpgrade;
	public static ItemStack rareDropUpgrade;
	
	public static void initList(){
		beetrootSkillNames.add("Beetroot luck");
		beetrootSkillNames.add("Stacked beetroot luck");
		beetrootSkillNames.add("Rare drops luck");
	}
	
	
	public static void initLevels(Player player){
		for (String skillName: beetrootSkillNames){
			if (!EnchantManager.beetrootLevels.get(player.getName()).containsKey(skillName)){
				EnchantManager.beetrootLevels.get(player.getName()).put(skillName, 1);
			}
		}
	}
	
	public static void init(Player player){
		initLevels(player);
		String playername = player.getName();
		HashMap<String, HashMap<ItemStack, Inventory>> iconMap = EnchantManager.menus;
		HashMap<String, ArrayList<Inventory>> invenClass = EnchantManager.invenClass;
		Inventory menu = Bukkit.createInventory(null, 54, "Beetroot skills");
		
		beetrootUpgrade = EnchantManager.createIcon(Material.BEETROOT_SEEDS, EnchantManager.beetrootLevels.get(playername).get("Beetroot luck"), 4, 0.6d, "Increases the beetroot you harvest ", "by ", "Beetroot luck", 160, " beetroot", true);
		stackBeetrootUpgrade = EnchantManager.createIcon(Material.BEETROOT, EnchantManager.beetrootLevels.get(playername).get("Stacked beetroot luck"), 2, 1.2d, "Increases the drop chances of stacked ", "beetroot by ", "Stacked beetroot luck", 130, " beetroot singularity", true);
		rareDropUpgrade = EnchantManager.createIcon(Material.DIAMOND, EnchantManager.beetrootLevels.get(playername).get("Rare drops luck"), 1, 2.5d, "Increases the drop chances of crystal ", "beetroot and liquidfied beetroot by ", "Rare drops luck", 80, " liquidfied beetroot", true);
		
		menu.setItem(10, beetrootUpgrade);
		menu.setItem(11, stackBeetrootUpgrade);
		menu.setItem(12, rareDropUpgrade);
		
		if (iconMap.containsKey(playername)){
			iconMap.get(playername).put(EnchantManager.icons.get(playername).get("Beetroot"), menu);
		} else {
			HashMap<ItemStack, Inventory> temp = new HashMap<ItemStack, Inventory>();
			temp.put(EnchantManager.icons.get(playername).get("Beetroot"), menu);
			iconMap.put(playername, temp);
		}
		
		if (invenClass.containsKey(playername)){
			 invenClass.get(playername).add(menu);
		} else {
			ArrayList<Inventory> temp = new ArrayList<Inventory>();
			temp.add(menu);
			invenClass.put(playername, temp);
		}
		
		beetrootInventory.put(playername, menu);
	}
	
	public static void clicked(ItemStack item, HumanEntity player){
		ItemMeta meta = item.getItemMeta();
		if (meta.equals(beetrootUpgrade.getItemMeta())){
			EnchantManager.clickStuff(player, new ItemStack(Material.BEETROOT), "Beetroot luck", "beetroot", 160, 4);	
		}
		else if (meta.equals(stackBeetrootUpgrade.getItemMeta())){
			EnchantManager.clickStuff(player, itemManager.exStackBeetroot, "Stacked beetroot luck", "extremely stacked beetroot", 130, 2);
		}
		else if (meta.equals(rareDropUpgrade.getItemMeta())){
			EnchantManager.clickStuff(player, itemManager.liquidBeetroot, "Rare drops luck", "liquidfied beetroot", 80, 1);
		}
		EnchantManager.initMenu((Player) player);
	}
}
