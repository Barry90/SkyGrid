package me.barry1990.skygrid.achievement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.barry1990.skygrid.ActionBarAPI;
import me.barry1990.utils.BarrysLogger;


public class SkyGridAchievements {
	
	public enum SGAchievement {
		
		////////////////////
		// Overworld
		////////////////////
		
		SO_IT_BEGINS,
		WOOD_MANIAC,
		STONE_MANIAC,
		IRON_MANIAC,
		DIAMOND_MANIAC,
		INFINITE_WATER_SOURCE,
		HOT_BUCKET,
		THAT_WAS_CLOSE,
		GET_RICH_OR_DIE_TRYIN,
		ON_TOP_OF_THE_WORLD,
		
		////////////////////
		// Nether
		////////////////////
		
		GO_DEEPER,
		
		////////////////////
		// End
		////////////////////
		
		GO_EVEN_DEER
	}
	
	private final static String PATH = "skygrid/achievements/";
	private List<SGAchievement> achievements;
	private UUID playeruuid;
	
	
	public SkyGridAchievements(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("uuid is null");
		}
		this.achievements = new ArrayList<SGAchievement>();
		this.playeruuid = uuid;
		this.loadAchievements();
		
	}
	
	public synchronized boolean constainsAchievement(SGAchievement a) {
		return this.achievements.contains(a);
	}
	
	public synchronized void addAchievement(SGAchievement a) {
		if (!this.constainsAchievement(a)) {
			this.achievements.add(a);
			ActionBarAPI.sendActionBar(Bukkit.getPlayer(this.playeruuid), "Achievement erhalten: §4" + a.name() );
			this.saveAchievements();
		}
	}
	
	private void loadAchievements() {
				
		File file = new File(PATH + this.playeruuid.toString());
		
		if (file.exists()) {
			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
				
			    for(String line; (line = br.readLine()) != null; ) {

			    	try {
			    		SGAchievement a = SGAchievement.valueOf(line);
			    		if (!this.constainsAchievement(a)) {
			    			this.achievements.add(a);
			    		}
			    	} catch (IllegalArgumentException e) {
			    		BarrysLogger.error(this, "IllegalArgumentException - konnte line nicht in SGAchievement umwandeln");
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
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			
			boolean firstline = true;
			for (SGAchievement a : this.achievements) {
				if (!firstline) 
					bw.newLine();
				bw.write(String.format("%s", a.name().toString()));
				firstline = false;				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
