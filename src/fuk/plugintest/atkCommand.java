package fuk.plugintest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class atkCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && args.length == 3 && command.getName().equalsIgnoreCase("atk")){
			Player sentPlayer = (Player) sender;
			if (args[1] == "level"){
				if (args[2] == "set"){
					if (!(isStringInt(args[3]))){
						sentPlayer.sendMessage(ChatColor.RED + "Please input an integer");
					}
					else {
						Integer level = Integer.parseInt(args[0]);
						if (level < 1){
							sentPlayer.sendMessage(ChatColor.RED + "Level needs to be bigger than 1");
							return false;
						}
						FileConfiguration playerConfig = fileSave.getPlayerConfig();
						playerConfig.set("players." + sentPlayer.getName() + ".attack.level", level);
						fileSave.attackLevel.put(sentPlayer.getName(), level);
						sentPlayer.sendMessage(ChatColor.YELLOW + "Attack level set to " + args[0]);
					}
				}
				if (args[2] == "add"){
					if (!(isStringInt(args[3]))){
						sentPlayer.sendMessage(ChatColor.RED + "Please input an integer");
					}
					else {
						Integer level = Integer.parseInt(args[0]) + fileSave.attackLevel.get(sentPlayer.getName());
						if (level < 1){
							sentPlayer.sendMessage(ChatColor.RED + "Level needs to be bigger than 1");
							return false;
						}
						FileConfiguration playerConfig = fileSave.getPlayerConfig();
						playerConfig.set("players." + sentPlayer.getName() + ".attack.level", level);
						fileSave.attackLevel.put(sentPlayer.getName(), level);
						sentPlayer.sendMessage(ChatColor.YELLOW + "Attack level set to " + args[0]);
					}
				}
			}
			return false;
		}
		return true;
	}
	
	
	public boolean isStringFloat(String s)
	{
	    try
	    {
	        Float.parseFloat(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
	
	public boolean isStringInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
}
