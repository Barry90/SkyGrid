package me.barry1990.skygrid.eventlistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


/**
 * SkyGridOnInventoryClickEvent - This class disables interaction with the achievement GUI
 * 
 * @author Barry1990
 */
public final class SkyGridOnInventoryClickEvent implements Listener {
	
	@EventHandler
	public void SkyGridonInventoryClickEvent(InventoryClickEvent e) {	
		if (e.getClickedInventory() == null || e.getClickedInventory().getTitle() == null)
			return;
		if (e.getClickedInventory().getTitle().equals("ACHIEVEMENTS§3")) {
			e.setCancelled(true);
		}

	}

}
