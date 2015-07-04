package me.barry1990.skygrid;

import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SkyGridBlockPopulator extends BlockPopulator {

	static final int chestsize = 27;
	
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		
		switch (world.getEnvironment()) {
		
			case NORMAL : {		
	
				// get queue
				String key = chunk.getX()+";"+chunk.getZ();
				List<ComplexBlock> list = SkyGrid.blockQueue.get(key);		
				if (list != null){
					for (ComplexBlock cb : list) {
						Block block = world.getBlockAt(cb.x, cb.y, cb.z);
						
						switch (cb.material) {
							case CHEST: {
								Chest chest = (Chest)block.getState();
								Inventory inv = chest.getInventory();
								setRandomInventoryContent(inv,random);
								break;
							}
							case MOB_SPAWNER: {
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
					SkyGrid.blockQueue.remove(key);
				}
				break;
			}
			
			case NETHER : {
				
				String key = chunk.getX()+";"+chunk.getZ();
				List<ComplexBlock> list = SkyGrid.blockQueue_nether.get(key);		
				if (list != null){
					for (ComplexBlock cb : list) {
						Block block = world.getBlockAt(cb.x, cb.y, cb.z);
						
						switch (cb.material) {
							/*case CHEST: {
								Chest chest = (Chest)block.getState();
								Inventory inv = chest.getInventory();
								setRandomInventoryContent(inv,random);
								break;
							}*/
							case MOB_SPAWNER: {
								((CreatureSpawner) block.getState()).setSpawnedType(SkyGridBlockPopulator.getNetherEntityType(random));
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
					SkyGrid.blockQueue_nether.remove(key);
				}
				
				
				break;
			}
			
			case THE_END : {
				break;
			}
		
		}

	}
	
	public static EntityType getOverworldEntityType(Random random) {
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
	
	public static EntityType getNetherEntityType(Random random) {
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
	
	public static void setRandomInventoryContent(Inventory inv, Random random){
		
		ItemStack[] items = new ItemStack[inv.getSize()];
		
		for (int i=0; i<inv.getSize(); i++){
			
			items[i] = ItemList.getRandomItemstack(random);
			if (random.nextFloat() < 0.1)
				break;
		}
		
		inv.setContents(items);		
	}
}
