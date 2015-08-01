package me.barry1990.skygrid.generators;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Tree;


public class SkyGridChunkGeneratorOverWorld extends Thread {
	
	static final int MAXQUEUE = 15;
    private Queue<ChunkWithBlockList> chunkqueue = new LinkedList<ChunkWithBlockList>();
    private Random random;
    private int worldMaxHeight;
    
    
 
    public SkyGridChunkGeneratorOverWorld(int worldMaxHeight) {
		super();
		this.random = new Random();
		this.worldMaxHeight = worldMaxHeight;
		BarrysLogger.info(this, "worldMaxHeight", worldMaxHeight);
	}

	@Override
    public void run() {
        try {
            while (true) {
            	putChunk();
            }
        } catch (InterruptedException e) {
        }
    }
 
    private synchronized void putChunk() throws InterruptedException {
        while (chunkqueue.size() == MAXQUEUE) {
            wait();
        }
        chunkqueue.add(this.generateOverworldChunk());
        notify();
        //Later, when the necessary event happens, the thread that is running it calls notify() from a block synchronized on the same object.
    }
 
    // Called by Consumer
    public synchronized ChunkWithBlockList getChunk() throws InterruptedException {
        notify();
        while (chunkqueue.size() == 0) {
        	BarrysLogger.info(this, "queue is empty. waiting...");
            wait();//By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
        }
        return chunkqueue.remove();
    }
    
    private ChunkWithBlockList generateOverworldChunk() {
		
    	ChunkWithBlockList result = new ChunkWithBlockList(this.worldMaxHeight);
		
		/* generate the grid */
		
		for (int y = 1; y < this.worldMaxHeight; y=y+4) {
		
			for (int z = 1; z < 16; z=z+4) {
							
				for (int x = 1; x < 16; x=x+4) {
					
					Material material = BlockList.getRandomMaterial(this.random);
					
					this.setBlock(result.chunk, x, y, z, material);
					MaterialData materialdata = null;
					switch (material) {
						case WOOL : {				
							materialdata = RandomMetaDataGenerator.getWool(this.random);
							break;
						}
						case JACK_O_LANTERN:
						case PUMPKIN : {
							materialdata = RandomMetaDataGenerator.getPumpkin(this.random);
							break;
						}
						case LOG :
						case LOG_2 : {
							materialdata = RandomMetaDataGenerator.getTree(material, this.random);
							break;
						}						
						case FURNACE: {
							materialdata = RandomMetaDataGenerator.getDirectionalContainer(material, this.random);
							break;
						}
						case MONSTER_EGGS: {
							materialdata = RandomMetaDataGenerator.getMonsterEggs(this.random);
							break;
						}
						case CHEST: {
							ComplexBlock cb = new ComplexBlock(material,null, x, y, z);
							result.list.add(cb);
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
							if (this.random.nextInt(100) <= 2) {
								this.setBlock(result.chunk, x, y+1, z, Material.SUGAR_CANE_BLOCK);
								switch (this.random.nextInt(4)) {
									case 0 : {this.setBlock(result.chunk, x+1, y, z, Material.STATIONARY_WATER); break;}
									case 1 : {this.setBlock(result.chunk, x-1, y, z, Material.STATIONARY_WATER); break;}
									case 2 : {this.setBlock(result.chunk, x, y, z+1, Material.STATIONARY_WATER); break;}
									case 3 : {this.setBlock(result.chunk, x, y, z-1, Material.STATIONARY_WATER); break;}
								}
							}
							break;
						}
						case MYCEL: {
							Material mushroom = this.random.nextBoolean() ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM;
							this.setBlock(result.chunk, x, y+1, z, mushroom);							
							break;
						}
						case DIRT: {
							if (this.random.nextInt(100) <= 2) {
								this.setBlock(result.chunk, x, y+1, z, Material.SAPLING);
								ComplexBlock cb = new ComplexBlock(material,new Tree(RandomMetaDataGenerator.getTreeSpecies(Material.SAPLING, this.random)), x, y+1, z);
								result.list.add(cb);								
							}
							break;
						}
						case MOB_SPAWNER: {
							ComplexBlock cb = new ComplexBlock(material,null, x, y, z);
							result.list.add(cb);
							break;
						}
						
						default:
							break;
					}
					if (materialdata != null) {
						ComplexBlock cb = new ComplexBlock(material,materialdata, x, y, z);
						result.list.add(cb);
					}
				}
				
			}
			
		}
		
		return result;
		
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
	
}
