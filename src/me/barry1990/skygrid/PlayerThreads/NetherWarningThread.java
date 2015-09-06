package me.barry1990.skygrid.PlayerThreads;

import me.barry1990.skygrid.TitleManager;
import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class NetherWarningThread extends BukkitRunnable {
	
	private Player player;
	
	public NetherWarningThread(Player player ) {
		this.player = player;
	}

	@Override
	public void run() {
		
		if (!Bukkit.getServer().getOnlinePlayers().contains(this.player)) {
			BarrysLogger.info(this,"player not found");	
			return;
		}
		
		if (!SkyGridAchievementManager.playerHasAchievementWithID(this.player, SGAIDENTIFIER.GO_DEEPER)) {
		
			double playerY = player.getLocation().getBlockY();
			
			if (playerY < 124 && playerY >= 120) {				
				TitleManager.sendActionBar(this.player, "§6Warning: Do not go deeper!");
				return;
			}
			if (playerY < 120 && playerY >=  48) {	
				
				TitleManager.sendTitles(this.player, "", "§4Its too hot!", 10, 15, 10);
				this.player.damage(2);
				
			}
		}
		
		/*
		TitleManager.sendTitles(this.player, "asdaasda", "asdadasdsad", 5, 5, 5);
		TitleManager.sendHeaderAndFooter(this.player, "HEADER", "FOOTER");
		TitleManager.sendActionBar(this.player, "No §4RED §rNo §lFETT §rNo §kasdas");
		*/	
		
	}
	
}
