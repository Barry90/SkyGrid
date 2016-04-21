package me.barry1990.skygrid.achievement;

import java.util.HashMap;
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

	public SGASoupKing(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected short getProgressTarget() {
		return 75;
	}

	@Override
	protected Byte getId() {
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
	
	private static class SGAListener implements Listener {
		
		@EventHandler
		public void onSGAVegetableMasterBlockBreakEvent(CraftItemEvent e) {
			
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
