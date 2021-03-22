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
	public static ItemStack hayUpgrade;
	public static ItemStack stackWheatUpgrade;
	public static ItemStack rareDropUpgrade;
	
	public static HashMap<String, Inventory> wheatInventory = new HashMap<String, Inventory>();
	
	public static ArrayList<String> wheatSkillNames = new ArrayList<String>();
	
	public static void initList(){
		wheatSkillNames.add("Wheat luck");
		wheatSkillNames.add("Hay luck");
		wheatSkillNames.add("Stacked wheat luck");
		wheatSkillNames.add("Rare drops luck");
	}
	
	
	private static ItemStack createIcon(Material material, Integer level, Integer magnifiy, Double perLevel, String descrip1, String descrip2, String name, Integer maxLevel, String upItem){
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + name + " level " + Integer.toString(level));
		List<String> lore = new ArrayList<>();
		if (level < maxLevel){
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "Now:");
			lore.add(ChatColor.DARK_GRAY + descrip1);
			lore.add(ChatColor.DARK_GRAY + descrip2 + Double.toString(level * perLevel) + "%");
			lore.add(ChatColor.GRAY + "After upgrade:");
			lore.add(ChatColor.GRAY + descrip1);
			lore.add(ChatColor.GRAY + descrip2 + Double.toString((level + 1) * perLevel) + "%");
			lore.add("");
			lore.add(ChatColor.GREEN + "Costs " + Integer.toString((level + 1) * magnifiy) + upItem + " to upgrade");
		}
		else {
			lore.add("");
			lore.add(ChatColor.GRAY + descrip1);
			lore.add(ChatColor.GRAY + descrip2 + Double.toString(level * perLevel) + "%");
			lore.add("");
			lore.add(ChatColor.RED + "Already at max level");
		}
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
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
		
		wheatUpgrade = createIcon(Material.WHEAT, EnchantManager.wheatLevels.get(playername).get("Wheat luck"), 5, 1d, "Increases the wheat you harvest ", "by ", "Wheat luck", 200, " wheat");
		hayUpgrade = createIcon(Material.HAY_BLOCK, EnchantManager.wheatLevels.get(playername).get("Hay luck"), 3, 1d, "Increases the drop chances of hay ", "bale by ", "Hay luck", 150, " hay bale");
		stackWheatUpgrade = createIcon(Material.WHEAT, EnchantManager.wheatLevels.get(playername).get("Stacked wheat luck"), 2, 1.5d, "Increases the drop chances of stacked ", "wheat by ", "Stacked wheat luck", 150, " extremely stacked wheat");
		rareDropUpgrade = createIcon(Material.DIAMOND, EnchantManager.wheatLevels.get(playername).get("Rare drops luck"), 1, 2.5d, "Increases the drop chances of crystal ", "seed and translucent wheat by ", "Rare drops luck", 80, " extremely condensed hay bale");
		
		menu.setItem(10, wheatUpgrade);
		menu.setItem(11, hayUpgrade);
		menu.setItem(12, stackWheatUpgrade);
		menu.setItem(13, rareDropUpgrade);
		
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
		ItemMeta meta = item.getItemMeta();
		if (meta.equals(wheatUpgrade.getItemMeta())){
			clickStuff(player, new ItemStack(Material.WHEAT), "Wheat luck", "wheat", 200, 5);	
		}
		else if (meta.equals(hayUpgrade.getItemMeta())){
			clickStuff(player, new ItemStack(Material.HAY_BLOCK), "Hay luck", "hay bale", 150, 3);
		}
		else if (meta.equals(stackWheatUpgrade.getItemMeta())){
			clickStuff(player, itemManager.exStackWheat, "Stacked wheat luck", "extremely stacked wheat", 150, 2);
		}
		else if (meta.equals(rareDropUpgrade.getItemMeta())){
			clickStuff(player, itemManager.exStackHay, "Rare drops luck", "extremely condensed hay bale", 80, 1);
		}
		EnchantManager.initMenu((Player) player);
	}
	
	private static void clickStuff(HumanEntity player, ItemStack itemstack, String skillName, String itemName, Integer maxLevel, Integer perLevel){
		String playername = player.getName();
		Integer level = EnchantManager.wheatLevels.get(playername).get(skillName);
		if (level >= maxLevel){
			player.sendMessage("Already at max level!");
		}
		else if (player.getInventory().containsAtLeast(itemstack, (level + 1) * perLevel)){
			ItemStack items = itemstack;
			items.setAmount((level + 1) * perLevel);
			player.getInventory().removeItem(items);
			EnchantManager.wheatLevels.get(playername).put(skillName, level + 1);
			player.sendMessage(ChatColor.GREEN + skillName + " upgraded to level " + Integer.toString(level + 1));
		}
		else {
			player.sendMessage(ChatColor.RED + "Not enough " + itemName);;
		}
	}
}
