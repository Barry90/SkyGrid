package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;

public final class PlayerThreads {

	private NetherWarningThread netherwarning;
	private EndWarningThread endwarning;
	
	public PlayerThreads(UUID playeruuid) {
		this.netherwarning = new NetherWarningThread(playeruuid);
		this.netherwarning.runTaskTimer(SkyGrid.sharedInstance(), 0, 100);
		this.endwarning = new EndWarningThread(playeruuid);
		this.endwarning.runTaskTimer(SkyGrid.sharedInstance(), 0, 60);
	}
	
	public void invalidateThreads() {
		this.netherwarning.cancel();
		this.endwarning.cancel();			
	}
	
}
