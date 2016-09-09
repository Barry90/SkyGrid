package me.barry1990.skygrid.skygridplayer;

import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;
import me.barry1990.skygrid.achievement.SkyGridAchievements;

/**
 * SkyGridPlayer - This class hold all information and resources for a skygrid player
 * 
 * @author Barry1990
 */
final class SkyGridPlayer {

	private UUID			playeruuid;

	private IPlayerThreads	playerthreads;
	SkyGridAchievements		achievements;
	SkyGridAFK				afk;

	// ///////////////////////
	// Constructor
	// ///////////////////////

	/**
	 * Creates a new instance of SkyGridPlayer
	 * 
	 * @param playeruuid The UUID of the player
	 */
	public SkyGridPlayer(UUID playeruuid) {

		this.playeruuid = playeruuid;
		this.load();
	}

	// ///////////////////////
	// Load / Unload
	// ///////////////////////

	/**
	 * Load neccessary data for this player
	 */
	private void load() {

		this.achievements = new SkyGridAchievements(this.playeruuid);
	}

	/**
	 * Release Resources of this player
	 */
	void unload() {

		this.achievements.saveAchievementAndProgress();
		this.playerthreads.invalidateThreads();
		this.afk.dispose();
	}

	// ///////////////////////
	// PlayerThreads
	// ///////////////////////

	/**
	 * This Method creates the player threads
	 * 
	 * @see IPlayerThreads
	 */
	void createPlayerThreads() {

		this.playerthreads = SkyGrid.getLevelManager().getLevel().getPlayerThreads(this.playeruuid);
		if (this.playerthreads == null) {
			this.playerthreads = IPlayerThreads.EMPTY;
		}
	}

	// ///////////////////////
	// AFK
	// ///////////////////////

	/**
	 * This method creates the AFK handler for this player
	 * 
	 * @see SkyGridAFK
	 */
	void createAFK() {

		this.afk = new SkyGridAFK(this.playeruuid);
	}

}
