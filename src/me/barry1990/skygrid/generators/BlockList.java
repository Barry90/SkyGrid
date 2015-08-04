package me.barry1990.skygrid.generators;

import java.util.Random;

import org.bukkit.Material;


class BlockList {
	
	//list of blocks used for the grid
	static private Material[] overworld_common = null;
	static private Material[] overworld_seldom = null;
	static private Material[] overworld_rare = null;
	static private Material[] overworld_epic = null;
	static private Material[] overworld_legend = null;
	
	static private Material[] nether_common = null;	
	static private Material[] nether_seldom = null;	
	static private Material[] nether_rare = null;	
	static private Material[] nether_epic = null;
	static private Material[] nether_legend = null;
	
	static private Material[] end_common = null;	
	static private Material[] end_seldom = null;	
	static private Material[] end_rare = null;	
	static private Material[] end_epic = null;
	static private Material[] end_legend = null;

	
	public static Material getRandomMaterial(Random random) {	
	
		// init the list
		if (BlockList.overworld_common == null) {
			BlockList.overworld_common = new Material[] { 
					Material.STONE,
					Material.DIRT,
					Material.COBBLESTONE,
					Material.LOG,
					Material.LOG_2
					
			};
		}
		if (BlockList.overworld_seldom == null) {
			BlockList.overworld_seldom = new Material[] { 
					Material.GRASS,
					Material.SAND,
					Material.GRAVEL,
					Material.SANDSTONE,
					Material.WOOL,
					Material.MONSTER_EGGS					
			};
		}
		if (BlockList.overworld_rare == null) {
			BlockList.overworld_rare = new Material[] { 
					
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
		if (BlockList.overworld_epic == null) {
			BlockList.overworld_epic = new Material[] { 
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
		if (BlockList.overworld_legend == null) {
			BlockList.overworld_legend = new Material[] { 
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
		Material[] chosenList = BlockList.overworld_common;
		
		if (randomList >= 590) {
			chosenList = BlockList.overworld_seldom;
		}
		if (randomList >= 880){
			chosenList = BlockList.overworld_rare;
		}		
		if (randomList >= 975){
			chosenList = BlockList.overworld_epic;
		}
		if (randomList >= 996){
			chosenList = BlockList.overworld_legend;
		} 
		
		return chosenList[random.nextInt(chosenList.length)];
		
	}
	
	public static Material getRandomMaterialForNether(Random random) {
		
		if (nether_common == null) {
			nether_common = new Material[] {
					Material.NETHERRACK,
					Material.NETHERRACK,
					Material.NETHERRACK,
					Material.QUARTZ_ORE,
					Material.NETHER_BRICK,
					Material.RED_SANDSTONE
			};
 		}
		
		if (nether_seldom == null) {
			nether_seldom = new Material[] {
					Material.SOUL_SAND,
					Material.GLOWSTONE,
					Material.GRAVEL,
					Material.PUMPKIN,
					
			};			
		}
		
		if (nether_rare == null) {
			nether_rare = new Material[] {
					Material.REDSTONE_ORE,
					Material.STATIONARY_LAVA,
					Material.JACK_O_LANTERN
			};			
		}
		
		if (nether_epic == null) {
			nether_epic = new Material[] {
					Material.REDSTONE_BLOCK,
					Material.OBSIDIAN
			};
		}
		
		if (nether_legend == null) {
			nether_legend = new Material[] {
					Material.SOUL_SAND,
					Material.NETHERRACK,
					Material.MOB_SPAWNER
			};
		}
		
		int randomList = random.nextInt(1000);
		Material[] chosenList = BlockList.nether_common;
		
		if (randomList >= 700){
			chosenList = BlockList.nether_seldom;
		}		
		if (randomList >= 900){
			chosenList = BlockList.nether_rare;
		}
		if (randomList >= 980){
			chosenList = BlockList.nether_epic;
		} 
		if (randomList >= 998){
			chosenList = BlockList.nether_legend;
		} 
		
		return chosenList[random.nextInt(chosenList.length)];
		
	}

	public static Material getRandomMaterialForEnd(Random random) {
		
		return Material.ENDER_STONE;
	}
}
