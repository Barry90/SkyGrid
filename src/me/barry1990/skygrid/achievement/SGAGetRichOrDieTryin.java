package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


final class SGAGetRichOrDieTryin extends IAchievementNP {
	
	private static final String name = "Get Rich Or Die Tryin";

	public SGAGetRichOrDieTryin(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.GET_RICH_OR_DIE_TRYIN;
	}

	@Override
	protected String getName() {
		return SGAGetRichOrDieTryin.name;
	}

}
