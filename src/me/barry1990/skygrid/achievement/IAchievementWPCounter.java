package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.utils.BarrysLogger;
import me.barry1990.utils.FileManagement;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


abstract class IAchievementWPCounter extends IAchievementWP {
	
	protected short progress;
	abstract protected short getProgressTarget();

	public IAchievementWPCounter(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
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
			SkyGridPlayerManager.awardAchievement(this.getPlayerUUID(), this.getId());
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
