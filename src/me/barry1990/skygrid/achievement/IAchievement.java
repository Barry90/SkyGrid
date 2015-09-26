package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.TitleManager;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;


abstract class IAchievement {
	
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
