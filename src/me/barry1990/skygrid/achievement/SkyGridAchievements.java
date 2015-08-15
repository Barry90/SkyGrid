package me.barry1990.skygrid.achievement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.ActionBarAPI;
import me.barry1990.utils.BarrysLogger;
import me.barry1990.utils.FileManagement;

import org.bukkit.Bukkit;
import org.bukkit.Material;


class SkyGridAchievements {
	
	private static final String PATH = "plugins/skygrid/achievements/";
	
	private UUID playeruuid;
	
	private List<SGAchievement> achievements;
	private List<Material> woodmaniacprogress;	
	private List<Material> stonemaniacprogress;	
	private List<Material> ironmaniacprogress;	
	private List<Material> goldmaniacprogress;	
	private List<Material> diamondmaniacprogress;	
	private short nethercleanerprogress;
	
	public SkyGridAchievements(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("uuid is null");
		}
		this.playeruuid = uuid;
		
		this.achievements = new ArrayList<SGAchievement>();
		this.woodmaniacprogress = new ArrayList<Material>();
		this.stonemaniacprogress = new ArrayList<Material>();
		this.ironmaniacprogress = new ArrayList<Material>();
		this.goldmaniacprogress = new ArrayList<Material>();
		this.diamondmaniacprogress = new ArrayList<Material>();
		this.nethercleanerprogress = 0;
		
