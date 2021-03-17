package fuk.plugintest;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class recipeManager {
	
	private Main plugin;
	
	public recipeManager(Main plugin){
		this.plugin = plugin;
		recipeCowStuff();
		recipeWheatStuff();
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
		ItemStack beef = itemManager.stackBeef.clone();
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
		ItemStack beef = itemManager.stackCookedBeef.clone();
		ItemStack milk = itemManager.godMilk.clone();
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
		ItemStack beef = itemManager.stackCookedBeef.clone();
		ItemStack milk = itemManager.godMilk.clone();
		ItemStack chest = itemManager.cowChestplate.clone();
		ItemStack cata = itemManager.milkCatalyst.clone();
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
	
	//stack wheat
	private void recipeStackWheat(){
		ItemStack item = itemManager.stackWheat;
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "stackWheat"), item);
		recipe.shape("aaa", "aaa", "aaa");
		recipe.setIngredient('a', Material.HAY_BLOCK);
		plugin.getServer().addRecipe(recipe);
	}
	
	//very stack wheat
	@SuppressWarnings("deprecation")
	private void recipeExStackWheat(){
		ItemStack item = itemManager.exStackWheat;
		ItemStack wheat = itemManager.stackWheat.clone();
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "exStackWheat"), item);
		recipe.shape("aaa", "aaa", "aaa");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(wheat));
		plugin.getServer().addRecipe(recipe);
	}
	
	//stack hay
	@SuppressWarnings("deprecation")
	private void recipeStackHay(){
		ItemStack item = itemManager.stackHay;
		ItemStack wheat = itemManager.exStackWheat.clone();
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "stackHay"), item);
		recipe.shape("aaa", "aaa", "aaa");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(wheat));
		plugin.getServer().addRecipe(recipe);
	}
	
	//ex stack hay
	@SuppressWarnings("deprecation")
	private void recipeExStackHay(){
		ItemStack item = itemManager.exStackHay;
		ItemStack hay = itemManager.stackHay.clone();
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "exStackHay"), item);
		recipe.shape("aaa", "aaa", "aaa");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(hay));
		plugin.getServer().addRecipe(recipe);
	}
	
	//bread
	@SuppressWarnings("deprecation")
	private void recipeBread(){
		ItemStack item = itemManager.betterBread;
		ItemStack wheat = itemManager.exStackWheat.clone();
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "betterBread"), item);
		recipe.shape("   ", "aaa", "   ");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(wheat));
		plugin.getServer().addRecipe(recipe);
	}
	
	//sandwich
	@SuppressWarnings("deprecation")
	private void recipeSandwich(){
		ItemStack item = itemManager.sandwich;
		ItemStack beef = itemManager.stackCookedBeef.clone();
		ItemStack bread = itemManager.betterBread.clone();
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "sandwich"), item);
		recipe.shape(" b ", "aaa", " b ");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(beef));
		recipe.setIngredient('b', new RecipeChoice.ExactChoice(bread));
		plugin.getServer().addRecipe(recipe);
	}
	
	//natural sandwich
	@SuppressWarnings("deprecation")
	private void recipeNaturalSandwich(){
		ItemStack item = itemManager.naturalSandwich;
		ItemStack sandwich = itemManager.sandwich.clone();
		ItemStack bread = itemManager.betterBread.clone();
		ItemStack seed = itemManager.crystalSeed.clone();
		ItemStack beef = itemManager.stackCookedBeef.clone();
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "naturalSandwich"), item);
		recipe.shape(" b ", "dac", " b ");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(sandwich));
		recipe.setIngredient('b', new RecipeChoice.ExactChoice(bread));
		recipe.setIngredient('c', new RecipeChoice.ExactChoice(seed));
		recipe.setIngredient('d', new RecipeChoice.ExactChoice(beef));
		plugin.getServer().addRecipe(recipe);
	}
	
	//natural sandwich t2
	@SuppressWarnings("deprecation")
	private void recipeNaturalSandwichT2(){
		ItemStack item = itemManager.naturalSandwichT2;
		ItemStack sandwich = itemManager.naturalSandwich.clone();
		ItemStack bread = itemManager.betterBread.clone();
		ItemStack wheat = itemManager.transWheat.clone();
		ItemStack beef = itemManager.stackCookedBeef.clone();
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "naturalSandwichT2"), item);
		recipe.shape("bbb", "dac", "bbb");
		recipe.setIngredient('a', new RecipeChoice.ExactChoice(sandwich));
		recipe.setIngredient('b', new RecipeChoice.ExactChoice(bread));
		recipe.setIngredient('c', new RecipeChoice.ExactChoice(wheat));
		recipe.setIngredient('d', new RecipeChoice.ExactChoice(beef));
		plugin.getServer().addRecipe(recipe);
	}
	
	private void recipeWheatStuff(){
		recipeStackWheat();
		recipeExStackWheat();
		recipeStackHay();
		recipeExStackHay();
		recipeBread();
		recipeSandwich();
		recipeNaturalSandwich();
		recipeNaturalSandwichT2();
	}
	
}
