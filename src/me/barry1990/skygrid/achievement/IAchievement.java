package me.barry1990.skygrid.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.TitleManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * IAchievement - Implement this class to create a new achievement.
 * @author Barry1990
 */
abstract public class IAchievement {
	
	final protected static String AWARDED = "§2 Owned.";
	final protected static String NOT_OWNED = "§4 Not owned.";
	
	private boolean hasAchievement;
	private UUID playeruuid;
	
	/**
	 * Creates a new instance of IAchievement
	 * @param playeruuid The UUID of the player
	 */
	public IAchievement(UUID playeruuid) {
		this.hasAchievement = false;
		this.playeruuid = playeruuid;
	}
	
	/**
	 * @return A unique ID for this IAchievement type
	 */
	abstract protected byte getUniqueId();
	
	/**
	 * @return The name of the Achievement
	 */
	abstract protected String getName();
	
	/**
	 * @return The ItemStack that will be displayed in the achievement GUI
	 */
	abstract protected ItemStack getAchievementItem();
	
	/**
	 * @return The description of this achievement that will be shown as lore on your ItemStack that will be displayed in the achievement GUI
	 */
	abstract protected List<String> getDescription();
	
	/**
	 * Call this method in a static context the register eventlisterners for your achievement
	 * @param event The Listener that should be registered
	 */
	final static protected void registerEvent(Listener event) {
		SkyGrid.registerEvent(event);
	}
	
	/**
	 * @return The final ItemStack that will be displayed in the achievement GUI
	 */
	protected ItemStack getAchievementInventoryItem() {		
		if (this.hasAchievement) {
			ItemStack item = this.getAchievementItem();
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§6§l" + this.getName());
			meta.setLore(this.createDescription());
			item.setItemMeta(meta);
			return item;
		}
		else
			return this.getUnawardedItem();
	}
	
	/**
	 * @return The Itemstack that is shown if the achievement is not awarded
	 */
	final ItemStack getUnawardedItem() {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6§l" + this.getName());
		meta.setLore(this.createDescription());
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * @return The Lore of an ItemStack
	 */
	protected List<String> createDescription() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(this.hasAchievement() ? AWARDED : NOT_OWNED);
		List<String> des = this.getDescription();
		
		if (des != null)
			lore.addAll(des);
		
		return lore;
	}
	
	/**
	 * This method award the achievement for the player assosciated with it
	 * <p>IMPORTANT: DO NOT CALL THIS METHOD FROM YOUR EVENTLISTENERS. USE THE SKYGRIDACHIEVEMENTS#AWARD METHOD</p>
	 * @see SkyGridAchievements#award(byte)
	 */
	final synchronized void award() {
		this.hasAchievement = true;
		this.saveEverything();
		BarrysLogger.info(this,String.format("%s got achievement: %s", Bukkit.getPlayer(this.playeruuid).getName(),this.getName()));
		TitleManager.sendActionBar(Bukkit.getPlayer(this.playeruuid), "Achievement get: §4" + this.getName());
		//Bukkit.getPlayer(this.playeruuid).sendMessage("Achievement get: §4" + this.getName());
		Bukkit.getServer().broadcastMessage(String.format("§f%s §agot the achiement: §4%s",Bukkit.getPlayer(this.playeruuid).getName(), this.getName()));
	}
	
	/**
	 * @return True if the player got this achievement
	 */
	final protected boolean hasAchievement() {
		return hasAchievement;
	}
	
	/**
	 * Sets this achievement as awarded
	 * <p>IMPORTANT: DO NOT CALL THIS METHOD FROM YOUR EVENTLISTENERS. USE THE SKYGRIDACHIEVEMENTS#AWARD METHOD</p>
	 * @see SkyGridAchievements#award(byte)
	 */
	final synchronized void setAchievementAwarded() {
		this.hasAchievement = true;
	}
	
	/**
	 * Save the progress of all achievements
	 */
	final protected void saveEverything() {
		SkyGrid.getPlayerManager().saveAchievementsForPlayer(playeruuid);
	}
	
	/**
	 * @return The UUID associated with this achievement
	 */
	final protected UUID getPlayerUUID() {
		return this.playeruuid;
	}

}
