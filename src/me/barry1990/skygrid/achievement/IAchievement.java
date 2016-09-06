package me.barry1990.skygrid.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.TitleManager;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


abstract public class IAchievement {
	
	final protected static String AWARDED = "§2 Owned.";
	final protected static String NOT_OWNED = "§4 Not owned.";
	
	private boolean hasAchievement;
	private UUID playeruuid;
	
	public IAchievement(UUID playeruuid) {
		this.hasAchievement = false;
		this.playeruuid = playeruuid;
	}
	
	abstract protected byte getUniqueId();
	abstract protected String getName();
	abstract protected ItemStack getAchievementItem();
	abstract protected List<String> getDescription();
	
	final static protected void registerEvent(Listener event) {
		SkyGrid.registerEvent(event);
	}
	
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
	
	final ItemStack getUnawardedItem() {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6§l" + this.getName());
		meta.setLore(this.createDescription());
		item.setItemMeta(meta);
		return item;
	}
	
	protected List<String> createDescription() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(this.hasAchievement() ? AWARDED : NOT_OWNED);
		List<String> des = this.getDescription();
		
		if (des != null)
			lore.addAll(des);
		
		return lore;
	}
	
	final synchronized void award() {
		this.hasAchievement = true;
		this.saveEverything();
		BarrysLogger.info(this,String.format("%s got achievement: %s", Bukkit.getPlayer(this.playeruuid).getName(),this.getName()));
		TitleManager.sendActionBar(Bukkit.getPlayer(this.playeruuid), "Achievement get: §4" + this.getName());
		//Bukkit.getPlayer(this.playeruuid).sendMessage("Achievement get: §4" + this.getName());
		Bukkit.getServer().broadcastMessage(String.format("§f%s §agot the achiement: §4%s",Bukkit.getPlayer(this.playeruuid).getName(), this.getName()));
	}
	
	final protected boolean hasAchievement() {
		return hasAchievement;
	}
	
	final synchronized void setAchievementAwarded() {
		this.hasAchievement = true;
	}
	
	final protected void saveEverything() {
		SkyGridPlayerManager.saveAchievementsForPlayer(playeruuid);
	}
	
	final protected UUID getPlayerUUID() {
		return this.playeruuid;
	}

}
