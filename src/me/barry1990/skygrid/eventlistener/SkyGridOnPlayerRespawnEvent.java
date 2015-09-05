package me.barry1990.skygrid.eventlistener;

import java.util.Random;

import me.barry1990.skygrid.sql.SkyGridSQL;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;


public final class SkyGridOnPlayerRespawnEvent implements Listener {	
	
	///////////////////////////////////////
	// RESPAWN AT SPAWNPOINT
	///////////////////////////////////////
	
	@EventHandler
	public void onPlayerPortalEvent(PlayerRespawnEvent e) {
		Location loc = SkyGridSQL.sharedInstance().getHome(e.getPlayer(), SkyGridSQL.SPAWN_POINT);
		
		if (loc != null) {
			e.setRespawnLocation(loc);
		} else {
			
			Player player = e.getPlayer();
			Random random = new Random();
			double x,y,z;
			x = (random.nextInt(1500)-750) * 4 + 1.5;
			z = (random.nextInt(1500)-750) * 4 + 1.5;
			y = player.getWorld().getEnvironment() == Environment.NORMAL ? random.nextInt(8) * 4 + 174 : 255;
			
			BarrysLogger.info(this,"x",x);
			BarrysLogger.info(this,"y",y);
			BarrysLogger.info(this,"z",z);
			
			loc = new Location(player.getWorld(), x, y, z);
			SkyGridSQL.sharedInstance().addHome(player, loc, SkyGridSQL.SPAWN_POINT);
			
		}
	}

}
