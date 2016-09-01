package me.barry1990.skygrid.skygridplayer;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;
import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.utils.BarrysLogger;

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
		
		// player is already unloaded when player leaves while recreating world
		if (players.get(player.getUniqueId()) == null)
			return;
		
		players.get(player.getUniqueId()).unload();
		players.remove(player.getUniqueId());
	}
	
	/////////////////////////
	// ACHIEVEMENTS
	/////////////////////////
	
	public static void awardAchievement(Player player, SGAIDENTIFIER SGA_ID) {
		SkyGridPlayerManager.awardAchievement(player.getUniqueId(), SGA_ID);
	}
	
	public static void awardAchievement(UUID playeruuid, SGAIDENTIFIER SGA_ID) {
		players.get(playeruuid).achievements.award(SGA_ID);
	}
	
	public static boolean playerHasAchievementWithID(Player player, SGAIDENTIFIER SGA_ID) {
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
	
	public static void addProgressForAchievement(Player player, SGAIDENTIFIER id, Object... values) {
		players.get(player.getUniqueId()).achievements.addProgress(id, values);
	}
	
	/////////////////////////
	// AFK
	/////////////////////////
	
	public static void toggleAFK(Player player, Location loc) {
		SkyGridPlayer p = players.get(player.getUniqueId());
		if (p != null)
			p.afk.toggleAFK(loc);
		else
			BarrysLogger.error("SkyGridPlayerManager:toggleAFK(Player player, Location loc): player not found");
	}
	
	public static SkyGridAFK getSkyGridAFK(UUID playeruuid) {
		SkyGridPlayer p = players.get(playeruuid);
		return p != null ? p.afk : null;
	}
	
	public static void setPlayerIsBack(UUID playeruuid, Location loc) {
		SkyGridPlayer p = players.get(playeruuid);
		if (p != null)
			p.afk.setPlayerIsBack(loc);
		else
			BarrysLogger.error("SkyGridPlayerManager:setPlayerIsBack(Player player, Location loc): player not found");
	}
	
	public static void setPlayerAFK(UUID playeruuid, Location loc, boolean fromcommand) {
		SkyGridPlayer p = players.get(playeruuid);
		if (p != null)
			p.afk.setPlayerAFK(loc, fromcommand);
		else
			BarrysLogger.error("SkyGridPlayerManager:setPlayerAFK(UUID playeruuid, Location loc, boolean fromcommand): player not found");
	}
}
