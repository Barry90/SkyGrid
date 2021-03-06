package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.sql.SkyGridSQL;
import me.barry1990.skygrid.world.SkyGridWorld;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

/**
 * SkyGridOnPlayerJoin - This class handles a player join and player leave.
 * 
 * @author Barry1990
 */
public final class SkyGridOnPlayerJoin implements Listener {

	/* These events will be called in the below order */

	@EventHandler(ignoreCancelled = true)
	public void onPlayerLoginEvent(PlayerLoginEvent e) {

		BarrysLogger.info(this, "OnPlayerLoginEvent called");

		if (e.getResult() == Result.ALLOWED) {
			SkyGridOnPlayerJoin.registerAndLoadPlayer(e.getPlayer());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerSpawnLocationEvent(PlayerSpawnLocationEvent e) {

		BarrysLogger.info(this, "PlayerSpawnLocationEvent called");
		Player player = e.getPlayer();

		if (SkyGridWorld.isReady()) {
			if (SkyGridSQL.sharedInstance().getHome(player, SkyGridSQL.SPAWN_POINT) == null) {

				Location loc = SkyGrid.getLevelManager().getLevel().generateSkyGridSpawnLocation(SkyGridWorld.getSkyGridWorld());
				SkyGridSQL.sharedInstance().addHome(player, loc, SkyGridSQL.SPAWN_POINT);
				e.setSpawnLocation(loc);
			}
		} else {
			e.setSpawnLocation(SkyGridWorld.getWaitingRoom());
		}

	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {

		BarrysLogger.info(this, "PlayerJoinEvent called");
		Player player = event.getPlayer();

		SkyGridOnPlayerJoin.loadAfterPlayerJoin(player);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerQuitEvent(PlayerQuitEvent e) {

		SkyGrid.getPlayerManager().unload(e.getPlayer());
	}

	/**
	 * Registers and loads the SkyPlayer. Make sure that the player loged in successfully
	 * @param p The Player to register
	 */
	public static void registerAndLoadPlayer(Player p) {

		SkyGridSQL.sharedInstance().addPlayer(p);
		SkyGrid.getPlayerManager().load(p);
	}

	/**
	 * This method setups stuff after the player joined 
	 * @param p The player
	 */
	public static void loadAfterPlayerJoin(Player p) {

		if (!p.getLocation().getWorld().getName().equals(SkyGridWorld.getSkyGridWorld().getName())) {
			Location loc = SkyGridSQL.sharedInstance().getHome(p, SkyGridSQL.SPAWN_POINT);
			if (loc == null) {
				loc = SkyGrid.getLevelManager().getLevel().generateSkyGridSpawnLocation(SkyGridWorld.getSkyGridWorld());
				SkyGridSQL.sharedInstance().addHome(p, loc, SkyGridSQL.SPAWN_POINT);
			}
		}

		SkyGrid.getPlayerManager().loadAfterPlayerJoin(p);
	}

}
