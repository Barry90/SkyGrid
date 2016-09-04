package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import org.bukkit.inventory.meta.ItemMeta;


final class SGAWoodManiac extends IAchievementWP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Wood Maniac";

	private ArrayList<Material> progress;
	
	public SGAWoodManiac(HashMap<SGAIDENTIFIER, IAchievement> map, UUID playeruuid) {
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
		while (((input = in.read()) != -1) && ((byte)input != SkyGridConst.END)) {
			Material m = SGAManiacConst.getSGAManiacConstFromByte((byte)input).m;
			this.progress.add(m);			
		}
		
	}

	@Override
	public SGAIDENTIFIER getId() {
		return SGAIDENTIFIER.WOOD_MANIAC;
	}
	
	@Override
	protected String getName() {
		return SGAWoodManiac.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {
		return new ItemStack(Material.LOG, 1);
	}
	
	@Override
	protected List<String> getDescription() {
		
		return Arrays.asList("Get all wooden tools.");
	}
	
	@Override
	protected boolean hasProgress() {
		return (this.progress.size() != 0);
	}
	
	@Override
	protected ItemStack getAchievementProgressItem() {	
		ItemStack item = new ItemStack(Material.FIREWORK_CHARGE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(String.format(PROGRESS_F, this.getName(), (this.progress.size() * 100 ) / 5));
		item.setItemMeta(meta);
		return item;
	}	
	
	@Override
	protected void addProgress(Object... values) {
		if (this.hasAchievement() || values == null || values.length != 1 || !(values[0] instanceof Material)) 
			return;
		
		Material m = (Material) values[0];
		
		if (!this.progress.contains(m)) {
			this.progress.add(m);
			this.saveEverything();
		}
		if (this.progress.size() == 5) {	
			SkyGridPlayerManager.awardAchievement(this.getPlayerUUID(), this.getId());
			this.progress.clear();
		}
	}

	private static class SGAListener implements Listener {
		
		@EventHandler
		public void onCraftItem(CraftItemEvent e) {		
			switch (e.getRecipe().getResult().getType()) {
				
				case WOOD_SWORD:
				case WOOD_AXE:
				case WOOD_HOE:
				case WOOD_SPADE:
				case WOOD_PICKAXE: {
					if (e.getWhoClicked() instanceof Player) {
						SkyGridPlayerManager.addProgressForAchievement((Player)e.getWhoClicked(),SGAIDENTIFIER.WOOD_MANIAC, e.getRecipe().getResult().getType());
					}
					break;
				}

				default:
					break;
			}
		}
	}

}
