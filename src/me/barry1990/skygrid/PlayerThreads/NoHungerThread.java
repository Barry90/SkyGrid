package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public final class NoHungerThread extends BukkitRunnable {

	private UUID playeruuid;
	
	public NoHungerThread(UUID playeruuid) {
		this.playeruuid = playeruuid;
	}
	
	@Override
	public void run() {

		Player player = Bukkit.getServer().getPlayer(this.playeruuid);
		
		if (player == null) {
			BarrysLogger.info(this,"player not found");	
			return;
		}
		
		if (player.getGameMode() != GameMode.SURVIVAL)
			return;
		
		player.setFoodLevel(18);
		player.setSaturation(10);
	}

}
