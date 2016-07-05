package me.barry1990.skygrid.PlayerThreads;

import java.util.UUID;

public abstract class IPlayerThreads {
	
	public static IPlayerThreads EMPTY = new NoPlayerThreads(null);
	
	private static class NoPlayerThreads extends IPlayerThreads {
		public NoPlayerThreads(UUID playeruuid) {
			super(playeruuid);
		}

		@Override
		public void invalidateThreads() {}		
	}
	
	public IPlayerThreads(UUID playeruuid) {};
	
	public abstract void invalidateThreads();
	
}
