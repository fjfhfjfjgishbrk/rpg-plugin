package fuk.plugintest;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import fuk.plugintest.enchants.EnchantManager;
import fuk.plugintest.items.itemManager;
import fuk.plugintest.mining.MiningManager;
import fuk.plugintest.mobs.MobManager;
import fuk.plugintest.mobs.SummonMobs;
import fuk.plugintest.recipes.recipeManager;

public class Main extends JavaPlugin implements Listener{
	
    private static Main instance;
    
    private static ProtocolManager protocolManager;
	
    public static Main getInstance(){
        return instance;
    }   
    
	@Override
	public void onEnable(){
		instance = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		new HealthBar(this);
		new fileSave(this);
		new experienceDisturbute(this);
		new itemManager(this);
		new recipeManager(this);
		new damageCalculate(this);
		new playerHealth(this);
		new playerDamage(this);
		new playerEat(this);
		new InventoryManager(this);
		new EntityElementDefense(this);
		new BossBarHealth(this);
		new MobManager(this);
		new MiningManager(this, protocolManager);
		getCommand("ws").setExecutor(new walkSpeedCommand());
		getCommand("atk").setExecutor(new atkCommand());
		getCommand("giveitem").setExecutor(new giveItems());
		getCommand("viewstats").setExecutor(new viewStatsCommand());
		getCommand("playjames").setExecutor(new JamesCommand());
		getCommand("spawnmob").setExecutor(new SummonMobs());
	}
	
	@Override
	public void onDisable(){
		fileSave.createMobConfig();
		fileSave.savePlayerConfig();
		EnchantManager.saveEnchantFile();
	}
	
	
}
