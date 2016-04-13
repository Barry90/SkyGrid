package me.barry1990.skygrid.generators;

import java.util.List;
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
	
	@SuppressWarnings("deprecation")
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		
		//start the thread for generating Inventorys for the chests
		if (this.inventoryGenerator == null) {
			this.inventoryGenerator = new SkyGridInventoryGeneratorThread();
			BarrysLogger.info(this,"Inventory generator start");
			this.inventoryGenerator.start();
		}
				
		if (world.getEnvironment() == Environment.NORMAL) {
		
			// get list
			String key = chunk.getX()+";"+chunk.getZ();
			List<ComplexBlock> list = SkyGridGenerator.blockQueue_get(key);
			
			if (list != null){
				for (ComplexBlock cb : list) {
					Block block = chunk.getBlock(cb.x, cb.y, cb.z);
					
					switch (cb.material) {
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
							
							if (cb.y <= 45) {
								((CreatureSpawner) block.getState()).setSpawnedType(SkyGridBlockPopulator.getEndEntityType(random));
								break;
							}							
							if (cb.y <= 117) {
								((CreatureSpawner) block.getState()).setSpawnedType(SkyGridBlockPopulator.getNetherEntityType(random));
								break;
							}							
							((CreatureSpawner) block.getState()).setSpawnedType(SkyGridBlockPopulator.getOverworldEntityType(random));
							break;
						}
						default: {
							block.setData(cb.materialData.getData());
							break;
						}
					}
				
							
				}
				// delete list
				list.clear();			
				SkyGridGenerator.blockQueue_remove(key);
			}
			
		}

	}
	
	private static EntityType getOverworldEntityType(Random random) {
		switch (random.nextInt(14)) {
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
