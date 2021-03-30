package fuk.plugintest.recipes;

import fuk.plugintest.Main;

public class recipeManager {
	
	private Main plugin;
	
	public recipeManager(Main plugin){
		this.plugin = plugin;
		new Cow(plugin);
		new Wheat(plugin);
		new Beetroot(plugin);
	}
	
	
}
