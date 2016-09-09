package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;
import me.barry1990.utils.FileManagement;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * IAchievementWPCounter - Implement this class to create a new achievement that requirs a counter.
 * @author Barry1990
 */
abstract public class IAchievementWPCounter extends IAchievementWP {
	
	protected short progress;
	
	/**
	 * @return The counter to reach to award this achievement. If the progress reaches this counter this achievement will be awarded automaticaly.
	 */
	abstract protected short getProgressTarget();

	/**
	 * Creates a new instance of IAchievementWPCounter
	 * @param playeruuid
	 */
	public IAchievementWPCounter(UUID playeruuid) {
		super(playeruuid);
	}

	@Override
	protected void save(FileOutputStream out) throws IOException {
		FileManagement.writeShortTo(out, this.progress);
	}

	@Override
	protected void load(FileInputStream in) throws IOException {

		this.progress = FileManagement.readShortFrom(in);
		int b;
		if (((byte) (b = in.read())) != SkyGridConst.END) {
			BarrysLogger.error(this, String.format("unexpected Value: %d", b));
		}

	}

	@Override
	protected void addProgress(Object... values) {
		
		if (this.hasAchievement()) 
			return;
		
		this.progress++;
		if (this.progress == this.getProgressTarget()) {
			SkyGrid.getPlayerManager().awardAchievement(this.getPlayerUUID(), this.getUniqueId());
		} else {
			this.saveEverything();
		}

	}

	@Override
	protected boolean hasProgress() {
		return (this.progress != 0);
	}
	
	@Override
	protected ItemStack getAchievementProgressItem() {	
		ItemStack item = new ItemStack(Material.FIREWORK_CHARGE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(String.format(IAchievementWP.PROGRESS_F, this.getName(), (this.progress * 100 ) / this.getProgressTarget()));
		item.setItemMeta(meta);
		return item;
	}

}
