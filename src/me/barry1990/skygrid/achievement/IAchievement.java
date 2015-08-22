package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.ActionBarAPI;

import org.bukkit.Bukkit;


abstract class IAchievement {
	
	private boolean hasAchievement;
	private UUID playeruuid;
	
	public IAchievement(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		map.put(this.getId(), this);
		this.hasAchievement = false;
		this.playeruuid = playeruuid;
	}
	
	abstract protected Byte getId();
	abstract protected String getName();
	
	final void award() {
		this.hasAchievement = true;
		ActionBarAPI.sendActionBar(Bukkit.getPlayer(this.playeruuid), "Achievement erhalten: §4" + this.getName());
		Bukkit.getPlayer(this.playeruuid).sendMessage("Achievement erhalten: §4" + this.getName());
	}
	
	final boolean hasAchievement() {
		return hasAchievement;
	};
	
	final void setAchievementAwarded() {
		this.hasAchievement = true;
	}

}
