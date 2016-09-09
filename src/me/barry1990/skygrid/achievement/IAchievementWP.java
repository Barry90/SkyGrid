package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * IAchievementWP - Implement this class to create a new achievement that requirs a certain progress.
 * @author Barry1990
 */
abstract public class IAchievementWP extends IAchievement {
	
	final protected static String PROGRESS_F = "§6§l%s§r - §f%d%%";

	/**
	 * Creates a new instance of IAchievementWP
	 * @param playeruuid
	 */
	public IAchievementWP(UUID playeruuid) {
		super(playeruuid);
	}
	
	/**
	 * Save your progress to the given FileOutputStream
	 * @param out The FileOutputStream to write your data
	 * @throws IOException
	 */
	abstract protected void save(FileOutputStream out) throws IOException;
	
	/**
	 * Load your progress from the given FileInputStream
	 * @param in The FileInputStream to read your data.
	 * @throws IOException
	 */
	abstract protected void load(FileInputStream in) throws IOException;
	
	/**
	 * Use this method from your evenhandlers to add progress
	 * @param values A list of the objects you need e.g. crafting results
	 */
	abstract protected void addProgress(Object... values);
	
	/**
	 * @return True if the player has any progress on this achievement
	 */
	abstract protected boolean hasProgress();
	
	/**
	 * @return The ItemStack that will be displayed in the achievement GUI if the user has any progress
	 */
	abstract protected ItemStack getAchievementProgressItem();

	@Override
	final protected ItemStack getAchievementInventoryItem() {
		
		if (this.hasProgress()) {
			BarrysLogger.info(this, "name", this.getName());
			ItemStack item = this.getAchievementProgressItem();
			ItemMeta meta = item.getItemMeta();
			meta.setLore(this.createDescription());
			item.setItemMeta(meta);
			return item;
		} else 
			return super.getAchievementInventoryItem();		
		
	}
	
}
