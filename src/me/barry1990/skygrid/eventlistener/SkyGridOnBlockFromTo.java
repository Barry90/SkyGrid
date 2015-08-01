package me.barry1990.skygrid.eventlistener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;


public class SkyGridOnBlockFromTo implements Listener {
	
	// to prevent water and lava from flowing
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		Material m = event.getBlock().getType();
		if(m == Material.WATER || m == Material.STATIONARY_WATER || m == Material.LAVA || m == Material.STATIONARY_LAVA) {
			event.setCancelled(true);
		}
	}

}
