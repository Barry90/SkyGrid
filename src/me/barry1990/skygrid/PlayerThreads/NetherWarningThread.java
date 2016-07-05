package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

import me.barry1990.skygrid.TitleManager;
import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class NetherWarningThread extends BukkitRunnable {
	
	private UUID playeruuid;
	
	public NetherWarningThread(UUID playeruuid) {
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
		
		if (!SkyGridPlayerManager.playerHasAchievementWithID(player, SGAIDENTIFIER.GO_DEEPER)) {
		
			double playerY = player.getLocation().getBlockY();
			
			if (playerY < 124 && playerY >= 120) {				
				TitleManager.sendActionBar(player, "§6Warning: Do not go deeper!");
				return;
			}
			if (playerY < 120 && playerY >=  48) {	
				
				TitleManager.sendTitles(player, "", "§4Its too hot!", 10, 15, 10);
				player.damage(3);
				
			}
		}
		
		/*
		TitleManager.sendTitles(this.player, "asdaasda", "asdadasdsad", 5, 5, 5);
		TitleManager.sendHeaderAndFooter(this.player, "HEADER", "FOOTER");
		TitleManager.sendActionBar(this.player, "No §4RED §rNo §lFETT §rNo §kasdas");
		*/	
		
	}
	
}
