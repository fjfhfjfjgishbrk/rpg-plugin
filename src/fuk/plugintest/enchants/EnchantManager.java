package fuk.plugintest.enchants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fuk.plugintest.InventoryManager;
import fuk.plugintest.Main;

public class EnchantManager implements Listener{
	
	private static Main plugin;
	
	private static File enchantFile;
	private static YamlConfiguration enchantConfig;
	
	//playername, skill, itemStack
	public static HashMap<String, HashMap<String, ItemStack>> icons = new HashMap<String, HashMap<String, ItemStack>>();
	
	//playername, skill icon, skill inven
	public static HashMap<String, HashMap<ItemStack, Inventory>> menus = new HashMap<String, HashMap<ItemStack, Inventory>>();
	
	//playername, different skill inven, skill class name
	public static HashMap<String, ArrayList<Inventory>> invenClass = new HashMap<String, ArrayList<Inventory>>();
	
	public static HashMap<String, HashMap<String, Integer>> wheatLevels = new HashMap<String, HashMap<String, Integer>>();
	public static HashMap<String, HashMap<String, Integer>> beetrootLevels = new HashMap<String, HashMap<String, Integer>>();
	
	public EnchantManager(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		createEnchantFile();
	}
	
	
	private static HashMap<String, ItemStack> createIconMap(Material material, HashMap<String, Integer> skills, String name, HashMap<String, ItemStack> iconmap){
		Boolean hasUpgrade = false;
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + name);
		List<String> lore = new ArrayList<>();
		lore.add("");
		for (String skillName: skills.keySet()){
			if (skillName == "None" || skills.get(skillName) == 0){
				continue;
			}
			else {
				lore.add(ChatColor.GRAY + skillName + ": " + ChatColor.GREEN + "Level " + Integer.toString(skills.get(skillName)));
				hasUpgrade = true;
			}
		}
		if (!hasUpgrade){
			lore.add(ChatColor.GRAY + "No skills upgraded yet.");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		iconmap.put(name, item);
		return iconmap;
	}
	
	
	public static void initMenu(Player player){
		String playername = player.getName();
		ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
		Inventory page1 = Bukkit.createInventory(null, 54, "Enchant skills");
		
		icons.put(playername, new HashMap<String, ItemStack>());
		icons.put(playername, createIconMap(Material.WHEAT_SEEDS, wheatLevels.get(playername), "Wheat", icons.get(playername)));
		icons.put(playername, createIconMap(Material.BEETROOT, beetrootLevels.get(playername), "Beetroot", icons.get(playername)));
		
		page1.setItem(10, icons.get(playername).get("Wheat"));
		page1.setItem(11, icons.get(playername).get("Beetroot"));
		
		
		inventoryList.add(page1);
		
		InventoryManager.enchantMenu.put(playername, inventoryList);
		
		EnchantWheat.init(player);
		EnchantBeetroot.init(player);
	}
	
	
	@EventHandler
	private void clickEnchantTable(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.ENCHANTING_TABLE){
			event.setCancelled(true);
			event.getPlayer().openInventory(InventoryManager.enchantMenu.get(event.getPlayer().getName()).get(0));
		}
	}
	
	
	@EventHandler
	private void clickInventory(InventoryClickEvent event){
		if (event.getAction() == InventoryAction.PICKUP_ALL){
			String name = event.getWhoClicked().getName();
			if (InventoryManager.enchantMenu.get(name).contains(event.getInventory())){
				event.setCancelled(true);
				Bukkit.getServer().getScheduler().runTask(plugin, new Runnable(){
					public void run(){
						HumanEntity clickedPlayer = event.getWhoClicked();
						clickedPlayer.closeInventory();
						clickedPlayer.openInventory(menus.get(name).get(event.getCurrentItem()));
					}
				});
			}
			else if (invenClass.get(event.getWhoClicked().getName()).contains(event.getInventory())){
				Inventory invClicked = event.getInventory();
				if (invClicked == EnchantWheat.wheatInventory.get(name)){
					EnchantWheat.clicked(event.getCurrentItem(), event.getWhoClicked());
					event.setCancelled(true);
					if (true){
						Bukkit.getServer().getScheduler().runTask(plugin, new Runnable(){
							public void run(){
								event.getWhoClicked().closeInventory();
							}
						});
					}
				}
				else if (invClicked == EnchantBeetroot.beetrootInventory.get(name)){
					EnchantBeetroot.clicked(event.getCurrentItem(), event.getWhoClicked());
					event.setCancelled(true);
					if (true){
						Bukkit.getServer().getScheduler().runTask(plugin, new Runnable(){
							public void run(){
								event.getWhoClicked().closeInventory();
							}
						});
					}
				}
			}
		}
	}
	
	
	public void createEnchantFile(){
		enchantFile = new File(plugin.getDataFolder(), "playerEnchantments.yml");
        if (!enchantFile.exists()) {
        	enchantFile.getParentFile().mkdirs();
            plugin.saveResource("playerEnchantments.yml", false);
         }

        enchantConfig = YamlConfiguration.loadConfiguration(enchantFile);
	}
	
	
	@EventHandler
	public void LoadEnchantFile(PlayerJoinEvent event){
		EnchantWheat.initList();
		EnchantBeetroot.initList();
		
		loadLevels(wheatLevels, ".wheat.", event.getPlayer(), EnchantWheat.wheatSkillNames);
		loadLevels(beetrootLevels, ".beetroot.", event.getPlayer(), EnchantBeetroot.beetrootSkillNames);
		
		initMenu(event.getPlayer());
	}
	
	
	public static void saveEnchantFile(){
		
		saveIntStuff(wheatLevels, ".wheat.");
		saveIntStuff(beetrootLevels, ".beetroot.");
		
		try {
 	    	enchantConfig.save(enchantFile);
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
	}
	
	
	private static void saveIntStuff(HashMap<String, HashMap<String, Integer>> h, String s){
 		Iterator<Entry<String, HashMap<String, Integer>>> skillIt = h.entrySet().iterator();
	    while (skillIt.hasNext()) {
	    	Map.Entry<String, HashMap<String, Integer>> skillPair = (Map.Entry<String, HashMap<String, Integer>>) skillIt.next();
	    	Iterator<Entry<String, Integer>> levelIt = skillPair.getValue().entrySet().iterator();
	    	while (levelIt.hasNext()){
	    		Map.Entry<String, Integer> levelPair = (Map.Entry<String, Integer>) levelIt.next();
	    		enchantConfig.set("players." + skillPair.getKey() + s + levelPair.getKey(), levelPair.getValue());
	    		levelIt.remove();
	    	}
	        skillIt.remove();
	    }
 	}
	
	
	private static void loadLevels(HashMap<String, HashMap<String, Integer>> h, String s, Player player, ArrayList<String> nameList){
		String playername = player.getName();
		HashMap<String, Integer> skillMap = new HashMap<String, Integer>();
		for (String skillName: nameList){
			Integer skillLevel = (Integer) enchantConfig.get("players." + playername + s + skillName, 0);
			skillMap.put(skillName, skillLevel);
		}
		h.put(playername, skillMap);
		
	}
	
	
	public static ItemStack createIcon(Material material, Integer level, Integer magnifiy, Double perLevel, String descrip1, String descrip2, String name, Integer maxLevel, String upItem, Boolean percent){
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + name + " level " + Integer.toString(level));
		List<String> lore = new ArrayList<>();
		if (level < maxLevel){
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "Now:");
			if (descrip1 != ""){
				lore.add(ChatColor.DARK_GRAY + descrip1);
			}
			if (percent) {
				lore.add(ChatColor.DARK_GRAY + descrip2 + Double.toString(level * perLevel) + "%");
			}
			else {
				lore.add(ChatColor.DARK_GRAY + descrip2 + Double.toString(level * perLevel));
			}
			lore.add(ChatColor.GRAY + "After upgrade:");
			if (descrip1 != ""){
				lore.add(ChatColor.GRAY + descrip1);
			}
			if (percent) {
				lore.add(ChatColor.GRAY + descrip2 + Double.toString((level + 1) * perLevel) + "%");
			}
			else {
				lore.add(ChatColor.GRAY + descrip2 + Double.toString((level + 1) * perLevel));
			}
			lore.add("");
			lore.add(ChatColor.GREEN + "Costs " + Integer.toString((level + 1) * magnifiy) + upItem + " to upgrade");
		}
		else {
			lore.add("");
			if (descrip1 != ""){
				lore.add(ChatColor.GRAY + descrip1);
			}
			if (percent) {
				lore.add(ChatColor.GRAY + descrip2 + Double.toString(level * perLevel) + "%");
			}
			else {
				lore.add(ChatColor.GRAY + descrip2 + Double.toString(level * perLevel));
			}
			lore.add("");
			lore.add(ChatColor.RED + "Already at max level");
		}
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static void clickStuff(HumanEntity player, ItemStack itemstack, String skillName, String itemName, Integer maxLevel, Integer perLevel){
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
