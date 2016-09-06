package me.barry1990.skygrid.skygridplayer;

import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;
import me.barry1990.skygrid.achievement.SkyGridAchievements;


final class SkyGridPlayer {
	
	private UUID playeruuid;
	

	private IPlayerThreads playerthreads;
	SkyGridAchievements achievements;
	SkyGridAFK afk;
	
	/////////////////////////
	// Constructor
	/////////////////////////
	
	public SkyGridPlayer(UUID playeruuid) {

		this.playeruuid = playeruuid;
		this.load();
	}
	
	/////////////////////////
	// Load / Unload
	/////////////////////////
	
	private void load() {
		this.achievements = new SkyGridAchievements(this.playeruuid);
	}
	
	void unload() {
		this.achievements.saveAchievementAndProgress();
		this.playerthreads.invalidateThreads();
		this.afk.dispose();
	}
	
	/////////////////////////
	// PlayerThreads
	/////////////////////////
	
	void createPlayerThreads() {
		this.playerthreads = SkyGrid.sharedInstance().getLevelManager().getLevel().getPlayerThreads(this.playeruuid);
		if (this.playerthreads == null) {
			this.playerthreads = IPlayerThreads.EMPTY;
		}
	}
	
	/////////////////////////
	// AFK
	/////////////////////////	
	
	void createAFK() {
		this.afk = new SkyGridAFK(this.playeruuid);
	}
	
}