		this.loadAchievements();
		
	}
	
	//////////////////////////////////////////////
	// STANDART ACHIEVEMENT OPARATIONS
	//////////////////////////////////////////////
	
	public synchronized boolean constainsAchievement(SGAchievement a) {
		return this.achievements.contains(a);
	}
	
	public synchronized void addAchievement(SGAchievement a) {
		if (!this.constainsAchievement(a)) {
			this.achievements.add(a);			
			this.saveAchievements();
			ActionBarAPI.sendActionBar(Bukkit.getPlayer(this.playeruuid), "Achievement erhalten: §4" + SGAchievement.getname(a.b));
			Bukkit.getPlayer(this.playeruuid).sendMessage("Achievement erhalten: §4" + SGAchievement.getname(a.b));
		}
	}
	
	public synchronized void saveAchievementAndProgress() {
		this.saveAchievements();
	}
	
	//////////////////////////////////////////////
	// SPECIFIC ACHIEVEMENT OPARATIONS
	//////////////////////////////////////////////
	
	/* WOOD MANIAC */	
	public void addMaterialToWoodManiac(Material m) {
		if (this.constainsAchievement(SGAchievement.WOOD_MANIAC)) 
			return;
		
		if (!this.woodmaniacprogress.contains(m)) {
			this.woodmaniacprogress.add(m);
			this.saveAchievements();
		}
		if (this.woodmaniacprogress.size() == 5) {			
			this.addAchievement(SGAchievement.WOOD_MANIAC);
			this.woodmaniacprogress.clear();
		}
	}
	
	/* STONE MANIAC */	
	public void addMaterialToStoneManiac(Material m) {
		if (this.constainsAchievement(SGAchievement.STONE_MANIAC)) 
			return;
		
		if (!this.stonemaniacprogress.contains(m)) {
			this.stonemaniacprogress.add(m);
			this.saveAchievements();
		}
		if (this.stonemaniacprogress.size() == 5) {			
			this.addAchievement(SGAchievement.STONE_MANIAC);
			this.stonemaniacprogress.clear();
		}
	}	
	
	/* IRON MANIAC */	
	public void addMaterialToIronManiac(Material m) {
		if (this.constainsAchievement(SGAchievement.IRON_MANIAC)) 
			return;
		
		if (!this.ironmaniacprogress.contains(m)) {
			this.ironmaniacprogress.add(m);
			this.saveAchievements();
		}
		if (this.ironmaniacprogress.size() == 9) {			
			this.addAchievement(SGAchievement.IRON_MANIAC);
			this.ironmaniacprogress.clear();
		}
	}	
	
	/* GOLD MANIAC */	
	public void addMaterialToGoldManiac(Material m) {
		if (this.constainsAchievement(SGAchievement.GOLD_MANIAC)) 
			return;
		
		if (!this.goldmaniacprogress.contains(m)) {
			this.goldmaniacprogress.add(m);
			this.saveAchievements();
		}
		if (this.goldmaniacprogress.size() == 9) {			
			this.addAchievement(SGAchievement.GOLD_MANIAC);
			this.goldmaniacprogress.clear();
		}
	}	
	
	/* DIAMOND MANIAC */	
	public void addMaterialToDiamondManiac(Material m) {
		if (this.constainsAchievement(SGAchievement.DIAMOND_MANIAC)) 
			return;
		
		if (!this.diamondmaniacprogress.contains(m)) {
			this.diamondmaniacprogress.add(m);
			this.saveAchievements();
		}
		if (this.diamondmaniacprogress.size() == 9) {			
			this.addAchievement(SGAchievement.DIAMOND_MANIAC);
			this.diamondmaniacprogress.clear();
		}
	}	
	
	/* NETHER CLEANER */
	public void addNetherCleanerProgress() {		
		if (this.constainsAchievement(SGAchievement.NETHER_CLEANER)) 
			return;
		
		this.nethercleanerprogress += 1;
		if (this.nethercleanerprogress == 400) {
			this.addAchievement(SGAchievement.NETHER_CLEANER);
		} 
	}
	
	
	//////////////////////////////////////////////
	// LOAD/SAVE
	//////////////////////////////////////////////	
	
	private synchronized void loadAchievements() {
				
		File file = new File(PATH + this.playeruuid.toString());
		
		if (file.exists()) {
			
			FileInputStream in = null;
			
			try{
					
				in = new FileInputStream(file);				
				int input;	
				
				while (((input = in.read()) != -1) && (input != SGAFileConst.END)) {
					
					//input = header-byte					
					switch (input) {					
						case SGAFileConst.ACHIEVEMENTS: { this.loadAchievementlist(in); break; }
						case SGAFileConst.A_WOODMANIAC: { this.loadWoodManiac(in); break; }
						case SGAFileConst.A_STONEMANIAC: { this.loadStoneManiac(in); break; }
						case SGAFileConst.A_IRONMANIAC: { this.loadIronManiac(in); break; }
						case SGAFileConst.A_GOLDMANIAC: { this.loadGoldManiac(in); break; }
						case SGAFileConst.A_DIAMMANIAC: { this.loadDiamondManiac(in); break; }
						case SGAFileConst.A_NETHERCLEANER: {this.loadNetherCleaner(in); break; }
						default: {
							BarrysLogger.error(this, "Unknown Header in File: " + String.valueOf(input));
							while (((input = in.read()) != -1) && (input != SGAFileConst.END)) {}
							break;
						}
					}
					
				}	
				
			    
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private synchronized void saveAchievements() {
		
		File file = new File(PATH + this.playeruuid.toString());
		
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		FileOutputStream out = null;

        try {        	
            out = new FileOutputStream(file);
            
            /* SAVE ACHIEVEMENTS */
			this.saveAchievementlist(out);			

			/* SAVE PROGRESS */	
			this.saveWoodManiac(out);
			this.saveStoneManiac(out);
			this.saveIronManiac(out);
			this.saveGoldManiac(out);
			this.saveDiamondManiac(out);
			this.saveNetherCleaner(out);
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/* GAINED ACHIEVEMENTS */
	
	private void  loadAchievementlist(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && (input != SGAFileConst.END)) {
			//convert byte to Material and add it
			SGAchievement a = SGAchievement.getSGAchievementFromByte((byte)input);
			this.achievements.add(a);			
		}
	}
	private void  saveAchievementlist(FileOutputStream out) throws IOException {
		out.write(SGAFileConst.ACHIEVEMENTS);
		for (SGAchievement a : this.achievements) {		
			out.write(a.b);
		}
		out.write(SGAFileConst.END);
	}
			
	/* WOOD MANIAC */
	
	private void loadWoodManiac(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && (input != SGAFileConst.END)) {
			//convert byte to Material and add it
			Material m = SGAManiacConst.getSGAManiacConstFromByte((byte)input).m;
			this.woodmaniacprogress.add(m);			
		}
		
	}
	private void saveWoodManiac(FileOutputStream out) throws IOException {
		if (this.constainsAchievement(SGAchievement.WOOD_MANIAC)) return;
		out.write(SGAFileConst.A_WOODMANIAC);
		for (Material m : this.woodmaniacprogress) {
			out.write(SGAManiacConst.valueOf(m.name()).b);
		}
		out.write(SGAFileConst.END);	
	}
	
	/* STONE MANIAC */
	
	private void loadStoneManiac(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && (input != SGAFileConst.END)) {
			//convert byte to Material and add it
			Material m = SGAManiacConst.getSGAManiacConstFromByte((byte)input).m;
			this.stonemaniacprogress.add(m);			
		}
		
	}
	private void saveStoneManiac(FileOutputStream out) throws IOException {
		if (this.constainsAchievement(SGAchievement.STONE_MANIAC)) return;
		out.write(SGAFileConst.A_STONEMANIAC);
		for (Material m : this.stonemaniacprogress) {
			out.write(SGAManiacConst.valueOf(m.name()).b);
		}
		out.write(SGAFileConst.END);	
	}
	
	/* IRON MANIAC */
	
	private void loadIronManiac(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && (input != SGAFileConst.END)) {
			//convert byte to Material and add it
			Material m = SGAManiacConst.getSGAManiacConstFromByte((byte)input).m;
			this.ironmaniacprogress.add(m);			
		}
		
	}
	private void saveIronManiac(FileOutputStream out) throws IOException {
		if (this.constainsAchievement(SGAchievement.IRON_MANIAC)) return;
		out.write(SGAFileConst.A_IRONMANIAC);
		if (!this.ironmaniacprogress.isEmpty()) {
			for (Material m : this.ironmaniacprogress) {
				out.write(SGAManiacConst.valueOf(m.name()).b);
			}
		}
		out.write(SGAFileConst.END);	
	}
	
	/* GOLD MANIAC */
	
	private void loadGoldManiac(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && (input != SGAFileConst.END)) {
			//convert byte to Material and add it
			Material m = SGAManiacConst.getSGAManiacConstFromByte((byte)input).m;
			this.goldmaniacprogress.add(m);			
		}
		
	}
	private void saveGoldManiac(FileOutputStream out) throws IOException {
		if (this.constainsAchievement(SGAchievement.GOLD_MANIAC)) return;
		out.write(SGAFileConst.A_GOLDMANIAC);
		if (!this.goldmaniacprogress.isEmpty()) {
			for (Material m : this.goldmaniacprogress) {
				out.write(SGAManiacConst.valueOf(m.name()).b);
			}
		}
		out.write(SGAFileConst.END);	
	}
	
	/* DIAMON MANIAC */
	
	private void loadDiamondManiac(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && (input != SGAFileConst.END)) {
			//convert byte to Material and add it
			Material m = SGAManiacConst.getSGAManiacConstFromByte((byte)input).m;
			this.diamondmaniacprogress.add(m);			
		}
		
	}
	private void saveDiamondManiac(FileOutputStream out) throws IOException {
		if (this.constainsAchievement(SGAchievement.DIAMOND_MANIAC)) return;
		out.write(SGAFileConst.A_DIAMMANIAC);
		if (!this.diamondmaniacprogress.isEmpty()) {
			for (Material m : this.diamondmaniacprogress) {
				out.write(SGAManiacConst.valueOf(m.name()).b);
			}
		}
		out.write(SGAFileConst.END);	
	}
	
	/* NETHER CLEANER */
	
	private void loadNetherCleaner(FileInputStream in) throws IOException {
		this.nethercleanerprogress = FileManagement.readShortFrom(in);
		
	}
	private void saveNetherCleaner(FileOutputStream out) throws IOException {
		if (this.constainsAchievement(SGAchievement.NETHER_CLEANER)) return;
		out.write(SGAFileConst.A_NETHERCLEANER);
		FileManagement.writeShortTo(out, this.nethercleanerprogress);
		out.write(SGAFileConst.END);	
	}
	

	
	
	

}
