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


final class SGAInfiniteWaterSource extends IAchievementNP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Infinite Water Source?";
	
	public SGAInfiniteWaterSource(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}
	
	@Override
	public Byte getId() {
		return SGAIDENTIFIER.INFINITE_WATER_SOURCE;
	}
	
	@Override
	protected String getName() {
		return SGAInfiniteWaterSource.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.WATER_BUCKET, 1);
	}
	
	private static class SGAListener implements Listener {
	
		@EventHandler
		public void onCraftItem(CraftItemEvent e) {		
			switch (e.getRecipe().getResult().getType()) {
			
				/////////////////////////////////////////////
				//	ACHIEVEMENT: INFINITE_WATER_SOURCE
				/////////////////////////////////////////////
			
				case WATER_BUCKET: {
					e.getInventory().setMatrix(new ItemStack[e.getInventory().getMatrix().length]);
					if (e.getWhoClicked() instanceof Player) {
						SkyGridPlayerManager.awardAchievement((Player)e.getWhoClicked(), SGAIDENTIFIER.INFINITE_WATER_SOURCE);
					}
					break;
				}
				default: 
					break;
			}			
		}
	}

}
