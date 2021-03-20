package fuk.plugintest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantWheat {
	
	public static ItemStack wheatUpgrade;
	
	public static HashMap<String, Inventory> wheatInventory = new HashMap<String, Inventory>();
	
	
	private static ItemStack createIcon(Material material, Integer level, Integer magnifiy, Double perLevel, String descrip, String name){
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + name);
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(ChatColor.GRAY + descrip + Double.toString(level * perLevel) + "%");
		lore.add("");
		lore.add(ChatColor.GREEN + "Costs " + Integer.toString(level * magnifiy) + " wheat to upgrade");
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static void initLevels(Player player){
		if (EnchantManager.wheatLevels.get(player.getName()).containsKey("None")){
			HashMap<String, Integer> newSkill = new HashMap<String, Integer>();
			newSkill.put("Wheat luck", 1);
			EnchantManager.wheatLevels.remove(player.getName());
			EnchantManager.wheatLevels.put(player.getName(), newSkill);
		}
	}
	
	public static void init(Player player){
		initLevels(player);
		String playername = player.getName();
		HashMap<String, HashMap<ItemStack, Inventory>> iconMap = EnchantManager.menus;
		HashMap<String, ArrayList<Inventory>> invenClass = EnchantManager.invenClass;
		Inventory menu = Bukkit.createInventory(null, 54, "Wheat skills");
		
		wheatUpgrade = createIcon(Material.WHEAT, EnchantManager.wheatLevels.get(playername).get("Wheat luck"), 8, 1d, "Increases the wheat you harvest by ", "Wheat luck");
		
		menu.setItem(10, wheatUpgrade);
		
		if (iconMap.containsKey(playername)){
			iconMap.get(playername).put(EnchantManager.icons.get(playername).get("Wheat harvesting"), menu);
		} else {
			HashMap<ItemStack, Inventory> temp = new HashMap<ItemStack, Inventory>();
			temp.put(EnchantManager.icons.get(playername).get("Wheat harvesting"), menu);
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
		if (item.getItemMeta().equals(wheatUpgrade.getItemMeta())){
			player.sendMessage("IM STILL TESTING THIS AHHHH I NEED SLEEP");
			
		}
		EnchantManager.initMenu((Player) player);
	}
}
