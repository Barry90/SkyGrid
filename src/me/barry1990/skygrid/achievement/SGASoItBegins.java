package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


final class SGASoItBegins extends IAchievementNP {
	
private static final String name = "So it begins";
	
	public SGASoItBegins(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	public Byte getId() {
		return SGAIDENTIFIER.SO_IT_BEGINS;
	}
	
	@Override
	protected String getName() {
		return SGASoItBegins.name;
	}

}
