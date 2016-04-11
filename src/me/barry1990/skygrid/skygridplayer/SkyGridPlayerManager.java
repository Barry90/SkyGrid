package me.barry1990.skygrid.skygridplayer;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


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
		SkyGridPlayerManager.awardAchievement(player.getUniqueId(), SGA_ID);
	}
	
	public static void awardAchievement(UUID playeruuid, byte SGA_ID) {
		players.get(playeruuid).achievements.award(SGA_ID);
	}
	
	public static boolean playerHasAchievementWithID(Player player, byte SGA_ID) {
		return players.get(player.getUniqueId()).achievements.hasAchievementWithID(SGA_ID);
	}
	
	public static void saveAchievementsForPlayer(UUID playeruuid) {
		players.get(playeruuid).achievements.saveAchievementAndProgress();
	}
	
	public static int getAchievementCountForPlayer(Player player) {
		return players.get(player.getUniqueId()).achievements.getAchievementCount();
	}
	
	public static Inventory createAchievementGUIForPlayer(Player player) {
		return players.get(player.getUniqueId()).achievements.createAchievementGUI();
	}
	
	//////////////////////////////////////////////
	// SPECIFIC ACHIEVEMENT HANDLING
	//////////////////////////////////////////////
	
	public static void addProgressForAchievement(Player player, byte id, Object... values) {
		players.get(player.getUniqueId()).achievements.addProgress(id, values);
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
