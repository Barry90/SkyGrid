package me.barry1990.skygrid.generators;

import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.material.Furnace;
import org.bukkit.material.MonsterEggs;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Tree;
import org.bukkit.material.Wool;


class RandomMetaDataGenerator {
	
	private static Random random = new Random();

	/* Public  */
	
	public static Wool getWool() {
		int x = RandomMetaDataGenerator.random.nextInt(DyeColor.class.getEnumConstants().length);
		return new Wool (DyeColor.class.getEnumConstants()[x]);
	}
	
	public static Furnace getFurnace() {
		return new Furnace(RandomMetaDataGenerator.getBlockFaceNESW());
	}
	
	@Deprecated
 	public static DirectionalContainer getDirectionalContainer(Material m) {
 		DirectionalContainer dcon = new DirectionalContainer(m);
 		dcon.setFacingDirection(RandomMetaDataGenerator.getBlockFaceNESW());
 		return dcon;
 	}
 	
 	public static Pumpkin getPumpkin() {
 		return new Pumpkin(RandomMetaDataGenerator.getBlockFaceNESW());
 	}
 	
 	public static Tree getTree(Material m) {
 		return new Tree(RandomMetaDataGenerator.getTreeSpecies(m), RandomMetaDataGenerator.getBlockFaceNESWUD());
 	}
 	
 	public static MonsterEggs getMonsterEggs() {
 		MonsterEggs me = new MonsterEggs();
 		me.setMaterial(me.getTextures().get(RandomMetaDataGenerator.random.nextInt(me.getTextures().size()))); 		
 		return me;
 		//return RandomMetaDataGenerator.random.nextBoolean() ? new MonsterEggs(Material.STONE) : new MonsterEggs(Material.COBBLESTONE);
 	}
	
	/* Private */
	
	private static BlockFace getBlockFaceNESW() {			
		switch (RandomMetaDataGenerator.random.nextInt(4)) {
			case 0 : return BlockFace.NORTH;
			case 1 : return BlockFace.EAST;
			case 2 : return BlockFace.SOUTH;
			case 3 : return BlockFace.WEST;
		}
		//we should never get here
		return BlockFace.NORTH;
	}
	
	private static BlockFace getBlockFaceNESWUD() {			
		switch (RandomMetaDataGenerator.random.nextInt(6)) {
			case 0 : return BlockFace.NORTH;
			case 1 : return BlockFace.EAST;
			case 2 : return BlockFace.SOUTH;
			case 3 : return BlockFace.WEST;
			case 4 : return BlockFace.UP;
			case 5 : return BlockFace.DOWN;
		}
		//we should never get here
		return BlockFace.NORTH;
	}
	
	public static TreeSpecies getTreeSpecies(Material m) {
		if (m == Material.LEAVES | m == Material.LOG) {
			switch (RandomMetaDataGenerator.random.nextInt(4)) {
				case 0 : return TreeSpecies.GENERIC;
				case 1 : return TreeSpecies.BIRCH;
				case 2 : return TreeSpecies.JUNGLE;
				case 3 : return TreeSpecies.DARK_OAK;
			}
		}
		if (m == Material.LEAVES_2 | m == Material.LOG_2) {
			return RandomMetaDataGenerator.random.nextBoolean() ? TreeSpecies.ACACIA : TreeSpecies.REDWOOD;
		}
		if (m == Material.SAPLING) {
			int x = RandomMetaDataGenerator.random.nextInt(TreeSpecies.class.getEnumConstants().length);
			return TreeSpecies.class.getEnumConstants()[x];
		}			
		//we should never get here
		return TreeSpecies.GENERIC;
		
	}
}
