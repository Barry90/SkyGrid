package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * SkyGridAFK - This class handles the afk state of a player
 * 
 * @author Barry1990
 */
public final class SkyGridAFK {

	private static final String	IS_AFK_MSG	= "§f *§8  %s is now afk.";
	private static final String	IS_BACK_MSG	= "§f *§8  %s is back.";
	private static long			idletime	= 180 * 20;

	private UUID				playeruuid;
	private Location			afkLocation;
	private Location			lastLocation;
	private boolean				isAFK;
	private SkyGridAFKThread	afkThread;

	// ///////////////////////
	// Constructor
	// ///////////////////////

	/**
	 * Creates a new instance of SkyGridAFK
	 * 
	 * @param playeruuid The uuid of a player
	 */
	public SkyGridAFK(UUID playeruuid) {

		this.isAFK = false;
		this.afkLocation = null;
		this.lastLocation = Bukkit.getServer().getPlayer(playeruuid).getLocation();
		this.playeruuid = playeruuid;
		this.startAFKTimer();

	}

	// ///////////////////////
	// Getter / Setter
	// ///////////////////////

	/**
	 * @return The last active Location of a player
	 */
	public Location getLastLocation() {

		return lastLocation;
	}

	/**
	 * @return The location where the player is afk
	 */
	public Location getAfkLocation() {

		return afkLocation;
	}

	/**
	 * @return True if the player is afk
	 */
	public boolean isAFK() {

		return isAFK;
	}

	// ///////////////////////
	// Methods
	// ///////////////////////

	/**
	 * Sets the player afk
	 * 
	 * @param loc The current location of the player
	 * @param fromcommand True if the player used the command
	 */
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

	/**
	 * The player is no longer afk
	 * 
	 * @param loc The new location of the player
	 */
	public void setPlayerIsBack(Location loc) {

		if (this.isAFK) {
			BarrysLogger.info(this, "player is activ");
			Bukkit.getServer().broadcastMessage(String.format(IS_BACK_MSG, Bukkit.getServer().getPlayer(this.playeruuid).getName()));
		}
		this.lastLocation = loc;
		this.isAFK = false;
		this.restartAFKTimer();

	}

	/**
	 * Toggles the afk state
	 * 
	 * @param loc The current location of the player
	 */
	public void toggleAFK(Location loc) {

		if (this.isAFK)
			this.setPlayerIsBack(loc);
		else
			this.setPlayerAFK(loc, true);
	}

	/**
	 * Disposes all resources
	 */
	public void dispose() {

		this.cancelTimer();
		this.afkThread = null;
		this.playeruuid = null;
		this.afkLocation = null;
		this.lastLocation = null;
	}

	// ///////////////////////
	// Timer
	// ///////////////////////

	/**
	 * Restart the AFK timer
	 */
	private void restartAFKTimer() {

		this.cancelTimer();
		this.startAFKTimer();
	}

	/**
	 * Cancel the AFK timer
	 */
	private void cancelTimer() {

		try {
			this.afkThread.cancel();
		} catch (IllegalStateException e) {
		}
	}

	/**
	 * Start the AFK timer
	 */
	private void startAFKTimer() {

		this.afkThread = new SkyGridAFKThread(playeruuid);
		this.afkThread.runTaskLater(SkyGrid.sharedInstance(), idletime);
	}
}
