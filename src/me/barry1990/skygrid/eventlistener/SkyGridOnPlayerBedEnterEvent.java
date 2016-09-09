package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.sql.SkyGridSQL;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

/**
 * SkyGridOnPlayerBedEnterEvent - This class updates the spawnpoint if the player enters a bed
 * 
 * @author Barry1990
 */
public final class SkyGridOnPlayerBedEnterEvent implements Listener {

	@EventHandler
	public void SkyGridonPlayerBedEnterEvent(PlayerBedEnterEvent e) {

		SkyGridSQL.sharedInstance().addHome(e.getPlayer(), e.getPlayer().getLocation(), SkyGridSQL.SPAWN_POINT);
	}

}
