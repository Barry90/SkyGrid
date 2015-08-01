package me.barry1990.skygrid;

//import eventlisteners
import me.barry1990.skygrid.eventlistener.SkyGridOnBlockFromTo;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerJoin;
import me.barry1990.skygrid.generators.SkyGridGenerator;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyGrid extends JavaPlugin {
	
	private static SkyGrid plugin = null;

	
	
	@Override
	public void onEnable() {
		BarrysLogger.PRINT_LOGS = true;
		
		plugin = this;
				
		//register eventlisteners
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerJoin(), this);
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
	

	

}
