package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public final class SkyGridOnBlockBreakEvent implements Listener {
	
	/////////////////////////////////////
	// GET RICH OR DIE TRYIN - Achievement
	/////////////////////////////////////
	
	@EventHandler
	public void SkyGridonBlockBreakEvent(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.DIAMOND_ORE) {
			SkyGridPlayerManager.awardAchievement(e.getPlayer(), SGAIDENTIFIER.GET_RICH_OR_DIE_TRYIN);
		}
	}
	
	
	
	

}
