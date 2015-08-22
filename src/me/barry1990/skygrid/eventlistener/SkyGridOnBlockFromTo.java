package me.barry1990.skygrid.eventlistener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;


public final class SkyGridOnBlockFromTo implements Listener {
	
	// to prevent dragonegg from teleporting
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		Material m = event.getBlock().getType();
		if (m == Material.DRAGON_EGG) {
			event.setCancelled(true);
		}
	}

}
