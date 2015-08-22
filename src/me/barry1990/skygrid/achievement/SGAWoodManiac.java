package me.barry1990.skygrid.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;


final class SGAWoodManiac extends IAchievementWP {
	
	private static final String name = "Wood Maniac";

	private ArrayList<Material> progress;
	
	public SGAWoodManiac(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
		this.progress = new ArrayList<Material>();
	}
	
	@Override
	protected void save(FileOutputStream out) throws IOException {
		if (this.hasAchievement()) return;
		for (Material m : this.progress) {
			out.write(SGAManiacConst.valueOf(m.name()).b);
		}
	}

	@Override
	protected void load(FileInputStream in) throws IOException {
		int input;
		while (((input = in.read()) != -1) && (input != SkyGridConst.END)) {
			Material m = SGAManiacConst.getSGAManiacConstFromByte((byte)input).m;
			this.progress.add(m);			
		}
		
	}

	@Override
	public Byte getId() {
		return SGAIDENTIFIER.WOOD_MANIAC;
	}
	
	@Override
	protected String getName() {
		return SGAWoodManiac.name;
	}
	
	
	public void addMaterial(Material m) {
		if (this.hasAchievement()) 
			return;
		
		if (!this.progress.contains(m)) {
			this.progress.add(m);
			this.saveEverything();
		}
		if (this.progress.size() == 5) {	
			this.award();;
			this.progress.clear();
		}
	}

	

}
