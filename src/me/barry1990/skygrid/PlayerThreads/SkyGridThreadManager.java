package me.barry1990.skygrid.PlayerThreads;

import java.util.HashMap;

import me.barry1990.skygrid.SkyGrid;

import org.bukkit.entity.Player;

public final class SkyGridThreadManager {
	
	private final static class PlayerThread {

		private NetherWarningThread netherwarning;
		private EndWarningThread endwarning;
		
		public PlayerThread(Player player) {
			this.netherwarning = new NetherWarningThread(player);
			this.netherwarning.runTaskTimer(SkyGrid.sharedInstance(), 0, 100);
			this.endwarning = new EndWarningThread(player);
			this.endwarning.runTaskTimer(SkyGrid.sharedInstance(), 0, 60);
		}
		
		public void invalidateThreads() {
			this.netherwarning.cancel();
			this.endwarning.cancel();			
		}
		
	}
	
	private static HashMap<Player, PlayerThread> map; 
	
	static {
		map = new HashMap<Player, SkyGridThreadManager.PlayerThread>();
	}
	
	public static void addPlayerThread(Player p) {
		
		map.put(p, new PlayerThread(p));
		
	}
	
	public static void invalidateThreadsForPlayer(Player p) {
		map.get(p).invalidateThreads();
		map.remove(p);
	}

}


