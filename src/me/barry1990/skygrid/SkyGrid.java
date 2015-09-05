package me.barry1990.skygrid;

//import eventlisteners
import me.barry1990.skygrid.eventlistener.SkyGridOnBlockBreakEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnBlockDamageEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnBlockFromTo;
import me.barry1990.skygrid.eventlistener.SkyGridOnCraftItem;
import me.barry1990.skygrid.eventlistener.SkyGridOnCreatureSpawnEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnEntityDeathEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerBedEnterEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerDeathEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerEggThrowEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerJoin;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerPickupItemEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerRespawnEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPortalCreateEvent;

//import the generator
import me.barry1990.skygrid.generators.SkyGridGenerator;
import me.barry1990.skygrid.sql.SkyGridSQL;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyGrid extends JavaPlugin {
		
	@Override
	public void onEnable() {
		
		SkyGridSQL.sharedInstance();
		
		//prepare the generator
		SkyGridGenerator.sharedInstance();
			
		//register eventlisteners
		this.getServer().getPluginManager().registerEvents(new SkyGridOnBlockFromTo(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerJoin(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerEggThrowEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnCreatureSpawnEvent(), this);		
		this.getServer().getPluginManager().registerEvents(new SkyGridOnCraftItem(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPortalCreateEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnEntityDeathEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerDeathEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerPickupItemEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerRespawnEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnPlayerBedEnterEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnBlockBreakEvent(), this);
		this.getServer().getPluginManager().registerEvents(new SkyGridOnBlockDamageEvent(), this);
		
		
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
	
	/* test commands */
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
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
			
		}
		return true;
					
	}

}
