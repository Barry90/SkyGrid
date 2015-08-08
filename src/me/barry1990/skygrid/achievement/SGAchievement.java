package me.barry1990.skygrid.achievement;

public enum SGAchievement {
	
	////////////////////
	// Overworld
	////////////////////
	
	SO_IT_BEGINS((byte)0x01),
	WOOD_MANIAC((byte)0x02),
	STONE_MANIAC((byte)0x03),
	IRON_MANIAC((byte)0x04),
	GOLD_MANIAC((byte)0x05),
	DIAMOND_MANIAC((byte)0x06),
	INFINITE_WATER_SOURCE((byte)0x07),
	HOT_BUCKET((byte)0x08),
	THAT_WAS_CLOSE((byte)0x09),
	GET_RICH_OR_DIE_TRYIN((byte)0x0A),
	ON_TOP_OF_THE_WORLD((byte)0x0B),
	TIME_TO_ENCHANT((byte)0x0C),
	
	////////////////////
	// Nether
	////////////////////
	
	GO_DEEPER((byte)0x21),
	OH_SHIT((byte)0x22),
	USELESS_ITEM((byte)0x23),
	
	////////////////////
	// End
	////////////////////
	
	GO_EVEN_DEER((byte)0x41),
	A_VERY_BIG_EGG((byte)0x42);
	
	public final byte b;
	
	private SGAchievement(byte b) {

		this.b = b;		
	}
	
	public static SGAchievement getSGAchievementFromByte(byte b) {
		int count = SGAchievement.class.getEnumConstants().length;
		for (int i=0;i<count;i++) {
			if (b == SGAchievement.class.getEnumConstants()[i].b)				
				return SGAchievement.class.getEnumConstants()[i];
		}
		throw new IllegalArgumentException();
	}
	
	
	public static String getname(byte b) {
		switch (b) {
			case 0x01:	return "SO IT BEGINS";
			case 0x02:	return "WOOD MANIAC";
			case 0x03:	return "STONE MANIAC";
			case 0x04:	return "IRON MANIAC";
			case 0x05:	return "GOLD MANIAC";
			case 0x06:	return "DIAMOND MANIAC";
			case 0x07:	return "INFINITE WATER SOURCE?";
			case 0x08:	return "HOT BUCKET";
			case 0x09:	return "THAT WAS CLOSE";
			case 0x0A:	return "GET RICH OR DIE TRYIN";
			case 0x0B:	return "ON TOP OF THE WORLD";
			case 0x0C:	return "TIME TO ENCHANT";
	
			case 0x21:	return "GO DEEPER";
			case 0x22:	return "OH SH**";
			case 0x23:	return "USELESS ITEM";
				
			case 0x41:	return "GO EVEN DEEPER";
			case 0x42:	return "A VERY BIG EGG";
			default: return "";
		
		}
	}
}