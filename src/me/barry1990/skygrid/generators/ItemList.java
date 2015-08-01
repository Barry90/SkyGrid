package me.barry1990.skygrid.generators;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemList {
	
	//list of materials used for inventories
	static private Material[] building_blocks = null;
	static private Material[] food_items = null;
	static private Material[] material_items = null;
	static private Material[] equipment_items = null;
	static private Material[] rare_items = null;
	static private Material[] legendary_items = null;

	public static ItemStack getRandomBuildingBlock(Random random) {
		if (ItemList.building_blocks == null) {
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
			
		}
		Material randomMaterial = ItemList.building_blocks[random.nextInt(ItemList.building_blocks.length)];
		return new ItemStack(randomMaterial, random.nextInt(10)>8 ? random.nextInt(14)+11 : random.nextInt(14)+1);
	}
	
	public static ItemStack getRandomMaterialItems(Random random) {
		if (ItemList.material_items == null) {
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
		}
		Material randomMaterial = ItemList.material_items[random.nextInt(ItemList.material_items.length)];
		return new ItemStack(randomMaterial, random.nextInt(16)+1);
	}
	
	public static ItemStack getRandomFoodItem(Random random) {
		if (ItemList.food_items == null) {
			ItemList.food_items = new Material[] { 						
					Material.BREAD,
					Material.COOKIE,
					Material.POTATO_ITEM,
					Material.BAKED_POTATO,
					Material.RAW_BEEF,
					Material.RAW_CHICKEN,
					Material.RAW_FISH,
					Material.CARROT_ITEM
			};
			
		}
		Material randomMaterial = ItemList.food_items[random.nextInt(ItemList.food_items.length)];
		return new ItemStack(randomMaterial, random.nextInt(16)+1);
	}
	
	public static ItemStack getRandomEquipmentItems(Random random) {
		if (ItemList.equipment_items == null) {
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
		}
		Material randomMaterial = ItemList.equipment_items[random.nextInt(ItemList.equipment_items.length)];
		return new ItemStack(randomMaterial, isStackable(randomMaterial) ? random.nextInt(16)+1 : 1);
	}
	
	public static ItemStack getRandomRareItems(Random random) {

		if (ItemList.rare_items == null) {
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
					Material.SUGAR_CANE					
			};
		}
		Material randomMaterial = ItemList.rare_items[random.nextInt(ItemList.rare_items.length)];
		return new ItemStack(randomMaterial, isStackable(randomMaterial) ? random.nextInt(8)+1 : 1);
	}
	
	public static ItemStack getRandomLendaryItem(Random random) {
		if (ItemList.legendary_items == null) {
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
		Material randomMaterial = ItemList.rare_items[random.nextInt(ItemList.rare_items.length)];
		return new ItemStack(randomMaterial, isStackable(randomMaterial) ? random.nextInt(8)+1 : 1);
	}
	
	private static boolean isStackable(Material mat) {
		ItemStack item = new ItemStack(mat);
		return (item.getMaxStackSize() != 1);
	}

}
