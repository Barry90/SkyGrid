package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


final class SkyGridAFKThread extends BukkitRunnable {
	
	private UUID playeruuid;
	
	 
	public SkyGridAFKThread(UUID playeruuid) {

		this.playeruuid = playeruuid;
	}
	

	@Override
	public void run() {
		Player player = Bukkit.getServer().getPlayer(this.playeruuid);
		if (player == null) 
			return;
		
		BarrysLogger.info(this,"checkAFK called");
		//get AFK-status		
		SkyGridAFK afk = SkyGridPlayerManager.getSkyGridAFK(this.playeruuid);
		
		if (!afk.isAFK()) {
		
			//get AFKLocation for easy access
			Location lastposition = afk.getLastLocation();
			Location newposition = player.getLocation();
			
			//check location
			if (lastposition == null) {
				SkyGridPlayerManager.setPlayerIsBack(this.playeruuid, newposition);
				return;
			}
			
			//check same world
			if (lastposition.getWorld().equals(newposition.getWorld())) {
	
				// check distance			
				if (lastposition.distanceSquared(newposition) < 9) {
					SkyGridPlayerManager.setPlayerAFK(this.playeruuid, newposition, false);				
				}
				
			} else {
				
				//player changed world
				SkyGridPlayerManager.setPlayerIsBack(this.playeruuid, newposition);
				return;
			}
		
		} 
		
	}
	
}
