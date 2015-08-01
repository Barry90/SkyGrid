package me.barry1990.skygrid.generators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;


public class SkyGridGenerator extends ChunkGenerator {
	
	private static SkyGridGenerator sharedInstance;
	
	private static SkyGridChunkGeneratorOverWorld overworldGenerator;
	
	private static HashMap<String,List<ComplexBlock>> blockQueue;
	private static HashMap<String,List<ComplexBlock>> blockQueue_nether;
	
	private SkyGridGenerator() {
		//initilize members
		SkyGridGenerator.blockQueue = new HashMap<String,List<ComplexBlock>>();
		SkyGridGenerator.blockQueue_nether = new HashMap<String,List<ComplexBlock>>();
		
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
			}
		}
		catch (InterruptedException ex) {
			ex.printStackTrace();
			return new short[world.getMaxHeight() / 16][4096]; //world height / chunk part height (=16)
		}
		
		if (chunk != null) {
			String key = chunkX+";"+chunkZ;			
			SkyGridGenerator.blockQueue_normal_put(key,chunk.list);	
			return chunk.chunk;
		}
		return new short[world.getMaxHeight() / 16][4096]; //world height / chunk part height (=16)
		
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator)new SkyGridBlockPopulator());
	}
	
	@Override
    public Location getFixedSpawnLocation(World world, Random random)
    {
		int x = random.nextInt(100)-50;
		int z = random.nextInt(100)-50;
        return new Location(world, x*4+1, 122, z*4+1);
    }
	
	//synchronized method for accessing the HashMaps
	public static synchronized List<ComplexBlock> blockQueue_normal_get(String key) {
		synchronized (SkyGridGenerator.blockQueue) {
			return SkyGridGenerator.blockQueue.get(key);
		}
	}
	
	public static synchronized void blockQueue_normal_put(String key, List<ComplexBlock> list) {
		synchronized (SkyGridGenerator.blockQueue) {
			SkyGridGenerator.blockQueue.put(key,list);
		}
	}
	
	public static synchronized void blockQueue_normal_remove(String key) {
		synchronized (SkyGridGenerator.blockQueue) {
			SkyGridGenerator.blockQueue.remove(key);
		}
	}
	
	public static synchronized List<ComplexBlock> blockQueue_nether_get(String key) {
		synchronized (SkyGridGenerator.blockQueue_nether) {
			return SkyGridGenerator.blockQueue_nether.get(key);
		}
	}
	
	public static synchronized void blockQueue_nether_put(String key, List<ComplexBlock> list) {
		synchronized (SkyGridGenerator.blockQueue_nether) {
			SkyGridGenerator.blockQueue_nether.put(key,list);
		}
	}
	
	public static synchronized void blockQueue_nether_remove(String key) {
		synchronized (SkyGridGenerator.blockQueue_nether) {
			SkyGridGenerator.blockQueue_nether.remove(key);
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
