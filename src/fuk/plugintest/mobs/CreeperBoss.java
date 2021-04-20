package fuk.plugintest.mobs;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fuk.plugintest.BossBarHealth;
import fuk.plugintest.EntityElementDefense;
import fuk.plugintest.HealthBar;
import fuk.plugintest.Main;
import fuk.plugintest.fileSave;
import fuk.plugintest.playerDamage;
import fuk.plugintest.playerHealth;
import net.minecraft.server.v1_16_R3.AttributeProvider;
import net.minecraft.server.v1_16_R3.DamageSource;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityCreeper;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityMonster;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomStrollLand;
import net.minecraft.server.v1_16_R3.SoundEffect;
import net.minecraft.server.v1_16_R3.SoundEffects;

public class CreeperBoss extends EntityMonster {
	
	static DustOptions warningParticle = new DustOptions(Color.fromRGB(252, 0, 0), 1.6f);
	
	private static ArrayList<String> deathChants = new ArrayList<String>();
	
	public CreeperBoss(EntityTypes<? extends EntityCreeper> entitytypes, Location loc) {
		super(entitytypes, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		setChant();
		
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35d);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(15d);
		this.getAttributeInstance(GenericAttributes.KNOCKBACK_RESISTANCE).setValue(0.9d);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100d);
		
		MobManager.phase.put(this.uniqueID, 1);
		MobManager.changePhase.put(this.uniqueID, false);
		
