package me.barry1990.skygrid.world;

import java.io.File;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;
import me.barry1990.utils.FileManagement;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.scheduler.BukkitRunnable;


public abstract class SkyGridWorld {
	
	private static String NAME = "skygrid";
	protected static boolean isReady = false;

	abstract protected void worldLoaded(World world);
	
	public void create() {
		
		WorldCreator wc = null;;
		wc = new WorldCreator(SkyGridWorld.NAME);
		wc.generator(SkyGrid.getLevelManager().getGenerator());
		wc.environment(Environment.NORMAL);
		wc.generateStructures(false);
		World w = wc.createWorld();
		SkyGridWorld.isReady = true;
		this.worldLoaded(w);
		
	}
	
	/*
	 * Bevor recreating the skygrid world make sure that all player are teleported to the waitingroom
	 */
	private static int i;
	public void recreate() {
		
		SkyGridWorld.isReady = false;
		
		BarrysLogger.info("will unload skygrid world");
		SkyGrid.sharedInstance().getServer().unloadWorld(SkyGrid.sharedInstance().getServer().getWorld(SkyGridWorld.NAME), true);
		
		i = 0;
		new BukkitRunnable() {
            @Override
            public void run() {
            	
            	i++;
                boolean loaded = false;
                for (World currentWorld : SkyGrid.sharedInstance().getServer().getWorlds()) {
                    if (currentWorld.getName().equals(SkyGridWorld.NAME)) {
                        loaded = true;
                        BarrysLogger.info("World is still loaded");
                    }
                }

                //If the world is not in Bukkit.getWorlds() then we can assume that the world is unloaded and begin the delete the world.
                if (!loaded) {
                    FileManagement.deleteDirectory(new File(SkyGridWorld.NAME));
                    SkyGridWorld.this.create();
                    this.cancel();
                    return;
                }
                
                if (i == 30) {
                	BarrysLogger.error(SkyGridWorld.this, "Could not create new skygridworld. Failed to delete old skygrid world. Remove the world skygrid manually and restart the server.");
                	this.cancel();
                }
                
                
            }
        }.runTaskTimer(SkyGrid.sharedInstance(), 20, 20);
	}
	
	public static boolean isReady() {
		return SkyGridWorld.isReady;
	}
	
	public static World getSkyGridWorld() {
		return SkyGrid.sharedInstance().getServer().getWorld(SkyGridWorld.NAME);
	}
	
	public static Location getWaitingRoom() {
		return new Location(SkyGrid.sharedInstance().getServer().getWorld("world"), 7,128,7);
	}

}
