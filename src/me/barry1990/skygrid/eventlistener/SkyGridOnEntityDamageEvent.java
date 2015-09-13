package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


public class SkyGridOnEntityDamageEvent implements Listener {
	
	/////////////////////////////////////
	// THAT WAS CLOSE - Achievement
	/////////////////////////////////////
	
	@EventHandler
	public void SkyGridonEntityDamageEvent(EntityDamageEvent e) {
		switch (e.getCause()) {
			case FALL: {
				if (e.getEntityType() == EntityType.PLAYER) {
					
					if  (e.getDamage() > 16) {						
						double finalhealth = ((Player)e.getEntity()).getHealth() - e.getFinalDamage();
						if (finalhealth > 0 && finalhealth <= 2 ) 
							SkyGridPlayerManager.awardAchievement((Player)e.getEntity(), SGAIDENTIFIER.THAT_WAS_CLOSE);					
					}
				}
				break;
			}
			default:
				return;
		}
	}

}
