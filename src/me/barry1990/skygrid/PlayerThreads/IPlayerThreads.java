package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

/**
 * IPlayerThreads - This class provides a wrapper class for BukkitRunnables for a single player
 * 
 * @author Barry1990
 */
public abstract class IPlayerThreads {

	/**
	 * There are no task for the player
	 */
	public static IPlayerThreads	EMPTY	= new NoPlayerThreads(null);

	/**
	 * NoPlayerThreads - static class if there are no task for the player
	 * 
	 * @author Barry1990
	 */
	private static class NoPlayerThreads extends IPlayerThreads {

		public NoPlayerThreads(UUID playeruuid) {

			super(playeruuid);
		}

		@Override
		public void invalidateThreads() {

		}
	}

	/**
	 * Creates a new instance of IPlayerThreads
	 * 
	 * @param playeruuid The UUID of the player
	 */
	public IPlayerThreads(UUID playeruuid) {

	};

	/**
	 * Implement this method and stop all BukkitTasks for this player
	 */
	public abstract void invalidateThreads();

}
