package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Location;


public final class SkyGridAFK {
	
	private static final String IS_AFK_MSG = "§f *§8  %s is now afk.";
	private static final String IS_BACK_MSG = "§f *§8  %s is back.";
	private static long idletime = 180 * 20;
	
	private UUID playeruuid;
	private Location afkLocation;
	private Location lastLocation;
	private boolean isAFK;
	private SkyGridAFKThread afkThread;
	
	/////////////////////////
	// Constructor
	/////////////////////////
	
	public SkyGridAFK(UUID playeruuid) {
		
		this.isAFK = false;
		this.afkLocation = null;
		this.lastLocation = Bukkit.getServer().getPlayer(playeruuid).getLocation();
		this.playeruuid = playeruuid;
		this.startAFKTimer();

	}
	
	/////////////////////////
	// Getter / Setter
	/////////////////////////
	
	
	public Location getLastLocation() {
		return lastLocation;
	}

	public Location getAfkLocation() {	
		return afkLocation;
	}
	
	public boolean isAFK() {	
		return isAFK;
	}

	/////////////////////////
	// Methods
	/////////////////////////
	
	public void setPlayerAFK(Location loc, boolean fromcommand) {
		if (fromcommand)
			this.cancelTimer();
		if (!this.isAFK) {
			BarrysLogger.info(this, "player is afk");
			Bukkit.getServer().broadcastMessage(String.format(IS_AFK_MSG, Bukkit.getServer().getPlayer(this.playeruuid).getName()));
			this.afkLocation = loc;		
		}
		this.isAFK = true;
	}
	
	public void setPlayerIsBack(Location loc) {
		if (this.isAFK) {
			BarrysLogger.info(this, "player is activ");
			Bukkit.getServer().broadcastMessage(String.format(IS_BACK_MSG, Bukkit.getServer().getPlayer(this.playeruuid).getName()));	
		}
		this.lastLocation = loc;
		this.isAFK = false;
		this.restartAFKTimer();

	}
	
		
	public void toggleAFK(Location loc) {
		if (this.isAFK)
			this.setPlayerIsBack(loc);
		else
			this.setPlayerAFK(loc, true);
	}
	
	public void dispose() {
		this.cancelTimer();
		this.afkThread = null;
		this.playeruuid = null;
		this.afkLocation = null;
		this.lastLocation = null;
	}
	
	/////////////////////////
	// Timer
	/////////////////////////	
	
	
	private void restartAFKTimer() {
		this.cancelTimer();
		this.startAFKTimer();
	}
	
	private void cancelTimer() {
		try {
			this.afkThread.cancel();
		} catch (IllegalStateException e) {}
	}
	
	private void startAFKTimer() {
		this.afkThread = new SkyGridAFKThread(playeruuid);
		this.afkThread.runTaskLater(SkyGrid.sharedInstance(), idletime);
	}
}
