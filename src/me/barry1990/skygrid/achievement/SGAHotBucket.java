package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;


class SGAHotBucket extends IAchievementNP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Hot Bucket";

	public SGAHotBucket(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.HOT_BUCKET;
	}

	@Override
	protected String getName() {
		return SGAHotBucket.name;
	}
	
	private static class SGAListener implements Listener {
		
		@EventHandler
		public void onCraftItem(CraftItemEvent e) {		
			switch (e.getRecipe().getResult().getType()) {
				
				case LAVA_BUCKET: {
					if (e.getWhoClicked() instanceof Player) {
						SkyGridPlayerManager.awardAchievement((Player)e.getWhoClicked(), SGAIDENTIFIER.HOT_BUCKET);
					}
					break;
				}

				default:
					break;
			}
		}
	}

}
