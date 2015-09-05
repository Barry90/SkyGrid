package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.sql.SkyGridSQL;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;


public final class SkyGridOnPlayerBedEnterEvent implements Listener {
	
	///////////////////////////////////////
	// SET NEW SPAWNPOINT
	///////////////////////////////////////
	
	@EventHandler
	public void SkyGridonPlayerBedEnterEvent(PlayerBedEnterEvent e) {		
		SkyGridSQL.sharedInstance().addHome(e.getPlayer(), e.getPlayer().getLocation(), SkyGridSQL.SPAWN_POINT);
	}

}
