package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


final class SGAAVeryBigEgg extends IAchievementNP {
	
	private static final String name = "A Very Big Egg";

	public SGAAVeryBigEgg(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.A_VERY_BIG_EGG;
	}

	@Override
	protected String getName() {
		return SGAAVeryBigEgg.name;
	}

}
