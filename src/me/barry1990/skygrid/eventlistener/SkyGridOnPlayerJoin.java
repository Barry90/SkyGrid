package me.barry1990.skygrid.eventlistener;

import java.util.Random;

import me.barry1990.skygrid.achievement.SGAchievement;
import me.barry1990.skygrid.achievement.SkyGridAchievementManager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class SkyGridOnPlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		SkyGridAchievementManager.loadAchievementsForPlayer(player);
		
		if (!SkyGridAchievementManager.playerHasAchievement(player, SGAchievement.SO_IT_BEGINS)) {
			
			player.sendMessage(ChatColor.GREEN + "Willkommen in SkyGrid");
			
			Random random = new Random();
			int x,y,z;
			x = (random.nextInt(1500)-750) * 4 + 1;
			z = (random.nextInt(1500)-750) * 4 + 1;
			y = player.getWorld().getEnvironment() == Environment.NORMAL ? random.nextInt(8) * 4 + 174 : 255;
			
			Location loc = new Location(player.getWorld(), x, y, z);
			player.teleport(loc);

			SkyGridAchievementManager.addAchievementForPlayer(player, SGAchievement.SO_IT_BEGINS);
		}

		
		
	}
}
