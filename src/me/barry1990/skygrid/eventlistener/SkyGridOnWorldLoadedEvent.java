package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.world.SkyGridWorld;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * SkyGridOnWorldLoadedEvent - This class handles the world loading.
 * 
 * @author Barry1990
 */
public class SkyGridOnWorldLoadedEvent implements Listener {

	@EventHandler
	public void OnWorldLoad(WorldLoadEvent e) {

		BarrysLogger.info("World loaded: " + e.getWorld().getName());

		if (e.getWorld().getName().equals("world")) {

			// After the default world (the waitingroom) has loaded, we can load/create the skygridworld.
			// We have to do this in a new BukkitRunnanble, otherwise we get an inconsistence exception:
			// (WorldLoadEventHandler causes a new WorldLoadEvent)

			new BukkitRunnable() {

				@Override
				public void run() {

					BarrysLogger.info(this, "Create SkyGridWorld...");
					new SkyGridWorld() {

						@Override
						protected void worldLoaded(World world) {

							// do nothing
							BarrysLogger.info(this, "World created..." + world.getName());
						}

					}.create();

				}
			}.runTaskLater(SkyGrid.sharedInstance(), 0);

		}
	}

	@EventHandler
	public void onWorldInit(WorldInitEvent e) {

		// avoid preloading chunks on generation, this saves a lot of time during startup
		e.getWorld().setKeepSpawnInMemory(false);
	}

}
