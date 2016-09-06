package me.barry1990.skygrid.level;

import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.achievement.IAchievement;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Recipe;

public interface ISkyGridLevelInterface extends IChunkDataGenerator, ISkyGridBlockPopulator {

	public void onEnabled();

	public void onDisabled();

	public String getLevelName();

	public int getUniqueId();

	public Location generateSkyGridSpawnLocation(World skyGridWorld);

	public List<Recipe> registerRecipes();

	public List<IAchievement> getAchievements(UUID playeruuid);

	public void onAchievementAwardedEvent(IAchievement achievement, UUID playeruuid);

	public boolean haveSkyGridAltar();

	public ISkyGridAlter getSkyGridAltar();

	public IPlayerThreads getPlayerThreads(UUID playeruuid);
}
