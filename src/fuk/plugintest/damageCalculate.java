package fuk.plugintest;

import java.util.ArrayList;

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
	
	private static ArrayList<Integer> nullDef = new ArrayList<Integer>();
	
	public damageCalculate(Main plugin){
		this.plugin = plugin;
		damageTag = new NamespacedKey(plugin, "damage");
		elementdamageTag = new NamespacedKey(plugin, "elementDamage");
		strengthTag = new NamespacedKey(plugin, "strength");
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
			else if (heldItem.getItemMeta().getPersistentDataContainer().has(elementdamageTag, PersistentDataType.INTEGER_ARRAY)){
				ArrayList<Integer> elementDef = EntityElementDefense.elementDefense.get(entity.getType());
				if (EntityElementDefense.customElementDefense.containsKey(entity.getUniqueId())){
					elementDef = EntityElementDefense.customElementDefense.get(entity.getUniqueId());
				}
				if (elementDef == null){
					elementDef = nullDef;
				}
				resultDamage = 0;
				int index = 0;
				for (Integer eledamage: heldItem.getItemMeta().getPersistentDataContainer().get(elementdamageTag, PersistentDataType.INTEGER_ARRAY)){
					double def = (double) elementDef.get(index);
					if (def > 0){
						resultDamage += eledamage * (1d - def / (double) (175 + def));
					}
					else if (def < 0){
						resultDamage += eledamage * (1d + Math.abs(def / 125d));
					}
					else {
						resultDamage += eledamage;
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
		
		if (heldItem.hasItemMeta()){
			
			//cow sword
			if (heldItem.getItemMeta().equals(itemManager.cowSword.getItemMeta())){
				if (entity.getType().equals(EntityType.COW)){
					resultDamage += 350;
					strength += 450;
				}
			}
			
		}
		
		resultDamage *= (float) (100 + strength) / 100f;
		resultDamage *= Math.pow(1.8, Math.pow((float) (atklvl - 1) / 10f, 0.5));
		return resultDamage;
	}
	
}
