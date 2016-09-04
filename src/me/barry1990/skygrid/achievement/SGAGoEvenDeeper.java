package me.barry1990.skygrid.achievement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


final class SGAGoEvenDeeper extends IAchievementNP {
	
	private static final String name = "Go Even Deeper!";

	public SGAGoEvenDeeper(HashMap<SGAIDENTIFIER, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected SGAIDENTIFIER getId() {
		return SGAIDENTIFIER.GO_EVEN_DEEPER;
	}

	@Override
	protected String getName() {
		return SGAGoEvenDeeper.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.ENDER_PORTAL, 1);
	}

	@Override
	protected List<String> getDescription() {
		if (this.hasAchievement())
			return Arrays.asList("This achievement allows you", "to go into the end layer.");
		else
			return Arrays.asList("Get at least 10", "SkyGridAchievements");
	}
}
