package me.barry1990.skygrid.level;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;


public interface ISkyGridBlockPopulator {

	public void populate(World world, Random random, Chunk chunk);
}
