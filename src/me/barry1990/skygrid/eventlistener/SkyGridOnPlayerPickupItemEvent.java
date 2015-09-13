package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;


public final class SkyGridOnPlayerPickupItemEvent implements Listener {
	
	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
		switch (e.getItem().getItemStack().getType()) {
		case DRAGON_EGG:
			SkyGridPlayerManager.awardAchievement(e.getPlayer(), SGAIDENTIFIER.A_VERY_BIG_EGG);			
			break;
		default:
			break;
		}
	}

}
