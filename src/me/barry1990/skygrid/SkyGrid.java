package me.barry1990.skygrid;

//import eventlisteners
import me.barry1990.skygrid.eventlistener.SkyGridOnBlockFromTo;
import me.barry1990.skygrid.eventlistener.SkyGridOnCraftItem;
import me.barry1990.skygrid.eventlistener.SkyGridOnCreatureSpawnEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnEntityDeathEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerDeathEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerEggThrowEvent;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerJoin;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerPickupItemEvent;
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

public class SkyGrid extends JavaPlugin {
		
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
	
	private TestThread tt;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(command.getName().equalsIgnoreCase("s")) {
				tt = new TestThread(p,true,5000);
				tt.start();
				p.sendMessage("Thread gestartet");
			}
			if(command.getName().equalsIgnoreCase("t")) {
				if (tt != null)
					tt.softstop();
				p.sendMessage("Thread gestoppt");
			}
			if(command.getName().equalsIgnoreCase("home")) {
				
				switch (args.length) {
					//no argument - show help
					case 0 : break;
					
					//one argument - teleport to home
					case 1 : {
						Location loc = SkyGridSQL.sharedInstance().getHome(p, args[0]);
						if (loc != null) {
							p.teleport(loc);
						}
						break;
					}
					
					//two arguments - teleport to other players home
					case 2: {
						break;
					}
				}
			}
			if(command.getName().equalsIgnoreCase("sethome")) {
				if (args.length == 1)
					SkyGridSQL.sharedInstance().addHome(p, p.getLocation(), args[0]);
			}
		}
		return true;
					
	}

}
