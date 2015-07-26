package me.barry1990.skygrid;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;


public class SkyGridGenerator extends ChunkGenerator {
	
	static SkyGridChunkGeneratorOverWorld overworldGenerator;
	static SkyGridChunkGeneratorNether netherGenerator;
	
	@Override
	public short[][] generateExtBlockSections(World world, Random random,
			int chunkX, int chunkZ, BiomeGrid biomes) {			
		
		ChunkWithBlockList chunk = null;
		try {
		switch (world.getEnvironment()) {
		case NORMAL: {
			// start the threads
			if (SkyGridGenerator.overworldGenerator == null) {
				SkyGridGenerator.overworldGenerator = new SkyGridChunkGeneratorOverWorld(world.getMaxHeight());
				SkyGridGenerator.overworldGenerator.start();
			}
			chunk = overworldGenerator.getChunk();	
			break;
		}
		case NETHER :	{
			// start the threads
			if (SkyGridGenerator.netherGenerator == null) {
				SkyGridGenerator.netherGenerator = new SkyGridChunkGeneratorNether(128);
				SkyGridGenerator.netherGenerator.start();
			}
			chunk = netherGenerator.getChunk();	
			break;
		}
		case THE_END :
			break;
		}
		}
		catch (InterruptedException ex) {
			ex.printStackTrace();
			return new short[world.getMaxHeight() / 16][4096]; //world height / chunk part height (=16)
		}
		
		if (chunk != null) {
			String key = chunkX+";"+chunkZ;			
			SkyGrid.blockQueue_normal_put(key,chunk.list);	
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
