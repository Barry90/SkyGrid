package me.barry1990.skygrid;

import java.util.Random;

import org.bukkit.Material;


public class BlockList {
	
	//list of blocks used for the grid
	static private Material[] materiallist0 = null;		//HÄUFIG		59.0%
	static private Material[] materiallist1 = null;		//SELTEN		30.0%
	static private Material[] materiallist2 = null;		//RAR			9.0%
	static private Material[] materiallist3 = null;		//EPISCH		1.8%
	static private Material[] materiallist4 = null;		//LEGENDÄR		0.2%
	static private Material[] materiallist5 = null;

	
	public static Material getRandomMaterial(Random random) {
	
	
		// init the list
		if (BlockList.materiallist0 == null) {
			BlockList.materiallist0 = new Material[] { 
					Material.STONE,
					Material.DIRT,
					Material.COBBLESTONE,
					Material.LOG,
					Material.LOG_2
					
			};
		}
		if (BlockList.materiallist1 == null) {
			BlockList.materiallist1 = new Material[] { 
					Material.GRASS,
					Material.SAND,
					Material.GRAVEL,
					Material.SANDSTONE,
					Material.WOOL,
					Material.MONSTER_EGGS					
			};
		}
		if (BlockList.materiallist2 == null) {
			BlockList.materiallist2 = new Material[] { 
					
					Material.GOLD_ORE,
					Material.IRON_ORE,
					Material.COAL_ORE,
					Material.GLASS,
					Material.SNOW_BLOCK,
					Material.PRISMARINE,
					Material.SEA_LANTERN,
					Material.HAY_BLOCK,
					Material.CAKE_BLOCK,
					Material.MELON_BLOCK,
			};
		}
		if (BlockList.materiallist3 == null) {
			BlockList.materiallist3 = new Material[] { 
					Material.STATIONARY_LAVA,
					Material.STATIONARY_WATER,
					Material.LAPIS_ORE,
					Material.NOTE_BLOCK,
					Material.BOOKSHELF,
					Material.OBSIDIAN,
					Material.CHEST,
					Material.DIAMOND_ORE,
					Material.FURNACE,
					Material.MYCEL,
					Material.SLIME_BLOCK,
					Material.COAL_BLOCK,
					Material.PUMPKIN
			};
		}
		if (BlockList.materiallist4 == null) {
			BlockList.materiallist4 = new Material[] { 
					Material.SPONGE,
					Material.LAPIS_BLOCK,
					Material.GOLD_BLOCK,
					Material.IRON_BLOCK,
					Material.TNT,
					Material.MOB_SPAWNER,
					Material.ENDER_CHEST
			};
		}
		
		// select a random materiallist
		
		int randomList = random.nextInt(1000);
		Material[] chosenList = BlockList.materiallist0;
		
		if (randomList >= 590) {
			chosenList = BlockList.materiallist1;
		}
		if (randomList >= 880){
			chosenList = BlockList.materiallist2;
		}		
		if (randomList >= 975){
			chosenList = BlockList.materiallist3;
		}
		if (randomList >= 996){
			chosenList = BlockList.materiallist4;
		} 
		
		return chosenList[random.nextInt(chosenList.length)];
		
	}
		
}
