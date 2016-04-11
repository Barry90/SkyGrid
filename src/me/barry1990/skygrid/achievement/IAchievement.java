package me.barry1990.skygrid.achievement;

import java.util.Arrays;
import java.util.HashMap;
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


abstract class IAchievement {
	
	final protected static String AWARDED = "§2 Owned.";
	final protected static String NOT_OWNED = "§4 Not owned.";
	
	private boolean hasAchievement;
	private UUID playeruuid;
	
	public IAchievement(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		if (map.containsKey(this.getId())) {
			BarrysLogger.warn(this, String.format("duplicated keys for IAchievement : %d", this.getId()));
		}
		map.put(this.getId(), this);
		this.hasAchievement = false;
		this.playeruuid = playeruuid;
	}
	
	abstract protected Byte getId();
	abstract protected String getName();
	abstract protected ItemStack getAchievementItem();
	
	final static void registerEvent(Listener event) {
		SkyGrid.registerEvent(event);
	}
	
	protected ItemStack getAchievementInventoryItem() {		
		if (this.hasAchievement) {
			ItemStack item = this.getAchievementItem();
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§6§l" + this.getName());
			meta.setLore(Arrays.asList(IAchievement.AWARDED));
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
		meta.setLore(Arrays.asList(IAchievement.NOT_OWNED));
		item.setItemMeta(meta);
		return item;
	}
	
	final synchronized void award() {
		this.hasAchievement = true;
		this.saveEverything();
		BarrysLogger.info(this,String.format("%s got achievement: %s", Bukkit.getPlayer(this.playeruuid).getName(),this.getName()));
		TitleManager.sendActionBar(Bukkit.getPlayer(this.playeruuid), "Achievement get: §4" + this.getName());
		Bukkit.getPlayer(this.playeruuid).sendMessage("Achievement get: §4" + this.getName());
		Bukkit.getServer().broadcastMessage(String.format("§f%s §agot the achiement: §4%s",Bukkit.getPlayer(this.playeruuid).getName(), this.getName()));
	}
	
	final boolean hasAchievement() {
		return hasAchievement;
	}
	
	final synchronized void setAchievementAwarded() {
		this.hasAchievement = true;
	}
	
	final void saveEverything() {
		SkyGridPlayerManager.saveAchievementsForPlayer(playeruuid);
	}
	
	final UUID getPlayerUUID() {
		return this.playeruuid;
	}

}
