package me.barry1990.skygrid.generators;

import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Chest;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.material.Furnace;
import org.bukkit.material.MonsterEggs;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Tree;
import org.bukkit.material.Wood;
import org.bukkit.material.Wool;


public class RandomMetaDataGenerator {
	
	private Random random = new Random();
	
	public RandomMetaDataGenerator() {
		this.random = new Random();
	}

	/* Public  */
	
	public Wool getWool() {
		int x = this.random.nextInt(DyeColor.class.getEnumConstants().length);
		return new Wool (DyeColor.class.getEnumConstants()[x]);
	}
	
	public Furnace getFurnace() {
		return new Furnace(this.getBlockFaceNESW());
	}
	
	public Chest getChest() {
		return new Chest(this.getBlockFaceNESW());
	}
	
	@Deprecated
 	public DirectionalContainer getDirectionalContainer(Material m) {
 		DirectionalContainer dcon = new DirectionalContainer(m);
 		dcon.setFacingDirection(this.getBlockFaceNESW());
 		return dcon;
 	}
 	
 	public Pumpkin getPumpkin() {
 		return new Pumpkin(this.getBlockFaceNESW());
 	}
 	
 	public Wood getWood(Material m) {
 		return new Wood(this.getTreeSpecies(m));
 	}
 	
 	public Tree getTree(Material m) {
 		return new Tree(this.getTreeSpecies(m), this.getBlockFaceNESWUD());
 	}
 	
 	public Tree getTreeFullTexture(Material m) {
 		return new Tree(this.getTreeSpecies(m), BlockFace.SELF);
 	}
 	
 	public MonsterEggs getMonsterEggs() {
 		MonsterEggs me = new MonsterEggs();
 		me.setMaterial(me.getTextures().get(this.random.nextInt(me.getTextures().size()))); 		
 		return me;
 		//return RandomMetaDataGenerator.random.nextBoolean() ? new MonsterEggs(Material.STONE) : new MonsterEggs(Material.COBBLESTONE);
 	}
	
	/* Private */
	
	private BlockFace getBlockFaceNESW() {			
		switch (this.random.nextInt(4)) {
			case 0 : return BlockFace.NORTH;
			case 1 : return BlockFace.EAST;
			case 2 : return BlockFace.SOUTH;
			case 3 : return BlockFace.WEST;
		}
		//we should never get here
		return BlockFace.NORTH;
	}
	
	private BlockFace getBlockFaceNESWUD() {			
		switch (this.random.nextInt(6)) {
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
	
	public TreeSpecies getTreeSpecies(Material m) {
		if (m == Material.LEAVES | m == Material.LOG) {
			switch (this.random.nextInt(4)) {
				case 0 : return TreeSpecies.GENERIC;
				case 1 : return TreeSpecies.BIRCH;
				case 2 : return TreeSpecies.JUNGLE;
				case 3 : return TreeSpecies.REDWOOD;
			}
		}
		if (m == Material.LEAVES_2 | m == Material.LOG_2) {
			return this.random.nextBoolean() ? TreeSpecies.ACACIA : TreeSpecies.DARK_OAK;
		}
		if (m == Material.SAPLING) {
			int x = this.random.nextInt(TreeSpecies.class.getEnumConstants().length);
			return TreeSpecies.class.getEnumConstants()[x];
		}			
		//we should never get here
		return TreeSpecies.GENERIC;
		
	}
}
