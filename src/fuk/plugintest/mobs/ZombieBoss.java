package fuk.plugintest.mobs;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fuk.plugintest.BossBarHealth;
import fuk.plugintest.EntityElementDefense;
import fuk.plugintest.HealthBar;
import fuk.plugintest.Main;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_16_R3.PathfinderGoalMoveTowardsTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomStroll;

public class ZombieBoss extends EntityZombie {
	
	private Integer phase;
	
	public ZombieBoss(EntityTypes<? extends EntityZombie> type, Location loc) {
		super(type, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25d);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(5d);
		this.getAttributeInstance(GenericAttributes.KNOCKBACK_RESISTANCE).setValue(0.75d);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100d);
		
		MobManager.phase.put(this.uniqueID, 1);
		MobManager.changePhase.put(this.uniqueID, false);
		
		ItemStack leatherHelm = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta meta = (LeatherArmorMeta) leatherHelm.getItemMeta();
		meta.setColor(Color.fromRGB(252, 115, 40));
		meta.setUnbreakable(true);
		leatherHelm.setItemMeta(meta);
		this.setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(leatherHelm), true);
		this.setSlot(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_BOOTS)), true);
		
		UUID mobId = this.uniqueID;
		BossBarHealth.customName.put(mobId, ChatColor.GOLD.toString() + "Cyrilla the corpse");
		HealthBar.mobLevel.put(mobId, 1800);
		HealthBar.mobMaxHealth.put(mobId, 875000d);
		HealthBar.mobHealth.put(mobId, 875000d);
		EntityElementDefense.customElementAttack.put(mobId, EntityElementDefense.setElementStats(0, 20, 10, 50, 30, 60));
		EntityElementDefense.customElementDefense.put(mobId, EntityElementDefense.setElementStats(-100, 50, -25, 125, 50, 250));
		MobManager.bossMobs.add(mobId);
		MobManager.noKill.add(mobId);
		MobManager.bossExp.put(mobId, 1250000);
		
	}
	
	
	@Override
	protected void initPathfinder() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(1, new CustomMeleeAttack((EntityCreature) this, 1.6d, false, 6));
		this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget((EntityCreature) this, 2.0d, 3.0f));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 2.0D));
		this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0d));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		
		this.targetSelector.a(0, new PathfinderGoalHurtByTarget((EntityCreature) this, EntityHuman.class));
		this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
	}
	
	public static void tasks(Entity entity, Main plugin) {
		double heal = 0;
		Integer phase = MobManager.phase.get(entity.getUniqueId());
		ChatColor mobColor;
		switch (phase){
			case 1:
				mobColor = ChatColor.YELLOW;
				break;
			case 2:
				mobColor = ChatColor.RED;
				break;
			case 3:
				mobColor = ChatColor.DARK_RED;
				break;
			default:
				mobColor = ChatColor.YELLOW;
		}
		DustOptions dustOptions;
		switch (phase){
			case 1:
				dustOptions = new DustOptions(Color.fromRGB(18, 64, 21), 1);
				entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 100, 0.4d, 1.7d, 0.4d, dustOptions);
				break;
			case 2:
				dustOptions = new DustOptions(Color.fromRGB(125, 19, 22), 1);
				entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 100, 0.4d, 1.7d, 0.4d, dustOptions);
				break;
			case 3:
				dustOptions = new DustOptions(Color.fromRGB(230, 91, 11), 1);
				entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 100, 0.4d, 1.7d, 0.4d, dustOptions);
				break;
			default:
				dustOptions = new DustOptions(Color.fromRGB(18, 64, 21), 1);
		}
		UUID mobID = entity.getUniqueId();
		for (Entity e : entity.getWorld().getEntities()){
			if (e instanceof Zombie){
		    	if (entity.getLocation().distance(e.getLocation()) < 15){
		    		heal += 712 * (phase + (double) (875000 - HealthBar.mobHealth.get(mobID)) / 225000d);
		    	}
		    }
		}
		HealthBar.mobHealth.put(mobID, Math.min(875000, HealthBar.mobHealth.get(mobID) + heal));
		HealthBar.barUpdate(entity, 0, false);
		
		ArrayList<Player> playerlist = new ArrayList<Player>();
		for (Player player : Bukkit.getOnlinePlayers()){
			if (player.getLocation().distance(entity.getLocation()) < 15) {
				playerlist.add(player);
			}
		}
		
		if (Math.random() < (0.015 + 0.015 * phase)){
			for (Player player : playerlist){
				player.sendMessage(mobColor + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + mobColor + " Rise, my minions!");
			}
			int mobAmount = (int) (Math.round(Math.random() * 4) + phase);
			World world = entity.getWorld();
			for (int i = 0; i < mobAmount; i++){
				double randomAngle = Math.random() * 2 * Math.PI;
				Location randomPos = entity.getLocation().add((0.4 + Math.random() * 3) * Math.cos(randomAngle), 0.3, (0.4 + Math.random() * 3) * Math.sin(randomAngle));
				if (randomPos.getBlock().isEmpty() || randomPos.getBlock().isLiquid()){
					world.spawnEntity(randomPos, EntityType.ZOMBIE);
					world.spawnParticle(Particle.REDSTONE, randomPos, 50, 0.3d, 2d, 0.3d, dustOptions);
				}
			}
		}
		
		
		if (Math.random() < (0.03 + 0.02 * phase)){
			for (Player player : playerlist){
				player.sendMessage(mobColor + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + mobColor + " You shall not escape!");
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1, true, true));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (200 - 40 * phase), 2 + phase, true, true));
				Player randomplayer = playerlist.get((int) Math.floor(Math.random() * playerlist.size()));
				double yaw = (double) (randomplayer.getLocation().getYaw() - 90) / 360d * 2d * Math.PI;
				entity.teleport(randomplayer.getLocation().add(0.5d * Math.cos(yaw), 0.6, 0.5d * Math.sin(yaw)));
				if (phase == 2){
					World world = entity.getWorld();
					double randomAngle = Math.random() * 2 * Math.PI;
					Location randomPos = entity.getLocation().add(Math.cos(randomAngle), 0.3, Math.sin(randomAngle));
					if (randomPos.getBlock().isEmpty() || randomPos.getBlock().isLiquid()){
						world.spawnEntity(randomPos, EntityType.ZOMBIE);
						world.spawnParticle(Particle.REDSTONE, randomPos, 50, 0.3d, 2d, 0.3d, dustOptions);
					}
				}
			}
		}
		
		if (phase >= 2 && Math.random() < (0.005 + phase * 0.05)){
			for (Player player: playerlist){
				player.sendMessage(mobColor + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + mobColor + " Come to me and face your death!");
				Vector pullvec = new Vector(entity.getLocation().getX() - player.getLocation().getX(), entity.getLocation().getY() - player.getLocation().getY(), entity.getLocation().getZ() - player.getLocation().getZ());
				pullvec.multiply(0.3d);
				player.setVelocity(player.getVelocity().add(pullvec));
			}
		}
		
		if (phase == 3){
			Double health = HealthBar.mobHealth.get(mobID);
			HealthBar.mobHealth.put(mobID, Math.max(1d, health - 10523d));
		}
		
		if (phase == 1 && HealthBar.mobHealth.get(mobID) < 150000 && !MobManager.changePhase.get(mobID)){
			for (Player player : playerlist){
				player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + ChatColor.YELLOW + " I will not die!");
				player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + ChatColor.YELLOW + " I will rise again,");
			}
			MobManager.changePhase.put(mobID, true);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					for (Player player : playerlist){
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + ChatColor.RED + " and you will DIE!");
						player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Cyrilla the cursed corpse", ChatColor.GRAY + "[Lv.1800]  Phase 2", 5, 60, 10);
						player.sendMessage(ChatColor.DARK_GRAY + "Cyrilla has gained defense, attack and has stronger abilities");
					}
					HealthBar.mobHealth.put(mobID, 875000d);
					DustOptions dustOptions = new DustOptions(Color.fromRGB(125, 19, 22), 1);
					entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 500, 1.9d, 2.1d, 1.9d, dustOptions);
					BossBarHealth.customName.put(mobID, ChatColor.RED.toString() + "Cyrilla the cursed corpse");
					EntityElementDefense.customElementDefense.put(mobID, EntityElementDefense.setElementStats(-65, 75, 10, 160, 75, 350));
					EntityElementDefense.customElementAttack.put(mobID, EntityElementDefense.setElementStats(0, 35, 25, 75, 40, 85));
					MobManager.phase.put(mobID, 2);
					MobManager.changePhase.put(mobID, false);
				}
			}, 30);
		}
		
		if (phase == 2 && HealthBar.mobHealth.get(mobID) < 150000 && !MobManager.changePhase.get(mobID)){
			for (Player player : playerlist){
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + ChatColor.RED + " NOOOOOO!");
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + ChatColor.RED + " THIS CAN'T BE HAPPENING!!!!");
			}
			MobManager.changePhase.put(mobID, true);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					for (Player player : playerlist){
						player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Cyrilla] " + ChatColor.RESET + "" + ChatColor.DARK_RED + " I WILL NOT BE DEFEATED!");
						player.sendTitle(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Cyrilla the enraged corpse", ChatColor.GRAY + "[Lv.2000]  Phase 3", 5, 60, 10);
						player.sendMessage(ChatColor.DARK_GRAY + "Cyrilla has gained more defense, attack and has stronger abilities of the cost of losing health");
					}
					HealthBar.mobHealth.put(mobID, 875000d);
					DustOptions dustOptions = new DustOptions(Color.fromRGB(230, 91, 11), 1);
					entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 750, 1.9d, 2.1d, 1.9d, dustOptions);
					BossBarHealth.customName.put(mobID, ChatColor.DARK_RED.toString() + "Cyrilla the enraged corpse");
					EntityElementDefense.customElementDefense.put(mobID, EntityElementDefense.setElementStats(-35, 100, 30, 200, 115, 450));
					EntityElementDefense.customElementAttack.put(mobID, EntityElementDefense.setElementStats(40, 95, 75, 125, 100, 145));
					HealthBar.mobLevel.put(mobID, 2000);
					MobManager.phase.put(mobID, 3);
					MobManager.changePhase.put(mobID, false);
					MobManager.noKill.remove(mobID);
				}
			}, 30);
		}
		
	}
	
}
