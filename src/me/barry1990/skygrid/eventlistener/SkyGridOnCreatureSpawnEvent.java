package me.barry1990.skygrid.eventlistener;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;


public final class SkyGridOnCreatureSpawnEvent implements Listener {
	
	@EventHandler
	public void onEntitySpawnEvent(CreatureSpawnEvent e) {
		
		/////////////////////////////////////
		// OH SH** - Achievement
		/////////////////////////////////////
		
		if (e.getSpawnReason() == SpawnReason.EGG) {
			if (e.getEntityType() == EntityType.CREEPER) {
				Creeper c = (Creeper) e.getEntity();
				c.setPowered(true);
			}
		}
		
	}

}
