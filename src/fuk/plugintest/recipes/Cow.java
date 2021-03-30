package fuk.plugintest.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import fuk.plugintest.Main;
import fuk.plugintest.items.itemManager;

public class Cow {
	
	private Main plugin;
	
	public Cow(Main plugin){
		this.plugin = plugin;
		recipeCowStuff();
	}
	
	//recipe for stack cow meat
	private void recipeStackBeef(){
		ItemStack item = itemManager.stackBeef;
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "stackBeef"), item);
		recipe.shape("bbb", "bbb", "bbb");
		recipe.setIngredient('b', new RecipeChoice.MaterialChoice(Material.BEEF));
		plugin.getServer().addRecipe(recipe);
	}
	
	//recipe for very stack cow meat
	@SuppressWarnings("deprecation")
	private void recipeStackCookedBeef(){
		ItemStack item = itemManager.stackCookedBeef;
		ItemStack beef = itemManager.stackBeef;
		beef.setAmount(8);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "stackCookedBeef"), item);
		recipe.shape("aaa", "aaa", "aaa");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(beef));
		plugin.getServer().addRecipe(recipe);
	}
	
	//milky chest
	@SuppressWarnings("deprecation")
	private void recipeCowChestplate(){
		ItemStack item = itemManager.cowChestplate;
		ItemStack beef = itemManager.stackCookedBeef;
		ItemStack milk = itemManager.godMilk;
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "cowChestplate"), item);
		recipe.shape("a a", "aba", "aaa");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(beef));
		recipe.setIngredient('b', new RecipeChoice.ExactChoice(milk));
		plugin.getServer().addRecipe(recipe);
	}
	
	//milky chest t2
	@SuppressWarnings("deprecation")
	private void recipeCowChestplateT2(){
		ItemStack item = itemManager.cowChestplateT2;
		ItemStack beef = itemManager.stackCookedBeef;
		ItemStack milk = itemManager.godMilk;
		ItemStack chest = itemManager.cowChestplate;
		ItemStack cata = itemManager.milkCatalyst;
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "cowChestplateT2"), item);
		recipe.shape("a a", "bcb", "ada");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(beef));
		recipe.setIngredient('b', new RecipeChoice.ExactChoice(milk));
		recipe.setIngredient('c', new RecipeChoice.ExactChoice(chest));
		recipe.setIngredient('d', new RecipeChoice.ExactChoice(cata));
		plugin.getServer().addRecipe(recipe);
	}
	
	private void recipeCowStuff(){
		recipeStackBeef();
		recipeStackCookedBeef();
		recipeCowChestplate();
		recipeCowChestplateT2();
	}
}
