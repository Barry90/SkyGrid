package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.utils.FileManagement;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;


final class SGAVegetableMaster extends IAchievementWP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Vegetable Master";
	
	private short progress;

	public SGAVegetableMaster(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected void save(FileOutputStream out) throws IOException {
		FileManagement.writeShortTo(out, this.progress);
	}

	@Override
	protected void load(FileInputStream in) throws IOException {
		this.progress = FileManagement.readShortFrom(in);
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
	protected void addProgress(Object... values) {	
		if (this.hasAchievement()) 
			return;
		
		this.progress += 1;
		if (this.progress == 500) {
			SkyGridPlayerManager.awardAchievement(this.getPlayerUUID(), this.getId());
		} else {
			this.saveEverything();
		}		
	}

	@Override
	protected ItemStack getAchievementItem() {
		return new ItemStack(Material.GOLDEN_CARROT, 1);
	}
	
	@Override
	protected boolean hasProgress() {
		return (this.progress != 0);
	}

	@Override
	protected ItemStack getAchievementProgressItem() {

		ItemStack item = new ItemStack(Material.FIREWORK_CHARGE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(String.format(PROGRESS_F, this.getName(), (this.progress * 100 ) / 500));
		item.setItemMeta(meta);
		return item;
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
