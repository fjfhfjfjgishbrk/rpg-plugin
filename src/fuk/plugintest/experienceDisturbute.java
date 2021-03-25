package fuk.plugintest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import fuk.plugintest.drops.customDrops;

public class experienceDisturbute implements Listener {
	
	private Main plugin;
	
	private static HashMap<Material, Double> cropXp = new HashMap<Material, Double>();
	private static ArrayList<Material> noPlaceCrop = new ArrayList<Material>();
	
	public experienceDisturbute(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		addCropBlocks();
	}
	
	private void addCropBlocks(){
		cropXp.put(Material.WHEAT, 1d);
		cropXp.put(Material.BEETROOTS, 1.3);
		cropXp.put(Material.CARROTS, 1.2);
		cropXp.put(Material.POTATOES, 1.2);
		cropXp.put(Material.MELON, 0.23);
		cropXp.put(Material.PUMPKIN, 0.23);
		cropXp.put(Material.BAMBOO, 0.1);
		cropXp.put(Material.COCOA, 1.4);
		cropXp.put(Material.SUGAR_CANE, 0.25);
		cropXp.put(Material.SWEET_BERRY_BUSH, 1.3);
		cropXp.put(Material.CACTUS, 1.4);
		cropXp.put(Material.BROWN_MUSHROOM, 1.2);
		cropXp.put(Material.BROWN_MUSHROOM_BLOCK, 1.2);
		cropXp.put(Material.BROWN_MUSHROOM, 1.2);
		cropXp.put(Material.BROWN_MUSHROOM_BLOCK, 1.2);
		cropXp.put(Material.KELP, 0.1);
		cropXp.put(Material.SEA_PICKLE, 1d);
		cropXp.put(Material.NETHER_WART, 1.3);
		noPlaceCrop.add(Material.MELON);
		noPlaceCrop.add(Material.PUMPKIN);
		noPlaceCrop.add(Material.SUGAR_CANE);
		noPlaceCrop.add(Material.CACTUS);
		noPlaceCrop.add(Material.SWEET_BERRY_BUSH);
		noPlaceCrop.add(Material.BROWN_MUSHROOM);
		noPlaceCrop.add(Material.RED_MUSHROOM);
		noPlaceCrop.add(Material.SEA_PICKLE);
		noPlaceCrop.add(Material.KELP);
	}
	
