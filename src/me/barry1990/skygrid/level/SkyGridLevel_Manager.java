package me.barry1990.skygrid.level;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ServiceLoader;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.generators.SkyGridChunkGenerator;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.inventory.Recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * SkyGridLevel_Manager - This class manages the SykGrid level
 * 
 * @author Barry1990
 */
public final class SkyGridLevel_Manager implements Listener {

	private int						x;
	private int						z;
	private int						id;

	private SkyGridChunkGenerator	chunkGenerator;
	private ISkyGridLevelInterface	level;
	private String					PATH	= "data.dat";

	/**
	 * Creates a new instance of SkyGridLevel_Manager
	 */
	public SkyGridLevel_Manager() {

		SkyGrid.registerEvent(this);
		this.loadLevelInfos();
		this.loadLevel(this.id);
	}

	// ////////////////////////////////////////////////////
	// LOAD INFOS
	// ////////////////////////////////////////////////////

	/**
	 * load level information from file
	 */
	private void loadLevelInfos() {

		// TODO maybe read from SQL DB?
		File file = new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH);
		if (!file.exists()) {
			this.createDefault();
			return;
		}

		try (FileReader freader = new FileReader(file)) {

			JsonParser parser = new JsonParser();
			JsonObject e = (JsonObject) parser.parse(freader);

			this.id = e.get("id").getAsInt();
			this.x = e.get("chunk_x").getAsInt();
			this.z = e.get("chunk_z").getAsInt();

			BarrysLogger.info(this, "level infos loaded");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates the default level file
	 */
	private void createDefault() {

		File file = new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH);

		Random r = new Random();
		this.id = 1;
		this.x = r.nextInt(5000) - 2500;
		this.z = r.nextInt(5000) - 2500;

		String json = String.format("{\"id\":%d,\"chunk_x\":%d,\"chunk_z\":%d}", this.id, this.x, this.z);
		file.getParentFile().mkdirs();

		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(json.getBytes(StandardCharsets.UTF_8), 0, json.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BarrysLogger.info(this, "altar infos created");
	}

	// ////////////////////////////////////////////////////
	// SKYGRID LEVEL
	// ////////////////////////////////////////////////////

	/**
	 * This methods load the SkyGridLevelInterface with the given unique level id from the external ressource
	 * 
	 * @param id The unique id of the level
	 */
	private void loadLevel(int id) {

		try {
			String levelpath = SkyGrid.sharedInstance().getDataFolder().getAbsolutePath() + File.separator + "SkyGridLevels";
			File levelDir = new File(levelpath);
			if (levelDir.getParentFile().mkdirs()) {
				BarrysLogger.info(this, "Directory created for levels.");
			}

			ArrayList<URL> levelList = new ArrayList<URL>();
			File[] flist = levelDir.listFiles(new FileFilter() {

				public boolean accept(File file) {

					return file.getPath().toLowerCase().endsWith(".jar");
				}
			});

			for (File plugin : flist) {
				try {
					levelList.add(plugin.toURI().toURL());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

			URLClassLoader classloader = new URLClassLoader(levelList.toArray(new URL[0]), getClass().getClassLoader());
			ServiceLoader<ISkyGridLevelInterface> loader = ServiceLoader.load(ISkyGridLevelInterface.class, classloader);
			Iterator<ISkyGridLevelInterface> levels = loader.iterator();

			while (levels.hasNext()) {
				ISkyGridLevelInterface level = levels.next();

				if (id == level.getUniqueId()) {
					this.level = level;
					BarrysLogger.info(this, "level found: " + level.getClass().getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (this.level == null) {
			throw new RuntimeException("No level was found in the SkyGridLevels directory with id " + id);
		}

		this.prepareLevel();
	}

	/**
	 * Prepare the level after loading
	 */
	private void prepareLevel() {

		List<Recipe> recipes = this.level.registerRecipes();
		if (recipes != null) {
			for (Recipe recipe : recipes) {
				SkyGrid.sharedInstance().getServer().addRecipe(recipe);
				BarrysLogger.infoEnum(this.level.getLevelName() + ": Recipes added for", recipe.getResult().getType());
			}
		}

		this.level.onEnabled();
	}

	/**
	 * @return The skygrid level
	 */
	public ISkyGridLevelInterface getLevel() {

		return this.level;
	}

	/**
	 * Reload the level.
	 */
	public void reload() {

		BarrysLogger.info(this, "Reloading...");
		this.dispose();

		this.loadLevelInfos();
		this.loadLevel(this.id);
		this.chunkGenerator = new SkyGridChunkGenerator();

	}

	/**
	 * This method releases all loaded resources of this manager
	 */
	public void dispose() {

		BarrysLogger.info(this, "Disposing...");
		if (this.chunkGenerator != null)
			this.chunkGenerator.dispose();
		this.level.onDisabled();
		this.level = null;
	}

	// ////////////////////////////////////////////////////
	// CHUNK GENERATION
	// ////////////////////////////////////////////////////

	/**
	 * @return A singleton instance of the SkyGridChunkGenerator
	 */
	public SkyGridChunkGenerator getGenerator() {

		return (this.chunkGenerator != null) ? this.chunkGenerator : (this.chunkGenerator = new SkyGridChunkGenerator());
	}

	/**
	 * Get the ChunkData of the Altar of this level
	 * 
	 * @param world The skygrid world
	 * @param chunkdata The prepared ChunkData for the Chunk
	 * @return ChunkData of the Altar or chunkdata if there is no altar
	 */
	public ChunkData getAltarChunkData(World world, ChunkData chunkdata) {

		if (this.level.haveSkyGridAltar() && this.level.getSkyGridAltar() != null)
			return this.level.getSkyGridAltar().getChunkData(world, chunkdata);
		else
			return chunkdata;
	}

	/**
	 * Test if the chunk on the given position is the altar chunk
	 * 
	 * @param x x-coordinate of the chunk
	 * @param z z-coordinate of the chunk
	 * @return True if the chunk coordinates match the coordinates of the altar chunk
	 */
	public boolean isAltarChunk(int x, int z) {

		return ((this.x == x) && (this.z == z));
	}

	/**
	 * Test if the chunk is the altar chunk
	 * 
	 * @param chunk The Chunk to test
	 * @return True if the chunk coordinates match the coordinates of the altar chunk
	 */
	public boolean isAltarChunk(Chunk chunk) {

		return ((this.x == chunk.getX()) && (this.z == chunk.getZ()));
	}

	// ////////////////////////////////////////////////////
	// EVENTHANDLER
	// ////////////////////////////////////////////////////

	@EventHandler
	private void skyGridAlterBlockBreakEvent(BlockBreakEvent e) {

		if (!this.isAltarChunk(e.getBlock().getChunk().getX(), e.getBlock().getChunk().getZ()))
			return;

		e.setCancelled(true);

	}

	@EventHandler
	private void skyGridAlterBlockPlaceEvent(BlockPlaceEvent e) {

		if (!this.isAltarChunk(e.getBlock().getChunk().getX(), e.getBlock().getChunk().getZ()))
			return;

		if (this.level.getSkyGridAltar() != null)
			this.level.getSkyGridAltar().alterChunkBlockPlaceEvent(e);

	}

}
