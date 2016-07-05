package me.barry1990.skygrid.level;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerJoin;
import me.barry1990.skygrid.world.SkyGridWorld;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.inventory.Recipe;


public class SkyGridLevelStone extends ISkyGridLevel {
	
	private ISkyGridAlter altar; 
	
	public SkyGridLevelStone() {

		this.altar = new SkyGridStoneAltar();
	}

	@Override
	public ChunkData fillChunkData(ChunkData chunkdata) {

		for (int y = 1; y < chunkdata.getMaxHeight(); y=y+4) {
				
			for (int z = 1; z < 16; z=z+4) {
							
				for (int x = 1; x < 16; x=x+4) {
					chunkdata.setBlock(x, y, z, Material.STONE);
				}
			}			
		}
		
		return chunkdata;
	}
	
	@Override
	public Location generateSkyGridSpawnLocation() {
		Random random = new Random();
		double x,y,z;
		x = (random.nextInt(1500)-750) * 4 + 1.5;
		z = (random.nextInt(1500)-750) * 4 + 1.5;
		y = random.nextInt(8) * 4 + 174;
		
		BarrysLogger.info(SkyGridOnPlayerJoin.class,"x",x);
		BarrysLogger.info(SkyGridOnPlayerJoin.class,"y",y);
		BarrysLogger.info(SkyGridOnPlayerJoin.class,"z",z);
		
		return new Location(SkyGridWorld.getSkyGridWorld(), x, y, z);
	};

	@Override
	public ISkyGridAlter getSkyGridAltar() {
		return this.altar;
	}
	
	@Override
	public IPlayerThreads getPlayerThreads(UUID playeruuid) {
		return IPlayerThreads.EMPTY;
	}
	
	@Override
	List<Recipe> registerRecipes() {
		return null;
	}

	@Override
	public boolean isAchievementAvailable(byte sga) {
		return false;
	}
	
	private class SkyGridStoneAltar extends ISkyGridAlter {

		@Override
		public ChunkData getChunkData(World world, ChunkData chunkdata) {

			chunkdata.setRegion(0, 0, 0, 16, 3, 16, Material.GOLD_BLOCK);
			return chunkdata;
		}

		@Override
		protected void loadAltarChunkData() {}

		@Override
		boolean isMaterialAllowed(Material material) {
			return (material == Material.GOLD_BLOCK);
		}

		@Override
		boolean canBuildonLocation(Location location) {
			return (location.getBlockY() < 30);
		}

		@Override
		void buildOnLocationEvent(Location location) {
		}

		@Override
		protected boolean isAltarComplete(Chunk chunk) {
			return false;
		}

		@Override
		protected void doEndAnimation(Chunk chunk) {
		}

		@Override
		void preprareNextAltar() {
		}
		
	}


}
