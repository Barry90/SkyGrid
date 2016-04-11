package me.barry1990.skygrid.achievement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class SkyGridAchievements {
	
	static {
		SkyGrid.registerEvent(new SkyGridOnAsyncPlayerChatEvent());
	}
	
	private static final String PATH = "plugins/skygrid/achievements/";
	
	//TODO: replace HashMap with ArrayList
	private HashMap<Byte, IAchievement> map = new HashMap<Byte, IAchievement>();
	private UUID playeruuid;
	
	/////////////////////////
	// Constructor
	/////////////////////////
	
	public SkyGridAchievements(UUID playeruuid) {
		this.playeruuid = playeruuid;
		
		new SGAWoodManiac(this.map, playeruuid);	
		new SGAStoneManiac(this.map, playeruuid);	
		new SGAIronManiac(this.map, playeruuid);	
		new SGAGoldManiac(this.map, playeruuid);	
		new SGADiamondManiac(this.map, playeruuid);	
		
		new SGAInfiniteWaterSource(this.map, playeruuid);	
		new SGAHotBucket(this.map, playeruuid);	
		new SGAThatWasClose(this.map, playeruuid);	
		new SGAGetRichOrDieTryin(this.map, playeruuid);
		new SGAVegetableMaster(this.map, playeruuid);
		// TODO: new SGAOnTopOfTheWorld(this.map, playeruuid);	
		new SGATimeToEnchant(this.map, playeruuid);	
		new SGAOhShit(this.map, playeruuid);	
		new SGANetherCleaner(this.map, playeruuid);	
		new SGAAVeryBigEgg(this.map, playeruuid);	
		
		
		new SGASoItBegins(this.map, playeruuid);	
		new SGAGoDeeper(this.map, playeruuid);	
		new SGAGoEvenDeeper(this.map, playeruuid);	
		
		this.loadAchievements();

	}
	
	//////////////////////////////////////////////
	// PUBLIC ACHIEVEMENTS OPARATIONS
	//////////////////////////////////////////////
	
	public synchronized Inventory createAchievementGUI() {
		int slots = (this.map.values().size() / 9 + 1) * 9;
		Inventory inv = Bukkit.createInventory(null, slots, "ACHIEVEMENTS§3");
		int i = 0;
		for (IAchievement a : map.values()) {
			inv.setItem(i, a.getAchievementInventoryItem());
			i++;
		}
		
		return inv;
	}
	
 	public synchronized boolean hasAchievementWithID(byte SGA_ID) {
		IAchievement achievement = this.map.get(SGA_ID);
		if (achievement != null ) 
			return achievement.hasAchievement();
		else {
			BarrysLogger.error(this, String.format("Could not check Achievement with ID : %d", SGA_ID));
			throw new IllegalArgumentException();
		}
	}
	
	public synchronized void award(byte SGA_ID) {
		IAchievement achievement = this.map.get(SGA_ID);
		if (achievement != null ) {
			if (!achievement.hasAchievement()) {
				achievement.award();			
				this.saveAchievements();
				this.testForLayerAchievement();
			}
		} else
			BarrysLogger.error(this, String.format("Could not award Achievement with ID : %d", SGA_ID));
	}
	
	public synchronized void saveAchievementAndProgress() {
		this.saveAchievements();
	}
	
	public synchronized int getAchievementCount() {
		int achievementcount = 0;
		for (IAchievement a : this.map.values()) {
			achievementcount += a.hasAchievement()?1:0;
		}
		return achievementcount;
	}
	
	synchronized void testForLayerAchievement() {
		int achievementcount = this.getAchievementCount();
		if (achievementcount > 5) {
			this.award(SGAIDENTIFIER.GO_DEEPER);
		}
		if (achievementcount > 9) {
			this.award(SGAIDENTIFIER.GO_EVEN_DEEPER);
		}
		
	}
	
	//////////////////////////////////////////////
	// LOAD/SAVE
	//////////////////////////////////////////////		
	
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
			
			this.saveAchievementlist(out);
			
			for (IAchievement achievement : this.map.values()) {
				if (achievement instanceof IAchievementWP) {
					if (!achievement.hasAchievement()) {
						out.write(achievement.getId());
						((IAchievementWP) achievement).save(out);
						out.write(SkyGridConst.END);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}
	
	private synchronized void loadAchievements() {
		
		File file = new File(PATH + this.playeruuid.toString());
		
		if (file.exists()) {
			
			FileInputStream in = null;
			
			try{
					
				in = new FileInputStream(file);				
				int input;	
				
				while (((input = in.read()) != -1) && (input != SkyGridConst.END)) {
					
					//input = header-byte					
					switch (input) {	
						//achievements with no progress
						case SkyGridConst.ACHIEVEMENTS: { this.loadAchievementlist(in); break; }
						
						//achievements with progress
						case SGAIDENTIFIER.WOOD_MANIAC: 						
						case SGAIDENTIFIER.STONE_MANIAC: 
						case SGAIDENTIFIER.IRON_MANIAC: 
						case SGAIDENTIFIER.GOLD_MANIAC: 
						case SGAIDENTIFIER.DIAMOND_MANIAC: 
						case SGAIDENTIFIER.VEGETABLE_MASTER: 
						case SGAIDENTIFIER.NETHER_CLEANER: { ((IAchievementWP) this.map.get((byte)input)).load(in); break; }
						
						//unknown header - read until next header or EOF
						default: {
							BarrysLogger.error(this, "Unknown Header in File: 0x" + Integer.toHexString(input));
							while (((input = in.read()) != -1) && ((byte)input != SkyGridConst.END)) {}
							break;
						}
					}
					
				}				
			    
			} catch (IOException e) {
				e.printStackTrace();
			}  finally {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
			
		} else {
			BarrysLogger.info(this, "Nothing to load. Achievementfile does not exist.");
		}
		
	}
	
	//////////////////////////////////////////////
	// SAVE/LOAD AWARDED ACHIEVEMENT
	//////////////////////////////////////////////	
	
	private void loadAchievementlist(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && ((byte) input != SkyGridConst.END)) {
			IAchievement achievement = this.map.get((byte) input);
			if (achievement != null) 
				achievement.setAchievementAwarded();
			else
				BarrysLogger.error(this, String.format("Could not find Achievement with ID : 0x%s", Integer.toHexString(input)));					
		}
	}
	
	private void saveAchievementlist(FileOutputStream out) throws IOException {
		out.write(SkyGridConst.ACHIEVEMENTS);
		for (IAchievement achievement : map.values()) {
			if (achievement.hasAchievement()) {
				out.write(achievement.getId());
			}
		}
		out.write(SkyGridConst.END);
	}
		
	//////////////////////////////////////////////
	// SPECIFIC ACHIEVEMENT HANDLING
	//////////////////////////////////////////////
	
	public synchronized void addProgress(byte id, Object... values) {
		((IAchievementWP) this.map.get(id)).addProgress(values);
	}

}
