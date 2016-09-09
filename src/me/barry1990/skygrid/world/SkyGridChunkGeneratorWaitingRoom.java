package me.barry1990.skygrid.world;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

/**
 * SkyGridChunkGeneratorWaitingRoom - This class is the chunk generator for the waiting romm
 * 
 * @author Barry1990
 */
public class SkyGridChunkGeneratorWaitingRoom extends ChunkGenerator {
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		
		ChunkData data = this.createChunkData(world);
		if (x == 0 && z == 0) {
			data.setRegion(0, 0, 0, 1, 255, 16, Material.BARRIER);
			data.setRegion(0, 0, 0, 16, 256, 1, Material.BARRIER);
			data.setRegion(0, 0, 15, 16, 256, 16, Material.BARRIER);
			data.setRegion(15, 0, 0, 16, 256, 16, Material.BARRIER);			
			data.setRegion(0, 127, 0, 16, 128, 16, Material.BEDROCK);
		}
		return data;
		
	}
}

