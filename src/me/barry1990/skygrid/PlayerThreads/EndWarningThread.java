package me.barry1990.skygrid.PlayerThreads;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.TitleManager;
import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


final class EndWarningThread extends BukkitRunnable {
	
	private Player player;

	public EndWarningThread(Player player) {
		this.player = player;
	}


	@Override
	public void run() {
		
		if (!Bukkit.getServer().getOnlinePlayers().contains(this.player)) {
			BarrysLogger.info(this,"player not found");
			return;
		}
					
		if (!SkyGridAchievementManager.playerHasAchievementWithID(this.player, SGAIDENTIFIER.GO_EVEN_DEEPER)) {
			
			double playerY = player.getLocation().getBlockY();
			
			if (playerY < 52 && playerY >= 48) {				
				TitleManager.sendTitles(this.player, "", "§4WARNING", 5, 10, 5);				
			}
			if (playerY < 48) {	
				//this.player.getVelocity().
				TitleManager.sendTitles(this.player, "§fRROOOOAAAAARRR", "§f§kGet killed by Enderdragon", 5, 30, 5);
				this.player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.5f);
				this.player.damage(2000);						
			}
		}
		
		/*
		TitleManager.sendTitles(this.player, "asdaasda", "asdadasdsad", 5, 5, 5);
		TitleManager.sendHeaderAndFooter(this.player, "HEADER", "FOOTER");
		TitleManager.sendActionBar(this.player, "No §4RED §rNo §lFETT §rNo §kasdas");
		*/
		
	}
	
}
