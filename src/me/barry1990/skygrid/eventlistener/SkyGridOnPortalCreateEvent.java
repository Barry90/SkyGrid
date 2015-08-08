package me.barry1990.skygrid.eventlistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;


public class SkyGridOnPortalCreateEvent implements Listener {

	@EventHandler
	public void OnPortalCreateEvent(PortalCreateEvent e) {
		
		e.setCancelled(true);	
	
	}
	
}
