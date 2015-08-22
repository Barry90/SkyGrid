package me.barry1990.skygrid.eventlistener;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


public final class SkyGridOnPlayerDeathEvent implements Listener {
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
				
		for (ItemStack is : e.getDrops()) {
			BarrysLogger.infoEnum(this, "Material", is.getType());
			if (is.getType() == Material.DRAGON_EGG) {
				e.setKeepInventory(true);
				BarrysLogger.info(this, "KeepInventory");
				//return;
			}
		}
	}

}
