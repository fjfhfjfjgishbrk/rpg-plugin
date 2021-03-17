package fuk.plugintest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class walkSpeedCommand implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && args.length == 1 && command.getName().equalsIgnoreCase("ws")){
			Player sentPlayer = (Player) sender;
			if (args.length != 1){
				sentPlayer.sendMessage(ChatColor.RED + "Usage: /ws [speed]");
			}
			else if (!(isStringFloat(args[0]))){
				sentPlayer.sendMessage(ChatColor.RED + "Walkspeed needs to be between 1 and -1");
			}
			else {
				float walkSpeed = Float.parseFloat(args[0]);
				if (walkSpeed > 1 || walkSpeed < -1){
					sentPlayer.sendMessage(ChatColor.RED + "Walkspeed needs to be between 1 and -1");
				}
				sentPlayer.setWalkSpeed(Float.parseFloat(args[0]));
				sentPlayer.sendMessage(ChatColor.YELLOW + "Walkspeed set to " + args[0]);
			}
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
