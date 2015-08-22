package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;


final class SGAInfiniteWaterSource extends IAchievementNP {
	
	private static final String name = "Infinite Water Source?";
	
	public SGAInfiniteWaterSource(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}
	
	@Override
	public Byte getId() {
		return SGAIDENTIFIER.INFINITE_WATER_SOURCE;
	}
	
	@Override
	protected String getName() {
		return SGAInfiniteWaterSource.name;
	}

}
