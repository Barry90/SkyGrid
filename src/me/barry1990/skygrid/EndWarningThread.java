package me.barry1990.skygrid;

import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;


final class EndWarningThread extends Thread {
	
	private Player player;
	private boolean stopped;
	private boolean repeatable;
	private long delay;
	
	public EndWarningThread(Player player) {
		this(player, false, 0);
	}
	 
	public EndWarningThread(Player player, boolean repeatable, long delay ) {
		this.player = player;
		this.repeatable = repeatable;
		this.delay = repeatable ? delay : 0;
	}


	@Override
	public void run() {
		
		while (!this.stopped && this.repeatable)  {
			
			if (!Bukkit.getServer().getOnlinePlayers().contains(this.player)) {
				BarrysLogger.info(this,"player not found");
				this.stopped = true;
				break;				
			}
						
			if (!SkyGridAchievementManager.playerHasAchievementWithID(this.player, SGAIDENTIFIER.GO_EVEN_DEER)) {
				
				double playerY = player.getLocation().getBlockY();
				
				if (playerY < 52 && playerY >= 48) {				
					TitleManager.sendTitles(this.player, "", "§4WARNING", 5, 10, 5);				
				}
				if (playerY < 48) {	
					//this.player.getVelocity().
					TitleManager.sendTitles(this.player, "§fRROOOOAAAAARRR", "§f§kGet killed by Enderdragon", 5, 30, 5);
					this.player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.5f);
					try {
						sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.player.damage(2000);
					
					
				}
			} else {
				this.stopped = true;
				break;
			}
			
			/*
			TitleManager.sendTitles(this.player, "asdaasda", "asdadasdsad", 5, 5, 5);
			TitleManager.sendHeaderAndFooter(this.player, "HEADER", "FOOTER");
			TitleManager.sendActionBar(this.player, "No §4RED §rNo §lFETT §rNo §kasdas");
			*/
			
			if (this.repeatable){
				try {
					sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		}
		
	}
	
	public void softstop() {
		this.stopped = true;
	}

}
