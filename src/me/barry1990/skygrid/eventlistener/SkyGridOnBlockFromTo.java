package me.barry1990.skygrid.eventlistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;


public class SkyGridOnBlockFromTo implements Listener {
	
	// to prevent water and lava from flowing
	@EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
      int id = event.getBlock().getTypeId();
      if(id == 8 || id == 9 || id == 10 || id == 11) {
        event.setCancelled(true);
      }
    }

}
