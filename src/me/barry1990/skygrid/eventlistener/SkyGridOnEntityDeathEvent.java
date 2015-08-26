package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.achievement.SkyGridAchievementManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public final class SkyGridOnEntityDeathEvent implements Listener {
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e) {
		
		/////////////////////////////////////
		// NETHER CLEANER - Achievement
		/////////////////////////////////////
		
		if (e.getEntity().getKiller() != null) {
			
			switch (e.getEntityType()) {
				case PIG_ZOMBIE:
				case GHAST:
				case BLAZE: {
					SkyGridAchievementManager.addNetherCleanerProgress(e.getEntity().getKiller());
					break;
				}
				default:
					break;
			}
			
		}
	}

}
