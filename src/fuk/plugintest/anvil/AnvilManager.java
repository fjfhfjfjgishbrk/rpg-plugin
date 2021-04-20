package fuk.plugintest.anvil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import fuk.plugintest.Main;
import fuk.plugintest.items.itemManager;

public class AnvilManager implements Listener {
	
	private Main plugin;
	
	private static HashMap<String, Inventory> anvilInven = new HashMap<String, Inventory>();
	private static ArrayList<Integer> canClickSlots = new ArrayList<Integer>();
	
	public AnvilManager(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		int a[] = new int[]{10, 12, 13, 14, 15, 16};
		for (int i: a){
			canClickSlots.add(i);
		}
	}
	
	private Inventory makeInven(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, "Upgrade items");
		ItemStack emptyPane = newGlassPane(" ", Material.GRAY_STAINED_GLASS_PANE);
		ItemStack confirmPane = newGlassPane(ChatColor.GREEN + "Upgrade!", Material.GREEN_STAINED_GLASS_PANE);
		ItemStack itemPane = newGlassPane(ChatColor.YELLOW + "↓ Put item below ↓", Material.YELLOW_STAINED_GLASS_PANE);
		ItemStack gemPane = newGlassPane(ChatColor.YELLOW + "↓ Put gems below ↓", Material.YELLOW_STAINED_GLASS_PANE);
		for (int i = 0; i < 9; i++){
			inv.setItem(i, emptyPane);
			inv.setItem(i + 18, emptyPane);
		}
		inv.setItem(9, emptyPane);
		inv.setItem(11, emptyPane);
		inv.setItem(17, emptyPane);
		inv.setItem(15, confirmPane);
		inv.setItem(1, itemPane);
		inv.setItem(4, gemPane);
		anvilInven.put(player.getName(), inv);
		return inv;
	}
	
	@EventHandler
	private void clickEnchantTable(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.ANVIL){
			event.setCancelled(true);
			event.getPlayer().openInventory(makeInven(event.getPlayer()));
		}
	}
	
	@EventHandler
	private void clickInventory(InventoryClickEvent event){
		if (!anvilInven.containsValue(event.getClickedInventory())){
			return;
		}
		if (true){
			Player player = (Player) event.getViewers().get(0);
			if (!canClickSlots.contains(event.getSlot())){
				event.setCancelled(true);
				return;
			}
			if (event.getSlot() == 15){
				event.setCancelled(true);
				Inventory inv = event.getClickedInventory();
				if (inv.getItem(16) != null){
					return;
				}
				ItemStack itemUpgrade = inv.getItem(10);
				String name = "";
				if (itemUpgrade == null){
					return;
				}
				if (itemUpgrade.hasItemMeta()){
					if (itemUpgrade.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "name"), PersistentDataType.STRING)){
						name = itemUpgrade.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "name"), PersistentDataType.STRING);
						if (!itemManager.canUpgrade.contains(name)){
							player.sendMessage(ChatColor.RED + "This item cannot be upgraded!");
							return;
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "This item cannot be upgraded!");
						return;
					}
				}
				else {
					player.sendMessage(ChatColor.RED + "This item cannot be upgraded!");
					return;
				}
				ArrayList<ItemStack> gems = new ArrayList<ItemStack>();
				for (int i = 12; i < 15; i++){
					ItemStack item = inv.getItem(i);
					if (item == null){
						continue;
					}
					ItemStack oneItem = item.clone();
					oneItem.setAmount(1);
					if (itemManager.gems.contains(oneItem)){
						gems.add(item);
					}
					else {
						player.sendMessage(ChatColor.RED + "This item cannot be used to upgrade an item!");
						return;
					}
				}
				ItemStack setItem = null;
				if (itemManager.sword.contains(name)){
					setItem = addGemSword(itemUpgrade, gems);
				}
				inv.setItem(10, null);
				inv.setItem(12, null);
				inv.setItem(13, null);
				inv.setItem(14, null);
				inv.setItem(16, setItem);
			}
		}
	}
	
	@EventHandler
	private void dragInventory(InventoryDragEvent event){
		if (!anvilInven.containsValue(event.getInventory())){
			return;
		}
		for (int i: event.getInventorySlots()){
			if (!canClickSlots.contains(i)){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	private void closeInventory(InventoryCloseEvent event){
		if (!anvilInven.containsValue(event.getInventory())){
			return;
		}
		List<ItemStack> returnItems = new ArrayList<ItemStack>(); 
		Player player = (Player) event.getPlayer();
		for (int i: canClickSlots){
			if (i == 15) {
				continue;
			}
			if (event.getInventory().getItem(i) != null){
				returnItems.add(event.getInventory().getItem(i));
			}
		}
		for (ItemStack items: returnItems){
			player.getInventory().addItem(items);
		}
	}
	
	private ItemStack addGemSword(ItemStack s, ArrayList<ItemStack> gems){
		ItemStack sword = s.clone();
		ItemMeta meta = sword.getItemMeta();
		int gemStat[] = new int[]{0, 0, 0, 0, 0, 0};
		ChatColor elementColor[] = new ChatColor[]{ChatColor.RED, ChatColor.AQUA, ChatColor.BLUE, ChatColor.DARK_GREEN, ChatColor.YELLOW, ChatColor.DARK_PURPLE};
		String gemMeta[] = new String[]{itemManager.fireGem.getItemMeta().getDisplayName(), itemManager.waterGem.getItemMeta().getDisplayName(),
				itemManager.iceGem.getItemMeta().getDisplayName(), itemManager.earthGem.getItemMeta().getDisplayName(), itemManager.thunderGem.getItemMeta().getDisplayName(), itemManager.magicGem.getItemMeta().getDisplayName()};
		for (ItemStack gem: gems){
			for (int i = 0; i < 6; i++){
				if (gem.getItemMeta().getDisplayName().equals(gemMeta[i])){
					gemStat[i] += gem.getAmount();
				}
			}
		}
		if (meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "gems"), PersistentDataType.INTEGER_ARRAY)){
			int temp[] = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "gems"), PersistentDataType.INTEGER_ARRAY);
			for (int i = 0; i < 6; i++){
				gemStat[i] += temp[i];
			}
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gems"), PersistentDataType.INTEGER_ARRAY, gemStat);
		}
		else {
			meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gems"), PersistentDataType.INTEGER_ARRAY, gemStat);
		}
		NamespacedKey elementdamageTag = new NamespacedKey(plugin, "elementDamage");
		NamespacedKey damageTag = new NamespacedKey(plugin, "damage");
		String damageLine = ChatColor.GRAY + "Damage:";
		if (meta.getPersistentDataContainer().has(damageTag, PersistentDataType.INTEGER)){
			damageLine += ChatColor.RED + " " + Integer.toString(sword.getItemMeta().getPersistentDataContainer().get(damageTag, PersistentDataType.INTEGER));
		}
		if (meta.getPersistentDataContainer().has(elementdamageTag, PersistentDataType.INTEGER_ARRAY)){
			int count = 0;
			for (int i: meta.getPersistentDataContainer().get(elementdamageTag, PersistentDataType.INTEGER_ARRAY)){
				double eledam = i;
				eledam += gemStat[count];
				eledam *= 1 + ((double) gemStat[count] / 1000d);
				DecimalFormat df = new DecimalFormat("###.#");
				if (eledam != 0){
					damageLine += elementColor[count] + " " + df.format(eledam) + "♦";
				}
				count++;
			}
		}
		Boolean hasBoostLine = false;
		String boostLine = ChatColor.GOLD + "Gem boosts: ";
		for (int i = 0; i < 6; i++){
			if (gemStat[i] != 0){
				boostLine += elementColor[i] + Integer.toString(gemStat[i]) + "♦ ";
			}
		}
		List<String> lore = sword.getItemMeta().getLore();
		for (String line: lore){
			if (line.toLowerCase().contains("damage:") && !line.toLowerCase().contains("critical")){
				lore.set(lore.indexOf(line), damageLine);
			}
			if (line.toLowerCase().contains("gem boosts:")){
				hasBoostLine = true;
				lore.set(lore.indexOf(line), boostLine);
			}
		}
		if (!hasBoostLine){
			lore.add(boostLine);
		}
		meta.setLore(lore);
		sword.setItemMeta(meta);
		return sword;
	}
	
	private static ItemStack newGlassPane(String name, Material material){
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}
