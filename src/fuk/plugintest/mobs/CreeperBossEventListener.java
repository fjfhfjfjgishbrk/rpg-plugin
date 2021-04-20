package fuk.plugintest.mobs;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.metadata.FixedMetadataValue;

import fuk.plugintest.Main;

public class CreeperBossEventListener implements Listener {
	
	private Main plugin;
	
	public CreeperBossEventListener(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void bombExplode(EntityChangeBlockEvent event) {
		if (event.getEntity().hasMetadata("creeperbossbomb")) {
			Entity entity = (Entity) event.getEntity().getMetadata("creeperbossbomb").get(0).value();
			Integer phase = MobManager.phase.get(entity.getUniqueId());
			entity.getWorld().createExplosion(event.getEntity().getLocation(), 3 + phase, false, false, entity);
			event.setCancelled(true);
			event.getEntity().remove();
		}
	}
	
}
