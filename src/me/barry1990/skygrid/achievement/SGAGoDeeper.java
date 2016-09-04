package me.barry1990.skygrid.achievement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	protected List<String> getDescription() {
		if (this.hasAchievement())
			return Arrays.asList("This achievement allows you", "to go into the nether layer.");
		else
			return Arrays.asList("Get at least 6", "SkyGridAchievements");
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.PORTAL, 1);
	}

}
