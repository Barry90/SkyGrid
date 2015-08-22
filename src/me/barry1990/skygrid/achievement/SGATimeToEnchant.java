package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


final class SGATimeToEnchant extends IAchievementNP {
	
	private static final String name = "Time To Enchant";

	public SGATimeToEnchant(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.TIME_TO_ENCHANT;
	}

	@Override
	protected String getName() {
		return SGATimeToEnchant.name;
	}

}
