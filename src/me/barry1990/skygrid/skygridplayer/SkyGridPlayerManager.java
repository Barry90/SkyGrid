package me.barry1990.skygrid.skygridplayer;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * SkyGridPlayerManager - This class handles all SkyGridPlayer
 * 
 * @author Barry1990
 */
public final class SkyGridPlayerManager {

	private HashMap<UUID, SkyGridPlayer>	players;

	public SkyGridPlayerManager() {

		this.players = new HashMap<UUID, SkyGridPlayer>();
	}

	/**
	 * Loads and registers the given player
	 * 
	 * @param player The player
	 */
	public void load(Player player) {

		UUID playeruuid = player.getUniqueId();
		SkyGridPlayer sp = new SkyGridPlayer(playeruuid);
		this.players.put(playeruuid, sp);
	}

	/**
	 * Additinal required setup after the player joined
	 * 
	 * @param player The Player that joined SkyGrid
	 */
	public void loadAfterPlayerJoin(Player player) {

		this.players.get(player.getUniqueId()).createPlayerThreads();
		this.players.get(player.getUniqueId()).createAFK();
	}

	/**
	 * Release all resources of this SkyGridPlayer
	 * 
	 * @param player The player that to unload
	 */
	public void unload(Player player) {

		// player is already unloaded when player leaves while recreating world
		if (this.players.get(player.getUniqueId()) == null)
			return;

		this.players.get(player.getUniqueId()).unload();
		this.players.remove(player.getUniqueId());
	}

	// ///////////////////////
	// ACHIEVEMENTS
	// ///////////////////////

	/**
	 * Awards an SkyGrid achievement for a player
	 * 
	 * @param player The player that got the achievement
	 * @param id The unique id of the achievement that should be awarded
	 */
	public void awardAchievement(Player player, byte id) {

		this.awardAchievement(player.getUniqueId(), id);
	}

	/**
	 * Awards an SkyGrid achievement for a player
	 * 
	 * @param playeruuid The UUID of player that got the achievement
	 * @param id The unique id of the achievement that should be awarded
	 */
	public void awardAchievement(UUID playeruuid, byte id) {

		this.players.get(playeruuid).achievements.award(id);
	}

	/**
	 * Checks if a player has an achievement with the given id
	 * 
	 * @param player The Player
	 * @param id The unique id of the achievement
	 * @return True if the player got the achievement
	 */
	public boolean playerHasAchievementWithID(Player player, byte id) {

		return this.players.get(player.getUniqueId()).achievements.hasAchievementWithID(id);
	}

	/**
	 * Save the achievement for the player
	 * 
	 * @param playeruuid The UUID of the player
	 */
	public void saveAchievementsForPlayer(UUID playeruuid) {

		this.players.get(playeruuid).achievements.saveAchievementAndProgress();
	}

	/**
	 * Get the number of awarded achievements from a player
	 * 
	 * @param player The olayer
	 * @return The number of awarded achievements
	 */
	public int getAchievementCountForPlayer(Player player) {

		return this.players.get(player.getUniqueId()).achievements.getAchievementCount();
	}

	/**
	 * Get the number of awarded achievements from a player
	 * 
	 * @param playeruuid The UUID of the player
	 * @return The number of awarded achievements
	 */
	public int getAchievementCountForPlayer(UUID playeruuid) {

		return this.players.get(playeruuid).achievements.getAchievementCount();
	}

	/**
	 * GEts the total number of achievements for this player
	 * 
	 * @param playeruuid The UUID of the player
	 * @return The total number of achievments
	 */
	public int getNumberOfAchievements(UUID playeruuid) {

		return this.players.get(playeruuid).achievements.getNumberOfAchievements();
	}

	/**
	 * Creates and return the inventory for achievement GUI for the given player
	 * 
	 * @param player The player
	 * @return The invetory for the achievement GUI
	 */
	public Inventory createAchievementGUIForPlayer(Player player) {

		return this.players.get(player.getUniqueId()).achievements.createAchievementGUI();
	}

	// ////////////////////////////////////////////
	// SPECIFIC ACHIEVEMENT HANDLING
	// ////////////////////////////////////////////

	/**
	 * Adds progress to an achievement for the given player.
	 * 
	 * @param player The player
	 * @param id The unique id of the achievement
	 * @param values
	 */
	public void addProgressForAchievement(Player player, byte id, Object... values) {

		this.players.get(player.getUniqueId()).achievements.addProgress(id, values);
	}

	// ///////////////////////
	// AFK
	// ///////////////////////

	/**
	 * Toggles the AFK state of a player
	 * 
	 * @param player The player
	 * @param loc The urrent location of the player
	 */
	public void toggleAFK(Player player, Location loc) {

		SkyGridPlayer p = this.players.get(player.getUniqueId());
		if (p != null)
			p.afk.toggleAFK(loc);
		else
			BarrysLogger.error("SkyGridPlayerManager:toggleAFK(Player player, Location loc): player not found");
	}

	/**
	 * Get the AFK handler for this player
	 * 
	 * @param playeruuid The UUID of the player
	 * @return The AFK handler for this player
	 */
	public SkyGridAFK getSkyGridAFK(UUID playeruuid) {

		SkyGridPlayer p = this.players.get(playeruuid);
		return p != null ? p.afk : null;
	}

	/**
	 * Sets the player no longer afk
	 * 
	 * @param playeruuid The UUID of the player
	 * @param loc The new Location of the player
	 */
	public void setPlayerIsBack(UUID playeruuid, Location loc) {

		SkyGridPlayer p = this.players.get(playeruuid);
		if (p != null)
			p.afk.setPlayerIsBack(loc);
		else
			BarrysLogger.error("SkyGridPlayerManager:setPlayerIsBack(Player player, Location loc): player not found");
	}

	/**
	 * Sets the player afk
	 * 
	 * @param player The player
	 */
	public void setPlayerAFK(Player player) {

		SkyGridPlayer p = this.players.get(player.getUniqueId());
		if (p != null)
			p.afk.setPlayerAFK(player.getLocation(), false);
		else
			BarrysLogger.error("SkyGridPlayerManager:setPlayerAFK(UUID playeruuid, Location loc, boolean fromcommand): player not found");
	}
}
