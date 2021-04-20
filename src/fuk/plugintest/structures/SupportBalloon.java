package fuk.plugintest.structures;

import java.util.Arrays;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fuk.plugintest.Main;
import fuk.plugintest.items.itemManager;

public class SupportBalloon {
	
	public SupportBalloon(Location loc, Main plugin) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				loc.clone().add(i, 0, j).getBlock().setType(Material.OAK_PLANKS);
			}
		}
		for (int i = 1; i < 4; i++) {
			loc.clone().add(1, i, 1).getBlock().setType(Material.OAK_FENCE);
			loc.clone().add(1, i, -1).getBlock().setType(Material.OAK_FENCE);
			loc.clone().add(-1, i, 1).getBlock().setType(Material.OAK_FENCE);
			loc.clone().add(-1, i, -1).getBlock().setType(Material.OAK_FENCE);
		}
		for (int k = 4; k < 8; k++) {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					loc.clone().add(i, k, j).getBlock().setType(Material.WHITE_WOOL);
				}
			}
		}
		for (int i = 5; i < 7; i++) {
			loc.clone().add(2, i, 0).getBlock().setType(Material.WHITE_WOOL);
			loc.clone().add(-2, i, 0).getBlock().setType(Material.WHITE_WOOL);
			loc.clone().add(0, i, 2).getBlock().setType(Material.WHITE_WOOL);
			loc.clone().add(0, i, -2).getBlock().setType(Material.WHITE_WOOL);
		}
		Location chestLoc = loc.clone().add(0, 1, 0);
		chestLoc.getBlock().setType(Material.CHEST);
		
		Bukkit.getServer().getScheduler().runTask(plugin, new Runnable(){
			public void run(){
				System.out.println(chestLoc.getBlock().getType());
				Inventory chestInven = ((Chest) chestLoc.getBlock().getState()).getBlockInventory();
				Integer[] slots = randomSlots();
				Integer itemCount = 0;
				
				if (Math.random() < 0.9) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.OAK_WOOD, StructuresManager.randomNumber(7, 18)));
					itemCount++;
				}
				if (Math.random() < 0.73) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.STICK, StructuresManager.randomNumber(4, 11)));
					itemCount++;
				}
				if (Math.random() < 0.7) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.COAL, StructuresManager.randomNumber(3, 7)));
					itemCount++;
				}
				if (Math.random() < 0.6) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.OAK_PLANKS, StructuresManager.randomNumber(8, 24)));
					itemCount++;
				}
				if (Math.random() < 0.5) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.COAL, StructuresManager.randomNumber(3, 7)));
					itemCount++;
				}
				if (Math.random() < 0.3) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.IRON_INGOT, StructuresManager.randomNumber(1, 4)));
					itemCount++;
				}
				if (Math.random() < 0.1) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.GOLD_INGOT, StructuresManager.randomNumber(1, 3)));
					itemCount++;
				}
				if (Math.random() < 0.08) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.FLINT_AND_STEEL));
					itemCount++;
				}
				if (Math.random() < 0.05) {
					chestInven.setItem(slots[itemCount], new ItemStack(Material.DIAMOND, StructuresManager.randomNumber(1, 3)));
					itemCount++;
				}
				if (Math.random() < 0.04) {
					chestInven.setItem(slots[itemCount], itemManager.sandwich);
					itemCount++;
				}
				if (Math.random() < 0.01) {
					chestInven.setItem(slots[itemCount], itemManager.thunderGem);
					itemCount++;
				}
			}
		});
	}
	
	private static Integer[] randomSlots() {
	    Integer[] arr = new Integer[27];
	    for (int i = 0; i < arr.length; i++) {
	        arr[i] = i;
	    }
	    Collections.shuffle(Arrays.asList(arr));
	    return arr;

	}
	
}
