package me.barry1990.skygrid.generators;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;


public final class SkyGridChunkGenerator extends ChunkGenerator implements SkyGridChunkDataGeneratorThread.IChunkDataProvider {
	
	private SkyGridChunkDataGeneratorThread chunkGenerator;	
	private static boolean asyncGeneration = true; // default true
			
	public SkyGridChunkGenerator() {
		//Nothing to do here
		BarrysLogger.info(this, "new instance");
	}

	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {

		// start the thread
		if (asyncGeneration && this.chunkGenerator == null) {
			this.chunkGenerator = new SkyGridChunkDataGeneratorThread(world, this, SkyGrid.getLevelManager().getLevel());
			BarrysLogger.info(this, "Start the SkyGridChunkGenerator-Thread");
			this.chunkGenerator.start();
		}
		
		ChunkData data;
		
		try {
			if (SkyGrid.getLevelManager().isAltarChunk(x, z))
				data = SkyGrid.getLevelManager().getAltarChunkData(world, this.createChunkData(world));
			else {
				if (asyncGeneration)
					data = chunkGenerator.getChunk();
				else
					data = SkyGrid.getLevelManager().getLevel().fillChunkData(this.createChunkData(world));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			data = this.createChunkData(world);
		}

		return data;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator)new SkyGridBlockPopulator());
	}
	
	@Override
	public ChunkData getChunkData(World world) {
		return this.createChunkData(world);
	}
	
	public void dispose() {
		if (this.chunkGenerator != null)
			this.chunkGenerator.softstop();
	}
		

}
