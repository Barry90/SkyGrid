package me.barry1990.skygrid.eventlistener;

import java.util.Random;

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
		
		if (this.random.nextInt(1000) == 0) {
			//0.1%
			e.setHatchingType(EntityType.CREEPER);
			e.setNumHatches(this.random.nextInt(2) == 0 ? (byte)1 : (byte)2);
			
		}
		
	}

}
