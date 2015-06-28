package me.barry1990.skygrid;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.material.MaterialData;
import org.bukkit.material.MonsterEggs;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Tree;
import org.bukkit.material.Wool;


public class SkyGridGenerator extends ChunkGenerator {
		
	@Override
	public short[][] generateExtBlockSections(World world, Random random,
			int chunkX, int chunkZ, BiomeGrid biomes) {		
		
		short[][] result = new short[world.getMaxHeight() / 16][]; //world height / chunk part height (=16)
		
		/* generate empty queue for chunk*/
		List<ComplexBlock> queue = new LinkedList<ComplexBlock>();
		String key = chunkX+";"+chunkZ;
		SkyGrid.blockQueue.put(key,queue);
		
		/* generate the grid */
		
		for (int y = 1; y < 256; y=y+4) {
		
			for (int z = 1; z < 16; z=z+4) {
							
				for (int x = 1; x < 16; x=x+4) {
					
					Material material = BlockList.getRandomMaterial(random);
					
					this.setBlock(result, x, y, z, (short)material.getId() );
					MaterialData materialdata = null;
					switch (material) {
						case WOOL : {				
							materialdata = RandomMetaDataGenerator.getWool(random);
							break;
						}
						case JACK_O_LANTERN:
						case PUMPKIN : {
							materialdata = RandomMetaDataGenerator.getPumpkin(random);
							break;
						}
						case LOG :
						case LOG_2 : {
							materialdata = RandomMetaDataGenerator.getTree(material, random);
							break;
						}						
						case FURNACE: {
							materialdata = RandomMetaDataGenerator.getDirectionalContainer(material, random);
							break;
						}
						case MONSTER_EGGS: {
							materialdata = RandomMetaDataGenerator.getMonsterEggs(random);
							break;
						}
						case CHEST: {
							ComplexBlock cb = new ComplexBlock(material,null, x+chunkX*16, y, z+chunkZ*16);
							queue.add(cb);
							break;
						}
						default:
							break;
					}
					if (materialdata != null) {
						ComplexBlock cb = new ComplexBlock(material,materialdata, x+chunkX*16, y, z+chunkZ*16);
						queue.add(cb);
					}
				}
				
			}
			
		}
		
		return result;
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
	
    void setBlock(short[][] result, int x, int y, int z, short blkid) {
	    // is this chunk part already initialized?
	    if (result[y >> 4] == null) {
		    // Initialize the chunk part
		    result[y >> 4] = new short[4096];
	    }
	    // set the block
	    result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }
    
    
    
    private static class RandomMetaDataGenerator{    
    	
    	/* Public  */
    	
	    public static Wool getWool(Random random) {
	        int x = random.nextInt(DyeColor.class.getEnumConstants().length);
	        return new Wool (DyeColor.class.getEnumConstants()[x]);
	    }
	    
	 	public static DirectionalContainer getDirectionalContainer(Material m, Random random) {
	 		DirectionalContainer dcon = new DirectionalContainer(m);
	 		dcon.setFacingDirection(RandomMetaDataGenerator.getBlockFaceNESW(random));
	 		return dcon;
	 	}
	 	
	 	public static Pumpkin getPumpkin(Random random) {
	 		return new Pumpkin(RandomMetaDataGenerator.getBlockFaceNESW(random));
	 	}
	 	
	 	public static Tree getTree(Material m, Random random) {
	 		return new Tree(RandomMetaDataGenerator.getTreeSpecies(m, random),RandomMetaDataGenerator.getBlockFaceNESWUD(random));
	 	}
	 	
	 	public static MonsterEggs getMonsterEggs(Random random) {
	 		return random.nextBoolean() ? new MonsterEggs(Material.STONE) : new MonsterEggs(Material.COBBLESTONE);
	 	}	    
	    
	    /* Private */
	    
	    private static BlockFace getBlockFaceNESW(Random random) {	    	
	    	switch (random.nextInt(4)) {
	    		case 0 : return BlockFace.NORTH;
	    		case 1 : return BlockFace.EAST;
	    		case 2 : return BlockFace.SOUTH;
	    		case 3 : return BlockFace.WEST;
	    	}
	    	//we should never get here
	        return BlockFace.NORTH;
	    }
	    
	    private static BlockFace getBlockFaceNESWUD(Random random) {	    	
	    	switch (random.nextInt(6)) {
	    		case 0 : return BlockFace.NORTH;
	    		case 1 : return BlockFace.EAST;
	    		case 2 : return BlockFace.SOUTH;
	    		case 3 : return BlockFace.WEST;
	    		case 4 : return BlockFace.UP;
	    		case 5 : return BlockFace.DOWN;
	    	}
	    	//we should never get here
	        return BlockFace.NORTH;
	    }
	    
	    private static TreeSpecies getTreeSpecies(Material m,Random random) {
	    	if (m == Material.LEAVES | m == Material.LOG) {
	    		switch (random.nextInt(4)) {
		    		case 0 : return TreeSpecies.GENERIC;
		    		case 1 : return TreeSpecies.BIRCH;
		    		case 2 : return TreeSpecies.JUNGLE;
		    		case 3 : return TreeSpecies.DARK_OAK;
	    		}
	    	}
	    	if (m == Material.LEAVES_2 | m == Material.LOG_2) {
	    		return random.nextBoolean() ? TreeSpecies.ACACIA : TreeSpecies.REDWOOD;
	    	}	    	
	    	//we should never get here
	    	return TreeSpecies.GENERIC;
	    	
	    }
	    
    }

    
}
