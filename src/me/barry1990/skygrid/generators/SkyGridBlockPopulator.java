package me.barry1990.skygrid.generators;

import java.util.Random;

import me.barry1990.skygrid.level.ISkyGridBlockPopulator;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * SkyGridBlockPopulator - This class is populator for the skygrid world
 * 
 * @author Barry1990
 */
class SkyGridBlockPopulator extends BlockPopulator {

	private ISkyGridBlockPopulator	iPopulator;

	/**
	 * Creates a new instance of SkyGridBlockPopulator
	 * 
	 * @param iPopulator
	 */
	public SkyGridBlockPopulator(ISkyGridBlockPopulator iPopulator) {

		this.iPopulator = iPopulator;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {

		this.iPopulator.populate(world, random, chunk);
	}

	/**
	 * This method disposes all resources
	 */
	public void dispose() {

		this.iPopulator = null;
	}

}
