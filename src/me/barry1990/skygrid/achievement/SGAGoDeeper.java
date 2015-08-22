package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


public class SGAGoDeeper extends IAchievementNP {
	
	private static final String name = "Go Deeper";

	public SGAGoDeeper(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.GO_DEEPER;
	}

	@Override
	protected String getName() {
		return SGAGoDeeper.name;
	}

}
