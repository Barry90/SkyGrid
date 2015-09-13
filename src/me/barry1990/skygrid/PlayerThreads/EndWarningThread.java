package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

import me.barry1990.skygrid.TitleManager;
import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


final class EndWarningThread extends BukkitRunnable {
	
	private UUID playeruuid;

	public EndWarningThread(UUID playeruuid) {
		this.playeruuid = playeruuid;
	}


	@Override
	public void run() {
		
		Player player = Bukkit.getServer().getPlayer(this.playeruuid);
		
		if (player == null) {
			BarrysLogger.info(this,"player not found");
			return;
		}
					
		if (!SkyGridPlayerManager.playerHasAchievementWithID(player, SGAIDENTIFIER.GO_EVEN_DEEPER) && (player.getHealth() > 0)) {
			
			double playerY = player.getLocation().getBlockY();
			
			if (playerY < 52 && playerY >= 48) {				
				TitleManager.sendTitles(player, "", "§4WARNING", 5, 10, 5);				
			}
			if (playerY < 48) {	
				//this.player.getVelocity().
				TitleManager.sendTitles(player, "§fRROOOOAAAAARRR", "§f§kGet killed by Enderdragon", 5, 30, 5);
				player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.5f);
				player.damage(2000);						
			}
		}
		
		/*
		TitleManager.sendTitles(this.player, "asdaasda", "asdadasdsad", 5, 5, 5);
		TitleManager.sendHeaderAndFooter(this.player, "HEADER", "FOOTER");
		TitleManager.sendActionBar(this.player, "No §4RED §rNo §lFETT §rNo §kasdas");
		*/
		
	}
	
}
