package me.barry1990.skygrid.generators;

import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.material.MonsterEggs;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Tree;
import org.bukkit.material.Wool;


public class RandomMetaDataGenerator {
	/* Public  */
	
    public static Wool getWool(Random random) {
        int x = random.nextInt(DyeColor.class.getEnumConstants().length);
        return new Wool (DyeColor.class.getEnumConstants()[x]);
    }
    
 	public static DirectionalContainer getDirectionalContainer(Material m, Random random) {
 		DirectionalContainer dcon = new DirectionalContainer(m);
 		dcon.setFacingDirection(RandomMetaDataGenerator.getBlockFaceNESW(random));
 		return dcon;
 	}
 	
 	public static Pumpkin getPumpkin(Random random) {
 		return new Pumpkin(RandomMetaDataGenerator.getBlockFaceNESW(random));
 	}
 	
 	public static Tree getTree(Material m, Random random) {
 		return new Tree(RandomMetaDataGenerator.getTreeSpecies(m, random),RandomMetaDataGenerator.getBlockFaceNESWUD(random));
 	}
 	
 	public static MonsterEggs getMonsterEggs(Random random) {
 		MonsterEggs me = new MonsterEggs();
 		me.setMaterial(me.getTextures().get(random.nextInt(me.getTextures().size()))); 		
 		return me;
 		//return random.nextBoolean() ? new MonsterEggs(Material.STONE) : new MonsterEggs(Material.COBBLESTONE);
 	}	    
    
    /* Private */
    
    private static BlockFace getBlockFaceNESW(Random random) {	    	
    	switch (random.nextInt(4)) {
    		case 0 : return BlockFace.NORTH;
    		case 1 : return BlockFace.EAST;
    		case 2 : return BlockFace.SOUTH;
    		case 3 : return BlockFace.WEST;
    	}
    	//we should never get here
        return BlockFace.NORTH;
    }
    
    private static BlockFace getBlockFaceNESWUD(Random random) {	    	
    	switch (random.nextInt(6)) {
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
    
    public static TreeSpecies getTreeSpecies(Material m,Random random) {
    	if (m == Material.LEAVES | m == Material.LOG) {
    		switch (random.nextInt(4)) {
	    		case 0 : return TreeSpecies.GENERIC;
	    		case 1 : return TreeSpecies.BIRCH;
	    		case 2 : return TreeSpecies.JUNGLE;
	    		case 3 : return TreeSpecies.DARK_OAK;
    		}
    	}
    	if (m == Material.LEAVES_2 | m == Material.LOG_2) {
    		return random.nextBoolean() ? TreeSpecies.ACACIA : TreeSpecies.REDWOOD;
    	}
    	if (m == Material.SAPLING) {
    		int x = random.nextInt(TreeSpecies.class.getEnumConstants().length);
	        return TreeSpecies.class.getEnumConstants()[x];
    	}	    	
    	//we should never get here
    	return TreeSpecies.GENERIC;
    	
    }
}
