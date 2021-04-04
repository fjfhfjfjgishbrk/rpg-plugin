package fuk.plugintest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class fileSave implements Listener {
	
	public static File mobLevelFile;
	private static YamlConfiguration mobConfig;
	private static File mobData;
	public static File playerLevelFile;
    public static YamlConfiguration customConfig;
    public static File blockFile;
    public static YamlConfiguration blockConfig;
    
    public static HashMap <String, Integer> attackLevel = new HashMap<String, Integer>();
    public static HashMap <String, Long> expToNextAtkLvl = new HashMap<String, Long>();
    public static HashMap <String, Long> atkExpRequired = new HashMap<String, Long>();
    public static HashMap <String, Integer> defenseLevel = new HashMap<String, Integer>();
    public static HashMap <String, Long> expToNextDefLvl = new HashMap<String, Long>();
    public static HashMap <String, Long> defExpRequired = new HashMap<String, Long>();
    public static HashMap <String, Integer> forageLevel = new HashMap<String, Integer>();
    public static HashMap <String, Long> expToNextForLvl = new HashMap<String, Long>();
    public static HashMap <String, Long> forExpRequired = new HashMap<String, Long>();
    public static HashMap <String, Integer> farmLevel = new HashMap<String, Integer>();
    public static HashMap <String, Long> expToNextFarmLvl = new HashMap<String, Long>();
    public static HashMap <String, Long> farmExpRequired = new HashMap<String, Long>();
    public static HashMap <String, Integer> fishLevel = new HashMap<String, Integer>();
    public static HashMap <String, Long> expToNextFishLvl = new HashMap<String, Long>();
    public static HashMap <String, Long> fishExpRequired = new HashMap<String, Long>();
    
    
    public static HashMap <String, Integer> strength = new HashMap<String, Integer>();
    public static HashMap <String, Integer> health = new HashMap<String, Integer>();
    public static HashMap <String, Integer> maxHealth = new HashMap<String, Integer>();
    public static HashMap <String, Integer> luck = new HashMap<String, Integer>();
    public static HashMap <String, ArrayList<Integer>> elementdefense = new HashMap<String, ArrayList<Integer>>();
    public static HashMap <String, Integer> defense = new HashMap<String, Integer>();
    public static HashMap <String, Integer> defenseBoost = new HashMap<String, Integer>();
    public static HashMap <String, Integer> defenseDuration = new HashMap<String, Integer>();
    public static HashMap <String, Integer> dodge = new HashMap<String, Integer>();
    public static HashMap <String, Integer> dodgeBoost = new HashMap<String, Integer>();
    public static HashMap <String, Integer> dodgeDuration = new HashMap<String, Integer>();
    public static HashMap <String, Integer> walkSpeed = new HashMap<String, Integer>();
    public static HashMap <String, Integer> walkBoost = new HashMap<String, Integer>();
    public static HashMap <String, Integer> walkDuration = new HashMap<String, Integer>();
    public static HashMap <String, Integer> mana = new HashMap<String, Integer>();
    public static HashMap <String, Integer> attackSpeed = new HashMap<String, Integer>();
    public static HashMap <String, Long> critRate = new HashMap<String, Long>();
    public static HashMap <String, Long> critDamage = new HashMap<String, Long>();
    public static HashMap <String, Long> heal = new HashMap<String, Long>();
    public static HashMap <String, Integer> healBoost = new HashMap<String, Integer>();
    public static HashMap <String, Integer> healDuration = new HashMap<String, Integer>();
    public static HashMap <String, Integer> miningSpeed = new HashMap<String, Integer>();
    
    
    public static ArrayList<Location> blockbreak = new ArrayList<Location>();
    private static Main plugin;
    
    public fileSave(Main plugin){
		fileSave.plugin = plugin;
		createPlayerConfig();
		loadMobConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
    
    
    //-------------------------------
    // mob save file
 	public static void createMobConfig() {
         mobLevelFile = new File(plugin.getDataFolder(), "mobLevel.yml");
         if (!mobLevelFile.exists()) {
             mobLevelFile.getParentFile().mkdirs();
             plugin.saveResource("mobLevel.yml", false);
          }

         mobConfig = YamlConfiguration.loadConfiguration(mobLevelFile);
         mobConfig.createSection("mobs");
         
         for (World w : Bukkit.getWorlds()) {
             for (Entity e : w.getEntities()){
                 if (e instanceof Player || e instanceof ArmorStand){
                 	continue;
                 }
                 else if (e instanceof LivingEntity){
                 	if (HealthBar.mobLevel.containsKey(e.getUniqueId())){
                 		getMobConfig().set("mobs." + e.getUniqueId().toString() + ".level", HealthBar.mobLevel.get(e.getUniqueId()));
                 	}
                 	if (HealthBar.mobMaxHealth.containsKey(e.getUniqueId())){
                 		getMobConfig().set("mobs." + e.getUniqueId().toString() + ".maxHealth", HealthBar.mobMaxHealth.get(e.getUniqueId()));
                 	}
                 	if (HealthBar.mobHealth.containsKey(e.getUniqueId())){
                 		getMobConfig().set("mobs." + e.getUniqueId().toString() + ".health", HealthBar.mobHealth.get(e.getUniqueId()));
                 	}
                 }

             }
         }
         
         try {
 			mobConfig.save(mobLevelFile);
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
     }
 	
 	
 	
 	//-------------------------------
 	//mob load file
 	public void loadMobConfig() {
         mobData = new File(plugin.getDataFolder(), "mobLevel.yml");
         if (!mobData.exists()) {
             return;
          }

         mobConfig = YamlConfiguration.loadConfiguration(mobData);
         
         Bukkit.getServer().getScheduler().runTaskTimer(this.plugin, new Runnable(){
        	 public void run(){
        		 for (World w : Bukkit.getWorlds()) {
                	 for (Entity e : w.getEntities()){
                		if (e instanceof Player || e instanceof ArmorStand){
                			continue;
                		}
                		else if (HealthBar.mobLevel.containsKey(e.getUniqueId())){
                        	continue;
                        }
                        else if (e instanceof LivingEntity){
                        	int mobLevel = Integer.valueOf(String.valueOf(getMobConfig().get("mobs." + e.getUniqueId().toString() + ".level", -1)));
                        	double mobMaxHealth = Double.valueOf(String.valueOf(getMobConfig().get("mobs." + e.getUniqueId().toString() + ".maxHealth", -1)));
                        	double mobHealth = Double.valueOf(String.valueOf(getMobConfig().get("mobs." + e.getUniqueId().toString() + ".health", -1)));
                         	if (!(mobLevel < 0 || mobMaxHealth < 0 || mobHealth < 0)){
                         		HealthBar.mobLevel.put(e.getUniqueId(), mobLevel);
                         		HealthBar.mobMaxHealth.put(e.getUniqueId(), mobMaxHealth);
                         		HealthBar.mobHealth.put(e.getUniqueId(), mobHealth);
                         		BossBarHealth.setHealthBar(e, true);
                         	}
                        }

                	 }
                 }
        	 }
         }, 10l, 20l);
     }
 	
 	
 	
 	//-------------------------------
 	//player join
 	@EventHandler
 	public void playerJoin(PlayerJoinEvent event){
 		playerLevelFile = new File(plugin.getDataFolder(), "players.yml");
 		customConfig = YamlConfiguration.loadConfiguration(playerLevelFile);
 		Player player = event.getPlayer();
 		FileConfiguration playerConfig = getPlayerConfig();
 		String playername = player.getName();
 		player.sendMessage(ChatColor.GOLD + "Hello " + playername + "!!!!!!!");
 		
 		//-------------------------------
 		// init atk stuff if first join
 		loadLevelStuff(player, "attack", attackLevel, expToNextAtkLvl, atkExpRequired);
 	 	
 	 	//-------------------------------
 		// init def stuff if first join
 	 	loadLevelStuff(player, "defense", defenseLevel, expToNextDefLvl, defExpRequired);
 	 	
 	 	defense.put(playername, defenseLevel.get(playername));
 	 	
 	 	//-------------------------------
 		// init foraging stuff if first join
 		loadLevelStuff(player, "forage", forageLevel, expToNextForLvl, forExpRequired);
 		
 		loadLevelStuff(player, "farming", farmLevel, expToNextFarmLvl, farmExpRequired);
 		
 		loadLevelStuff(player, "fishing", fishLevel, expToNextFishLvl, fishExpRequired);
 	 	
 	 	ArrayList<Integer> nulllist = new ArrayList<Integer>();
 	 	for (int i = 0; i < 6; i++){
 	 		nulllist.add(0);
 	 	}
 	 	
 	 	elementdefense.put(playername, (ArrayList<Integer>) playerConfig.getIntegerList("players." + playername + ".elementDefense"));
 		if (elementdefense.get(playername).isEmpty()){
 			elementdefense.put(playername, nulllist);
 		}
 	 	
 	 	
 	 	//-------------------------------
 	 	//init health stuff
 	 	int playerHealth = Integer.valueOf(String.valueOf(customConfig.get("players." + playername + ".health", -1)));
 	 	if (!(playerHealth < 0)){
 	 		health.put(playername, playerHealth);
 	 	}
 	 	else {
	 	 	health.put(playername, 100);
 	 	}
 	 	int playerMaxHealth = Integer.valueOf(String.valueOf(customConfig.get("players." + playername + ".maxHealth", -1)));
 	 	if (!(playerMaxHealth < 0)){
 	 		maxHealth.put(playername, playerMaxHealth);
 	 	}
 	 	else {
	 	 	maxHealth.put(playername, 100);
 	 	}
 	 	
 	 	//-------------------------------
 	 	//calculate luck
 	 	luck.put(playername, attackLevel.get(playername) + defenseLevel.get(playername) + forageLevel.get(playername) + farmLevel.get(playername) + fishLevel.get(playername));
 	 	
 	 	//-------------------------------
 	 	//calculate dodge
 	 	dodge.put(playername, attackLevel.get(playername));
 	 	
 	 	//-------------------------------
 	 	//calculate walkspeed
 	 	walkSpeed.put(playername, farmLevel.get(playername));
 	 	
 	 	//-------------------------------
 	 	//calculate strength
 	 	strength.put(playername, forageLevel.get(playername));
 	 	
 	 	critRate.put(playername, (long) (attackLevel.get(playername) * 4));
 	 	
 	 	critDamage.put(playername, 2000 + (long) (forageLevel.get(playername) * 10));
 	 	
 	 	heal.put(playername, (long) (forageLevel.get(playername) * 2));
 	 	
 	 	mana.put(playername, fishLevel.get(playername));
 	 	
 	 	attackSpeed.put(playername, defenseLevel.get(playername));
 	 	
 	 	miningSpeed.put(playername, defenseLevel.get(playername));
 	 	
 	 	//-------------------------------
 	 	//read boosts
 	 	loadBoosts(player, defenseDuration, defenseBoost, "defense");
 	 	loadBoosts(player, walkDuration, walkBoost, "walk");
 	 	loadBoosts(player, dodgeDuration, dodgeBoost, "dodgeRate");
 	 	loadBoosts(player, healDuration, healBoost, "heal");
 	 	
 	 	
 	 	
 	 	//--------------------------------
 	 	//load loactions
 	 	ArrayList<String> posRaw = new ArrayList<String>();
 	 	ArrayList<String> defaultPos = new ArrayList<String>();
 	 	defaultPos.add("-1");
 	 	posRaw = (ArrayList<String>) blockConfig.getList("blocks", defaultPos);
 	 	if (!(posRaw.isEmpty())){
 	 		if (posRaw.get(0) != "-1"){
	 	 		for (String pos: posRaw){
	 	 			String[] splitPos = pos.split("/");
	 	 			blockbreak.add(new Location(Bukkit.getServer().getWorld(splitPos[3]), Double.valueOf(splitPos[0]), Double.valueOf(splitPos[1]),Double.valueOf(splitPos[2])));
	 	 		}
 	 		}
 	 	}
 	}
 	
 	
 	//-------------------------------
 	//player level file
 	public void createPlayerConfig() {
         playerLevelFile = new File(plugin.getDataFolder(), "players.yml");
         if (!playerLevelFile.exists()) {
             playerLevelFile.getParentFile().mkdirs();
             plugin.saveResource("players.yml", false);
          }

         customConfig = YamlConfiguration.loadConfiguration(playerLevelFile);
         customConfig.createSection("players");
         
         blockFile = new File(plugin.getDataFolder(), "blocks.yml");
	 	 if (!blockFile.exists()) {
	 		 blockFile.getParentFile().mkdirs();
	 	     plugin.saveResource("blocks.yml", false);
	 	 }
	 	 blockConfig = YamlConfiguration.loadConfiguration(blockFile);
         
     }
 	
 	
 	//-------------------------------
 	// player save file
 	public static void savePlayerConfig() {
 		playerLevelFile = new File(plugin.getDataFolder(), "players.yml");
 	    if (!playerLevelFile.exists()) {
 	        playerLevelFile.getParentFile().mkdirs();
 	        plugin.saveResource("players.yml", false);
 	    }

 	    customConfig = YamlConfiguration.loadConfiguration(playerLevelFile);
 	        
 	    
 	    //-------------------------------
 	    //save atk stuff
 	    saveIntStuff(attackLevel, ".attack.level");
 	    saveLongStuff(expToNextAtkLvl, ".attack.exp");
 	    saveLongStuff(atkExpRequired, ".attack.expTillNext");
	    
	    
	    //-------------------------------
	    //save health stuff
 	    saveIntStuff(health, ".health");
 	    saveIntStuff(maxHealth, ".maxHealth");
	    
 	    //-------------------------------
 	    //save def stuff
 	    saveIntStuff(defenseLevel, ".defense.level");
 	    saveLongStuff(expToNextDefLvl, ".defense.exp");
 	    saveLongStuff(defExpRequired, ".defense.expTillNext");
 	    
 	    //-------------------------------
 	    //save def boost stuff
 	    saveIntStuff(defenseDuration, ".defense.boost.duration");
 	    saveIntStuff(defenseBoost, ".defense.boost.amount");
 	    
 	    //-------------------------------
 	    //ws and dodge
 	    saveIntStuff(walkSpeed, ".walkspeed");
 	    saveIntStuff(dodge, ".dodge");
 	    saveIntStuff(walkDuration, ".walk.boost.duration");
	    saveIntStuff(walkBoost, ".walk.boost.amount");
	    saveIntStuff(dodgeDuration, ".dodgeRate.boost.duration");
 	    saveIntStuff(dodgeBoost, ".dodgeRate.boost.amount");
 	    
 	    //-------------------------------
 	    //heal
 	    saveIntStuff(healDuration, ".heal.boost.duration");
	    saveIntStuff(healBoost, ".heal.boost.amount");
 	    
 	    
 	    //-------------------------------
 	    //save forage stuff
 	    saveIntStuff(forageLevel, ".forage.level");
 	    saveLongStuff(expToNextForLvl, ".forage.exp");
 	    saveLongStuff(forExpRequired, ".forage.expTillNext");
 	    
 	    //-------------------------------
 	    //save farm stuff
 	    saveIntStuff(farmLevel, ".farming.level");
 	    saveLongStuff(expToNextFarmLvl, ".farming.exp");
 	    saveLongStuff(farmExpRequired, ".farming.expTillNext");
 	    
 	    //-------------------------------
 	    //save fish stuff
 	    saveIntStuff(fishLevel, ".fishing.level");
 	    saveLongStuff(expToNextFishLvl, ".fishing.exp");
 	    saveLongStuff(fishExpRequired, ".fishing.expTillNext");
 	    
 	    
 	    //save ele def
 	    Iterator<Entry<String, ArrayList<Integer>>> it = elementdefense.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, ArrayList<Integer>> pair = (Map.Entry<String, ArrayList<Integer>>) it.next();
	        getPlayerConfig().set("players." + pair.getKey() + ".elementDefense", pair.getValue());
	        it.remove();
	    }
	    
 	    
 	    //---------------------------------
 	    //save block break location
 	    blockFile = new File(plugin.getDataFolder(), "blocks.yml");
	    if (!blockFile.exists()) {
	    	blockFile.getParentFile().mkdirs();
	        plugin.saveResource("blocks.yml", false);
	    }
	    blockConfig = YamlConfiguration.loadConfiguration(blockFile);
 	    ArrayList<String> locations = new ArrayList<String>();
 	    for (Location pos: blockbreak){
 	    	locations.add(pos.getBlockX() + "/" + pos.getBlockY() + "/" + pos.getBlockZ() + "/" + pos.getWorld().getName());
 	    }
 	    blockConfig.set("blocks", locations);
 	    
 	        
 	        
 	    try {
 	    	customConfig.save(playerLevelFile);
 	    	blockConfig.save(blockFile);
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 	}
 	
 	public static FileConfiguration getPlayerConfig() {
         return customConfig;
     }
 	
 	public static FileConfiguration getMobConfig() {
 		return mobConfig;
 	}
 	
 	public static long CalculateExpReqiuired(int level){
 		return (long) (20 * Math.pow(level, 2) + 8 * Math.pow(level, 2.3 * level / 5000));
 	}
 	
 	private static void saveLongStuff(HashMap<String, Long> h, String s){
 		Iterator<Entry<String, Long>> it = h.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Long> pair = (Map.Entry<String, Long>) it.next();
	        getPlayerConfig().set("players." + pair.getKey() + s, pair.getValue());
	        it.remove();
	    }
 	}
 	
 	private static void saveIntStuff(HashMap<String, Integer> h, String s){
 		Iterator<Entry<String, Integer>> it = h.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
	        getPlayerConfig().set("players." + pair.getKey() + s, pair.getValue());
	        it.remove();
	    }
 	}
 	
 	private static void loadLevelStuff(Player player, String skill, HashMap<String, Integer> level, HashMap<String, Long> expHas, HashMap<String, Long> expNeed){
 		FileConfiguration playerConfig = getPlayerConfig();
 		String playername = player.getName();
 		if (level.containsKey(playername)){
 			return;
 		}
 		int playerlevel = Integer.valueOf(String.valueOf(customConfig.get("players." + playername + "." + skill + ".level", -1)));
 		if (!(playerlevel < 0)){
 			level.put(playername, playerlevel);
 			long exp = CalculateExpReqiuired(playerlevel);
 			expNeed.put(playername, exp);
 		}
 		else {
 			playerConfig.set("players." + playername + "." + skill + "level", 1);
 			level.put(playername, 1);
 			expNeed.put(playername, CalculateExpReqiuired(1));
 		}
 		
 	 	long playerExp = Long.valueOf(String.valueOf(customConfig.get("players." + playername + "." + skill + ".exp", -1)));
 	 	if (!(playerExp < 0)){
 	 		expHas.put(playername, playerExp);
 	 	}
 	 	else {
 	 		playerConfig.set("players." + playername + "." + skill + ".exp", 0);
 	 		expHas.put(playername, 0l);
 	 	}
 	}

 	private static void loadBoosts(Player player, HashMap<String, Integer> duration, HashMap<String, Integer> boost, String skill){
 		String playername = player.getName();
 		if (duration.containsKey(playername)){
 			return;
 		}
 		int boostAmount = Integer.valueOf(String.valueOf(customConfig.get("players." + playername + "." + skill + ".boost.amount", -1)));
 	 	if (boostAmount != -1){
 	 		duration.put(playername, Integer.valueOf(String.valueOf(customConfig.get("players." + playername + "." + skill + ".boost.duration"))));
 	 		boost.put(playername, boostAmount);
 	 	}
 	}
}
