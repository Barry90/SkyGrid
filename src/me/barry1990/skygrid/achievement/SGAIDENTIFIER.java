package me.barry1990.skygrid.achievement;

import org.bukkit.Material;


///////////////////////////////////
//ACHIEVEMENT-IDENTIFIER
///////////////////////////////////

public enum SGAIDENTIFIER {
	/* Maniac */
	WOOD_MANIAC((byte) 0x10),
	STONE_MANIAC((byte) 0x11),
	IRON_MANIAC((byte) 0x12),
	GOLD_MANIAC((byte) 0x13),
	DIAMOND_MANIAC((byte) 0x14),
	
	/* No progress */
	INFINITE_WATER_SOURCE((byte)0x20),
	HOT_BUCKET((byte)0x21),
	THAT_WAS_CLOSE((byte)0x22),
	GET_RICH_OR_DIE_TRYIN((byte)0x23),
	ON_TOP_OF_THE_WORLD((byte)0x24),
	TIME_TO_ENCHANT((byte)0x25),
	OH_SHIT((byte)0x26),
	NETHER_CLEANER((byte)0x27),
	A_VERY_BIG_EGG((byte)0x28),
	VEGETABLE_MASTER((byte)0x29),
	SOUP_KING((byte)0x2A),
	
	/* permissions achievements */
	SO_IT_BEGINS((byte) 0xF0),
	GO_DEEPER((byte)0xF1),
	GO_EVEN_DEEPER((byte)0xF2);	
	
	public final byte		id;

	private SGAIDENTIFIER(byte id) {
		this.id = id;
	}
	
	public static SGAIDENTIFIER getSGAIDENTIFIERFromId(byte b) {

		int count = SGAIDENTIFIER.class.getEnumConstants().length;
		for (int i = 0; i < count; i++) {
			if (b == SGAIDENTIFIER.class.getEnumConstants()[i].id)
				return SGAIDENTIFIER.class.getEnumConstants()[i];
		}
		throw new IllegalArgumentException();
	}

}

///////////////////////////////////
//FILE-CONSTANT
///////////////////////////////////

class SkyGridConst {
	public static final byte END = (byte) 0xEF;
	public static final byte ACHIEVEMENTS = (byte) 0x01;
}

///////////////////////////////////
//MANIAC-ENUM-TABLE
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

	public final Material	m;
	public final byte		b;

	private SGAManiacConst(Material m, byte b) {

		this.m = m;
		this.b = b;
	}

	public static SGAManiacConst getSGAManiacConstFromByte(byte b) {

		int count = SGAManiacConst.class.getEnumConstants().length;
		for (int i = 0; i < count; i++) {
			if (b == SGAManiacConst.class.getEnumConstants()[i].b)
				return SGAManiacConst.class.getEnumConstants()[i];
		}
		throw new IllegalArgumentException();
	}

	public static SGAManiacConst getSGAManiacConstFromMaterial(Material m) {

		int count = SGAManiacConst.class.getEnumConstants().length;
		for (int i = 0; i < count; i++) {
			if (m == SGAManiacConst.class.getEnumConstants()[i].m)
				return SGAManiacConst.class.getEnumConstants()[i];
		}
		throw new IllegalArgumentException();
	}

	public static boolean isMaterialManiacMaterial(Material m) {

		int count = SGAManiacConst.class.getEnumConstants().length;
		for (int i = 0; i < count; i++) {
			if (m == SGAManiacConst.class.getEnumConstants()[i].m)
				return true;
		}
		return false;
	}



}