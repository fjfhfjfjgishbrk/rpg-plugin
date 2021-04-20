package fuk.plugintest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fuk.plugintest.abilities.AbilitiesManager;
import fuk.plugintest.enchants.EnchantManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class playerHealth implements Listener {

	private Main plugin;
	public static HashMap<String, Integer> maxHealthActual = new HashMap<String, Integer>();
	public static int defenseActual;
	
	List<NamespacedKey> tags = new ArrayList<NamespacedKey>();
	
	public playerHealth(Main plugin){
		this.plugin = plugin;
		tags.add(new NamespacedKey(plugin, "health"));
		tags.add(new NamespacedKey(plugin, "defense"));
		tags.add(new NamespacedKey(plugin, "dodge"));
		tags.add(new NamespacedKey(plugin, "heal"));
		tags.add(new NamespacedKey(plugin, "critDamage"));
		tags.add(new NamespacedKey(plugin, "attackSpeed"));
		actionBarUpdate();
	}
	
	private int[] checkTags(ItemStack item, int[] boosts){
		for (int i = 0; i < tags.size(); i++) {
			if (item.getItemMeta().getPersistentDataContainer().has(tags.get(i), PersistentDataType.INTEGER)){
				boosts[i] += item.getItemMeta().getPersistentDataContainer().get(tags.get(i), PersistentDataType.INTEGER);
			}
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
					long critDamage = 2000 + fileSave.forageLevel.get(playername) * 10;
					int attackSpeed = fileSave.defenseLevel.get(playername) + AbilitiesManager.attackSpeedValue.getOrDefault(playername, 0);
					
					double healpercent = 1d + (double) heal * 0.0004;
					
					
					//food boosts
					defense = setBoosts(player, fileSave.defenseBoost, fileSave.defenseDuration, defense);
					dodge = setBoosts(player, fileSave.dodgeBoost, fileSave.dodgeDuration, dodge);
					speed = setBoosts(player, fileSave.walkBoost, fileSave.walkDuration, speed);
					int healfood = setBoosts(player, fileSave.healBoost, fileSave.healDuration, 0);
					
					healpercent += (double) healfood / 10000d;
					
					// atk, def, dodge, heal, critdamage, attackspeed
					int boost[] = new int[]{0, 0, 0, 0, 0, 0};
					ArrayList<Integer> eleDefBoost = new ArrayList<Integer>();
					for (int i = 0; i < 6; i++){
						eleDefBoost.add(0);
					}
					
					//helmet
					ItemStack helmet = player.getInventory().getHelmet();
					
					if (helmet != null){
						boost = checkTags(helmet, boost);
						eleDefBoost = checkEleDef(helmet, eleDefBoost);
					}
					
					
					//chestplate
					ItemStack chestplate = player.getInventory().getChestplate();
					
					if (chestplate != null){
						boost = checkTags(chestplate, boost);
						eleDefBoost = checkEleDef(chestplate, eleDefBoost);
					}
					
					//leggings
					ItemStack leggings = player.getInventory().getLeggings();
					
					if (leggings != null){
						boost = checkTags(leggings, boost);
						eleDefBoost = checkEleDef(leggings, eleDefBoost);
					}
					
					//boots
					ItemStack boots = player.getInventory().getBoots();
					
					if (boots != null){
						boost = checkTags(boots, boost);
						eleDefBoost = checkEleDef(boots, eleDefBoost);
					}
					
					//helditem
					ItemStack hold = player.getInventory().getItemInMainHand();
					
					if (hold != null){
						if (hold.hasItemMeta()) {
							boost = checkTags(hold, boost);
							eleDefBoost = checkEleDef(hold, eleDefBoost);
						}
					}
					
					//enchant buff
					
					dodge += EnchantManager.wheatLevels.get(playername).get("Dodge upgrade");
					
					
					//add boosts
					maxHealth += boost[0];
					defense += boost[1];
					healpercent += (double) boost[3]/ 10000d;
					critDamage += boost[4];
					attackSpeed += boost[5];
					
					//add abilities percent
					attackSpeed *= (1d + (double) AbilitiesManager.attackSpeedPercent.getOrDefault(playername, 0) / 100d);
					
					fileSave.dodge.put(playername, dodge + boost[2]);
					fileSave.walkSpeed.put(playername, speed);
					fileSave.heal.put(playername, heal);
					fileSave.critDamage.put(playername, critDamage);
					fileSave.attackSpeed.put(playername, attackSpeed);
					
					fileSave.elementdefense.put(playername, eleDefBoost);
					
					//-------------------------
					//looking for custom item buffs
					NamespacedKey nameTag = new NamespacedKey(plugin, "name");
					
					if (leggings != null){
						if (leggings.getItemMeta().getPersistentDataContainer().has(nameTag, PersistentDataType.STRING)){
							String name = leggings.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING);
							switch (name) {
								case "goldleggings":
									player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 1, false, false));
									break;
								case "goldleggings2":
									player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 1, false, false));
									if (player.getFireTicks() > 0) {
										health += (int) ((float) maxHealth * 0.01) * healpercent;
									}
									break;
								default:
									
							}
						}
					}
					
					//chestplate buff
					if (chestplate != null){
						if (chestplate.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING) == "cowchest"){
							health += (int) ((float) maxHealth * 0.01) * healpercent;
						}
						else if (chestplate.getItemMeta().getPersistentDataContainer().get(nameTag, PersistentDataType.STRING) == "cowchest2"){
							health += (int) ((float) maxHealth * 0.03) * healpercent;
						}
					}
					
					maxHealthActual.put(playername, maxHealth);
					
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
