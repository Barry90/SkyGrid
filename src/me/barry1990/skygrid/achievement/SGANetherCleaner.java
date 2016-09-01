package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;


final class SGANetherCleaner extends IAchievementWPCounter {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Nether Cleaner";

	public SGANetherCleaner(HashMap<SGAIDENTIFIER, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected SGAIDENTIFIER getId() {
		return SGAIDENTIFIER.NETHER_CLEANER;
	}

	@Override
	protected String getName() {
		return SGANetherCleaner.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {
		return new ItemStack(Material.NETHERRACK, 1);
	}
	
	@Override
	protected short getProgressTarget() {
		return 400;
	}

	private static class SGAListener implements Listener {
		
		@EventHandler
		public void onEntityDeathEvent(EntityDeathEvent e) {
			
			if (e.getEntity().getKiller() != null) {
				
				switch (e.getEntityType()) {
					case PIG_ZOMBIE:
					case GHAST:
					case BLAZE: {
						SkyGridPlayerManager.addProgressForAchievement(e.getEntity().getKiller(), SGAIDENTIFIER.NETHER_CLEANER);
						break;
					}
					default:
						break;
				}
				
			}
		}
	}

}
