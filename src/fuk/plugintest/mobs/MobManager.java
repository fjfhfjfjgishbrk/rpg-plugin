package fuk.plugintest.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import fuk.plugintest.BossBarHealth;
import fuk.plugintest.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.EntityCreeper;
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
	public static ArrayList<UUID> noKill = new ArrayList<UUID>();
	public static ArrayList<UUID> stopTasks = new ArrayList<UUID>();
	
	public MobManager(Main plugin){
		this.plugin = plugin;
		loopTasks();
		new CreeperBossEventListener(plugin);
	}
	
	private static void loopTasks(){
		Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable(){
			public void run(){
				for (World w : Bukkit.getWorlds()){
				    for (Entity e : w.getEntities()){
				    	if (!(bossMobs.contains(e.getUniqueId()))){
				    		continue;
				    	}
				    	if (stopTasks.contains(e.getUniqueId())) {
				    		continue;
				    	}
				    	String name = BossBarHealth.customName.get(e.getUniqueId()).toLowerCase();
				    	if (e instanceof EntityZombie){
				    		ZombieBoss.tasks(e, plugin);
				    	}
				    	else if (name.contains("zephyros")) {
				    		CreeperBoss.tasks(e, plugin);
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
			if (p.getLocation().distance(loc) < 25) {
				p.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "Cyrilla the corpse", ChatColor.GRAY + "[Lv.1800]  The fallen zombie king", 5, 60, 10);
			}
		}
		return;
	}
	
	public static void summonCreeperBoss(Player player, Location loc){
		EntityCreeper creeper = new EntityCreeper(EntityTypes.CREEPER, ((CraftWorld) player.getWorld()).getHandle());
		CreeperBoss boss = new CreeperBoss((EntityTypes<? extends EntityCreeper>) creeper.getEntityType(), loc);
		
		WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
		world.addEntity(boss);
		
		for (Player p : Bukkit.getOnlinePlayers()){
			if (p.getLocation().distance(loc) < 25) {
				p.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "Overhyped Zephyros", ChatColor.GRAY + "[Lv.3100]  The creeper that forgot to eat medicine", 5, 60, 10);
			}
		}
		return;
	}
}
