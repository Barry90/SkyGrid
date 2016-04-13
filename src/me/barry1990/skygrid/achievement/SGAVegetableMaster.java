package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;


final class SGAVegetableMaster extends IAchievementWPCounter {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Vegetable Master";
	
	public SGAVegetableMaster(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.VEGETABLE_MASTER;
	}

	@Override
	protected String getName() {
		return SGAVegetableMaster.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {
		return new ItemStack(Material.GOLDEN_CARROT, 1);
	}
	
	@Override
	protected short getProgressTarget() {
		return 500;
	}

	private static class SGAListener implements Listener {
		
		@EventHandler
		public void onSGAVegetableMasterBlockBreakEvent(BlockBreakEvent e) {
			if (this.isHarvestableVegetable(e.getBlock().getState().getData())) {
				SkyGridPlayerManager.addProgressForAchievement(e.getPlayer(), SGAIDENTIFIER.VEGETABLE_MASTER);
			}
		}
		
		private boolean isHarvestableVegetable(MaterialData data) {
			if (data instanceof Crops) {
				if (((Crops) data).getState() == CropState.RIPE) {
					return true;
				}
			}
			return false;
		}
	}

	


}