		UUID mobId = this.uniqueID;
		BossBarHealth.customName.put(mobId, ChatColor.GOLD.toString() + "Overhyped Zephyros");
		HealthBar.mobLevel.put(mobId, 3100);
		HealthBar.mobMaxHealth.put(mobId, 14550000d);
		HealthBar.mobHealth.put(mobId, 14550000d);
		EntityElementDefense.customElementAttack.put(mobId, EntityElementDefense.setElementStats(0, 0, 0, 100, 20, 0));
		EntityElementDefense.customElementDefense.put(mobId, EntityElementDefense.setElementStats(-30, -30, -30, 150, 300, 30));
		MobManager.bossMobs.add(mobId);
		MobManager.noKill.add(mobId);
		MobManager.bossExp.put(mobId, 22350000);
	}
	
	@Override
    protected void initPathfinder() {
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.5D, false));
        this.goalSelector.a(5, new PathfinderGoalRandomStrollLand(this, 1.2D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, new Class[0]));
	}
	
	public static AttributeProvider.Builder m() {
        return EntityMonster.eR().a(GenericAttributes.MOVEMENT_SPEED, 0.45D);
    }
	
	@Override
    public int bP() {
        return this.getGoalTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }
	
	@Override
    protected SoundEffect getSoundHurt(DamageSource damagesource) {
        return SoundEffects.ENTITY_CREEPER_HURT;
    }

    @Override
    protected SoundEffect getSoundDeath() {
        return SoundEffects.ENTITY_CREEPER_DEATH;
    }
    
    protected boolean eK() {
        return true;
    }
    
    @Override
    public boolean attackEntity(Entity entity) {
    	return true;
    }
    
    public static void tasks(org.bukkit.entity.Entity entity, Main plugin) {
    	Integer phase = MobManager.phase.get(entity.getUniqueId());
		ChatColor mobColor;
		EntityInsentient nmsEntity = (EntityInsentient) ((CraftEntity) entity).getHandle();
		
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
		case 4:
			mobColor = ChatColor.GREEN;
			break;
		default:
			mobColor = ChatColor.YELLOW;
		}
		
		DustOptions dustOptions;
		switch (phase){
			case 1:
				dustOptions = new DustOptions(Color.fromRGB(94, 255, 0), 1);
				entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 100, 0.4d, 1.7d, 0.4d, dustOptions);
				break;
			case 2:
				dustOptions = new DustOptions(Color.fromRGB(67, 186, 28), 1);
				entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 100, 0.4d, 1.7d, 0.4d, dustOptions);
				break;
			case 3:
				dustOptions = new DustOptions(Color.fromRGB(33, 122, 40), 1);
				entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 100, 0.4d, 1.7d, 0.4d, dustOptions);
				break;
			case 4:
				dustOptions = new DustOptions(Color.fromRGB(46, 82, 56), 1);
				entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 200, 0.5d, 1.9d, 0.5d, dustOptions);
				break;
			default:
				dustOptions = new DustOptions(Color.fromRGB(94, 255, 0), 1);
		}
		
		UUID mobID = entity.getUniqueId();
		
		ArrayList<Player> playerlist = new ArrayList<Player>();
		for (Player player : Bukkit.getOnlinePlayers()){
			double distance = player.getLocation().distance(entity.getLocation());
			if (distance < 40) {
				playerlist.add(player);
			}
			
		}
		
		if (Math.random() < (0.03 + 0.03 * phase)){
			for (Player player : playerlist){
				player.sendMessage(mobColor + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + mobColor + " Meteors yay!!");
			}
			for (int i = 0; i < phase * 2; i++) {
				ArmorStand meteor = (ArmorStand) entity.getWorld().spawnEntity(entity.getLocation().add(Math.random() * 25, 256, Math.random() * 25), EntityType.ARMOR_STAND);
				meteor.setGravity(false);
				meteor.setInvulnerable(true);
				meteor.setInvisible(true);
				meteorUpdate(plugin, meteor, entity, phase);
			}
			if (phase >= 3) {
				for (Player player : playerlist){
					for (int i = 0; i < phase; i++) {
						ArmorStand meteor = (ArmorStand) entity.getWorld().spawnEntity(player.getLocation().add(Math.random() * 10, 256, Math.random() * 10), EntityType.ARMOR_STAND);
						meteor.setGravity(false);
						meteor.setInvulnerable(true);
						meteor.setInvisible(true);
						meteorUpdate(plugin, meteor, entity, phase);
					}
				}
			}
		}
		
		if (Math.random() < (0.01 + 0.015 * phase)){
			for (Player player : playerlist){
				player.sendMessage(mobColor + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + mobColor + " OMG IM SO HYPPPPPPPEEEDDDDDDD!!!!");
				player.sendMessage(ChatColor.DARK_GRAY + "Zephyros is going to explode in 2 seconds");
				if (phase >= 3) {
					player.setVelocity(player.getVelocity().add(entity.getLocation().subtract(player.getLocation()).multiply(0.1d).toVector()));
				}
			}
			((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 45, 2 + phase, false, false));
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					entity.getWorld().createExplosion(entity.getLocation(), 2 + phase, false, false, entity);
				}
			}, 40);
		}
		
		if (Math.random() < (0.01 + 0.01 * phase) && phase >= 2){
			for (Player player : playerlist){
				player.sendMessage(mobColor + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + mobColor + " zoom zoom zap ZAP ZAP!");
				player.sendMessage(ChatColor.DARK_GRAY + "Zephyros is summoning exploding lightning from the sky");
			}
			summonLightning(plugin, entity, 1 + phase, playerlist, phase);
			
		}
		
		if (Math.random() < (0.02 + 0.025 * phase) && phase >= 2){
			BlockData bombData = Bukkit.createBlockData(Material.TNT);
			for (Player player : playerlist){
				player.sendMessage(mobColor + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + mobColor + " I have so much bombs!");
				Double time = 1d / 3d;
				FallingBlock bomb = player.getWorld().spawnFallingBlock(entity.getLocation().add(0, 1, 0), bombData);
				bomb.setVelocity(player.getLocation().subtract(entity.getLocation()).multiply(time).add(0, time * 3.9, 0).multiply(0.26).toVector());
				bomb.setMetadata("creeperbossbomb", new FixedMetadataValue(plugin, entity));
				bomb.setInvulnerable(true);
				bomb.setDropItem(false);
				if (phase == 4) {
					for (int i = 0; i < 2; i++) {
						FallingBlock randomBomb = player.getWorld().spawnFallingBlock(entity.getLocation().add(0, 1, 0), bombData);
						double randomAngle = Math.random() * 2 * Math.PI;
						randomBomb.setVelocity(new Vector(player.getLocation().distance(randomBomb.getLocation()) * Math.cos(randomAngle), 6 * time, player.getLocation().distance(randomBomb.getLocation()) * Math.sin(randomAngle)).multiply(0.35));
						randomBomb.setMetadata("creeperbossbomb", new FixedMetadataValue(plugin, entity));
						randomBomb.setInvulnerable(true);
						randomBomb.setDropItem(false);
					}
				}
			}
		}
		
		if (phase == 1 && HealthBar.mobHealth.get(mobID) < 280000 && !MobManager.changePhase.get(mobID)){
			for (Player player : playerlist){
				player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.YELLOW + " Why don't you boom with me?");
				player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.YELLOW + " I will teach you how to boom!");
			}
			MobManager.changePhase.put(mobID, true);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					for (Player player : playerlist){
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.RED + " BOOM BOOM BOOM!");
						player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Superhyped Zephyros", ChatColor.GRAY + "[Lv.3150]  Phase 2", 5, 60, 10);
						player.sendMessage(ChatColor.DARK_GRAY + "Zephyros will now have more booms!");
					}
					HealthBar.mobHealth.put(mobID, 14550000d);
					DustOptions dustOptions = new DustOptions(Color.fromRGB(67, 186, 28), 1);
					entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 500, 1.9d, 2.1d, 1.9d, dustOptions);
					BossBarHealth.customName.put(mobID, ChatColor.RED.toString() + "Superhyped Zephyros");
					EntityElementDefense.customElementAttack.put(mobID, EntityElementDefense.setElementStats(0, 0, 0, 125, 35, 30));
					EntityElementDefense.customElementDefense.put(mobID, EntityElementDefense.setElementStats(-10, -10, -10, 200, 400, 70));
					entity.getWorld().createExplosion(entity.getLocation(), 3, false, false, entity);
					HealthBar.mobLevel.put(mobID, 3150);
					MobManager.phase.put(mobID, 2);
					MobManager.changePhase.put(mobID, false);
					HealthBar.barUpdate(entity, 0, false);
				}
			}, 30);
		}
		
		if (phase == 2 && HealthBar.mobHealth.get(mobID) < 250000 && !MobManager.changePhase.get(mobID)){
			for (Player player : playerlist){
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.YELLOW + " I still want to BOOM");
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.YELLOW + " Let's explode!");
			}
			MobManager.changePhase.put(mobID, true);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					for (Player player : playerlist){
						player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.RED + " BOOM BOOM BOOM BOOM BOOM!");
						player.sendTitle(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ultrahyped Zephyros", ChatColor.GRAY + "[Lv.3225]  Phase 3", 5, 60, 10);
						player.sendMessage(ChatColor.DARK_GRAY + "Zephyros will now pull you when it booms!");
					}
					HealthBar.mobHealth.put(mobID, 14550000d);
					DustOptions dustOptions = new DustOptions(Color.fromRGB(33, 122, 40), 1);
					entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 500, 1.9d, 2.1d, 1.9d, dustOptions);
					BossBarHealth.customName.put(mobID, ChatColor.RED.toString() + "Ultrahyped Zephyros");
					EntityElementDefense.customElementAttack.put(mobID, EntityElementDefense.setElementStats(0, 0, 10, 135, 45, 35));
					EntityElementDefense.customElementDefense.put(mobID, EntityElementDefense.setElementStats(30, 30, 30, 270, 480, 120));
					entity.getWorld().createExplosion(entity.getLocation(), 5, false, false, entity);
					HealthBar.mobLevel.put(mobID, 3225);
					MobManager.phase.put(mobID, 3);
					MobManager.changePhase.put(mobID, false);
					HealthBar.barUpdate(entity, 0, false);
				}
			}, 30);
		}
		
		if (phase == 3 && HealthBar.mobHealth.get(mobID) < 150000 && !MobManager.changePhase.get(mobID)){
			for (Player player : playerlist){
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.YELLOW + " i wAnrT ExploDISOnnN!nn!");
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.YELLOW + " leT me ExplODdEE!Eee1!");
			}
			MobManager.changePhase.put(mobID, true);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					for (Player player : playerlist){
						player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Zephyros] " + ChatColor.RESET + "" + ChatColor.RED + " BOOM BOOM BOOM BOOM BOOM BOOM BOOM!");
						player.sendTitle(ChatColor.DARK_RED + "" + ChatColor.BOLD + "HYPE HYPE HYPE HYPE ZEPHYROS", ChatColor.GRAY + "[Lv.3400]  Phase 4", 5, 60, 10);
						player.sendMessage(ChatColor.DARK_GRAY + "Zephyros is so hyped that it explodes on death! Yay!");
					}
					HealthBar.mobHealth.put(mobID, 14550000d);
					DustOptions dustOptions = new DustOptions(Color.fromRGB(46, 82, 56), 1);
					entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 500, 1.9d, 2.1d, 1.9d, dustOptions);
					BossBarHealth.customName.put(mobID, ChatColor.RED.toString() + "HYPE HYPE HYPE HYPE ZEPHYROS");
					EntityElementDefense.customElementAttack.put(mobID, EntityElementDefense.setElementStats(0, 10, 25, 150, 50, 45));
					EntityElementDefense.customElementDefense.put(mobID, EntityElementDefense.setElementStats(60, 60, 60, 360, 590, 195));
					entity.getWorld().createExplosion(entity.getLocation(), 7.6f, false, false, entity);
					HealthBar.mobLevel.put(mobID, 3400);
					MobManager.phase.put(mobID, 4);
					MobManager.changePhase.put(mobID, false);
					HealthBar.barUpdate(entity, 0, false);
				}
			}, 30);
		}
    }
    
    
    private static void meteorUpdate(Main plugin, org.bukkit.entity.Entity meteor, org.bukkit.entity.Entity source, Integer phase) {
    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				Location nowPos = meteor.getLocation().add(0, -3.5 - phase * 0.5, 0);
				meteor.teleport(nowPos);
				meteor.getWorld().spawnParticle(Particle.REDSTONE, nowPos, 7, 0.7d, 3.5d, 0.7d, warningParticle);
				Location highestLoc = meteor.getWorld().getHighestBlockAt(nowPos.getBlockX(), nowPos.getBlockZ()).getLocation();
				meteor.getWorld().spawnParticle(Particle.REDSTONE, highestLoc, 9, 1.4d, 0.9d, 1.4d, new DustOptions(Color.fromRGB(247, 226, 30), 1));
				if (nowPos.getBlock().isEmpty()) {
					meteorUpdate(plugin, meteor, source, phase);
				}
				else {
					meteor.getWorld().createExplosion(highestLoc, 4 + phase, false, false, source);
					if (phase == 4) {
						meteor.getWorld().createExplosion(highestLoc.add(3, 0, 0), 3, false, false, source);
						meteor.getWorld().createExplosion(highestLoc.add(-3, 0, 0), 3, false, false, source);
						meteor.getWorld().createExplosion(highestLoc.add(0, 0, 3), 3, false, false, source);
						meteor.getWorld().createExplosion(highestLoc.add(0, 0, -3), 3, false, false, source);
					}
					meteor.remove();
				}
			}
    	}, 1);
    }
    
    private static void summonLightning(Main plugin, org.bukkit.entity.Entity source, Integer amountLeft, ArrayList<Player> playerlist, Integer phase) {
    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				for (Player player: playerlist) {
					Location playerLoc = player.getLocation();
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
						public void run(){
							player.getWorld().createExplosion(playerLoc, 2 + phase, false, false, source);
							player.getWorld().strikeLightningEffect(playerLoc);
						}
					}, 23 - phase * 3);
				}
				if (amountLeft > 1) {
					summonLightning(plugin, source, amountLeft - 1, playerlist, phase);
				}
			}
    	}, 20);
    }
    
    private static void setChant() {
    	deathChants.add("My name is Zephyros! I am a user of the finest magic crimson demons possess, "
    			+ "and I command explosion magic! The Devil King fancies himself superior to us and dares call himself \"strongest\"! "
    			+ "I shall obliterate his vile presence with our strongest of magics! EXPLOSION!");
    	deathChants.add("Darkness blacker than black, and darker than dark, I beseech thee, combine with my deep crimson. "
    			+ "The time of awakening cometh. Justice, fallen upon the infallible boundary, appear now as an intangible distortion! "
    			+ "This is the mightiest means of attack known to man, the ultimate attack magic! EXPLOSION!");
    	deathChants.add("Oh, blackness shrouded in light... Frenzied blade clad in the night... In the name of the crimson demons, "
    			+ "let the collapse of thine origin manifest. Summon before me the root of thy power hidden withing the lands... "
    			+ "of the kingdom of demise! EXPLOSION!");
    	deathChants.add("Crimson-black blaze, king of myriad worlds, though I promulgate the laws of nature, I am the alias of "
    			+ "destruction incarnate in accordance with the principles of all creation. Let the hammer of eternity descend unto me! EXPLOSION!");
    	deathChants.add("Darkness blacker than black and darker than dark, The time of awakening cometh. Justice, fallen upon the infallible boundary, "
    			+ "appear now as an intangible distortion! EXPLOSION!");
    	deathChants.add("The tower of rebellion creeps upon man's world... The unspoken faith displayed before me... The time has come! Now, awaken from "
    			+ "your slumber, and by my madness be wrought! Strike forth, EXPLOSION!");
    	deathChants.add("I am called Zephyros! The foremost and greatest mage among the Crimson Demons! "
    			+ "Because Megumin allowed me to pursue Explosion magic on that day, I became who I am now! "
    			+ "Let the storm blow! Let the flames roar! Explosion magic is the stuff of dreams! It is the ultimate magic that turns the impossible possible! EXPLOSION!");
    }
    
    public static void deathExplode(Main plugin, org.bukkit.entity.Entity entity) {
    	entity.setInvulnerable(true);
    	((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 50, true, true));
    	MobManager.stopTasks.add(entity.getUniqueId());
    	String chosenChant = deathChants.get((int) Math.floor(Math.random() * deathChants.size()));
    	ArrayList<String> seperateLineChant = new ArrayList<String>();
    	int begin = 0;
		int counter = 0;
		for (int i = 0; i < chosenChant.length(); i++) {
			counter++;
			if (chosenChant.charAt(i) == '!' || chosenChant.charAt(i) == ',') {
				if (begin + counter + 1 >= chosenChant.length()) {
					seperateLineChant.add(chosenChant.substring(begin));
				}
				else {
					seperateLineChant.add(chosenChant.substring(begin, begin + counter + 1));
					counter = 0;
					begin = i + 1;
				}
			}
			else if (chosenChant.charAt(i) == '.' && chosenChant.charAt(i + 1) != '.') {
				seperateLineChant.add(chosenChant.substring(begin, begin + counter + 1));
				counter = 0;
				begin = i + 1;
			}
		}
		chant(plugin, entity, seperateLineChant, 0);
    }
    
    private static void chant(Main plugin, org.bukkit.entity.Entity entity, ArrayList<String> chant, Integer index) {
    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				ArrayList<Player> playerlist = new ArrayList<Player>();
				for (Player player : Bukkit.getOnlinePlayers()){
					double distance = player.getLocation().distance(entity.getLocation());
					if (distance < 55) {
						playerlist.add(player);
					}
					if (distance < 35) {
						player.sendTitle(ChatColor.RED + "You are INSIDE Zephyros' explosion raidus!", ChatColor.DARK_RED + "Run before it's too late!", 5, 35, 5);
					}
					
				}
				if (index == chant.size() - 1) {
					for (Player player: playerlist) {
						player.sendMessage(ChatColor.RED + "[Zephyros] " + chant.get(index));
						if (player.getLocation().distance(entity.getLocation()) < 35) {
							String playername = player.getName();
							int health = fileSave.health.get(playername);
							int maxHealth = playerHealth.maxHealthActual.get(playername);
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1, true, true));
							playerDamage.updatePlayerHealth(player, health, maxHealth, maxHealth * (0.99 - player.getLocation().distance(entity.getLocation()) * 0.003));
						}
					}
					entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 7000, 30d, 30d, 30d, 0.3, new DustOptions(Color.fromRGB(161, 14, 49), 4), true);
					entity.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, entity.getLocation(), 2500, 35d, 35d, 35d, 0.2, null, true);
					entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 0);
					MobManager.noKill.remove(entity.getUniqueId());
					HealthBar.barUpdate(entity, 100000000, false);
					MobManager.stopTasks.remove(entity.getUniqueId());
				}
				else {
					for (Player player: playerlist) {
						player.sendMessage(ChatColor.GOLD + "[Zephyros] " + chant.get(index));
					}
					entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 5000, 30d, 30d, 30d, new DustOptions(Color.fromRGB(232, 211, 23), 2));
					chant(plugin, entity, chant, index + 1);
				}
			}
		}, 45);
    }
}
