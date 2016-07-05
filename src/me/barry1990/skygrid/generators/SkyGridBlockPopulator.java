package me.barry1990.skygrid.generators;

import java.util.Random;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

class SkyGridBlockPopulator extends BlockPopulator {

	static final int chestsize = 27;
	private SkyGridInventoryGeneratorThread inventoryGenerator;
	
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		
		if (world.getEnvironment() != Environment.NORMAL) 
			return;
		
		//start the thread for generating Inventorys for the chests
		if (this.inventoryGenerator == null) {
			this.inventoryGenerator = new SkyGridInventoryGeneratorThread();
			BarrysLogger.info(this,"Inventory generator start");
			this.inventoryGenerator.start();
		}
				
		
		for (int y = 1; y < world.getMaxHeight(); y=y+4) {				
			for (int z = 1; z < 16; z=z+4) {								
				for (int x = 1; x < 16; x=x+4) {
					
					Block block = chunk.getBlock(x, y, z);
					
					switch (block.getType()) {
						case CHEST: {								
							Chest chest = (Chest)block.getState();
							try {
								chest.getInventory().setContents(this.inventoryGenerator.getItemStacks());
							} catch (InterruptedException e) {
								e.printStackTrace();
								chest.getInventory().setContents(new ItemStack[chestsize]);
							}
							break;
						}
						case MOB_SPAWNER: {							
							
							if (y <= 45) {
								((CreatureSpawner) block.getState()).setSpawnedType(SkyGridBlockPopulator.getEndEntityType(random));
							} else if (y <= 117) {
								((CreatureSpawner) block.getState()).setSpawnedType(SkyGridBlockPopulator.getNetherEntityType(random));
							} else {						
								((CreatureSpawner) block.getState()).setSpawnedType(SkyGridBlockPopulator.getOverworldEntityType(random));
							}
							block.getState().update();
							break;
						}
						default: {
							break;
						}
					}
					
				}
			}
		}	

	}
	
	private static EntityType getOverworldEntityType(Random random) {
		switch (random.nextInt(15)) {
			case 0 : return EntityType.CAVE_SPIDER;
			case 1 : return EntityType.CHICKEN;
			case 2 : return EntityType.COW;
			case 3 : return EntityType.CREEPER;
			case 4 : return EntityType.ENDERMAN;
			case 5 : return EntityType.ENDERMITE;
			case 6 : return EntityType.PIG;
			case 7 : return EntityType.RABBIT;
			case 8 : return EntityType.SHEEP;
			case 9 : return EntityType.SKELETON;
			case 10 : return EntityType.SLIME;
			case 11 : return EntityType.SPIDER;
			case 12 : return EntityType.WITCH;
			case 13 : return EntityType.ZOMBIE;
			case 14 : return EntityType.POLAR_BEAR;
			default: break;
		}	
		
		/* we should never get here */
		return EntityType.ZOMBIE;
	}
	
	private static EntityType getNetherEntityType(Random random) {
		switch (random.nextInt(8)) {
			case 0 : return EntityType.BLAZE;
			case 1 : return EntityType.ENDERMAN;
			case 2 : return EntityType.ENDERMITE;
			case 3 : return EntityType.GHAST;
			case 4 : return EntityType.MAGMA_CUBE;
			case 5 : return EntityType.SKELETON;
			case 6 : return EntityType.ZOMBIE;
			case 7 : return EntityType.PIG_ZOMBIE;
			default: break;
		}	
		
		/* we should never get here */
		return EntityType.ZOMBIE;
	}
	
	private static EntityType getEndEntityType(Random random) {
		switch (random.nextInt(4)) {
			case 0 : return EntityType.ENDERMAN;
			case 1 : return EntityType.ENDERMITE;
			case 2 : return EntityType.WITCH;
			case 3 : return EntityType.SHULKER;
			default: break;
		}	
		
		/* we should never get here */
		return EntityType.ZOMBIE;
	}


}
