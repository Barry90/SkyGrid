package me.barry1990.skygrid.generators;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sapling;


class SkyGridChunkDataGeneratorThread extends Thread {	
	
	interface iChunkDataGeneratorThread {
		ChunkData getChunkData(World world);
	}
	
	static final int MAXQUEUE = 15;
	private Queue<ChunkData> chunkqueue = new LinkedList<ChunkData>();
	private Random random;
	private World world;
	
	private iChunkDataGeneratorThread _interface;
 
	public SkyGridChunkDataGeneratorThread(World world,  iChunkDataGeneratorThread _interface) {
		super();
		this.random = new Random();
		this.world = world;
		this._interface = _interface;
		BarrysLogger.info(this, "worldMaxHeight", world.getMaxHeight());
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
	public synchronized ChunkData getChunk() throws InterruptedException {
		notify();
		while (chunkqueue.size() == 0) {
			BarrysLogger.info(this, "queue is empty. waiting...");
			wait();//By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
		}
		return chunkqueue.remove();
	}
	
	//@SuppressWarnings("deprecation")
	private ChunkData generateOverworldChunk() {
		
		ChunkData result = this._interface.getChunkData(this.world);
		
		//ChunkWithBlockList result = new ChunkWithBlockList(this.world.getMaxHeight());
		
		/* generate the grid */
		
		for (int y = 1; y < this.world.getMaxHeight(); y=y+4) {
					
			if (y <= 45) {
				
				//////////////////////////////
				// generate End-layer
				//////////////////////////////
				
				for (int z = 1; z < 16; z=z+4) {
								
					for (int x = 1; x < 16; x=x+4) {					
						
						Material material = BlockList.getRandomMaterialForEnd();
						result.setBlock(x, y, z, material);
						switch (material) {			
							case MOB_SPAWNER: {
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
						
						result.setBlock(x, y, z, material);
						MaterialData materialdata = null;
						switch (material) {
							case JACK_O_LANTERN:
							case PUMPKIN : {
								materialdata = RandomMetaDataGenerator.getPumpkin();
								break;
							}						
							case ENDER_CHEST:
							case CHEST: {
								materialdata = RandomMetaDataGenerator.getChest();
								break;
							}				
							case MOB_SPAWNER: {
								//TODO
								//ComplexBlock cb = new ComplexBlock(material,null, x, y, z);
								//result.list.add(cb);
								break;
							}	
							case SOUL_SAND: {
								if (this.random.nextInt(100) <= 2) {
									result.setBlock(x, y, z, Material.NETHER_WARTS);
								}
								break;
							}
							default:
								break;
						}
						
						if (materialdata != null) {
							result.setBlock(x, y, z, materialdata);
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
						
						result.setBlock(x, y, z, material);
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
							case ENDER_CHEST:
							case CHEST: {
								materialdata = RandomMetaDataGenerator.getChest();
								break;
							}				
							case SAND: {
								if (this.random.nextInt(100) <= 2) {
									result.setBlock(x, y+1, z, Material.SUGAR_CANE_BLOCK);
									switch (this.random.nextInt(4)) {
										case 0 : {result.setBlock(x+1, y, z, Material.STATIONARY_WATER); break;}
										case 1 : {result.setBlock(x-1, y, z, Material.STATIONARY_WATER); break;}
										case 2 : {result.setBlock(x, y, z+1, Material.STATIONARY_WATER); break;}
										case 3 : {result.setBlock(x, y, z-1, Material.STATIONARY_WATER); break;}
									}
								}
								break;
							}
							case MYCEL: {
								Material mushroom = this.random.nextBoolean() ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM;
								result.setBlock(x, y+1, z, mushroom);
								break;
							}
							case DIRT: {
								if (this.random.nextInt(100) <= 2) {
									result.setBlock(x, y+1, z, Material.SAPLING);
									result.setBlock(x, y+1, z, new Sapling(RandomMetaDataGenerator.getTreeSpecies(Material.SAPLING)));
								}
								break;
							}
							case MOB_SPAWNER: {
								break;
							}
							
							default:
								break;
						}
						if (materialdata != null) {
							result.setBlock(x, y, z, materialdata);
						}
					}
					
				}
			}
		}
		
		return result;
		
	}
	
}
