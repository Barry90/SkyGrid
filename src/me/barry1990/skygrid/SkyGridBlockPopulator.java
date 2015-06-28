package me.barry1990.skygrid;

import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SkyGridBlockPopulator extends BlockPopulator {

	static final int chestsize = 27;
	
	@Override
	public void populate(World world, Random random, Chunk chunk) {

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
						((CreatureSpawner) block.getState()).setSpawnedType(SkyGridBlockPopulator.getEntityType(random));
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

	}
	
	public static EntityType getEntityType(Random random) {
		switch (random.nextInt(18)) {
			case 0 : return EntityType.BLAZE;
			case 1 : return EntityType.CAVE_SPIDER;
			case 2 : return EntityType.CHICKEN;
			case 3 : return EntityType.COW;
			case 4 : return EntityType.CREEPER;
			case 5 : return EntityType.ENDERMAN;
			case 6 : return EntityType.ENDERMITE;
			case 7 : return EntityType.GHAST;
			case 8 : return EntityType.MAGMA_CUBE;
			case 9 : return EntityType.PIG;
			case 10 : return EntityType.RABBIT;
			case 11 : return EntityType.SHEEP;
			case 12 : return EntityType.SKELETON;
			case 13 : return EntityType.SLIME;
			case 14 : return EntityType.SPIDER;
			case 15 : return EntityType.WITCH;
			case 16 : return EntityType.ZOMBIE;
			case 17 : return EntityType.PIG_ZOMBIE;
			default: break;
		}	
		
        /* we should never get here */
        return EntityType.PIG_ZOMBIE;
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
