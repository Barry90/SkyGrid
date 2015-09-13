package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public final class SkyGridOnAsyncPlayerChatEvent implements Listener {
	
	@EventHandler
	public void SkyGridonAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
		SkyGridPlayerManager.setPlayerIsBack(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
	}
}
