package me.barry1990.skygrid;

import java.util.HashMap;

import org.bukkit.entity.Player;


final public class SkyGridThreadManager {
	
	final private static class PlayerThread {

		private NetherWarningThread netherwarning;
		private EndWarningThread endwarning;
		
		public PlayerThread(Player player) {
			this.netherwarning = new NetherWarningThread(player, true, 5000);
			this.netherwarning.start();
			this.endwarning = new EndWarningThread(player, true, 3000);
			this.endwarning.start();
		}
		
		public void invalidateThreads() {
			
//			this.netherwarning.softstop();
//			this.endwarning.softstop();
			
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


