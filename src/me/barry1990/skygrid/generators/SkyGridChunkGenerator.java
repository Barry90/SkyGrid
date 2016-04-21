package me.barry1990.skygrid.generators;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;


public final class SkyGridChunkGenerator extends ChunkGenerator implements SkyGridChunkDataGeneratorThread.iChunkDataGeneratorThread {
	
	private static SkyGridChunkGenerator sharedInstance;
	
	private static SkyGridChunkDataGeneratorThread chunkGenerator;
		
	private SkyGridChunkGenerator() {
		//Nothing to do here		
	}
	
	public static SkyGridChunkGenerator sharedInstance() {
		if (SkyGridChunkGenerator.sharedInstance == null) {
			SkyGridChunkGenerator.sharedInstance = new SkyGridChunkGenerator();
		}
		return SkyGridChunkGenerator.sharedInstance;
	}

	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		
		// start the thread
		if (SkyGridChunkGenerator.chunkGenerator == null) {
			SkyGridChunkGenerator.chunkGenerator = new SkyGridChunkDataGeneratorThread(world, this);
			BarrysLogger.info(this, "Start the SkyGridChunkGenerator-Thread");
			SkyGridChunkGenerator.chunkGenerator.start();
		}
		
		ChunkData data;
		try {
			data = chunkGenerator.getChunk();
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
		

}
