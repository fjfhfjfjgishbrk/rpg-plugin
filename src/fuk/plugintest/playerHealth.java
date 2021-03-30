package fuk.plugintest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fuk.plugintest.enchants.EnchantManager;
import fuk.plugintest.items.itemManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class playerHealth implements Listener {

	private Main plugin;
	public static int maxHealthActual;
	public static int defenseActual;
	
	public playerHealth(Main plugin){
		this.plugin = plugin;
		actionBarUpdate();
	}
	
	private int[] checkTags(ItemStack item, int[] boosts){
		NamespacedKey healthTag = new NamespacedKey(plugin, "health");
		NamespacedKey defenseTag = new NamespacedKey(plugin, "defense");
		NamespacedKey dodgeTag = new NamespacedKey(plugin, "dodge");
		if (item.getItemMeta().getPersistentDataContainer().has(healthTag, PersistentDataType.INTEGER)){
			boosts[0] += item.getItemMeta().getPersistentDataContainer().get(healthTag, PersistentDataType.INTEGER);
		}
		if (item.getItemMeta().getPersistentDataContainer().has(defenseTag, PersistentDataType.INTEGER)){
			boosts[1] += item.getItemMeta().getPersistentDataContainer().get(defenseTag, PersistentDataType.INTEGER);
		}
		if (item.getItemMeta().getPersistentDataContainer().has(dodgeTag, PersistentDataType.INTEGER)){
			boosts[2] += item.getItemMeta().getPersistentDataContainer().get(dodgeTag, PersistentDataType.INTEGER);
		}
		return boosts;
	}
	
	private ArrayList<Integer> checkEleDef(ItemStack item, ArrayList<Integer> eledef){
		NamespacedKey eleDefTag = new NamespacedKey(plugin, "elementDefense");
		if (item.getItemMeta().getPersistentDataContainer().has(eleDefTag, PersistentDataType.INTEGER)){
			int a[] = item.getItemMeta().getPersistentDataContainer().get(eleDefTag, PersistentDataType.INTEGER_ARRAY);
			eledef.clear();
			for (int b: a){
				eledef.add(b);
			}
		}
		return eledef;
	}
	
	private Integer setBoosts(Player player, HashMap<String, Integer> boost, HashMap<String, Integer> durationMap, int stat){
		String playername = player.getName();
		if (boost.containsKey(playername)){
			stat += boost.get(playername);
			int duration = durationMap.get(playername);
			if (duration <= 1){
				boost.remove(playername);
				durationMap.remove(playername);
			}
			else {
				durationMap.put(playername, duration - 1);
			}
		}
		return stat;
	}
	
	private void actionBarUpdate(){
		Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					String playername = player.getName();
					if (!player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000, 6, false, false));
					}
					if (!player.hasPotionEffect(PotionEffectType.SATURATION)){
						player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000, 6, false, false));
					}
					if (!player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)){
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100000000, 3, false, false));
					}
					int health = fileSave.health.get(playername);
					int maxHealth = fileSave.maxHealth.get(playername);
					int defense = fileSave.defense.get(playername);
					int dodge = fileSave.attackLevel.get(playername);
					int speed = fileSave.farmLevel.get(playername);
					long heal = fileSave.fishLevel.get(playername);
					
					double healpercent = 1d + (double) heal * 0.0004;
					
					
					//food boosts
					defense = setBoosts(player, fileSave.defenseBoost, fileSave.defenseDuration, defense);
					dodge = setBoosts(player, fileSave.dodgeBoost, fileSave.dodgeDuration, dodge);
					speed = setBoosts(player, fileSave.walkBoost, fileSave.walkDuration, speed);
					int healfood = setBoosts(player, fileSave.healBoost, fileSave.healDuration, 0);
					
					healpercent += (double) healfood / 10000d;
					
					// atk, def, dodge
					int boost[] = new int[]{0, 0, 0};
					ArrayList<Integer> eleDefBoost = new ArrayList<Integer>();
					for (int i = 0; i < 6; i++){
						eleDefBoost.add(0);
					}
					
					
					//chestplate
					ItemStack chestplate = player.getInventory().getChestplate();
					
					if (chestplate != null){
						boost = checkTags(chestplate, boost);
						eleDefBoost = checkEleDef(chestplate, eleDefBoost);
					}
					
					//enchant buff
					
					dodge += EnchantManager.wheatLevels.get(playername).get("Dodge upgrade");
					
					
					
					maxHealth += boost[0];
					defense += boost[1];
					fileSave.dodge.put(playername, dodge + boost[2]);
					fileSave.walkSpeed.put(playername, speed);
					fileSave.heal.put(playername, heal);
					
					fileSave.elementdefense.put(playername, eleDefBoost);
					
					//-------------------------
					//looking for custom item buffs
					
					//chestplate buff
					if (chestplate != null){
						if (chestplate.equals(itemManager.cowChestplate)){
							health += (int) ((float) maxHealth * 0.01) * healpercent;
						}
						else if (chestplate.equals(itemManager.cowChestplateT2)){
							health += (int) ((float) maxHealth * 0.03) * healpercent;
						}
					}
					
					maxHealthActual = maxHealth;
					
					//setting health
					health += (int) ((float) maxHealth * 0.01) * healpercent;
					if (health > maxHealth){
						health = maxHealth;
					}
					fileSave.health.put(playername, health);
					player.setHealth(Math.min(20d, 20d * (double) health / (double) maxHealth)); 
					
					//setting defense
					double damageReduction = (((double) defense / (double) (defense + 150)) * 100d);
					DecimalFormat df = new DecimalFormat("###.###");
					
					defenseActual = defense;
					
					//setting walkspeed
					int walkLvl = fileSave.walkSpeed.get(playername);
					if (fileSave.walkBoost.containsKey(playername)){
						walkLvl += fileSave.walkBoost.get(playername);
					}
					float walkSpeed = 0.2f + Math.min(0.4f, 0.1f * (float) (walkLvl / 1250));
					player.setWalkSpeed(walkSpeed);
					
					
					String healthMessage = ChatColor.RED + "♥ " + Integer.toString(health) + " / " + Integer.toString(maxHealth) + "    ";
					String defenseMessage = ChatColor.GREEN + "※ " + Integer.toString(defense) + ChatColor.YELLOW + "  " + df.format(damageReduction) + "%";
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(healthMessage + defenseMessage));
				}
			}
		}, 10l, 20l);
	}
}
