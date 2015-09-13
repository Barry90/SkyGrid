package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;


public final class SkyGridOnEnchantItemEvent implements Listener {
	
	/////////////////////////////////////
	// TIME TO ENCHANT - Achievement
	/////////////////////////////////////
	
	@EventHandler
	public void SkyGridonEnchantItemEvent(EnchantItemEvent e) {
		if (!(e.getEnchantsToAdd().values().contains(4) || e.getEnchantsToAdd().values().contains(5)))
			return;		
		SkyGridPlayerManager.awardAchievement(e.getEnchanter(), SGAIDENTIFIER.TIME_TO_ENCHANT);
	}

}
