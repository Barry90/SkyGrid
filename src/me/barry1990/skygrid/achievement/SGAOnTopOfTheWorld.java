package me.barry1990.skygrid.achievement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;


final class SGAOnTopOfTheWorld extends IAchievementNP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "On Top Of The World";

	public SGAOnTopOfTheWorld(HashMap<SGAIDENTIFIER, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected SGAIDENTIFIER getId() {
		return SGAIDENTIFIER.ON_TOP_OF_THE_WORLD;
	}

	@Override
	protected String getName() {
		return SGAOnTopOfTheWorld.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.FEATHER, 1);
	}
	
	@Override
	protected List<String> getDescription() {
		
		return Arrays.asList("Reach for the Sky.");
	}
	
	private static class SGAListener implements Listener {
		
		@EventHandler
		public void SGAOnTopOfTheWorldPlayerMoveEvent(PlayerMoveEvent e) {
			if (e.getTo().getBlockY() >= 254)
				SkyGridPlayerManager.awardAchievement(e.getPlayer(), SGAIDENTIFIER.ON_TOP_OF_THE_WORLD);			
		}
	}
}
