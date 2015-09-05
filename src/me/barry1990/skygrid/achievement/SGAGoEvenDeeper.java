package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


final class SGAGoEvenDeeper extends IAchievementNP {
	
	private static final String name = "Go Even Deeper!";

	public SGAGoEvenDeeper(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.GO_EVEN_DEEPER;
	}

	@Override
	protected String getName() {
		return SGAGoEvenDeeper.name;
	}

}
