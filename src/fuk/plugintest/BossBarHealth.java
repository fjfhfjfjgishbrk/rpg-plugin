package fuk.plugintest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class BossBarHealth {
	
	private Main plugin;
	
	public static HashMap<UUID, BossBar> bossbar = new HashMap<UUID, BossBar>();
	
	public static HashMap<UUID, String> customName = new HashMap<UUID, String>();
	
	private static ArrayList<String> symbols = new ArrayList<String>();
	private static ArrayList<Integer> nullDef = new ArrayList<Integer>();
	
	
	public BossBarHealth(Main plugin){
		this.plugin = plugin;
		init();
	}
	
	public void init(){
		symbols.add(ChatColor.RED + "♦");
		symbols.add(ChatColor.AQUA + "♦");
		symbols.add(ChatColor.BLUE + "♦");
		symbols.add(ChatColor.DARK_GREEN + "♦");
		symbols.add(ChatColor.YELLOW + "♦");
		symbols.add(ChatColor.LIGHT_PURPLE + "♦");
		for (int i = 0; i < 6; i++){
			nullDef.add(0);
		}
		Bukkit.getServer().getScheduler().runTaskTimer(this.plugin, new Runnable(){
			public void run(){
				for (UUID mobid: bossbar.keySet()){
					for (Player player: bossbar.get(mobid).getPlayers()){
						if (player.getLocation().distance(Bukkit.getEntity(mobid).getLocation()) > 30){
							bossbar.get(mobid).removePlayer(player);
						}
					}
				}
			}
		}, 20l, 20l);
	}
	
	
	@SuppressWarnings("deprecation")
	public static void setHealthBar(Entity entity, Boolean isNew){
		BossBar bar;
		if (isNew){
			bar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
		}
		else {
			bar = bossbar.get(entity.getUniqueId());
		}
		UUID mobid = entity.getUniqueId();
		Double maxHealth = HealthBar.mobMaxHealth.get(mobid);
		Double health = HealthBar.mobHealth.get(mobid);
		Integer level = HealthBar.mobLevel.get(mobid);
		ArrayList<Integer> elementDef;
		ArrayList<Integer> elementAtk;
		if (EntityElementDefense.elementDefense.containsKey(entity.getType())){
			elementDef = EntityElementDefense.elementDefense.get(entity.getType());
		}
		else {
			elementDef = nullDef;
		}
		if (EntityElementDefense.customElementDefense.containsKey(mobid)){
			elementDef = EntityElementDefense.customElementDefense.get(mobid);
		}
		if (EntityElementDefense.elementAttack.containsKey(entity.getType())){
			elementAtk = EntityElementDefense.elementAttack.get(entity.getType());
		}
		else {
			elementAtk = nullDef;
		}
		if (EntityElementDefense.customElementAttack.containsKey(mobid)){
			elementAtk = EntityElementDefense.customElementAttack.get(mobid);
		}
		DecimalFormat df = new DecimalFormat("###.#");
		Boolean haveWeak = false;
		Boolean haveDef = false;
		Boolean haveStr = false;
		Integer count = 0;
		String title = "";
		title += ChatColor.WHITE + "[Lv. " + Integer.toString(level) + "] ";
		if (customName.containsKey(entity.getUniqueId())){
			title += customName.get(entity.getUniqueId()) + "  ";
		}
		else {
			title += ChatColor.YELLOW + StringUtils.capitalize(entity.getType().getName()) + "  ";
		}
		title += ChatColor.RED + df.format(health) + "♥  ";
		title += ChatColor.GREEN + "Weak: ";
		for (Integer def: elementDef){
			if (def < 0){
				title += symbols.get(count);
				haveWeak = true;
			}
			count++;
		}
		count = 0;
		if (!haveWeak){
			title += "None";
		}
		title += ChatColor.DARK_RED + " Def: ";
		for (Integer def: elementDef){
			if (def > 0){
				title += symbols.get(count);
				haveDef = true;
			}
			count++;
		}
		if (!haveDef){
			title += "None";
		}
		count = 0;
		title += ChatColor.GOLD + " Dam: ";
		for (Integer atk: elementAtk){
			if (atk > 0){
				title += symbols.get(count);
				haveStr = true;
			}
			count++;
		}
		if (!haveStr){
			title += "None";
		}
		bar.setTitle(title);
		bar.setProgress(Math.max(health, 0d) / maxHealth);
		bossbar.put(mobid, bar);
		return;
	}
	
	
	
}
