package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


abstract class IAchievementWP extends IAchievement {
	
	final protected static String PROGRESS_F = "§6§l%s§r - §f%d%%";
	final protected static String TODO = "You are on the rigth track.";

	public IAchievementWP(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}
	
	abstract protected void save(FileOutputStream out) throws IOException;
	abstract protected void load(FileInputStream in) throws IOException;
	abstract protected boolean hasProgress();
	abstract protected ItemStack getAchievementProgressItem();

	@Override
	final protected ItemStack getAchievementInventoryItem() {
		
		if (this.hasProgress()) {
			BarrysLogger.info(this, "name", this.getName());
			ItemStack item = this.getAchievementProgressItem();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(IAchievementWP.TODO);
			ItemMeta meta = item.getItemMeta();
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		} else 
			return super.getAchievementInventoryItem();		
		
	}
	
	

}
