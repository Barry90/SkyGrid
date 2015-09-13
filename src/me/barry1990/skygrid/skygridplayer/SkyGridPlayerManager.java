package me.barry1990.skygrid.skygridplayer;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public final class SkyGridPlayerManager {
	
	private static HashMap<UUID, SkyGridPlayer> players;
	
	static {
		SkyGridPlayerManager.players = new HashMap<UUID, SkyGridPlayer>();
	}
	
	public static void load(Player player) {
		UUID playeruuid = player.getUniqueId();
		SkyGridPlayer sp = new SkyGridPlayer(playeruuid);
		SkyGridPlayerManager.players.put(playeruuid, sp);
	}
	
	public static void loadAfterPlayerJoin(Player player) {
		players.get(player.getUniqueId()).createPlayerThreads();
		players.get(player.getUniqueId()).createAFK();
	}
	
	public static void unload(Player player) {
		players.get(player.getUniqueId()).unload();
		players.remove(player.getUniqueId());
	}
	
	/////////////////////////
	// ACHIEVEMENTS
	/////////////////////////
	
	public static void awardAchievement(Player player, byte SGA_ID) {
		players.get(player.getUniqueId()).achievements.award(SGA_ID);
	}
	
	public static boolean playerHasAchievementWithID(Player player, byte SGA_ID) {
		return players.get(player.getUniqueId()).achievements.hasAchievementWithID(SGA_ID);
	}
	
	public static void saveAchievementsForPlayer(UUID playeruuid) {
		players.get(playeruuid).achievements.saveAchievementAndProgress();
	}
	
	//////////////////////////////////////////////
	// SPECIFIC ACHIEVEMENT HANDLING
	//////////////////////////////////////////////

	/* The Wood-Maniac-Achievements */

	public static void addMaterialForWoodManiac(Player player, Material m) {
		players.get(player.getUniqueId()).achievements.addMaterialForWoodManiac(m);
	}

	/* The Stone-Maniac-Achievements */

	public static void addMaterialForStoneManiac(Player player, Material m) {
		players.get(player.getUniqueId()).achievements.addMaterialForStoneManiac(m);
	}

	/* The Iron-Maniac-Achievements */

	public static void addMaterialForIronManiac(Player player, Material m) {
		players.get(player.getUniqueId()).achievements.addMaterialForIronManiac(m);
	}

	/* The Gold-Maniac-Achievements */

	public static void addMaterialForGoldManiac(Player player, Material m) {
		players.get(player.getUniqueId()).achievements.addMaterialForGoldManiac(m);
	}

	/* The Diamond-Maniac-Achievements */

	public static void addMaterialForDiamondManiac(Player player, Material m) {
		players.get(player.getUniqueId()).achievements.addMaterialForDiamondManiac(m);
	}

	/* The Nether Cleaner-Achievement */

	public static void addNetherCleanerProgress(Player player) {
		players.get(player.getUniqueId()).achievements.addNetherCleanerProgress();
	}

	
	/////////////////////////
	// AFK
	/////////////////////////
	
	public static void toggleAFK(Player player, Location loc) {
		players.get(player.getUniqueId()).afk.toggleAFK(loc);
	}
	
	public static SkyGridAFK getSkyGridAFK(UUID playeruuid) {
		return players.get(playeruuid).afk;
	}
	
	public static void setPlayerIsBack(UUID playeruuid, Location loc) {
		players.get(playeruuid).afk.setPlayerIsBack(loc);
	}
	
	public static void setPlayerAFK(UUID playeruuid, Location loc, boolean fromcommand) {
		players.get(playeruuid).afk.setPlayerAFK(loc, fromcommand);
	}
}
