package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;


final class SGAGoldManiac extends IAchievementWP {	
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Gold Maniac";

	private ArrayList<Material> progress;
	
	public SGAGoldManiac(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
		this.progress = new ArrayList<Material>();
	}

	@Override
	protected void save(FileOutputStream out) throws IOException {
		if (this.hasAchievement()) return;
		for (Material m : this.progress) {
			out.write(SGAManiacConst.valueOf(m.name()).b);
		}
	}

	@Override
	protected void load(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && (input != SkyGridConst.END)) {
			Material m = SGAManiacConst.getSGAManiacConstFromByte((byte)input).m;
			this.progress.add(m);			
		}
		
	}

	@Override
	public Byte getId() {
		return SGAIDENTIFIER.GOLD_MANIAC;
	}
	
	@Override
	protected String getName() {
		return SGAGoldManiac.name;
	}
	
	
	public void addMaterial(Material m) {
		if (this.hasAchievement()) 
			return;
		
		if (!this.progress.contains(m)) {
			this.progress.add(m);
			this.saveEverything();
		}
		if (this.progress.size() == 9) {	
			SkyGridPlayerManager.awardAchievement(this.getPlayerUUID(), this.getId());
			this.progress.clear();
		}
	}
	
	private static class SGAListener implements Listener {
		
		@EventHandler
		public void onCraftItem(CraftItemEvent e) {		
			switch (e.getRecipe().getResult().getType()) {
				
				case GOLD_SWORD:
				case GOLD_AXE:
				case GOLD_HOE:
				case GOLD_SPADE:
				case GOLD_PICKAXE: 
				case GOLD_HELMET:
				case GOLD_CHESTPLATE:
				case GOLD_LEGGINGS:
				case GOLD_BOOTS: {
					if (e.getWhoClicked() instanceof Player) {
						SkyGridPlayerManager.addMaterialForGoldManiac((Player)e.getWhoClicked(), e.getRecipe().getResult().getType());
					}
					break;
				}

				default:
					break;
			}
		}
	}
	
}
