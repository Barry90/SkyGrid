package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


final class SGAThatWasClose extends IAchievementNP {
	
	private static final String name = "That was close";

	public SGAThatWasClose(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.THAT_WAS_CLOSE;
	}

	@Override
	protected String getName() {
		return SGAThatWasClose.name;
	}

}
