package me.barry1990.skygrid.generators;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class ItemList {
	
	//list of materials used for inventories
	static private Material[] building_blocks = null;
	static private Material[] food_items = null;
	static private Material[] material_items = null;
	static private Material[] equipment_items = null;
	static private Material[] rare_items = null;
	static private Material[] legendary_items = null;
	
	static {
		ItemList.building_blocks = new Material[] { 						
				Material.WOOD,
				Material.STONE,
				Material.LOG,
				Material.LOG_2,
				Material.SAND,
				Material.DIRT,
				Material.GRAVEL,						
				Material.COBBLESTONE,
				Material.SANDSTONE
		};
		
		ItemList.material_items = new Material[] { 
				Material.PAPER,
				Material.TORCH,
				Material.STICK,
				Material.LEATHER,						
				Material.SNOW_BALL,
				Material.WHEAT,
				Material.SAPLING,
				Material.STRING,
				Material.CLAY_BALL,
				Material.INK_SACK,
				Material.COAL
		};
		
		ItemList.food_items = new Material[] { 						
				Material.BREAD,
				Material.COOKIE,
				Material.POTATO_ITEM,
				Material.BAKED_POTATO,
				Material.RAW_BEEF,
				Material.RAW_CHICKEN,
				Material.RAW_FISH,
				Material.CARROT_ITEM,
				Material.BEETROOT
		};
		
		ItemList.equipment_items = new Material[] { 
				Material.WOOD_PICKAXE,
				Material.STONE_AXE,
				Material.STONE_SWORD,
				Material.STONE_PICKAXE,	
				Material.BED,
				Material.BOW,
				Material.ARROW,
				Material.BUCKET,
				Material.TORCH,
				Material.STICK,
				Material.LEATHER_BOOTS,
				Material.LEATHER_CHESTPLATE,
				Material.LEATHER_HELMET,
				Material.LEATHER_LEGGINGS,
				Material.IRON_PICKAXE					
		};
		
		ItemList.rare_items = new Material[] { 
				Material.VINE,
				Material.WATER_BUCKET,
				Material.LAVA_BUCKET,
				Material.MILK_BUCKET,
				Material.IRON_INGOT,
				Material.GOLD_INGOT,
				Material.SULPHUR,
				Material.BONE,
				Material.SEEDS,
				Material.OBSIDIAN,
				Material.CACTUS,
				Material.SUGAR_CANE,
				Material.MONSTER_EGG
		};
		
		ItemList.legendary_items = new Material[] { 
				Material.RECORD_3,
				Material.RECORD_4,
				Material.RECORD_5,
				Material.RECORD_6,
				Material.RECORD_7,
				Material.RECORD_8,
				Material.RECORD_9,
				Material.RECORD_10,
				Material.RECORD_11,
				Material.RECORD_12,
				Material.DIAMOND,
				Material.EMERALD,
				Material.ENDER_PEARL,
				Material.EXP_BOTTLE,
				Material.SADDLE,
				Material.NAME_TAG,
				Material.GOLDEN_APPLE,
				Material.GHAST_TEAR
		};
	}

	public static ItemStack getRandomBuildingBlock(Random random) {
		Material randomMaterial = ItemList.building_blocks[random.nextInt(ItemList.building_blocks.length)];
		return new ItemStack(randomMaterial, random.nextInt(10)>8 ? random.nextInt(14)+11 : random.nextInt(14)+1);
	}
	
	public static ItemStack getRandomMaterialItems(Random random) {
		Material randomMaterial = ItemList.material_items[random.nextInt(ItemList.material_items.length)];
		return new ItemStack(randomMaterial, random.nextInt(16)+1);
	}
	
	public static ItemStack getRandomFoodItem(Random random) {
		Material randomMaterial = ItemList.food_items[random.nextInt(ItemList.food_items.length)];
		return new ItemStack(randomMaterial, random.nextInt(16)+1);
	}
	
	public static ItemStack getRandomEquipmentItems(Random random) {
		Material randomMaterial = ItemList.equipment_items[random.nextInt(ItemList.equipment_items.length)];
		return new ItemStack(randomMaterial, isStackable(randomMaterial) ? random.nextInt(16)+1 : 1);
	}
	
	public static ItemStack getRandomRareItems(Random random) {
		Material randomMaterial = ItemList.rare_items[random.nextInt(ItemList.rare_items.length)];
		if (randomMaterial == Material.MONSTER_EGG) 
			return new ItemStack(randomMaterial, random.nextInt(10)>8 ? 2 : 1,ItemList.getRandomAnimal(random));
		else
			return new ItemStack(randomMaterial, isStackable(randomMaterial) ? random.nextInt(8)+1 : 1);
	}
	
	public static ItemStack getRandomLendaryItem(Random random) {
		Material randomMaterial = ItemList.legendary_items[random.nextInt(ItemList.legendary_items.length)];
		return new ItemStack(randomMaterial, isStackable(randomMaterial) ? random.nextInt(8)+1 : 1);
	}
	
	private static boolean isStackable(Material mat) {
		ItemStack item = new ItemStack(mat);
		return (item.getMaxStackSize() != 1);
	}
	
	private static short getRandomAnimal(Random random) {
		switch (random.nextInt(9)) {
			case 0 : return (short) 91;		//SHEEP
			case 1 : return (short) 90;		//PIG
			case 2 : return (short) 92;		//COW
			case 3 : return (short) 96;		//MUSHROOM_COW
			case 4 : return (short) 101;	//RABBIT
			case 5 : return (short) 100; 	//HORSE
			case 6 : return (short) 95;		//WOLF
			case 7 : return (short) 98;		//OCELOT
			default : return (short) 93;	//CHICKEN
		}

	}

}
