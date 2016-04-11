package me.barry1990.skygrid.skygridplayer;

import java.util.UUID;

import me.barry1990.skygrid.PlayerThreads.PlayerThreads;
import me.barry1990.skygrid.PlayerThreads.SkyGridAFK;
import me.barry1990.skygrid.achievement.SkyGridAchievements;


final class SkyGridPlayer {
	
	private UUID playeruuid;
	

	SkyGridAchievements achievements;
	PlayerThreads playerthreads;
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
		this.playerthreads = new PlayerThreads(this.playeruuid);
	}
	
	/////////////////////////
	// AFK
	/////////////////////////	
	
	void createAFK() {
		this.afk = new SkyGridAFK(this.playeruuid);
	}
	
}
