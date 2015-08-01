package me.barry1990.skygrid.eventlistener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class SkyGridOnPlayerJoin implements Listener {
	
	private List<UUID> list;
	 
	public SkyGridOnPlayerJoin() {

		this.list =  new ArrayList<UUID>();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if (!(list.contains(player.getUniqueId()))) {
			list.add(player.getUniqueId());
			player.sendMessage(ChatColor.GREEN + "Willkommen in SkyGrid");
			
			Random random = new Random();
			int x,y,z;
			x = (random.nextInt(1500)-750) * 4 + 1;
			z = (random.nextInt(1500)-750) * 4 + 1;
			y = 255;
			switch (player.getWorld().getEnvironment()) {
				case NORMAL : y = random.nextInt(16) * 4 + 66; break;
				case NETHER : y =  random.nextInt(16) * 4 + 2; break;
				case THE_END : y = 255;
			}
			Location loc = new Location(player.getWorld(), x, y, z);
			//System.out.printf("%d %d %d", x, y, z);
			player.teleport(loc);
		}
		
		
	}
}