	@EventHandler
	public void entityDeath(EntityDeathEvent event){
		if (event.getEntity() instanceof ArmorStand || event.getEntity() instanceof Player){
			return;
		}
		UUID mobId = event.getEntity().getUniqueId();
		double experienceEarned = 0;
		event.getDrops().clear();
		double experiencePercent = event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 25;
		if (HealthBar.mobAttack.containsKey(mobId)){
			int mobLevel = HealthBar.mobLevel.get(mobId);
			experienceEarned = 1.5 * Math.pow(mobLevel, 1.26) * (0.9 + Math.random() * 0.2) * experiencePercent;
			for (Player player : HealthBar.mobAttack.get(mobId)){
				String playername = player.getName();
				fileSave.expToNextAtkLvl.put(playername, Math.round(fileSave.expToNextAtkLvl.get(playername) + experienceEarned));
				if (fileSave.expToNextAtkLvl.get(playername) > fileSave.atkExpRequired.get(playername)){
					fileSave.expToNextAtkLvl.put(playername, fileSave.expToNextAtkLvl.get(playername) - fileSave.atkExpRequired.get(playername));
					fileSave.attackLevel.put(playername, fileSave.attackLevel.get(playername) + 1);
					fileSave.atkExpRequired.put(playername, fileSave.CalculateExpReqiuired(fileSave.attackLevel.get(playername)));
					fileSave.dodge.put(playername, (int) (fileSave.attackLevel.get(playername) * 1));
					fileSave.critRate.put(playername, fileSave.critRate.get(playername) + 4);
					int dodgeLvl = fileSave.dodge.get(playername);
					double dodgeRate = (double) dodgeLvl / (double) (dodgeLvl + 1000) * 100;
					Long critChance = fileSave.critRate.get(playername);
					DecimalFormat df = new DecimalFormat("###.##");
					player.sendMessage(ChatColor.AQUA + "----------------------------------------------------------");
					player.sendMessage(ChatColor.BOLD.toString() + ChatColor.AQUA + "Congratulations on leveling up " + ChatColor.BOLD.toString() + ChatColor.BLUE + "Attack Skill!");
					player.sendMessage(ChatColor.AQUA + "You are now level " + ChatColor.GOLD + Integer.toString(fileSave.attackLevel.get(playername)));
					player.sendMessage(ChatColor.RED + "Your critical chance ✹ is now" + df.format(critChance / 10000) + "%");
					player.sendMessage(ChatColor.RED + "Your dodge rate ↺ is now " + Double.toString(dodgeRate) + "%");
					player.sendMessage(ChatColor.GRAY + "Deal " + Integer.toString((int) (Math.pow(1.8, Math.pow((float) (fileSave.attackLevel.get(playername) - 1) / 10f, 0.5))*100) - 100) + "% more damage to mobs");
				}
				int levelPercent = (int) ((double) fileSave.expToNextAtkLvl.get(playername) / (double) fileSave.atkExpRequired.get(playername) * 100d);
				player.sendMessage(ChatColor.AQUA + "+" + Integer.toString((int) Math.round(experienceEarned)) + " attack xp " + ChatColor.GOLD + "[" + Integer.toString(levelPercent) + "%]");
				List<ItemStack> dropList = customDrops.getDrops(event.getEntity(), player);
				for (ItemStack item: dropList){
					player.getInventory().addItem(item);
				}
			}
		}
		HealthBar.mobAttack.remove(mobId);
		
		
		
		//display experience got
		ArmorStand expNum = (ArmorStand) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation().add(0, -.9, 0), EntityType.ARMOR_STAND);
		expNum.setCustomName(ChatColor.BOLD.toString() + ChatColor.AQUA + "[+" + Integer.toString((int) Math.round(experienceEarned)) + " attack xp]");
		expNum.setCustomNameVisible(true);
		expNum.setGravity(false);
		expNum.setInvulnerable(true);
		expNum.setInvisible(true);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				expNum.remove();
			}
		}, 40);
	}
	
	@EventHandler
	public void breakBlocks(BlockBreakEvent event){
		Block blockBroken = event.getBlock();
		if (fileSave.blockbreak.contains(blockBroken.getLocation())){
			fileSave.blockbreak.remove(blockBroken.getLocation());
			return;
		}
		event.setDropItems(false);
		if (cropXp.containsKey(blockBroken.getType())){
			if (blockBroken.getBlockData() instanceof Ageable){
				if (!(((Ageable) blockBroken.getBlockData()).getAge() == ((Ageable) blockBroken.getBlockData()).getMaximumAge())){
					return;
				}
			}
			String playername = event.getPlayer().getName();
			Player player = event.getPlayer();
			int farmlvl = fileSave.farmLevel.get(playername);
			double experienceEarned = Math.ceil(1.9 * Math.pow(farmlvl, 1.15) * cropXp.get(blockBroken.getType()));
			fileSave.expToNextFarmLvl.put(playername, (long) (fileSave.expToNextFarmLvl.get(playername) + experienceEarned));
			if (fileSave.expToNextFarmLvl.get(playername) > fileSave.farmExpRequired.get(playername)){
				fileSave.expToNextFarmLvl.put(playername, fileSave.expToNextFarmLvl.get(playername) - fileSave.farmExpRequired.get(playername));
				fileSave.farmLevel.put(playername, fileSave.farmLevel.get(playername) + 1);
				fileSave.farmExpRequired.put(playername, fileSave.CalculateExpReqiuired(fileSave.farmLevel.get(playername)));
				fileSave.maxHealth.put(playername, fileSave.maxHealth.get(playername) + 1);
				fileSave.walkSpeed.put(playername, fileSave.walkSpeed.get(playername) + 1);
				int walkSpeed = fileSave.walkSpeed.get(playername);
				DecimalFormat df = new DecimalFormat("###.##");
				player.sendMessage(ChatColor.AQUA + "----------------------------------------------------------");
				player.sendMessage(ChatColor.BOLD.toString() + ChatColor.AQUA + "Congratulations on leveling up " + ChatColor.BOLD.toString() + ChatColor.BLUE + "Farming Skill!");
				player.sendMessage(ChatColor.AQUA + "You are now level " + ChatColor.GOLD + Integer.toString(fileSave.farmLevel.get(playername)));
				player.sendMessage(ChatColor.RED + "Your max health is now " + Integer.toString(fileSave.maxHealth.get(playername)) + "♥");
				player.sendMessage(ChatColor.RED + "You now walk " + df.format(walkSpeed / 2500) + "%༄ faster");
			}
			int levelPercent = (int) ((double) fileSave.expToNextFarmLvl.get(playername) / (double) fileSave.farmExpRequired.get(playername) * 100d);
			player.sendMessage(ChatColor.AQUA + "+" + Integer.toString((int) Math.round(experienceEarned)) + " farming xp " + ChatColor.GOLD + "[" + Integer.toString(levelPercent) + "%]");
			List<ItemStack> dropList = customDrops.getCropDrops(event.getBlock().getType(), player);
			for (ItemStack item: dropList){
				player.getInventory().addItem(item);
			}
		}
	}
	
	@EventHandler
	public void placeBlocks(BlockPlaceEvent event){
		Block blockPlaced = event.getBlockPlaced();
		if (noPlaceCrop.contains(blockPlaced.getType())){
			fileSave.blockbreak.add(blockPlaced.getLocation());
		}
	}
}
