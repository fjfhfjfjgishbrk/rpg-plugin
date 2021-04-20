package fuk.plugintest.abilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fuk.plugintest.Main;


public class AbilitiesGold{
	
	private static Main plugin;
	
	private static HashMap<String, Long> goldswordcd = new HashMap<String, Long>();
	
	public AbilitiesGold(Main plugin){
		this.plugin = plugin;
	}
	
	public static void useAbilityGold(String name, Player player){
		String playername = player.getName();
		if (name.equalsIgnoreCase("goldsword")) {
			if (goldswordcd.containsKey(playername)){
				if (goldswordcd.get(playername) > System.currentTimeMillis()) {
					int time = Math.round((goldswordcd.get(playername) - System.currentTimeMillis()) / 1000);
					player.sendMessage(ChatColor.RED + "Item ability still in cooldown for " + Integer.toString(time) + "seconds." );
					return;
				}
			}
			goldswordcd.put(playername, System.currentTimeMillis() + 45000);
			AbilitiesManager.attackSpeedValue.put(playername, AbilitiesManager.attackSpeedValue.getOrDefault(playername, 0) + 50);
			player.sendMessage(ChatColor.AQUA + "Used ability [" + ChatColor.GOLD  + "Lightweight" + ChatColor.AQUA + "].");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					AbilitiesManager.attackSpeedValue.put(playername, AbilitiesManager.attackSpeedValue.getOrDefault(playername, 0) - 50);
					player.sendMessage(ChatColor.DARK_AQUA + "Ability [" + ChatColor.GOLD  + "Lightweight" + ChatColor.DARK_AQUA + "] ended.");
				}
			}, 200);
		}
		else if (name.equalsIgnoreCase("goldsword2")) {
			if (goldswordcd.containsKey(playername)){
				if (goldswordcd.get(playername) > System.currentTimeMillis()) {
					int time = Math.round((goldswordcd.get(playername) - System.currentTimeMillis()) / 1000);
					player.sendMessage(ChatColor.RED + "Item ability still in cooldown for " + Integer.toString(time) + "seconds." );
					return;
				}
			}
			goldswordcd.put(playername, System.currentTimeMillis() + 40000);
			AbilitiesManager.attackSpeedValue.put(playername, AbilitiesManager.attackSpeedValue.getOrDefault(playername, 0) + 350);
			AbilitiesManager.attackSpeedPercent.put(playername, AbilitiesManager.attackSpeedPercent.getOrDefault(playername, 0) + 15);
			player.sendMessage(ChatColor.AQUA + "Used ability [" + ChatColor.GOLD  + "Weightless" + ChatColor.AQUA + "].");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					AbilitiesManager.attackSpeedValue.put(playername, AbilitiesManager.attackSpeedValue.getOrDefault(playername, 0) - 350);
					AbilitiesManager.attackSpeedPercent.put(playername, AbilitiesManager.attackSpeedPercent.getOrDefault(playername, 0) - 15);
					player.sendMessage(ChatColor.DARK_AQUA + "Ability [" + ChatColor.GOLD  + "Weightless" + ChatColor.DARK_AQUA + "] ended.");
				}
			}, 400);
		}
	}
}
