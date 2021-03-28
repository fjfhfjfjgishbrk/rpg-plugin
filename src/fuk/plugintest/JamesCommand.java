package fuk.plugintest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JamesCommand implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && command.getName().equalsIgnoreCase("playjames")){
			for (Player player : Bukkit.getOnlinePlayers()){
				if (player.getName() == args[0]){
					player.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), (float) (Math.random() * 360 - 180), (float) (Math.random() * 180 - 90)));
					player.sendMessage("haha lol get rekt");
					return true;
				}
			}
			sender.sendMessage("Player not online :(");
		}
		return true;
	}
	
}
