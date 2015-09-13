package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public final class SkyGridOnPlayerMoveEvent implements Listener {
	
	@EventHandler
	public void SkyGridonPlayerMoveEvent(PlayerMoveEvent e) {
		SkyGridAFK afk = SkyGridPlayerManager.getSkyGridAFK(e.getPlayer().getUniqueId());
		if (afk == null)
			return;
		if (afk.isAFK()) {
			if (afk.getAfkLocation() == null)
				return;
			if (afk.getAfkLocation().distanceSquared(e.getTo()) >= 9) {
				SkyGridPlayerManager.setPlayerIsBack(e.getPlayer().getUniqueId(), e.getTo());
			}
		} else {
			if (afk.getLastLocation() == null)
				return;
			if (afk.getLastLocation().distanceSquared(e.getTo()) >= 2) {
				SkyGridPlayerManager.setPlayerIsBack(e.getPlayer().getUniqueId(), e.getTo());
			}
		}
		
	}

}
