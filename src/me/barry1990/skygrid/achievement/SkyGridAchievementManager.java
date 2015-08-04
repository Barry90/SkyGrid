package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class SkyGridAchievementManager {
	
	private static HashMap<UUID, SkyGridAchievements> pa; //playerachievements
	
	static {
		SkyGridAchievementManager.pa = new HashMap<UUID, SkyGridAchievements>();
	}
	
	public static void loadAchievementsForPlayer(Player player) {	
		
		UUID uuid = player.getUniqueId();		
		SkyGridAchievements sga = new SkyGridAchievements(uuid);
		SkyGridAchievementManager.pa.put(uuid, sga);		
		
	}
	
	public static void closeAchievementsForPlayer(Player player) {
		pa.remove(player.getUniqueId());			
	}
	
	public static void addAchievementForPlayer(Player player, SGAchievement a) {
		pa.get(player.getUniqueId()).addAchievement(a);
	}
	
	public static boolean playerHasAchievement(Player player, SGAchievement a) {
		return pa.get(player.getUniqueId()).constainsAchievement(a);
	}
	
	

}
