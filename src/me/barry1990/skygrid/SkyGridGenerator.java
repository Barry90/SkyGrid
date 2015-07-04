package me.barry1990.skygrid;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.CropState;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.Crops;
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
		
		
		switch (world.getEnvironment()) {
		case NORMAL:	return this.generateOverworldChunk(world.getMaxHeight(), random, chunkX, chunkZ);	
		case NETHER :	return this.generateNetherChunk(world.getMaxHeight(), random, chunkX, chunkZ);
		case THE_END :
			break;
		}
		
		
		return new short[world.getMaxHeight() / 16][]; //world height / chunk part height (=16)
		
	}
	
	private short[][] generateOverworldChunk(int worldMaxHeight, Random random, int chunkX, int chunkZ) {
		
		short[][] result = new short[worldMaxHeight / 16][]; //world height / chunk part height (=16)
		
		/* generate empty queue for chunk*/
		List<ComplexBlock> list = new LinkedList<ComplexBlock>();
		String key = chunkX+";"+chunkZ;
		SkyGrid.blockQueue.put(key,list);
		
		/* generate the grid */
		
		for (int y = 1; y < worldMaxHeight; y=y+4) {
		
			for (int z = 1; z < 16; z=z+4) {
							
				for (int x = 1; x < 16; x=x+4) {
					
					Material material = BlockList.getRandomMaterial(random);
					
					this.setBlock(result, x, y, z, material);
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
							list.add(cb);
							break;
						}
						/*case SOIL: {
							Material crop = RandomBlockAppendix.getRandomCrop(random);
							this.setBlock(result, x, y+1, z, crop);
							ComplexBlock cb = new ComplexBlock(material,new Crops(CropState.SEEDED), x+chunkX*16, y+1, z+chunkZ*16);
							list.add(cb);
							break;
						}	*/					
						case SAND: {
							if (random.nextInt(100) <= 2) {
								this.setBlock(result, x, y+1, z, Material.SUGAR_CANE_BLOCK);
								switch (random.nextInt(4)) {
									case 0 : {this.setBlock(result, x+1, y, z, Material.STATIONARY_WATER); break;}
									case 1 : {this.setBlock(result, x-1, y, z, Material.STATIONARY_WATER); break;}
									case 2 : {this.setBlock(result, x, y, z+1, Material.STATIONARY_WATER); break;}
									case 3 : {this.setBlock(result, x, y, z-1, Material.STATIONARY_WATER); break;}
								}
							}
							break;
						}
						case MYCEL: {
							Material mushroom = random.nextBoolean() ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM;
							this.setBlock(result, x, y+1, z, mushroom);							
							break;
						}
						case DIRT: {
							if (random.nextInt(100) <= 2) {
								this.setBlock(result, x, y+1, z, Material.SAPLING);
								ComplexBlock cb = new ComplexBlock(material,new Tree(RandomMetaDataGenerator.getTreeSpecies(Material.SAPLING, random)), x+chunkX*16, y+1, z+chunkZ*16);
								list.add(cb);								
							}
							break;
						}
						case MOB_SPAWNER: {
							ComplexBlock cb = new ComplexBlock(material,null, x+chunkX*16, y, z+chunkZ*16);
							list.add(cb);
							break;
						}
						
						default:
							break;
					}
					if (materialdata != null) {
						ComplexBlock cb = new ComplexBlock(material,materialdata, x+chunkX*16, y, z+chunkZ*16);
						list.add(cb);
					}
				}
				
			}
			
		}
		
		return result;
		
	}
	
	private short[][] generateNetherChunk(int worldMaxHeight, Random random, int chunkX, int chunkZ) {
		
		
		short[][] result = new short[worldMaxHeight / 16][]; //world height / chunk part height (=16)
		
		/* generate empty queue for chunk*/
		List<ComplexBlock> list = new LinkedList<ComplexBlock>();
		String key = chunkX+";"+chunkZ;
		SkyGrid.blockQueue_nether.put(key,list);
		
		/* generate the grid */
		
		for (int y = 1; y < 128; y=y+4) {
		
			for (int z = 1; z < 16; z=z+4) {
							
				for (int x = 1; x < 16; x=x+4) {
					
					Material material = y == 125 || y == 1 ? Material.BEDROCK : BlockList.getRandomMaterialForNether(random);
					
					this.setBlock(result, x, y, z, material);
					MaterialData materialdata = null;
					switch (material) {
						case JACK_O_LANTERN:
						case PUMPKIN : {
							materialdata = RandomMetaDataGenerator.getPumpkin(random);
							break;
						}						
						case CHEST: {
							ComplexBlock cb = new ComplexBlock(material,null, x+chunkX*16, y, z+chunkZ*16);
							list.add(cb);
							break;
						}				
						case MOB_SPAWNER: {
							ComplexBlock cb = new ComplexBlock(material,null, x+chunkX*16, y, z+chunkZ*16);
							list.add(cb);
							break;
						}						
						default:
							break;
					}
					if (materialdata != null) {
						ComplexBlock cb = new ComplexBlock(material,materialdata, x+chunkX*16, y, z+chunkZ*16);
						list.add(cb);
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
	
    void setBlock(short[][] result, int x, int y, int z, Material material) {
	    // is this chunk part already initialized?
	    if (result[y >> 4] == null) {
		    // Initialize the chunk part
		    result[y >> 4] = new short[4096];
	    }
	    // set the block
	    result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (short) material.getId();
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
	    	if (m == Material.SAPLING) {
	    		int x = random.nextInt(TreeSpecies.class.getEnumConstants().length);
		        return TreeSpecies.class.getEnumConstants()[x];
	    	}	    	
	    	//we should never get here
	    	return TreeSpecies.GENERIC;
	    	
	    }
	    
    }
    
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
    
}
