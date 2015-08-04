package me.barry1990.skygrid.eventlistener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
import me.barry1990.skygrid.achievement.SGAchievement;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class SkyGridOnPlayerJoin implements Listener {
	
	private List<UUID> list;
	 
	public SkyGridOnPlayerJoin() {

		this.list =  new ArrayList<UUID>();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		SkyGridAchievementManager.loadAchievementsForPlayer(player);
		
		if (!(list.contains(player.getUniqueId()))) {
			if (!SkyGridAchievementManager.playerHasAchievement(player, SGAchievement.SO_IT_BEGINS)) {
				
				list.add(player.getUniqueId());
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
}
