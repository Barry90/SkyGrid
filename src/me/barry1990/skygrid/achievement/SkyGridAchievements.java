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

import org.bukkit.Bukkit;
import org.bukkit.Material;


class SkyGridAchievements {
	
	private static final String PATH = "plugins/skygrid/achievements/";
	
	private UUID playeruuid;
	
	private List<SGAchievement> achievements;
	private List<Material> woodmaniacprogress;	
//	private List<Material> stonemaniacprogress;	
//	private List<Material> ironmaniacprogress;	
//	private List<Material> goldmaniacprogress;	
//	private List<Material> diamondmaniacprogress;	
	
	public SkyGridAchievements(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("uuid is null");
		}
		this.achievements = new ArrayList<SGAchievement>();
		this.woodmaniacprogress = new ArrayList<Material>();
//		this.stonemaniacprogress = new ArrayList<Material>();
//		this.ironmaniacprogress = new ArrayList<Material>();
//		this.goldmaniacprogress = new ArrayList<Material>();
//		this.diamondmaniacprogress = new ArrayList<Material>();
		this.playeruuid = uuid;
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
		}
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
			this.woodmaniacprogress.clear();
			this.addAchievement(SGAchievement.WOOD_MANIAC);
		}
	}
	
	
	
	
	//////////////////////////////////////////////
	// LOAD/SAVE
	//////////////////////////////////////////////
	
	
	private void loadAchievements() {
				
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
						default: {BarrysLogger.error(this, "Unknown Header in File: " + String.valueOf(input)); break;}
					}
					
				}	
				
			    
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void saveAchievements() {
		
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
		out.write(SGAFileConst.A_WOODMANIAC);
		if (!this.woodmaniacprogress.isEmpty()) {
			for (Material m : this.woodmaniacprogress) {
				out.write(SGAManiacConst.valueOf(m.name()).b);
			}
		}
		out.write(SGAFileConst.END);	
	}

}
