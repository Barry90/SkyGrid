package me.barry1990.skygrid.achievement;

import org.bukkit.Material;

class SGAFileConst {
	public static final byte END = (byte) 0x00;
	public static final byte ACHIEVEMENTS = (byte) 0x01;
	public static final byte A_WOODMANIAC = (byte) 0x10;
	public static final byte A_STONEMANIAC = (byte) 0x11;
	public static final byte A_IRONMANIAC = (byte) 0x12;
	public static final byte A_GOLDMANIAC = (byte) 0x13;
	public static final byte A_DIAMMANIAC = (byte) 0x14;
	public static final byte A_NETHERCLEANER = (byte) 0x20;
	
}

///////////////////////////////////
// MANIAC-ENUM-TABLE
///////////////////////////////////

enum SGAManiacConst {
	
	WOOD_SWORD(Material.WOOD_SWORD,(byte) 0x01),	
	WOOD_AXE(Material.WOOD_AXE ,(byte) 0x02),
	WOOD_HOE(Material.WOOD_HOE ,(byte) 0x03),
	WOOD_SPADE(Material.WOOD_SPADE ,(byte) 0x04),
	WOOD_PICKAXE(Material.WOOD_PICKAXE ,(byte) 0x05),
	
	STONE_SWORD(Material.STONE_SWORD ,(byte) 0x01),
	STONE_AXE(Material.STONE_AXE ,(byte) 0x02),
	STONE_HOE(Material.STONE_HOE ,(byte) 0x03),
	STONE_SPADE(Material.STONE_SPADE ,(byte) 0x04),
	STONE_PICKAXE(Material.STONE_PICKAXE ,(byte) 0x05),
	
	IRON_SWORD(Material.IRON_SWORD ,(byte) 0x01),
	IRON_AXE(Material.IRON_AXE ,(byte) 0x02),
	IRON_HOE(Material.IRON_HOE ,(byte) 0x03),
	IRON_SPADE(Material.IRON_SPADE ,(byte) 0x04),
	IRON_PICKAXE(Material.IRON_PICKAXE ,(byte) 0x05),
	IRON_HELMET(Material.IRON_HELMET ,(byte) 0x06),
	IRON_CHESTPLATE(Material.IRON_CHESTPLATE ,(byte) 0x07),
	IRON_LEGGINGS(Material.IRON_LEGGINGS ,(byte) 0x08),
	IRON_BOOTS(Material.IRON_BOOTS ,(byte) 0x09),
	
	GOLD_SWORD(Material.GOLD_SWORD ,(byte) 0x01),
	GOLD_AXE(Material.GOLD_AXE ,(byte) 0x02),
	GOLD_HOE(Material.GOLD_HOE ,(byte) 0x03),
	GOLD_SPADE(Material.GOLD_SPADE ,(byte) 0x04),
	GOLD_PICKAXE(Material.GOLD_PICKAXE ,(byte) 0x05),
	GOLD_HELMET(Material.GOLD_HELMET ,(byte) 0x06),
	GOLD_CHESTPLATE(Material.GOLD_CHESTPLATE ,(byte) 0x07),
	GOLD_LEGGINGS(Material.GOLD_LEGGINGS ,(byte) 0x08),
	GOLD_BOOTS(Material.GOLD_BOOTS ,(byte) 0x09),
	
	DIAMOND_SWORD(Material.DIAMOND_SWORD ,(byte) 0x01),
	DIAMOND_AXE(Material.DIAMOND_AXE ,(byte) 0x02),
	DIAMOND_HOE(Material.DIAMOND_HOE ,(byte) 0x03),
	DIAMOND_SPADE(Material.DIAMOND_SPADE ,(byte) 0x04),
	DIAMOND_PICKAXE(Material.DIAMOND_PICKAXE ,(byte) 0x05),
	DIAMOND_HELMET(Material.DIAMOND_HELMET ,(byte) 0x06),
	DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE ,(byte) 0x07),
	DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS ,(byte) 0x08),
	DIAMOND_BOOTS(Material.DIAMOND_BOOTS ,(byte) 0x09);	
	
	public final Material m;
	public final byte b;
	
	
	private SGAManiacConst(Material m, byte b) {
		this.m = m;
		this.b = b;		
	}
	
	public static SGAManiacConst getSGAManiacConstFromByte(byte b) {
		int count = SGAManiacConst.class.getEnumConstants().length;
		for (int i=0;i<count;i++) {
			if (b == SGAManiacConst.class.getEnumConstants()[i].b)				
				return SGAManiacConst.class.getEnumConstants()[i];
		}
		throw new IllegalArgumentException();
	}
	
	public static SGAManiacConst getSGAManiacConstFromMaterial(Material m) {
		int count = SGAManiacConst.class.getEnumConstants().length;
		for (int i=0;i<count;i++) {
			if (m == SGAManiacConst.class.getEnumConstants()[i].m)				
				return SGAManiacConst.class.getEnumConstants()[i];
		}
		throw new IllegalArgumentException();
	}
	
	public static boolean isMaterialManiacMaterial(Material m) {
		int count = SGAManiacConst.class.getEnumConstants().length;
		for (int i=0;i<count;i++) {
			if (m == SGAManiacConst.class.getEnumConstants()[i].m)				
				return true;
		}
		return false;
	}
	
	
	
}
