package me.barry1990.skygrid;

import java.util.HashMap;
import java.util.List;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyGrid extends JavaPlugin {
	
	private static SkyGrid plugin = null;

	public static HashMap<String,List<ComplexBlock>> blockQueue;
	public static HashMap<String,List<ComplexBlock>> blockQueue_nether;
	
	@Override
	public void onEnable() {
		BarrysLogger.PRINT_LOGS = true;
		
		plugin = this;
		//initilize members
		SkyGrid.blockQueue = new HashMap<String,List<ComplexBlock>>();
		SkyGrid.blockQueue_nether = new HashMap<String,List<ComplexBlock>>();
		
		//register eventlisteners
		this.getServer().getPluginManager().registerEvents(new SkyGridPlayerJoin(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnBlockFromTo(), this);
		
		this.getLogger().info("v" + this.getDescription().getVersion() + " enabled.");
	}
	
	@Override
	public void onDisable() {
	
		// TODO Auto-generated method stub
		this.getLogger().info("v" + this.getDescription().getVersion() + " disabled.");
		
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new SkyGridGenerator();
	}
	
	public static SkyGrid getSkyGridPlugin() {
		return plugin;
	}
	
	//synchronized method for accessing the HashMaps
	public static synchronized List<ComplexBlock> blockQueue_normal_get(String key) {
		synchronized (blockQueue) {
			return blockQueue.get(key);
		}
	}
	
	public static synchronized void blockQueue_normal_put(String key, List<ComplexBlock> list) {
		synchronized (blockQueue) {
			SkyGrid.blockQueue.put(key,list);
		}
	}
	
	public static synchronized void blockQueue_normal_remove(String key) {
		synchronized (blockQueue) {
			SkyGrid.blockQueue.remove(key);
		}
	}
	
	public static synchronized List<ComplexBlock> blockQueue_nether_get(String key) {
		synchronized (blockQueue_nether) {
			return blockQueue_nether.get(key);
		}
	}
	
	public static synchronized void blockQueue_nether_put(String key, List<ComplexBlock> list) {
		synchronized (blockQueue_nether) {
			SkyGrid.blockQueue_nether.put(key,list);
		}
	}
	
	public static synchronized void blockQueue_nether_remove(String key) {
		synchronized (blockQueue_nether) {
			SkyGrid.blockQueue_nether.remove(key);
		}
	}
	
	

}
