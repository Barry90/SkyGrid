package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;


final class SGASoItBegins extends IAchievementNP implements Listener {
	
private static final String name = "So it begins";

	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	public SGASoItBegins(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	public Byte getId() {
		return SGAIDENTIFIER.SO_IT_BEGINS;
	}
	
	@Override
	protected String getName() {
		return SGASoItBegins.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.GRASS, 1);
	}
	
	private static class SGAListener implements Listener {
		
		@EventHandler (ignoreCancelled=true)
		public void onPlayerJoin(PlayerJoinEvent event) {
			Player player = event.getPlayer();		
			if (!SkyGridPlayerManager.playerHasAchievementWithID(player, SGAIDENTIFIER.SO_IT_BEGINS)) {
				
				player.sendMessage(ChatColor.GREEN + "Willkommen in SkyGrid");
				SkyGridPlayerManager.awardAchievement(player, SGAIDENTIFIER.SO_IT_BEGINS);
				
			} else {
				player.sendMessage(ChatColor.GREEN + "Willkommen zurück");
			}

		}
	}
	
	

}
