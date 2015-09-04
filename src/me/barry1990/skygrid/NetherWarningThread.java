package me.barry1990.skygrid;

import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

final class NetherWarningThread extends Thread {
	
	private Player player;
	private boolean stopped;
	private boolean repeatable;
	private long delay;
	
	public NetherWarningThread(Player player) {
		this(player, false, 0);
	}
	 
	public NetherWarningThread(Player player, boolean repeatable, long delay ) {
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
			
			if (!SkyGridAchievementManager.playerHasAchievementWithID(this.player, SGAIDENTIFIER.GO_DEEPER)) {
			
				double playerY = player.getLocation().getBlockY();
				
				if (playerY < 124 && playerY >= 120) {				
					TitleManager.sendActionBar(this.player, "§6Warning: Do not go deeper!");				
				}
				if (playerY < 120 && playerY >=  48) {	
					
					TitleManager.sendTitles(this.player, "", "§4Its to hot!", 10, 15, 10);
					this.player.damage(2);
					
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
