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
		pa.get(player.getUniqueId()).saveAchievementAndProgress();
		pa.remove(player.getUniqueId());			
	}
	
	public static void award(Player player, byte SGA_ID) {
		pa.get(player.getUniqueId()).award(SGA_ID);
	}
	
	public static boolean playerHasAchievementWithID(Player player, byte SGA_ID) {
		return pa.get(player.getUniqueId()).hasAchievementWithID(SGA_ID);
	}
	
	public static SkyGridAchievements getAchievementsForPlayer(Player player) {
		return pa.get(player.getUniqueId());
	}

	//////////////////////////////////////////////
	// PACKAGE HANDLING
	//////////////////////////////////////////////
	
	static void saveAchievementsForPlayer(UUID playeruuid) {
		pa.get(playeruuid).saveAchievementAndProgress();
	}
	
	//////////////////////////////////////////////
	// SPECIFIC ACHIEVEMENT HANDLING
	//////////////////////////////////////////////
	
	
	/* The Wood-Maniac-Achievements */
	
	public static void addMaterialForWoodManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).getWoodManiac().addMaterial(m);
	}
	
	/* The Stone-Maniac-Achievements */
	
	public static void addMaterialForStoneManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).getStoneManiac().addMaterial(m);
	}
	
	/* The Iron-Maniac-Achievements */
	
	public static void addMaterialForIronManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).getIronManiac().addMaterial(m);
	}
	
	/* The Gold-Maniac-Achievements */
	
	public static void addMaterialForGoldManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).getGoldManiac().addMaterial(m);
	}
	
	/* The Diamond-Maniac-Achievements */
	
	public static void addMaterialForDiamondManiac(Player player,Material m) {
		pa.get(player.getUniqueId()).getDiamondManiac().addMaterial(m);
	}
	
	/* The Nether Cleaner-Achievement */
	
	public static void addNetherCleanerProgress(Player player) {
		pa.get(player.getUniqueId()).getNetherCleaner().addProgress();
	}
	

}
