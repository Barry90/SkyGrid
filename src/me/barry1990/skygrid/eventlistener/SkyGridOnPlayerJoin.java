package me.barry1990.skygrid.eventlistener;

import java.util.Random;


import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
//import me.barry1990.skygrid.achievement.SGAchievement;
import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;


public final class SkyGridOnPlayerJoin implements Listener {
	
	/* These events will be called in the below order */
	
	@EventHandler (ignoreCancelled=true, priority=EventPriority.HIGH)
	public void onPlayerLoginEvent(PlayerLoginEvent e) {
		BarrysLogger.info(this, "OnPlayerLoginEvent called");
		if (e.getResult() == Result.ALLOWED) {			
			SkyGridAchievementManager.loadAchievementsForPlayer(e.getPlayer());
		}
	}
	
	@EventHandler (ignoreCancelled=true, priority=EventPriority.HIGHEST)
	public void onPlayerSpawnLocationEvent(PlayerSpawnLocationEvent e) {
		BarrysLogger.info(this, "PlayerSpawnLocationEvent called");
		Player player = e.getPlayer();
		
		if (!SkyGridAchievementManager.playerHasAchievementWithID(player, SGAIDENTIFIER.SO_IT_BEGINS)) {
						
			Random random = new Random();
			double x,y,z;
			x = (random.nextInt(1500)-750) * 4 + 1.5;
			z = (random.nextInt(1500)-750) * 4 + 1.5;
			y = player.getWorld().getEnvironment() == Environment.NORMAL ? random.nextInt(8) * 4 + 174 : 255;
			
			Location loc = new Location(player.getWorld(), x, y, z);
			e.setSpawnLocation(loc);

		} 
	}
	
	@EventHandler (ignoreCancelled=true, priority=EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		BarrysLogger.info(this, "PlayerJoinEvent called");
		Player player = event.getPlayer();		
		if (!SkyGridAchievementManager.playerHasAchievementWithID(player, SGAIDENTIFIER.SO_IT_BEGINS)) {
			
			player.sendMessage(ChatColor.GREEN + "Willkommen in SkyGrid");
			SkyGridAchievementManager.award(player, SGAIDENTIFIER.SO_IT_BEGINS);
			
		} else {
			player.sendMessage(ChatColor.GREEN + "Willkommen zurück");
		}

	}
	
	@EventHandler (ignoreCancelled=true, priority=EventPriority.HIGHEST)
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		SkyGridAchievementManager.closeAchievementsForPlayer(e.getPlayer());
	}

}
