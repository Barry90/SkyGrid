package me.barry1990.skygrid.generators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.World;
import org.bukkit.World.Environment;
//import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;


public class SkyGridGenerator extends ChunkGenerator {
	
	private static SkyGridGenerator sharedInstance;
	
	private static SkyGridChunkGeneratorOverWorld overworldGenerator;
	
	private static HashMap<String,List<ComplexBlock>> blockQueue;
	
	private SkyGridGenerator() {
		//initilize members
		SkyGridGenerator.blockQueue = new HashMap<String,List<ComplexBlock>>();
		
		// start the thread
		if (SkyGridGenerator.overworldGenerator == null) {
			SkyGridGenerator.overworldGenerator = new SkyGridChunkGeneratorOverWorld(256);
			BarrysLogger.info(this, "Start the SkyGridChunkGenerator-Thread");
			SkyGridGenerator.overworldGenerator.start();
		}
	}
	
	public static SkyGridGenerator sharedInstance() {
		if (SkyGridGenerator.sharedInstance == null) {
			SkyGridGenerator.sharedInstance = new SkyGridGenerator();
		}
		return SkyGridGenerator.sharedInstance;
	}
	
	@Override
	public short[][] generateExtBlockSections(World world, Random random,
			int chunkX, int chunkZ, BiomeGrid biomes) {			
		
		ChunkWithBlockList chunk = null;
		try {
			if (world.getEnvironment() == Environment.NORMAL) {
				chunk = overworldGenerator.getChunk();
				
				// Set Biome tp PLAINS
//				for (int x=0; x<16; x++) {
//					for (int z=0; z<16; z++) {
//						biomes.setBiome(x, z, Biome.PLAINS);
//					}
//				}
			}
		}
		catch (InterruptedException ex) {
			ex.printStackTrace();
			return new short[world.getMaxHeight() / 16][4096]; //world height / chunk part height (=16)
		}
		
		if (chunk != null) {
			String key = chunkX+";"+chunkZ;			
			SkyGridGenerator.blockQueue_put(key,chunk.list);	
			return chunk.chunk;
		}
		return new short[world.getMaxHeight() / 16][4096]; //world height / chunk part height (=16)
		
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator)new SkyGridBlockPopulator());
	}
	
//	@Override
//	public Location getFixedSpawnLocation(World world, Random random)
//	{
//		int x = random.nextInt(100)-50;
//		int z = random.nextInt(100)-50;
//		return new Location(world, x*4+1, 122, z*4+1);
//	}
	
	//synchronized method for accessing the HashMaps
	public static synchronized List<ComplexBlock> blockQueue_get(String key) {
		synchronized (SkyGridGenerator.blockQueue) {
			return SkyGridGenerator.blockQueue.get(key);
		}
	}
	
	public static synchronized void blockQueue_put(String key, List<ComplexBlock> list) {
		synchronized (SkyGridGenerator.blockQueue) {
			SkyGridGenerator.blockQueue.put(key,list);
		}
	}
	
	public static synchronized void blockQueue_remove(String key) {
		synchronized (SkyGridGenerator.blockQueue) {
			SkyGridGenerator.blockQueue.remove(key);
		}
	}

	/*
	private static class RandomBlockAppendix {
		
		private static Material croplist[] = null;
		
		public static Material getRandomCrop(Random random) {
			if (croplist == null) {
				croplist = new Material[] {
					Material.CROPS,
					Material.CARROT,
					Material.POTATO
				};
			}
			
			return croplist[random.nextInt(croplist.length)];
		}
	}
	*/
	
}
