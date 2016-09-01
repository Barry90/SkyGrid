package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class SGAGoDeeper extends IAchievementNP {
	
	private static final String name = "Go Deeper";

	public SGAGoDeeper(HashMap<SGAIDENTIFIER, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected SGAIDENTIFIER getId() {
		return SGAIDENTIFIER.GO_DEEPER;
	}

	@Override
	protected String getName() {
		return SGAGoDeeper.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.PORTAL, 1);
	}

}
