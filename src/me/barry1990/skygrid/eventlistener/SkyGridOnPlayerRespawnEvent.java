package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.sql.SkyGridSQL;
import me.barry1990.skygrid.world.SkyGridWorld;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * SkyGridOnPlayerRespawnEvent - This class handles the player respawn
 * 
 * @author Barry1990
 */
public final class SkyGridOnPlayerRespawnEvent implements Listener {

	@EventHandler
	public void onPlayerPortalEvent(PlayerRespawnEvent e) {

		Location loc = SkyGridSQL.sharedInstance().getHome(e.getPlayer(), SkyGridSQL.SPAWN_POINT);

		if (loc != null) {
			e.setRespawnLocation(loc);
		} else {

			loc = SkyGrid.getLevelManager().getLevel().generateSkyGridSpawnLocation(SkyGridWorld.getSkyGridWorld());
			SkyGridSQL.sharedInstance().addHome(e.getPlayer(), loc, SkyGridSQL.SPAWN_POINT);

			e.setRespawnLocation(loc);
		}
	}

}
