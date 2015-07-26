package me.barry1990.skygrid;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;


public class SkyGridChunkGeneratorNether extends Thread {
	static final int MAXQUEUE = 5;
    private Queue<ChunkWithBlockList> chunkqueue = new LinkedList<ChunkWithBlockList>();
    private Random random;
    private int worldMaxHeight;
    
    
 
    public SkyGridChunkGeneratorNether(int worldMaxHeight) {
		super();
		this.random = new Random();
		this.worldMaxHeight = worldMaxHeight;
	}

	@Override
    public void run() {
        try {
            while (true) {
            	putChunk();
                //sleep(5000);
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
            wait();//By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
        }
        
        return chunkqueue.remove();
    }
    
    private ChunkWithBlockList generateOverworldChunk() {
		
    	ChunkWithBlockList result = new ChunkWithBlockList(this.worldMaxHeight);
		
		/* generate the grid */
		
		for (int y = 1; y < 128; y=y+4) {
		
			for (int z = 1; z < 16; z=z+4) {
							
				for (int x = 1; x < 16; x=x+4) {
					
					Material material = y == 125 || y == 1 ? Material.BEDROCK : BlockList.getRandomMaterialForNether(random);
					
					this.setBlock(result.chunk, x, y, z, material);
					MaterialData materialdata = null;
					switch (material) {
						case JACK_O_LANTERN:
						case PUMPKIN : {
							materialdata = RandomMetaDataGenerator.getPumpkin(random);
							break;
						}						
						case CHEST: {
							ComplexBlock cb = new ComplexBlock(material,null, x, y, z);
							result.list.add(cb);
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
