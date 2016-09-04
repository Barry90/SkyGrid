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


final class SGAInfiniteWaterSource extends IAchievementNP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Infinite Water Source?";
	
	public SGAInfiniteWaterSource(HashMap<SGAIDENTIFIER, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}
	
	@Override
	public SGAIDENTIFIER getId() {
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
	
	@Override
	protected List<String> getDescription() {
		
		return Arrays.asList("Craft Water.");
	}
	
	private static class SGAListener implements Listener {
		
		//private static ItemStack[] = new
	
		@EventHandler
		public void onCraftItem(CraftItemEvent e) {		
			switch (e.getRecipe().getResult().getType()) {
			
				/////////////////////////////////////////////
				//	ACHIEVEMENT: INFINITE_WATER_SOURCE
				/////////////////////////////////////////////
			
				case WATER_BUCKET: {
					e.getInventory().setMatrix(new ItemStack[]{
							new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR),
							new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR),
							new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR)});
					if (e.getWhoClicked() instanceof Player) {
						e.getWhoClicked().setItemOnCursor(e.getRecipe().getResult());
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
