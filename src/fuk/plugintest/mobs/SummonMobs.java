package fuk.plugintest.mobs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SummonMobs implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && args.length == 1 && command.getName().equalsIgnoreCase("spawnmob")){
			Player player = (Player) sender;
			switch (args[0]){
				case "zombieboss":
					MobManager.summonZombieBoss(player, player.getLocation());
				default:
					
			}
		}
		return true;
	}
}
