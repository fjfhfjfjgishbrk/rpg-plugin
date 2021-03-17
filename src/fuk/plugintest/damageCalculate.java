package fuk.plugintest;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class damageCalculate {
	
	public Main plugin;
	private static NamespacedKey damageTag;
	private static NamespacedKey strengthTag;
	
	public damageCalculate(Main plugin){
		this.plugin = plugin;
		damageTag = new NamespacedKey(plugin, "damage");
		strengthTag = new NamespacedKey(plugin, "strength");
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
