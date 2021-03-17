package fuk.plugintest;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
    private static Main instance;
	
    public static Main getInstance(){
        return instance;
    }   
    
	@Override
	public void onEnable(){
		instance = this;
		new HealthBar(this);
		new fileSave(this);
		new experienceDisturbute(this);
		new itemManager(this);
		new recipeManager(this);
		new damageCalculate(this);
		new playerHealth(this);
		new playerDamage(this);
		getCommand("ws").setExecutor(new walkSpeedCommand());
		getCommand("atk").setExecutor(new atkCommand());
		getCommand("giveitem").setExecutor(new giveItems());
		getCommand("viewstats").setExecutor(new viewStatsCommand());
	}
	
	@Override
	public void onDisable(){
		fileSave.createMobConfig();
		fileSave.savePlayerConfig();
		
	}
	
	
}
