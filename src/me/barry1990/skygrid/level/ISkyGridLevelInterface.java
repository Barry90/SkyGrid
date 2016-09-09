package me.barry1990.skygrid.level;

import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.achievement.IAchievement;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Recipe;

/**
 * ISkyGridLevelInterface - Implement this interface as service to create a new skygrid levevl
 * 
 * @author Barry1990
 */
public interface ISkyGridLevelInterface extends IChunkDataGenerator, ISkyGridBlockPopulator {

	/**
	 * Setup your level
	 */
	public void onEnabled();

	/**
	 * Cleanup the level
	 */
	public void onDisabled();

	/**
	 * @return The name of this level
	 */
	public String getLevelName();

	/**
	 * @return A unique id of this level
	 */
	public int getUniqueId();

	/**
	 * Depending on your world generation, you have return a valid spawn location for new players in your world
	 * @param skyGridWorld The SkyGrid world the player will spawn in
	 * @return A spawn location
	 */
	public Location generateSkyGridSpawnLocation(World skyGridWorld);

	/**
	 * @return A list of Recipes that should be added to the default minecraft recipes
	 */
	public List<Recipe> registerRecipes();

	/**
	 * THis method returns new instances of your achievements for this world for a player
	 * @param playeruuid The uuid of player
	 * @return a list of achievements
	 */
	public List<IAchievement> getAchievements(UUID playeruuid);

	/**
	 * This event get called if a player got one of your defined achievements
	 * @param achievement The achievement that the player got
	 * @param playeruuid the uuid of the player
	 */
	public void onAchievementAwardedEvent(IAchievement achievement, UUID playeruuid);

	/**
	 * @return True if this world has an altar
	 */
	public boolean haveSkyGridAltar();

	/**
	 * @return The altar of this world
	 */
	public ISkyGridAlter getSkyGridAltar();

	/**
	 * Creates custom PlayerThreads for each player
	 * @param playeruuid The uuid of the player
	 * @return PlayerThreads for the player
	 */
	public IPlayerThreads getPlayerThreads(UUID playeruuid);
}
