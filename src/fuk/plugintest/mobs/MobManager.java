package fuk.plugintest.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import fuk.plugintest.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.WorldServer;

public class MobManager {
	
	private static Main plugin;
	
	public static Zombie zombieboss;
	
	public static ArrayList<UUID> bossMobs = new ArrayList<UUID>();
	public static HashMap<UUID, Integer> bossExp = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> phase = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Boolean> changePhase = new HashMap<UUID, Boolean>();
	
	public MobManager(Main plugin){
		this.plugin = plugin;
		loopTasks();
	}
	
	private static void loopTasks(){
		Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable(){
			public void run(){
				for (World w : Bukkit.getWorlds()){
				    for (Entity e : w.getEntities()){
				    	if (!(bossMobs.contains(e.getUniqueId()))){
				    		continue;
				    	}
				    	if (e instanceof Zombie){
				    		ZombieBoss.tasks(e, plugin);
				    	}
				    }
				}
			}
		}, 15l, 10l);
	}
	
	
	public static void summonZombieBoss(Player player, Location loc){
		EntityZombie zombie = new EntityZombie(((CraftWorld) player.getWorld()).getHandle());
		ZombieBoss boss = new ZombieBoss((EntityTypes<? extends EntityZombie>) zombie.getEntityType(), loc);
		
		WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
		world.addEntity(boss);
		
		for (Player p : Bukkit.getOnlinePlayers()){
			if (p.getLocation().distance(loc) < 15) {
				p.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "Cyrilla the corpse", ChatColor.GRAY + "[Lv.1800]  The fallen zombie king", 5, 60, 10);
			}
		}
		return;
	}
}
