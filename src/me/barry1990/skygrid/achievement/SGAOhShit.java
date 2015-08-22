package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


final class SGAOhShit extends IAchievementNP {
	
	private static final String name = "Oh Sh**!";

	public SGAOhShit(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}
	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.OH_SHIT;
	}
	@Override
	protected String getName() {
		return SGAOhShit.name;
	}

}
