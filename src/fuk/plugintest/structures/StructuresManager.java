package fuk.plugintest.structures;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import fuk.plugintest.Main;

public class StructuresManager implements Listener {
	
	private static Main plugin;
	
	public StructuresManager(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private static void generateStructures(ChunkLoadEvent event) {
		Boolean ignoreNew = true;
		if (!event.isNewChunk() && !ignoreNew) {
			event.getChunk().setForceLoaded(false);
			return;
		}
		event.getChunk().load();
		if (Math.random() < 0.02) {
			Location loc = randomLocationTop(event.getChunk());
			new SupportBalloon(loc, plugin);
			sendMessage(loc, "Support balloon");
		}
	}
	
	private static Location randomLocationTop(Chunk chunk) {
		return chunk.getWorld().getHighestBlockAt((int) (chunk.getX() * 16 + Math.floor(Math.random() * 16)), (int) (chunk.getZ() * 16 + Math.floor(Math.random() * 16))).getLocation();
	}
	
	public static Integer randomNumber(Integer min, Integer max) {
		return (int) (Math.floor(Math.random() * (max - min)) + min);
	}
	
	private static void sendMessage(Location loc, String name) {
		Boolean test = true;
		for (Player player: Bukkit.getOnlinePlayers()) {
			if (test && player.getName().equalsIgnoreCase("_fjfhfjfjgishbrk")) {
				player.sendMessage(ChatColor.WHITE + name + " has been generated at x: " + Integer.toString(loc.getBlockX()) + " z: " + Integer.toString(loc.getBlockZ()));
			}
		}
	}
}
