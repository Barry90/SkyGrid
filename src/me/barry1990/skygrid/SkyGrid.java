package me.barry1990.skygrid;

//import commands
import me.barry1990.skygrid.commands.SkyGridAchievementCommand;
import me.barry1990.skygrid.commands.SkyGridAfkCommand;
import me.barry1990.skygrid.commands.SkyGridDebugCommand;
import me.barry1990.skygrid.commands.SkyGridDeleteHomeCommand;
import me.barry1990.skygrid.commands.SkyGridHomeCommand;
import me.barry1990.skygrid.commands.SkyGridInviteHomeCommand;
import me.barry1990.skygrid.commands.SkyGridListHomesCommand;
import me.barry1990.skygrid.commands.SkyGridSetHomeCommand;
//import eventlisteners
import me.barry1990.skygrid.eventlistener.SkyGridOnInventoryClickEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerBedEnterEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerDeathEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerJoin;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerMoveEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerRespawnEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPortalCreateEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnWorldLoadedEvent;
//import the generator
import me.barry1990.skygrid.level.SkyGridLevel_Manager;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.skygrid.sql.SkyGridSQL;
import me.barry1990.skygrid.world.SkyGridChunkGeneratorWaitingRoom;
import me.barry1990.skygrid.world.SkyGridWorld;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyGrid extends JavaPlugin {

	private static SkyGrid						sharedinstance;
	private SkyGridLevel_Manager				levelmanager;
	private SkyGridChunkGeneratorWaitingRoom	waitingRoomGenerator;

	// Fix reload bug:
	// Thank you Spigot for not giving the option to reload a ChunkGenerator for a loaded world.
	// To get the reload command work correctly we have reload it in onEnable if the world is loaded.
	// Thats why we have to teleport all player to the watingroom in onDisable

	@Override
	public void onEnable() {

		SkyGrid.sharedinstance = this;
		this.levelmanager = new SkyGridLevel_Manager();
		this.waitingRoomGenerator = new SkyGridChunkGeneratorWaitingRoom();

		SkyGridSQL.sharedInstance();

		this.getServer().getPluginManager().registerEvents(new SkyGridOnWorldLoadedEvent(), this);

		boolean skyGridWorldWasloaded = false;
		for (World currentWorld : SkyGrid.sharedInstance().getServer().getWorlds()) {
			if (currentWorld.getName().equals("skygrid")) {
				skyGridWorldWasloaded = true;
				new SkyGridWorld() {

					@Override
					protected void worldLoaded(World world) {

						BarrysLogger.info("SkyGrid-World reloaded");
						SkyGrid.this.registerEventListenerAndCommands();
						for (Player p : SkyGrid.sharedInstance().getServer().getOnlinePlayers()) {
							SkyGridOnPlayerJoin.registerAndLoadPlayer(p);
							SkyGridOnPlayerJoin.loadAfterPlayerJoin(p);
						}
					}
				}.reload(false);
				break;
			}
		}

		if (!skyGridWorldWasloaded) {
			this.registerEventListenerAndCommands();
		}

		this.getLogger().info("v" + this.getDescription().getVersion() + " enabled.");
	}

	@Override
	public void onDisable() {

		// TODO - test in onEnable if world equals skygrid
		for (Player p : this.getServer().getOnlinePlayers()) {
			SkyGridPlayerManager.unload(p);
			p.teleport(new Location(SkyGrid.sharedInstance().getServer().getWorld("world"), 7, 129, 7));
		}

		SkyGridSQL.sharedInstance().close();
		this.levelmanager.dispose();
		SkyGrid.sharedinstance = null;
		this.getLogger().info("v" + this.getDescription().getVersion() + " disabled.");

	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {

		return this.waitingRoomGenerator;
	}

	public static SkyGrid sharedInstance() {

		return SkyGrid.sharedinstance;
	}

	public SkyGridLevel_Manager getLevelManager() {

		return this.levelmanager;
	}

	public static void registerEvent(Listener event) {

		SkyGrid.sharedinstance.getServer().getPluginManager().registerEvents(event, SkyGrid.sharedinstance);
	}

	private void registerEventListenerAndCommands() {

		// register eventlisteners
		BarrysLogger.info("Register events and commands");
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerBedEnterEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerDeathEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerJoin(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerMoveEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerRespawnEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPortalCreateEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnInventoryClickEvent(), this);

		// register commands
		this.getCommand("home").setExecutor(new SkyGridHomeCommand());
		this.getCommand("sethome").setExecutor(new SkyGridSetHomeCommand());
		this.getCommand("deletehome").setExecutor(new SkyGridDeleteHomeCommand());
		this.getCommand("listhomes").setExecutor(new SkyGridListHomesCommand());
		this.getCommand("invitehome").setExecutor(new SkyGridInviteHomeCommand());
		this.getCommand("afk").setExecutor(new SkyGridAfkCommand());
		this.getCommand("achievements").setExecutor(new SkyGridAchievementCommand());
		this.getCommand("debug").setExecutor(new SkyGridDebugCommand());
	}

}
