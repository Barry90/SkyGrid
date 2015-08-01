package me.barry1990.skygrid;

//import eventlisteners
import me.barry1990.skygrid.eventlistener.SkyGridOnBlockFromTo;
import me.barry1990.skygrid.eventlistener.SkyGridOnCraftItem;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerJoin;
//import the generator
import me.barry1990.skygrid.generators.SkyGridGenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyGrid extends JavaPlugin {
		
	@Override
	public void onEnable() {
		
		//prepare the generator
		SkyGridGenerator.sharedInstance();
			
		//register eventlisteners
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerJoin(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnBlockFromTo(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnCraftItem(), this);
		
		//add skygrid recipes
		SkyGridRecipes.addSkyGridRecipes(this);
		
		this.getLogger().info("v" + this.getDescription().getVersion() + " enabled.");		
	}
	
	@Override
	public void onDisable() {
	
		this.getLogger().info("v" + this.getDescription().getVersion() + " disabled.");
		
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return SkyGridGenerator.sharedInstance();
	}


}
