package fuk.plugintest.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import fuk.plugintest.Main;
import fuk.plugintest.items.itemManager;

public class Beetroot {
	
private Main plugin;
	
	public Beetroot(Main plugin){
		this.plugin = plugin;
		recipeBeetrootStuff();
	}
	
	@SuppressWarnings("deprecation")
	private void recipeStackBeetroot(){
		ItemStack item = itemManager.stackBeetroot;
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "stackBeetroot"), item);
		recipe.shape("aaa", "aaa", "aaa");
		recipe.setIngredient('a', Material.BEETROOT);
		plugin.getServer().addRecipe(recipe);
	}
	
	@SuppressWarnings("deprecation")
	private void recipeExStackBeetroot(){
		ItemStack item = itemManager.exStackBeetroot;
		ItemStack beetroot = itemManager.stackBeetroot;
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "exStackBeetroot"), item);
		recipe.shape("aaa", "aaa", "aaa");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(beetroot));
		plugin.getServer().addRecipe(recipe);
	}
	
	@SuppressWarnings("deprecation")
	private void recipeExSinguBeetroot(){
		ItemStack item = itemManager.beetrootSingularity;
		ItemStack beetroot = itemManager.exStackBeetroot;
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "beetrootSingularity"), item);
		recipe.shape("aaa", "aaa", "aaa");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(beetroot));
		plugin.getServer().addRecipe(recipe);
	}
	
	@SuppressWarnings("deprecation")
	private void recipeRoastBeetroot(){
		ItemStack item = itemManager.roastedBeetroot;
		ItemStack beetroot = itemManager.stackBeetroot;
		FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(plugin, "roastedBeetroot"), item, new RecipeChoice.ExactChoice(beetroot), 0f, 600);
		plugin.getServer().addRecipe(recipe);
	}
	
	@SuppressWarnings("deprecation")
	private void recipeMoistRoastBeetroot(){
		ItemStack item = itemManager.moistRoastBeetroot;
		ItemStack beetroot = itemManager.roastedBeetroot;
		ItemStack wetBeetroot = itemManager.liquidBeetroot;
		ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, "moistRoastBeetroot"), item);
		recipe.addIngredient(new RecipeChoice.ExactChoice(beetroot));
		recipe.addIngredient(new RecipeChoice.ExactChoice(wetBeetroot));
		plugin.getServer().addRecipe(recipe);
	}
	
	
	private void recipeBeetrootStuff(){
		recipeStackBeetroot();
		recipeExStackBeetroot();
		recipeExSinguBeetroot();
		recipeRoastBeetroot();
		recipeMoistRoastBeetroot();
	}
}
