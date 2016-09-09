package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * SkyGridAFKThread - This class is used to detect if a player if afk
 * 
 * @author Barry1990
 */
final class SkyGridAFKThread extends BukkitRunnable {
	
	private UUID playeruuid;
	
	/**
	 * Creates a new instance of SkyGridAFKThread
	 * @param playeruuid The UUID of the player
	 */
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
		SkyGridAFK afk = SkyGrid.getPlayerManager().getSkyGridAFK(this.playeruuid);
		
		if (!afk.isAFK()) {
		
			//get AFKLocation for easy access
			Location lastposition = afk.getLastLocation();
			Location newposition = player.getLocation();
			
			//check location
			if (lastposition == null) {
				SkyGrid.getPlayerManager().setPlayerIsBack(this.playeruuid, newposition);
				return;
			}
			
			//check same world
			if (lastposition.getWorld().equals(newposition.getWorld())) {
	
				// check distance			
				if (lastposition.distanceSquared(newposition) < 9) {
					SkyGrid.getPlayerManager().setPlayerAFK(player);				
				}
				
			} else {
				
				//player changed world
				SkyGrid.getPlayerManager().setPlayerIsBack(this.playeruuid, newposition);
				return;
			}
		
		} 
		
	}
	
}
