package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;


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
