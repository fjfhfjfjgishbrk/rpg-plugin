package fuk.plugintest;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fuk.plugintest.items.itemManager;

public class giveItems implements CommandExecutor {

	private static HashMap<String, ItemStack> items = new HashMap<String, ItemStack>();
	
	
	public static void init(){
		items.put("cowsword", itemManager.cowSword);
		items.put("godmilk", itemManager.godMilk);
		items.put("cowchest", itemManager.cowChestplate);
		items.put("cowchest2", itemManager.cowChestplateT2);
		items.put("milkcata", itemManager.milkCatalyst);
		items.put("crystalseed", itemManager.crystalSeed);
		items.put("transwheat", itemManager.transWheat);
		items.put("naturalsandwich", itemManager.naturalSandwich);
		items.put("naturalsandwich2", itemManager.naturalSandwichT2);
		items.put("stackhay", itemManager.stackHay);
		items.put("stackwheat", itemManager.stackWheat);
		items.put("testfiresword", itemManager.testFireSword);
		items.put("testfiresword2", itemManager.testFireSwordt2);
		items.put("testpickaxe", itemManager.testPickaxe);
		items.put("stackbeetroot", itemManager.stackBeetroot);
		items.put("crystalbeetroot", itemManager.crystalBeetroot);
		items.put("liquidbeetroot", itemManager.liquidBeetroot);
		items.put("roastbeetroot", itemManager.roastedBeetroot);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && args.length == 1 && command.getName().equalsIgnoreCase("giveitem")){
			Player player = (Player) sender;
			if (items.containsKey(args[0].toLowerCase())){
				player.getInventory().addItem(items.get(args[0].toLowerCase()));
			}
		}
		return true;
	}

}
