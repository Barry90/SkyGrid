package me.barry1990.skygrid.achievement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;
import me.barry1990.utils.FileManagement;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

/**
 * SkyGridAchievements - This class handles achievements for a player
 * 
 * @author Barry1990
 */
public class SkyGridAchievements {

	static {
		SkyGrid.registerEvent(new SkyGridOnAsyncPlayerChatEvent());
	}

	private static final String			PATH	= "achievements";

	private HashMap<Byte, IAchievement>	map;
	private UUID						playeruuid;

	/////////////////////////
	// Constructor
	/////////////////////////

	/**
	 * Creates a new instance of SkyGridAchievements
	 * 
	 * @param playeruuid The player that will be associated with the achievements
	 */
	public SkyGridAchievements(UUID playeruuid) {

		this.playeruuid = playeruuid;
		this.map = new HashMap<Byte, IAchievement>();

		List<IAchievement> achievments = SkyGrid.getLevelManager().getLevel().getAchievements(playeruuid);

		for (IAchievement achievment : achievments) {
			if (map.containsKey(achievment.getUniqueId())) {
				BarrysLogger.warn(achievment, String.format("duplicated keys for IAchievement : %d", achievment.getUniqueId()));
			}
			map.put(achievment.getUniqueId(), achievment);
		}

		this.loadAchievements();
	}

	// ////////////////////////////////////////////
	// PUBLIC ACHIEVEMENTS OPARATIONS
	// ////////////////////////////////////////////

	/**
	 * Creates and return the inventory for achievement GUI
	 * 
	 * @return
	 */
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

	/**
	 * Checks if a player has a achievement
	 * 
	 * @param id The unique id of the achievement
	 * @return True if the player has the achievement
	 */
	public synchronized boolean hasAchievementWithID(byte id) {

		IAchievement achievement = this.map.get(id);
		if (achievement != null)
			return achievement.hasAchievement();
		else {
			BarrysLogger.error(this, String.format("Could not check Achievement with ID : %d", id));
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Awards an achievement
	 * 
	 * @param id The unique id of the achievement to award
	 */
	public synchronized void award(byte id) {

		IAchievement achievement = this.map.get(id);
		if (achievement != null) {
			if (!achievement.hasAchievement()) {
				achievement.award();
				this.saveAchievements();
				SkyGrid.getLevelManager().getLevel().onAchievementAwardedEvent(achievement, achievement.getPlayerUUID());
			}
		} else
			BarrysLogger.error(this, String.format("Could not award Achievement with ID : %d", id));
	}

	/**
	 * Saves all achievements
	 */
	public synchronized void saveAchievementAndProgress() {

		this.saveAchievements();
	}

	/**
	 * @return The number of awarded achievements
	 */
	public synchronized int getAchievementCount() {

		int achievementcount = 0;
		for (IAchievement a : this.map.values()) {
			achievementcount += a.hasAchievement() ? 1 : 0;
		}
		return achievementcount;
	}

	/**
	 * @return The total number of achievments
	 */
	public synchronized int getNumberOfAchievements() {

		return this.map.values().size();
	}

	// ////////////////////////////////////////////
	// LOAD/SAVE
	// ////////////////////////////////////////////

	/**
	 * Saves all achievements to a file
	 */
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
						out.write(achievement.getUniqueId());
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

	/**
	 * loads achievements from a file
	 */
	private synchronized void loadAchievements() {

		File file = new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH + File.separator + this.playeruuid.toString());

		if (file.exists()) {

			FileInputStream in = null;

			try {

				in = new FileInputStream(file);
				int input;

				while (((input = in.read()) != -1) && (input != SkyGridConst.END)) {
					byte binput = (byte) input;

					if (binput == SkyGridConst.ACHIEVEMENTS)
						this.loadAchievementlist(in);
					else {
						Object object = this.map.get(binput);
						if (object instanceof IAchievementWP)
							((IAchievementWP) object).load(in);
						else {
							BarrysLogger.error(this, "Unknown Header in File: 0x" + Integer.toHexString(input));
							while (((input = in.read()) != -1) && ((byte) input != SkyGridConst.END)) {
							}
						}
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (Exception e) {
				}
			}

		} else {
			BarrysLogger.info(this, "Nothing to load. Achievementfile does not exist.");
		}

	}

	// ////////////////////////////////////////////
	// SAVE/LOAD AWARDED ACHIEVEMENT
	// ////////////////////////////////////////////

	/**
	 * helper method for loading awarded achievements
	 * 
	 * @param in The FileInputStream of the achievement file
	 * @throws IOException
	 */
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

	/**
	 * helper method for saving awarded achievements
	 * 
	 * @param out The FileOutputStream of the achievement file
	 * @throws IOException
	 */
	private void saveAchievementlist(FileOutputStream out) throws IOException {

		out.write(SkyGridConst.ACHIEVEMENTS);
		for (IAchievement achievement : map.values()) {
			if (achievement.hasAchievement()) {
				out.write(achievement.getUniqueId());
			}
		}
		out.write(SkyGridConst.END);
	}

	// ////////////////////////////////////////////
	// SPECIFIC ACHIEVEMENT HANDLING
	// ////////////////////////////////////////////

	/**
	 * Adds progress to an achievement. The achievement must be an instace of IAchievemntWP
	 * 
	 * @param id The unique id of the achievement
	 * @param values
	 */
	public synchronized void addProgress(byte id, Object... values) {

		if (this.map.get(id) instanceof IAchievementWP) {
			((IAchievementWP) this.map.get(id)).addProgress(values);
		}
	}

	// ///////////////////////
	// RESET
	// ///////////////////////

	/**
	 * Deletes all achievements for all players
	 */
	public static void deleteAllProgress() {

		FileManagement.deleteDirectory(new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH));
	}

}
