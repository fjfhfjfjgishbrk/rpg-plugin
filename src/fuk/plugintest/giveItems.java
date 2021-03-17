package fuk.plugintest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class giveItems implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && args.length == 1 && command.getName().equalsIgnoreCase("giveitem")){
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("cowsword")){
				player.getInventory().addItem(itemManager.cowSword);
			}
			else if (args[0].equalsIgnoreCase("godmilk")){
				player.getInventory().addItem(itemManager.godMilk);
			}
			else if (args[0].equalsIgnoreCase("cowchest")){
				player.getInventory().addItem(itemManager.cowChestplate);
			}
			else if (args[0].equalsIgnoreCase("cowchest2")){
				player.getInventory().addItem(itemManager.cowChestplateT2);
			}
			else if (args[0].equalsIgnoreCase("milkcata")){
				player.getInventory().addItem(itemManager.milkCatalyst);
			}
			else if (args[0].equalsIgnoreCase("crystalseed")){
				player.getInventory().addItem(itemManager.crystalSeed);
			}
			else if (args[0].equalsIgnoreCase("transwheat")){
				player.getInventory().addItem(itemManager.transWheat);
			}
			else if (args[0].equalsIgnoreCase("naturalsandwich")){
				player.getInventory().addItem(itemManager.naturalSandwich);
			}
			else if (args[0].equalsIgnoreCase("naturalsandwicht2")){
				player.getInventory().addItem(itemManager.naturalSandwichT2);
			}
			else if (args[0].equalsIgnoreCase("stackhay")){
				player.getInventory().addItem(itemManager.stackHay);
			}
			else if (args[0].equalsIgnoreCase("stackwheat")){
				player.getInventory().addItem(itemManager.stackWheat);
			}
		}
		return false;
	}

}
