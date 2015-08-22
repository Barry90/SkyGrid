package me.barry1990.skygrid.eventlistener;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;


public class SkyGridOnPlayerPortalEvent implements Listener {
	
	/*
	 *    [][][]	─────── p_x_max
	 *  []▒▒▒▒▒▒[]
	 *  []▒▒><▒▒[]		 >< dragon egg
	 *  []▒▒▒▒▒▒[]
	 *    [][][] 	─────── p_y_min
	 *
	 *  │        │
	 * 	p_z_min  │
	 * 	         p_z_max
	 * 
	 */
	
	@EventHandler
	public void onPlayerPortalEvent(PlayerPortalEvent e) {
		
		/* Get the location of the player */		
		Player p = e.getPlayer();			
		int x = p.getLocation().getBlockX();
		int y = p.getLocation().getBlockY();
		int z = p.getLocation().getBlockZ();		
		
		/* get a reference for the world for easy access */		
		World w = p.getWorld();		
		
		/* portal position variables */
		int p_x_min = 0;			
		int p_x_max = 0; 			
		int p_z_min = 0; 
		int p_z_max = 0;
		int p_y = 0;				
		
		/* we need to find an end portal block */
		boolean found = false;
		for (int ty = y; !found && (ty < y + 3) && (ty < 256); ty++) {
			for (int tx = x - 1; !found && tx < x + 2; tx++) {
				for (int tz = z - 1; tz < z + 2; tz++) {
					
					if (w.getBlockAt(tx,ty,tz).getType() == Material.ENDER_PORTAL) {
						p_y = ty;
						found = true;
						break;
					}
				
				}
			}		
		}		
		if (!found) {
			BarrysLogger.warn(this, "could not find a portal-block");
			return;
		}		
		BarrysLogger.info(this, "p_y",p_y);		
		
		/* lets find the portalframe for X direction */
		found = false;
		for (int i = x; i < x+6; i++) {
			Block b = p.getWorld().getBlockAt(i,p_y,z);
			if (b.getType() == Material.ENDER_PORTAL_FRAME) {
				p_x_max = i;
				p_x_min = i-4;
				found = true;
				break;
			}			
		}		
		if (!found) {
			BarrysLogger.warn(this, "could not find the portalframe for X direction");
			return;
		}
		BarrysLogger.info(this, "p_x_min",p_x_min);	
		BarrysLogger.info(this, "p_x_max",p_x_max);	
		
		
		/* lets find the portalframe for Y direction */
		found = false;
		for (int i = z; i < z+6; i++) {
			Block b = p.getWorld().getBlockAt(x,p_y,i);
			if (b.getType() == Material.ENDER_PORTAL_FRAME) {
				p_z_max = i;
				p_z_min = i-4;
				found = true;
				break;
			}			
		}		
		if (!found) {
			BarrysLogger.warn(this, "could not find the portalframe for Z direction");
			return;
		}	
		BarrysLogger.info(this, "p_z_min",p_z_min);	
		BarrysLogger.info(this, "p_z_max",p_z_max);	
		
		/* remove the endportal */
		for (int i = p_x_min; i <= p_x_max; i++) {
			for (int j = p_z_min; j <= p_z_max; j++) {
				if (((i == p_x_min) || (i == p_x_max)) && ((j == p_z_min) || (j == p_z_max))) {
					continue;
				}
				w.getBlockAt(i, p_y, j).setType(Material.AIR);
			}
		}
		/* set the dragon egg */
		w.getBlockAt(p_x_min+2, p_y, p_z_min+2).setType(Material.DRAGON_EGG);
		
		/* prevent the egg from falling down */
		if (p_y > 0 && w.getBlockAt(p_x_min+2, p_y-1, p_z_min+2).getType() == Material.AIR)
			w.getBlockAt(p_x_min+2, p_y-1, p_z_min+2).setType(Material.ENDER_STONE);

	}
}
