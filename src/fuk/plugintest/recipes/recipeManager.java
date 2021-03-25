package fuk.plugintest.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import fuk.plugintest.Main;
import fuk.plugintest.items.itemManager;

public class recipeManager {
	
	private Main plugin;
	
	public recipeManager(Main plugin){
		this.plugin = plugin;
		new Cow(plugin);
		new Wheat(plugin);
		new Beetroot(plugin);
	}
	
	
}
