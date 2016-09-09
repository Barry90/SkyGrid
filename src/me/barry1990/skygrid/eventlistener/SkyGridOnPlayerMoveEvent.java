package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * SkyGridOnPlayerMoveEvent - this class handles move events of a player and is used for AFK
 * 
 * @author Barry1990
 */
public final class SkyGridOnPlayerMoveEvent implements Listener {

	@EventHandler
	public void SkyGridonPlayerMoveEvent(PlayerMoveEvent e) {

		SkyGridAFK afk = SkyGrid.getPlayerManager().getSkyGridAFK(e.getPlayer().getUniqueId());
		if (afk == null)
			return;

		if (afk.isAFK()) {
			if (afk.getAfkLocation() == null)
				return;
			if (!e.getTo().getWorld().equals(afk.getAfkLocation().getWorld())) {
				BarrysLogger.warn(this, "Can not calculated distances between worlds. Set current position to last position.");
				SkyGrid.getPlayerManager().setPlayerIsBack(e.getPlayer().getUniqueId(), e.getTo());
				return;
			}
			if (afk.getAfkLocation().distanceSquared(e.getTo()) >= 9) {
				SkyGrid.getPlayerManager().setPlayerIsBack(e.getPlayer().getUniqueId(), e.getTo());
			}
		} else {
			if (afk.getLastLocation() == null)
				return;
			if (!e.getTo().getWorld().equals(afk.getLastLocation().getWorld())) {
				BarrysLogger.warn(this, "Can not calculated distances between worlds. Set current position to last position.");
				SkyGrid.getPlayerManager().setPlayerIsBack(e.getPlayer().getUniqueId(), e.getTo());
				return;
			}
			if (afk.getLastLocation().distanceSquared(e.getTo()) >= 2) {
				SkyGrid.getPlayerManager().setPlayerIsBack(e.getPlayer().getUniqueId(), e.getTo());
			}
		}
	}
}
