package me.barry1990.skygrid.eventlistener;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;


public class SkyGridOnCraftItem implements Listener {

	@EventHandler
	public void OnCraftItem(CraftItemEvent e) {
		if (e.getRecipe().getResult().getType() == Material.WATER_BUCKET) {
			e.getInventory().setMatrix(new ItemStack[e.getInventory().getMatrix().length]);
			BarrysLogger.info(this, "cleared CraftInventoryMatrix");
		}
				
	}
}
