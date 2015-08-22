package me.barry1990.skygrid.eventlistener;

import java.util.Random;

import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
//import me.barry1990.skygrid.achievement.SGAchievement;


import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;


public class SkyGridOnPlayerEggThrowEvent implements Listener {
	
	private Random random;
	
	
	public SkyGridOnPlayerEggThrowEvent() {
		this.random = new Random();
	}
	
	@EventHandler
	public void onPlayerEggThrowEvent(PlayerEggThrowEvent e) {
		
		/////////////////////////////////////
		// OH SH** - Achievement
		/////////////////////////////////////
		
		if (this.random.nextInt(250) == 0) {
			//0.4%
			e.setHatchingType(EntityType.CREEPER);
			e.setNumHatches(this.random.nextInt(2) == 0 ? (byte)2 : (byte)3);
			SkyGridAchievementManager.award(e.getPlayer(), SGAIDENTIFIER.OH_SHIT);
		}
		
	}

}
