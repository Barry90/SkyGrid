package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


abstract class IAchievementNP extends IAchievement {

	public IAchievementNP(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}
	
}
