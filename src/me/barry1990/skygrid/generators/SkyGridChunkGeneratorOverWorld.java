package me.barry1990.skygrid.generators;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sapling;
import org.bukkit.material.Tree;


class SkyGridChunkGeneratorOverWorld extends Thread {
	
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
			
			// is this chunk part already initialized?
			if (result.chunk[y >> 4] == null) {
				// Initialize the chunk part
				result.chunk[y >> 4] = new short[4096];
			}
			
			if (y <= 45) {
				
				//////////////////////////////
				// generate End-layer
				//////////////////////////////
				
				for (int z = 1; z < 16; z=z+4) {
								
					for (int x = 1; x < 16; x=x+4) {					
						
						Material material = BlockList.getRandomMaterialForEnd();
						result.chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (short) material.getId();
						switch (material) {			
							case MOB_SPAWNER: {
								ComplexBlock cb = new ComplexBlock(material,null, x, y, z);
								result.list.add(cb);
								break;
							}						
							default:
								break;
						}
					}
					
				}
			} else if (y <= 117) {
				
				//////////////////////////////
				// generate Nether-layer
				//////////////////////////////
			
				for (int z = 1; z < 16; z=z+4) {
					
					for (int x = 1; x < 16; x=x+4) {
						
						Material material = BlockList.getRandomMaterialForNether();		
						
						result.chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (short) material.getId();
						MaterialData materialdata = null;
						switch (material) {
							case JACK_O_LANTERN:
							case PUMPKIN : {
								materialdata = RandomMetaDataGenerator.getPumpkin();
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
				
			} else {
				
				//////////////////////////////
				//generate Normal-layer
				//////////////////////////////
				
				for (int z = 1; z < 16; z=z+4) {
					
					for (int x = 1; x < 16; x=x+4) {
						
						Material material = BlockList.getRandomMaterial();
						
						result.chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (short) material.getId();
						MaterialData materialdata = null;
						switch (material) {
							case WOOL : {				
								materialdata = RandomMetaDataGenerator.getWool();
								break;
							}
							case JACK_O_LANTERN:
							case PUMPKIN : {
								materialdata = RandomMetaDataGenerator.getPumpkin();
								break;
							}
							case LOG :
							case LOG_2 : {
								materialdata = RandomMetaDataGenerator.getTree(material);
								break;
							}						
							case FURNACE: {
								materialdata = RandomMetaDataGenerator.getFurnace();
								break;
							}
							case MONSTER_EGGS: {
								materialdata = RandomMetaDataGenerator.getMonsterEggs();
								break;
							}
							case CHEST: {
								ComplexBlock cb = new ComplexBlock(material,null, x, y, z);
								result.list.add(cb);
								break;
							}				
							case SAND: {
								if (this.random.nextInt(100) <= 2) {
									result.chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (short) material.getId();
									switch (this.random.nextInt(4)) {
										case 0 : {result.chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | (x+1)] = (short)  Material.STATIONARY_WATER.getId(); break;}
										case 1 : {result.chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | (x-1)] = (short)  Material.STATIONARY_WATER.getId(); break;}
										case 2 : {result.chunk[y >> 4][((y & 0xF) << 8) | ((z+1) << 4) | x] = (short)  Material.STATIONARY_WATER.getId(); break;}
										case 3 : {result.chunk[y >> 4][((y & 0xF) << 8) | ((z-1) << 4) | x] = (short)  Material.STATIONARY_WATER.getId(); break;}
									}
								}
								break;
							}
							case MYCEL: {
								Material mushroom = this.random.nextBoolean() ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM;
								result.chunk[y >> 4][(((y+1) & 0xF) << 8) | (z << 4) | x] = (short) mushroom.getId();
								break;
							}
							case DIRT: {
								if (this.random.nextInt(100) <= 2) {
									result.chunk[y >> 4][(((y+1) & 0xF) << 8) | (z << 4) | x] = (short) Material.SAPLING.getId();
									ComplexBlock cb = new ComplexBlock(material,new Sapling(RandomMetaDataGenerator.getTreeSpecies(Material.SAPLING)), x, y+1, z);
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
		}
		
		return result;
		
	}
	
}
