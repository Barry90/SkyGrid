package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


abstract class IAchievementWP extends IAchievement {

	public IAchievementWP(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}
	
	abstract protected void save(FileOutputStream out) throws IOException;
	abstract protected void load(FileInputStream in) throws IOException;

}
