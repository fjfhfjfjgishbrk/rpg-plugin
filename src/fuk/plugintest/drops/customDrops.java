package fuk.plugintest.drops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fuk.plugintest.Main;
import fuk.plugintest.fileSave;
import fuk.plugintest.enchants.EnchantManager;
import fuk.plugintest.items.itemManager;

public class customDrops {
	
	private Main plugin;
	
	static int luck;
	
	public customDrops(Main plugin){
		this.plugin = plugin;
	}
	
	
	public static List<ItemStack> getDrops(LivingEntity entity, Player player){
		
		List<ItemStack> drops = new ArrayList<ItemStack>();
		String playername = player.getName();
		luck = fileSave.luck.get(playername);
		
		// cow drops
		if (entity.getType().equals(EntityType.COW)){
			drops = Cow.addDrops(drops, player, luck);
			
		}
		
		return drops;
	}
	
	public static List<ItemStack> getCropDrops(Material crop, Player player){
		
		List<ItemStack> drops = new ArrayList<ItemStack>();
		String playername = player.getName();
		luck = fileSave.luck.get(playername);
		
		if (crop.equals(Material.WHEAT)){
			drops = Wheat.addDrops(drops, player, luck);
		}
		else if (crop.equals(Material.BEETROOTS)){
			drops = Beetroot.addDrops(drops, player, luck);
		}
		
		return drops;
	}
	
	
}
