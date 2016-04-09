package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


final class SGAOnTopOfTheWorld extends IAchievementNP {
	
	private static final String name = "On Top Of The World";

	public SGAOnTopOfTheWorld(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.ON_TOP_OF_THE_WORLD;
	}

	@Override
	protected String getName() {
		return SGAOnTopOfTheWorld.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.AIR, 1);
	}

}
