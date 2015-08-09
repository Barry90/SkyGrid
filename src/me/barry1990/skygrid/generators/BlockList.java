package me.barry1990.skygrid.generators;

import java.util.Random;

import org.bukkit.Material;


class BlockList {
	
	static private Random random;
	
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
	static private Material[] end_rare = null;	
	static private Material[] end_legend = null;
	
	static {
		BlockList.random = new Random();
		
		//////////////////////////
		// OVEWORLD-LAYER
		//////////////////////////
		
		BlockList.overworld_common = new Material[] { 
				Material.STONE,
				Material.DIRT,
				Material.COBBLESTONE,
				Material.LOG,
				Material.LOG_2				
		};
		BlockList.overworld_seldom = new Material[] { 
				Material.GRASS,
				Material.SAND,
				Material.GRAVEL,
				Material.SANDSTONE,
				Material.WOOL,
				Material.MONSTER_EGGS					
		};
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
		BlockList.overworld_legend = new Material[] { 
				Material.SPONGE,
				Material.LAPIS_BLOCK,
				Material.GOLD_BLOCK,
				Material.IRON_BLOCK,
				Material.TNT,
				Material.MOB_SPAWNER,
				Material.ENDER_CHEST
		};
		
		//////////////////////////
		// NETHER-LAYER
		//////////////////////////
		
		BlockList.nether_common = new Material[] {
				Material.NETHERRACK,
				Material.NETHERRACK,
				Material.NETHERRACK,
				Material.QUARTZ_ORE,
				Material.NETHER_BRICK,
				Material.RED_SANDSTONE
		};
		BlockList.nether_seldom = new Material[] {
				Material.SOUL_SAND,
				Material.GLOWSTONE,
				Material.GRAVEL,
				Material.PUMPKIN,
				
		};
		BlockList.nether_rare = new Material[] {
				Material.REDSTONE_ORE,
				Material.STATIONARY_LAVA,
				Material.JACK_O_LANTERN
		};	
		BlockList.nether_epic = new Material[] {
				Material.REDSTONE_BLOCK,
				Material.OBSIDIAN
		};
		BlockList.nether_legend = new Material[] {
				Material.SOUL_SAND,
				Material.NETHERRACK,
				Material.MOB_SPAWNER
		};
		
		//////////////////////////
		// END-LAYER
		//////////////////////////
		
		BlockList.end_common = new Material[] {
				Material.ENDER_STONE
		};	
		BlockList.end_rare = new Material[] {
				Material.STATIONARY_LAVA,
				Material.OBSIDIAN
		};
		BlockList.end_legend = new Material[] {
				Material.SOUL_SAND,
				Material.NETHERRACK,
				Material.MOB_SPAWNER
		};
		
	}

	
	public static Material getRandomMaterial() {	

		// select a random materiallist
		
		int randomint = BlockList.random.nextInt(1000);
		
		if (randomint < 590) 
			return BlockList.overworld_common[BlockList.random.nextInt(BlockList.overworld_common.length)];
		if (randomint < 880) 
			return BlockList.overworld_seldom[BlockList.random.nextInt(BlockList.overworld_seldom.length)];
		if (randomint < 975) 
			return BlockList.overworld_rare[BlockList.random.nextInt(BlockList.overworld_rare.length)];
		if (randomint < 996) 
			return BlockList.overworld_epic[BlockList.random.nextInt(BlockList.overworld_epic.length)];
		return BlockList.overworld_legend[BlockList.random.nextInt(BlockList.overworld_legend.length)];
				
	}
	
	public static Material getRandomMaterialForNether() {
		
		int randomint = random.nextInt(1000);		
		
		if (randomint < 700)
			return BlockList.nether_common[BlockList.random.nextInt(BlockList.nether_common.length)];
		if (randomint < 900)
			return BlockList.nether_seldom[BlockList.random.nextInt(BlockList.nether_seldom.length)];
		if (randomint < 980)
			return BlockList.nether_rare[BlockList.random.nextInt(BlockList.nether_rare.length)];
		if (randomint < 998)
			return BlockList.nether_epic[BlockList.random.nextInt(BlockList.nether_epic.length)];
		return BlockList.nether_legend[BlockList.random.nextInt(BlockList.nether_legend.length)];
				
	}

	public static Material getRandomMaterialForEnd() {
		
		//return Material.ENDER_STONE;
		
		int randomint = random.nextInt(1000);		
		
		if (randomint < 800)
			return BlockList.end_common[BlockList.random.nextInt(BlockList.end_common.length)];
		if (randomint < 950)
			return BlockList.end_rare[BlockList.random.nextInt(BlockList.end_rare.length)];
		return BlockList.end_legend[BlockList.random.nextInt(BlockList.end_legend.length)];

	}
}
