package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.utils.FileManagement;


final class SGANetherCleaner extends IAchievementWP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Nether Cleaner";
	
	private short progress;

	public SGANetherCleaner(HashMap<Byte, IAchievement> map, UUID playeruuid) {
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
		return SGAIDENTIFIER.NETHER_CLEANER;
	}

	@Override
	protected String getName() {
		return SGANetherCleaner.name;
	}
	
	public void addProgress() {		
		if (this.hasAchievement()) 
			return;
		
		this.progress += 1;
		if (this.progress == 400) {
			SkyGridPlayerManager.awardAchievement(this.getPlayerUUID(), this.getId());
		} else {
			this.saveEverything();
		}
	}
	
	private static class SGAListener implements Listener {
		
		@EventHandler
		public void onEntityDeathEvent(EntityDeathEvent e) {
			
			if (e.getEntity().getKiller() != null) {
				
				switch (e.getEntityType()) {
					case PIG_ZOMBIE:
					case GHAST:
					case BLAZE: {
						SkyGridPlayerManager.addNetherCleanerProgress(e.getEntity().getKiller());
						break;
					}
					default:
						break;
				}
				
			}
		}
	}

}
