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
			if (args[0].equalsIgnoreCase("godmilk")){
				player.getInventory().addItem(itemManager.godMilk);
			}
			if (args[0].equalsIgnoreCase("cowchest")){
				player.getInventory().addItem(itemManager.cowChestplate);
			}
			if (args[0].equalsIgnoreCase("cowchest2")){
				player.getInventory().addItem(itemManager.cowChestplateT2);
			}
			if (args[0].equalsIgnoreCase("milkcata")){
				player.getInventory().addItem(itemManager.milkCatalyst);
			}
		}
		return false;
	}

}
