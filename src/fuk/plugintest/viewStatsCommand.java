package fuk.plugintest;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class viewStatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && args.length == 1 && command.getName().equalsIgnoreCase("viewstats")){
			Player sentPlayer = (Player) sender;
			if (args[0].equalsIgnoreCase("attack")){
				sentPlayer.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "Your attack stats:");
				sentPlayer.sendMessage(ChatColor.GOLD + "Attack level " + Long.toString(fileSave.attackLevel.get(sentPlayer.getName())));
				sentPlayer.sendMessage(ChatColor.GOLD + "Exp: " + Long.toString(fileSave.expToNextAtkLvl.get(sentPlayer.getName())) + " / " + Long.toString(fileSave.atkExpRequired.get(sentPlayer.getName())));
			}
			else if (args[0].equalsIgnoreCase("defense")){
				sentPlayer.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "Your defense stats:");
				sentPlayer.sendMessage(ChatColor.GOLD + "Defense level " + Long.toString(fileSave.defenseLevel.get(sentPlayer.getName())));
				sentPlayer.sendMessage(ChatColor.GOLD + "Exp: " + Long.toString(fileSave.expToNextDefLvl.get(sentPlayer.getName())) + " / " + Long.toString(fileSave.defExpRequired.get(sentPlayer.getName())));
			}
			else if (args[0].equalsIgnoreCase("farming")){
				sentPlayer.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "Your farming stats:");
				sentPlayer.sendMessage(ChatColor.GOLD + "Farming level " + Long.toString(fileSave.farmLevel.get(sentPlayer.getName())));
				sentPlayer.sendMessage(ChatColor.GOLD + "Exp: " + Long.toString(fileSave.expToNextFarmLvl.get(sentPlayer.getName())) + " / " + Long.toString(fileSave.farmExpRequired.get(sentPlayer.getName())));
			}
			else if (args[0].equalsIgnoreCase("dodge")){
				int dodgeLvl = fileSave.dodge.get(sender.getName());
				if (fileSave.dodgeBoost.containsKey(sender.getName())){
					dodgeLvl += fileSave.dodgeBoost.get(sender.getName());
				}
				double dodgeRate = (double) dodgeLvl / (double) (dodgeLvl + 1000) * 100;
				DecimalFormat df = new DecimalFormat("###.##");
				sentPlayer.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Your dodge rate ↺ is " + df.format(dodgeRate) + "%");
			}
			else if (args[0].equalsIgnoreCase("critrate")){
				Long critRate = fileSave.critRate.get(sender.getName());
				DecimalFormat df = new DecimalFormat("###.##");
				sentPlayer.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "Your critical rate ✹ is " + df.format((double)critRate / 100d) + "%");
			}
			else if (args[0].equalsIgnoreCase("critdamage")){
				Long critDamage = fileSave.critDamage.get(sender.getName());
				DecimalFormat df = new DecimalFormat("###.##");
				sentPlayer.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "Your critical damage ✹ is " + df.format((double) critDamage / 100d) + "%");
			}
			else if (args[0].equalsIgnoreCase("speed")){
				int speed = fileSave.walkSpeed.get(sender.getName());
				if (fileSave.walkBoost.containsKey(sender.getName())){
					speed += fileSave.walkBoost.get(sender.getName());
				}
				DecimalFormat df = new DecimalFormat("###.##");
				sentPlayer.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "Your speed ༄ lets you walk " + df.format(0.2 + speed / 1250) + "% faster");
			}
			else if (args[0].equalsIgnoreCase("strength")){
				int strength = fileSave.strength.get(sender.getName());
				sentPlayer.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Your strength ֍ is " + Integer.toString(strength) + "%");
			}
			else if (args[0].equalsIgnoreCase("attackspeed")){
				int attackspeed = fileSave.attackSpeed.get(sender.getName());
				int cooldown = (int) (500 - 9.6 * Math.pow(attackspeed, 0.4));
				DecimalFormat df = new DecimalFormat("###.##");
				sentPlayer.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Your attack speed ⚔ lets you attack " + df.format(1000d / (double) cooldown) + " times per second");
			}
			else if (args[0].equalsIgnoreCase("speed")){
				long heal = fileSave.heal.get(sender.getName());
				DecimalFormat df = new DecimalFormat("###.##");
				sentPlayer.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "You heal ❉ " + df.format(heal / 10000) + "% more");
			}
			return true;
		}
		return false;
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
