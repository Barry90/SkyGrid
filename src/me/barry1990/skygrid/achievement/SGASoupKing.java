package me.barry1990.skygrid.achievement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;


final class SGASoupKing extends IAchievementWPCounter {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Soup King";

	public SGASoupKing(HashMap<SGAIDENTIFIER, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected short getProgressTarget() {
		return 75;
	}

	@Override
	protected SGAIDENTIFIER getId() {
		return SGAIDENTIFIER.SOUP_KING;
	}

	@Override
	protected String getName() {
		return SGASoupKing.name;
	}

	@Override
	protected ItemStack getAchievementItem() {
		return new ItemStack(Material.MUSHROOM_SOUP);
	}
	
	@Override
	protected List<String> getDescription() {
		
		return Arrays.asList("Who loves soups?");
	}
	
	private static class SGAListener implements Listener {
		
		@EventHandler
		public void onSGAListenerCraftItemEvent(CraftItemEvent e) {
			
			switch (e.getRecipe().getResult().getType()) {
				case MUSHROOM_SOUP:
				case RABBIT_STEW:
				case BEETROOT_SOUP: {
					SkyGridPlayerManager.addProgressForAchievement((Player)e.getWhoClicked(), SGAIDENTIFIER.SOUP_KING);
				}
				default:
					break;
					
			}
			

		}
		
	}

}
