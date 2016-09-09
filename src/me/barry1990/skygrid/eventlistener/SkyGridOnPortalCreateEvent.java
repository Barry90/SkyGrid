package me.barry1990.skygrid.eventlistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

/**
 * SkyGridOnPortalCreateEvent - This class denies the creation of nether portals
 * 
 * @author Barry1990
 */
public final class SkyGridOnPortalCreateEvent implements Listener {

	@EventHandler
	public void OnPortalCreateEvent(PortalCreateEvent e) {

		e.setCancelled(true);
	}
}
