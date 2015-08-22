package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


class SGAHotBucket extends IAchievementNP {
	
	private static final String name = "Hot Bucket";

	public SGAHotBucket(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.HOT_BUCKET;
	}

	@Override
	protected String getName() {
		return SGAHotBucket.name;
	}

}
