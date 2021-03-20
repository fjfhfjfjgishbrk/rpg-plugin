package fuk.plugintest;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantManager implements Listener{
	
	private static Main plugin;
	
	private File enchantFile;
	private static YamlConfiguration enchantConfig;
	
	//playername, skill, itemStack
	public static HashMap<String, HashMap<String, ItemStack>> icons = new HashMap<String, HashMap<String, ItemStack>>();
	
	//playername, skill icon, skill inven
	public static HashMap<String, HashMap<ItemStack, Inventory>> menus = new HashMap<String, HashMap<ItemStack, Inventory>>();
	
	//playername, different skill inven, skill class name
	public static HashMap<String, ArrayList<Inventory>> invenClass = new HashMap<String, ArrayList<Inventory>>();
	
	public static HashMap<String, HashMap<String, Integer>> wheatLevels = new HashMap<String, HashMap<String, Integer>>();
	
	public EnchantManager(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		createEnchantFile();
	}
	
	
	private static HashMap<String, ItemStack> createIconMap(Material material, HashMap<String, Integer> skills, String name){
		Boolean hasUpgrade = false;
		HashMap<String, ItemStack> iconmap = new HashMap<String, ItemStack>();	
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + name);
		List<String> lore = new ArrayList<>();
		lore.add("");
		for (String skillName: skills.keySet()){
			if (skillName == "None" || skills.get(skillName) == 1){
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
		
		icons.put(playername, createIconMap(Material.WHEAT_SEEDS, wheatLevels.get(playername), "Wheat harvesting"));
		
		page1.setItem(10, icons.get(playername).get("Wheat harvesting"));
		
		
		inventoryList.add(page1);
		
		InventoryManager.enchantMenu.put(playername, inventoryList);
		
		EnchantWheat.init(player);
	}
	
	
	@EventHandler
	public void clickEnchantTable(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.ENCHANTING_TABLE){
			event.setCancelled(true);
			event.getPlayer().openInventory(InventoryManager.enchantMenu.get(event.getPlayer().getName()).get(0));
		}
	}
	
	
	@EventHandler
	public void clickInventory(InventoryClickEvent event){
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
					Bukkit.getServer().getScheduler().runTask(plugin, new Runnable(){
						public void run(){
							event.getWhoClicked().closeInventory();
						}
					});
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
		
		loadLevels(wheatLevels, ".wheat.", event.getPlayer());
		
		initMenu(event.getPlayer());
	}
	
	
	public void saveEnchantFile(){
		
		saveIntStuff(wheatLevels, ".wheat.");
		
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
	
	
	private static void loadLevels(HashMap<String, HashMap<String, Integer>> h, String s, Player player){
		String playername = player.getName();
		HashMap<String, Integer> skillMap = new HashMap<String, Integer>();
		Map<String, Integer> nullMap = new HashMap<String, Integer>();
		nullMap.put("None", 1);
		Map<String, Integer> skillData = (Map<String, Integer>) enchantConfig.get("players." + playername + s, nullMap);
		for (String skillName: skillData.keySet()){
			skillMap.put(skillName, skillData.get(skillName));
		}
		h.put(playername, skillMap);
		
	}
	
	
}
