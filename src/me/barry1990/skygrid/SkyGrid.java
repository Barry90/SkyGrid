package me.barry1990.skygrid;

//import eventlisteners
import me.barry1990.skygrid.achievement.SkyGridAchievements;
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

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyGrid extends JavaPlugin {
	
	private static SkyGrid sharedinstance;
	private static SkyGridLevel_Manager levelmanager;
	private static SkyGridChunkGeneratorWaitingRoom waitingroom;
	//private boolean doReset = false;
		
	@Override
	public void onEnable() {
		
		SkyGrid.sharedinstance = this;
		SkyGrid.levelmanager = SkyGridLevel_Manager.sharedInstance();
		SkyGrid.waitingroom = new SkyGridChunkGeneratorWaitingRoom();

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
		//return SkyGrid.levelmanager.getGenerator();
		return SkyGrid.waitingroom;
	}
		
	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (sender instanceof Player) {
			final Player p = (Player) sender;
			if(command.getName().equalsIgnoreCase("home")) {
				
				switch (args.length) {
					//no argument - teleport to default home "home"
					case 0 : {
						Location loc = SkyGridSQL.sharedInstance().getHome(p, "home");
						if (loc != null) {
							p.teleport(loc);
						}
						return true;
					}					
					//one argument - teleport to home
					case 1 : {
						Location loc = SkyGridSQL.sharedInstance().getHome(p, args[0]);
						if (loc != null) {
							p.teleport(loc);
						}
						return true;
					}
					
					//two arguments - teleport to other players home
					case 2: {
						Location loc = SkyGridSQL.sharedInstance().getInvitedHome(p, args[0], args[1]);
						if (loc != null) {
							p.teleport(loc);
						}
						return true;
					}
					default:
						return false;
				}
			}
			if(command.getName().equalsIgnoreCase("sethome")) {
				switch (args.length) {
					//no argument - set default home "home"
					case 0 : {
						SkyGridSQL.sharedInstance().addHome(p, p.getLocation(), "home");
						return true;
					}
					//one argument - set home
					case 1 : {
						SkyGridSQL.sharedInstance().addHome(p, p.getLocation(), args[0]);
						return true;
					}
					default:
						return false;
				}
			}
			if(command.getName().equalsIgnoreCase("deletehome")) {
				switch (args.length) {
					//no argument - delete default home "home"
					case 0 : {
						SkyGridSQL.sharedInstance().deleteHome(p, "home");
						return true;
					}
					//one argument - delete home
					case 1 : {
						SkyGridSQL.sharedInstance().deleteHome(p, args[0]);
						return true;
					}
					default:
						return false;
				}
			}
			if(command.getName().equalsIgnoreCase("listhomes")) {
				switch (args.length) {
					//no argument - show all homes
					case 0 : {
						SkyGridSQL.sharedInstance().getHomesList(p);
						return true;
					}
					default:
						return false;
				}
			}
			if(command.getName().equalsIgnoreCase("invitehome")) {
				switch (args.length) {
					//no argument - show all homes
					case 2 : {
						SkyGridSQL.sharedInstance().addInvite(p, args[0], args[1]);
						return true;
					}
					default:
						return false;
				}
			}
			if(command.getName().equalsIgnoreCase("afk")) {
				switch (args.length) {
					//no argument - show all homes
					case 0 : {
						SkyGridPlayerManager.toggleAFK(p,p.getLocation());
						return true;
					}
					default:
						return false;
				}
			}
			
			if(command.getName().equalsIgnoreCase("achievements")) {		
				switch (args.length) {
					//no argument - show all homes
					case 0 : {
						Inventory inv = SkyGridPlayerManager.createAchievementGUIForPlayer(p);
						p.openInventory(inv);
						return true;
					}
					default:
						return false;
				}
			}
			if(command.getName().equalsIgnoreCase("debug")) {	
				// debug command 
				for (Player player : this.getServer().getOnlinePlayers()) {
					player.teleport(new Location(this.getServer().getWorld("world"), 7, 129, 7));
					SkyGridPlayerManager.unload(p);
				}
				
				SkyGridSQL.sharedInstance().resetDatabaseTables();
				SkyGridAchievements.deleteAllProgress();				
				SkyGrid.getLevelManager().reload();
				
				new SkyGridWorld() {
							
					@Override
					protected void worldLoaded(World world) {
					
						for (Player player : SkyGrid.this.getServer().getOnlinePlayers()) {
							SkyGridOnPlayerJoin.registerAndLoadPlayer(player);
							player.teleport(SkyGrid.getLevelManager().getLevel().generateSkyGridSpawnLocation());
							SkyGridOnPlayerJoin.loadAfterPlayerJoin(player);
						}
						
					}
				}.recreate();
						
				
			}
			
		}
		return true;
					
	}
	
	public static SkyGrid sharedInstance() {
		return SkyGrid.sharedinstance;
	}
	public static SkyGridLevel_Manager getLevelManager() {
		return SkyGrid.levelmanager;
	}
	
	public static void registerEvent(Listener event) {
		SkyGrid.sharedinstance.getServer().getPluginManager().registerEvents(event, SkyGrid.sharedinstance);
	}
		
}
