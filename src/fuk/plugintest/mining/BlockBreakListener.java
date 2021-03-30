package fuk.plugintest.mining;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import fuk.plugintest.Main;
import fuk.plugintest.experienceDisturbute;
import fuk.plugintest.fileSave;
import net.minecraft.server.v1_16_R3.BlockPosition;

public class BlockBreakListener implements Listener {
	
	private ArrayList<Integer> sentPacketId = new ArrayList<Integer>();
	
	private NamespacedKey miningTag;
	
	public BlockBreakListener(Main plugin, ProtocolManager protocolManager){
		interceptPackets(plugin, protocolManager);
		miningTag = new NamespacedKey(plugin, "miningspeed");
		
	}
	
	
	private void interceptPackets(Main thisPlugin, ProtocolManager protocolManager){
		protocolManager.addPacketListener(new PacketAdapter(thisPlugin, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event){
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();
                List<Object> packetValues = packet.getModifier().getValues();
                Object action = packetValues.get(2);
                BlockPosition blockPos = (BlockPosition) packetValues.get(0);
                if (action.toString().equals("START_DESTROY_BLOCK")){
                	MiningManager.isMining.put(player.getName(), true);
                	Block block = player.getWorld().getBlockAt(new Location(player.getWorld(), blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                	float hardness = block.getType().getHardness();
                	if (MiningManager.breakSpeed.containsKey(block.getType())){
                		hardness = MiningManager.breakSpeed.get(block.getType());
                	}
                	int miningSpeed = fileSave.miningSpeed.get(player.getName());
                	if (player.getEquipment().getItemInMainHand().hasItemMeta()){
                		if (player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(miningTag, PersistentDataType.INTEGER)){
                			miningSpeed += player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(miningTag, PersistentDataType.INTEGER);
                		}
                	}
                	breakBlocks(thisPlugin, protocolManager, hardness * 8 * (1d - (double) miningSpeed / (double) (miningSpeed + 800d)), 0, player, block.getLocation());
                }
                else {
                	MiningManager.isMining.put(player.getName(), false);
                	PacketContainer cancelPacket = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                	int x = blockPos.getX();
					int y = blockPos.getY();
					int z = blockPos.getZ();
					cancelPacket.getModifier().writeSafely(0, player.getEntityId() + 10000);
					cancelPacket.getModifier().writeSafely(1, new BlockPosition(x, y, z));
					cancelPacket.getModifier().writeSafely(2, 100);
					try {
						protocolManager.sendServerPacket(player, cancelPacket);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
                }
            }
        });
		protocolManager.addPacketListener(new PacketAdapter(thisPlugin, ListenerPriority.NORMAL, PacketType.Play.Server.BLOCK_BREAK_ANIMATION) {
			@Override
            public void onPacketSending(PacketEvent event){
				PacketContainer packet = event.getPacket();
				if (sentPacketId.contains(packet.getId())){
					sentPacketId.remove(sentPacketId.indexOf(packet.getId()));
					return;
				}
				event.setCancelled(true);
			}
		});
    }
	
	private void breakBlocks(Main thisPlugin, ProtocolManager protocolManager, double timePerState, Integer state, Player player, Location blockLoc){
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(thisPlugin, new Runnable(){
			public void run(){
				if (!player.getTargetBlockExact(8).getLocation().equals(blockLoc)){
					return;
				}
				if (MiningManager.isMining.get(player.getName())){
					if (state > 9){
						experienceDisturbute.breakBlocks(new BlockBreakEvent(blockLoc.getBlock(), player));
						Location loc = blockLoc;
						Bukkit.getServer().getScheduler().runTask(thisPlugin, new Runnable(){
							public void run(){
								player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc.add(0.5,0.5,0.5), 20, 0.5, 0.5, 0.5, 0.1, blockLoc.getBlock().getType().createBlockData());
								loc.getBlock().setType(Material.AIR);
							}
						});
						return;
					}
					PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
					int x = blockLoc.getBlockX();
					int y = blockLoc.getBlockY();
					int z = blockLoc.getBlockZ();
					packet.getModifier().writeSafely(0, player.getEntityId() + 10000);
					packet.getModifier().writeSafely(1, new BlockPosition(x, y, z));
					packet.getModifier().writeSafely(2, state);
					try {
						protocolManager.sendServerPacket(player, packet);
						sentPacketId.add(packet.getId());
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					breakBlocks(thisPlugin, protocolManager, timePerState, state + 1, player, blockLoc);
				}
			}
		}, (long) timePerState);
	}
}
