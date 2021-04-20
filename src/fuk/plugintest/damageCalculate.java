package fuk.plugintest;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import fuk.plugintest.items.itemManager;

public class damageCalculate {
	
	public Main plugin;
	private static NamespacedKey damageTag;
	//fire. water, ice, earth, thunder, poison
	private static NamespacedKey elementdamageTag;
	private static NamespacedKey strengthTag;
	private static NamespacedKey nameTag;
	private static NamespacedKey gemTag;
	
	private static ArrayList<Integer> nullDef = new ArrayList<Integer>();
	
	public damageCalculate(Main plugin){
		this.plugin = plugin;
		damageTag = new NamespacedKey(plugin, "damage");
		elementdamageTag = new NamespacedKey(plugin, "elementDamage");
		strengthTag = new NamespacedKey(plugin, "strength");
		nameTag = new NamespacedKey(plugin, "name");
		gemTag = new NamespacedKey(plugin, "gems");
		for (int i = 0; i < 6; i++){
			nullDef.add(0);
		}
	}
	
	
	public static double calculate(Player player, double damage, LivingEntity entity){
		String playername = player.getName();
		ItemStack heldItem = player.getInventory().getItemInMainHand();
		
		//damage
		double resultDamage = damage;
		if (heldItem.hasItemMeta()){
			if (heldItem.getItemMeta().getPersistentDataContainer().has(damageTag, PersistentDataType.INTEGER)){
				resultDamage = heldItem.getItemMeta().getPersistentDataContainer().get(damageTag, PersistentDataType.INTEGER);
			}
			if (heldItem.getItemMeta().getPersistentDataContainer().has(elementdamageTag, PersistentDataType.INTEGER_ARRAY)){
				if (resultDamage == damage){
					resultDamage = 0;
				}
				ArrayList<Integer> elementDef = EntityElementDefense.elementDefense.get(entity.getType());
				if (EntityElementDefense.customElementDefense.containsKey(entity.getUniqueId())){
					elementDef = EntityElementDefense.customElementDefense.get(entity.getUniqueId());
				}
				if (elementDef == null){
					elementDef = nullDef;
				}
				int gems[] = new int[]{0, 0, 0, 0, 0, 0};
				if (heldItem.getItemMeta().getPersistentDataContainer().has(gemTag, PersistentDataType.INTEGER_ARRAY)){
					gems = heldItem.getItemMeta().getPersistentDataContainer().get(gemTag, PersistentDataType.INTEGER_ARRAY);
				}
				resultDamage = 0;
				int index = 0;
				for (Integer eledamage: heldItem.getItemMeta().getPersistentDataContainer().get(elementdamageTag, PersistentDataType.INTEGER_ARRAY)){
					double def = (double) elementDef.get(index);
					if (def > 0){
						resultDamage += ((eledamage + gems[index]) * ((1000d + (double) gems[index]) / 1000d)) * (1d - def / (double) (175 + def));
					}
					else if (def < 0){
						resultDamage += ((eledamage + gems[index]) * ((1000d + (double) gems[index]) / 1000d)) * (1d + Math.abs(def / 125d));
					}
					else {
						resultDamage += (eledamage + gems[index]) * ((1000d + (double) gems[index]) / 1000d);
					}
					index += 1;
				}
			}
		}
			
		//strength
		int strength = fileSave.strength.get(playername);
		if (heldItem.hasItemMeta()){
			if (heldItem.getItemMeta().getPersistentDataContainer().has(strengthTag, PersistentDataType.INTEGER)){
				strength += heldItem.getItemMeta().getPersistentDataContainer().get(strengthTag, PersistentDataType.INTEGER);
			}
		}
		
		int atklvl = fileSave.attackLevel.get(playername);
		
		//custom item abilities
		HashMap<String, Integer> sets = new HashMap<String, Integer>();
		
		ItemStack helmet = player.getInventory().getHelmet();
		if (helmet != null){
			if (helmet.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
				String name = helmet.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
				switch (name) {
					case "goldhelmet2":
						putSet(sets, "gold");
						break;
					default:
						
				}
			}
		}
		
		ItemStack chestplate = player.getInventory().getChestplate();
		if (chestplate != null){
			if (chestplate.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
				String name = chestplate.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
				switch (name) {
					case "goldchest":
						if (player.getWorld().getTime() < 12000) {
							resultDamage *= 1.05;
						}
						putSet(sets, "gold");
						break;
					case "goldchest2":
						if (player.getWorld().getTime() < 12000) {
							resultDamage *= 1.15;
						}
						else {
							resultDamage *= 1.05;
						}
						putSet(sets, "gold");
						break;
					default:
						
				}
			}
		}
		
		ItemStack leggings = player.getInventory().getLeggings();
		if (leggings != null){
			if (leggings.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
				String name = leggings.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
				switch (name) {
					case "goldleggings2":
						putSet(sets, "gold");
						break;
					default:
						
				}
			}
		}
		
		ItemStack boots = player.getInventory().getBoots();
		if (boots != null){
			if (boots.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
				String name = boots.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
				switch (name) {
					case "goldboots2":
						putSet(sets, "gold");
						resultDamage *= (1d + sets.get("gold") * 0.02);
						break;
					default:
						
				}
			}
		}
		
		if (heldItem != null){
			if (heldItem.hasItemMeta()) {
				if (heldItem.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
					String name = heldItem.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
					switch (name) {
						case "cowsword":
							if (entity.getType() == EntityType.COW) {
								resultDamage += 350;
								strength += 450;
							}
							break;
						default:
							
					}
				}
			}
		}
		
		
		resultDamage *= (float) (100 + strength) / 100f;
		resultDamage *= Math.pow(1.8, Math.pow((float) (atklvl - 1) / 10f, 0.5));
		return resultDamage;
	}
	
	private static void putSet(HashMap<String, Integer> map, String s) {
		if (map.containsKey(s)) {
			map.put(s, map.get(s) + 1);
		}
		else {
			map.put(s, 1);
		}
	}
	
}
