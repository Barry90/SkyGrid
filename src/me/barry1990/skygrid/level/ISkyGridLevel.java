package me.barry1990.skygrid.level;

/*import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.inventory.Recipe;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.generators.RandomMetaDataGenerator;
import me.barry1990.skygridlevel.achievements.SGAIDENTIFIER;
import me.barry1990.utils.BarrysLogger;
*/

@Deprecated
public abstract class ISkyGridLevel implements IChunkDataGenerator {
	
	/*protected Random random;
	protected RandomMetaDataGenerator randomMetaData;
	
	ISkyGridLevel() {
		this.random = new Random();
		this.randomMetaData = new RandomMetaDataGenerator();
	}
	
	void prepareLevel() {
		List<Recipe> recipes = this.registerRecipes();
		if (recipes != null) {
			for (Recipe recipe : recipes) {
				SkyGrid.sharedInstance().getServer().addRecipe(recipe);
				BarrysLogger.infoEnum("SkyGrid recipes added for", recipe.getResult().getType());
			}
		}
	}
	
	abstract public Location generateSkyGridSpawnLocation();
	abstract public ISkyGridAlter getSkyGridAltar();
	abstract public IPlayerThreads getPlayerThreads(UUID playeruuid);
	abstract List<Recipe> registerRecipes();
	abstract public boolean isAchievementAvailable(SGAIDENTIFIER sga_id);
*/

}
