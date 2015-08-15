package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SkyGridAchievementManager {
	
	private static HashMap<UUID, SkyGridAchievements> pa; //playerachievements
	
	static {
		SkyGridAchievementManager.pa = new HashMap<UUID, SkyGridAchievements>();
	}
	
	//////////////////////////////////////////////
	// STANDART ACHIEVEMENT HANDLING
	//////////////////////////////////////////////
	
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
	
	//////////////////////////////////////////////
	// PRECIFIC ACHIEVEMENT HANDLING
	//////////////////////////////////////////////
	
	/* The Wood-Maniac-Achievements */
	
	public static void addMaterialForWoodManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).addMaterialToWoodManiac(m);
	}
	
	/* The Stone-Maniac-Achievements */
	
	public static void addMaterialForStoneManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).addMaterialToStoneManiac(m);
	}
	
	/* The Iron-Maniac-Achievements */
	
	public static void addMaterialForIronManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).addMaterialToIronManiac(m);
	}
	
	/* The Gold-Maniac-Achievements */
	
	public static void addMaterialForGoldManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).addMaterialToGoldManiac(m);
	}
	
	/* The Diamond-Maniac-Achievements */
	
	public static void addMaterialForDiamondManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).addMaterialToDiamondManiac(m);
	}
	
	/* The Nether Cleaner-Achievement */
	
	public static void addNetherCleanerProgress(Player player) {
		pa.get(player.getUniqueId()).addNetherCleanerProgress();
	}
	

}
