package me.barry1990.skygrid.level;

import java.util.List;
import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.world.SkyGridWorld;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.Tree;


public final class SkyGridLevelWood extends ISkyGridLevel {
	
//	private ISkyGridAlter altar;
//	private IPlayerThreads pthreads;
	
	public SkyGridLevelWood() {
		
		SkyGrid.registerEvent(new SkyGridLevelWoodListerner());
	}

	@Override
	public ChunkData fillChunkData(ChunkData chunkdata) {
		
		
		for (int y = 0; y < 256; y += 5) {
			
			for (int x = 1; x < 16; x += 4) {
				
				for (int z = 1; z < 16; z += 4) {
					
					chunkdata.setBlock(x, y, z, this.randomMetaData.getTreeFullTexture(Material.LOG));
					chunkdata.setBlock(x, y, z + 1, this.randomMetaData.getTreeFullTexture(Material.LOG));
					chunkdata.setBlock(x, y + 1, z, this.randomMetaData.getTreeFullTexture(Material.LOG));
					chunkdata.setBlock(x, y + 1, z + 1, this.randomMetaData.getTreeFullTexture(Material.LOG));
					chunkdata.setBlock(x + 1, y, z, this.randomMetaData.getTreeFullTexture(Material.LOG));
					chunkdata.setBlock(x + 1, y, z + 1, this.randomMetaData.getTreeFullTexture(Material.LOG));
					chunkdata.setBlock(x + 1, y + 1, z, this.randomMetaData.getTreeFullTexture(Material.LOG));
					chunkdata.setBlock(x + 1, y + 1, z + 1, this.randomMetaData.getTreeFullTexture(Material.LOG));
					
				}
				
			}
			
			
			
		}

		// TODO Auto-generated method stub
		return chunkdata;
	}

	@Override
	public Location generateSkyGridSpawnLocation() {

		// TODO Auto-generated method stub
		return new Location(SkyGridWorld.getSkyGridWorld(), 9, 20, 9);
	}

	@Override
	public ISkyGridAlter getSkyGridAltar() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPlayerThreads getPlayerThreads(UUID playeruuid) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	List<Recipe> registerRecipes() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAchievementAvailable(byte sga_id) {

		// TODO Auto-generated method stub
		return false;
	}
	
	private class SkyGridLevelWoodListerner implements Listener {
		
		@EventHandler
		public void onBlockPlaceEvent(BlockPlaceEvent e) {
			
			//  only set full textured log
			Block placed = e.getBlockPlaced();
			if (placed.getType() == Material.LOG) {
				
				Tree tree = ((Tree)placed.getState().getData());
				tree.setDirection(BlockFace.SELF);
				BlockState bs = placed.getState();
				bs.setData(tree);
				bs.update(true);				
			}
		}
	
	}

}
