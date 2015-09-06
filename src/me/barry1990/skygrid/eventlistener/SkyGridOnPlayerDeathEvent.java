package me.barry1990.skygrid.eventlistener;

import java.util.Random;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class SkyGridOnPlayerDeathEvent implements Listener {
	
	////////////////////////////////////////////////
	// INVENTORY LOSS SYSTEM
	////////////////////////////////////////////////
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
				
		e.setKeepInventory(true);
		
		//dont loose items if the player has a dragon egg
		for (ItemStack is : e.getDrops()) {
			if (is.getType() == Material.DRAGON_EGG) {				
				BarrysLogger.info(this, "KeepInventory");
				return;
			}
		}
		
		//save one random item per playerlevel
		int level = e.getEntity().getLevel();
		Random random = new Random();
		while (level > 0 && !e.getDrops().isEmpty()) {
			e.getDrops().remove(random.nextInt(e.getDrops().size())).getType();
			level--;
		}
		
		//chance to save more items randomly
		while (random.nextBoolean() && !e.getDrops().isEmpty()) {
			e.getDrops().remove(random.nextInt(e.getDrops().size()));
		}	
		
		//drop items from inventory
		PlayerInventory inv = e.getEntity().getInventory();
		for (ItemStack item : e.getDrops()) {
			//TODO: use Bukkit scheduler
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), item);
			inv.remove(item);
		}
		
		//dont spawn exp 
		e.setDroppedExp(0);
	}

}
