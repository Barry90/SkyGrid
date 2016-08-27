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

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyGrid extends JavaPlugin {
	
	private static SkyGrid sharedinstance;
	private SkyGridLevel_Manager levelmanager;
	private SkyGridChunkGeneratorWaitingRoom waitingRoomGenerator;
		
	@Override
	public void onEnable() {
		
		SkyGrid.sharedinstance = this;
		this.levelmanager = new SkyGridLevel_Manager();
		this.waitingRoomGenerator = new SkyGridChunkGeneratorWaitingRoom();

		SkyGridSQL.sharedInstance();		
			
		//register eventlisteners
		this.getServer().getPluginManager().registerEvents(new SkyGridOnWorldLoadedEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerBedEnterEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerDeathEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerJoin(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerMoveEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerRespawnEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPortalCreateEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnInventoryClickEvent(), this);
		
		//register commands
		this.getCommand("home").setExecutor(new SkyGridHomeCommand());
		this.getCommand("sethome").setExecutor(new SkyGridSetHomeCommand());
		this.getCommand("deletehome").setExecutor(new SkyGridDeleteHomeCommand());
		this.getCommand("listhomes").setExecutor(new SkyGridListHomesCommand());
		this.getCommand("invitehome").setExecutor(new SkyGridInviteHomeCommand());
		this.getCommand("afk").setExecutor(new SkyGridAfkCommand());
		this.getCommand("achievements").setExecutor(new SkyGridAchievementCommand());
		this.getCommand("debug").setExecutor(new SkyGridDebugCommand());
				
		//to fix reload bug
		
		for (Player p : this.getServer().getOnlinePlayers()) {
			SkyGridSQL.sharedInstance().addPlayer(p);
			SkyGridPlayerManager.load(p);
			SkyGridPlayerManager.loadAfterPlayerJoin(p);
		}		
		
		this.getLogger().info("v" + this.getDescription().getVersion() + " enabled.");		
	}
	
	@Override
	public void onDisable() {
		
		SkyGridSQL.sharedInstance().close();
		this.getLogger().info("v" + this.getDescription().getVersion() + " disabled.");
		
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return this.waitingRoomGenerator;
	}
			
	public static SkyGrid sharedInstance() {
		return SkyGrid.sharedinstance;
	}
	
	public static SkyGridLevel_Manager getLevelManager() {
		return SkyGrid.sharedinstance.levelmanager;
	}
	
	public static void registerEvent(Listener event) {
		SkyGrid.sharedinstance.getServer().getPluginManager().registerEvents(event, SkyGrid.sharedinstance);
	}
		
}
