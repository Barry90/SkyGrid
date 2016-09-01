package me.barry1990.skygrid.achievement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;
import me.barry1990.utils.FileManagement;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class SkyGridAchievements {
	
	static {
		SkyGrid.registerEvent(new SkyGridOnAsyncPlayerChatEvent());
	}
	
	private static final String PATH = "achievements";
	
	private HashMap<SGAIDENTIFIER, IAchievement> map = new HashMap<SGAIDENTIFIER, IAchievement>();
	private UUID playeruuid;
	
	/////////////////////////
	// Constructor
	/////////////////////////
	
	public SkyGridAchievements(UUID playeruuid) {
		this.playeruuid = playeruuid;
		
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.WOOD_MANIAC)) new SGAWoodManiac(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.STONE_MANIAC)) new SGAStoneManiac(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.IRON_MANIAC)) new SGAIronManiac(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.GOLD_MANIAC)) new SGAGoldManiac(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.DIAMOND_MANIAC)) new SGADiamondManiac(this.map, playeruuid);	
		
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.INFINITE_WATER_SOURCE)) new SGAInfiniteWaterSource(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.HOT_BUCKET)) new SGAHotBucket(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.THAT_WAS_CLOSE)) new SGAThatWasClose(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.GET_RICH_OR_DIE_TRYIN)) new SGAGetRichOrDieTryin(this.map, playeruuid);
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.VEGETABLE_MASTER)) new SGAVegetableMaster(this.map, playeruuid);
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.SOUP_KING)) new SGASoupKing(this.map, playeruuid);
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.ON_TOP_OF_THE_WORLD)) new SGAOnTopOfTheWorld(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.TIME_TO_ENCHANT)) new SGATimeToEnchant(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.OH_SHIT)) new SGAOhShit(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.NETHER_CLEANER)) new SGANetherCleaner(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.A_VERY_BIG_EGG)) new SGAAVeryBigEgg(this.map, playeruuid);	
		
		
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.SO_IT_BEGINS)) new SGASoItBegins(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.GO_DEEPER)) new SGAGoDeeper(this.map, playeruuid);	
		if (SkyGrid.getLevelManager().getLevel().isAchievementAvailable(SGAIDENTIFIER.GO_EVEN_DEEPER)) new SGAGoEvenDeeper(this.map, playeruuid);	
		
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
	
 	public synchronized boolean hasAchievementWithID(SGAIDENTIFIER SGA_ID) {
		IAchievement achievement = this.map.get(SGA_ID);
		if (achievement != null ) 
			return achievement.hasAchievement();
		else {
			BarrysLogger.error(this, String.format("Could not check Achievement with ID : %d", SGA_ID));
			throw new IllegalArgumentException();
		}
	}
	
	public synchronized void award(SGAIDENTIFIER SGA_ID) {
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
 		if (this.map.isEmpty())
 			return;
 		
		File file = new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH + File.separator + this.playeruuid.toString());
		
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
						out.write(achievement.getId().id);
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
		
		File file = new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH + File.separator + this.playeruuid.toString());
		
		if (file.exists()) {
			
			FileInputStream in = null;
			
			try{
					
				in = new FileInputStream(file);				
				int input;	
				
				while (((input = in.read()) != -1) && (input != SkyGridConst.END)) {
					byte binput = (byte)input;
					
					if (binput == SkyGridConst.ACHIEVEMENTS)
						this.loadAchievementlist(in);
					else {
						Object object = this.map.get(SGAIDENTIFIER.getSGAIDENTIFIERFromId(binput));
						if (object instanceof IAchievementWP) 
							((IAchievementWP) object).load(in); 
						else {						
							BarrysLogger.error(this, "Unknown Header in File: 0x" + Integer.toHexString(input));
							while (((input = in.read()) != -1) && ((byte)input != SkyGridConst.END)) {}
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
			IAchievement achievement = this.map.get(SGAIDENTIFIER.getSGAIDENTIFIERFromId((byte)input));
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
				out.write(achievement.getId().id);
			}
		}
		out.write(SkyGridConst.END);
	}
		
	//////////////////////////////////////////////
	// SPECIFIC ACHIEVEMENT HANDLING
	//////////////////////////////////////////////
	
	public synchronized void addProgress(SGAIDENTIFIER id, Object... values) {
		((IAchievementWP) this.map.get(id)).addProgress(values);
	}
	
	/////////////////////////
	// RESET
	/////////////////////////
	
	public static void deleteAllProgress() {
		FileManagement.deleteDirectory(new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH));
	}

}
