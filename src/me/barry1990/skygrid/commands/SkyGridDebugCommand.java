package me.barry1990.skygrid.commands;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.achievement.SkyGridAchievements;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerJoin;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;
import me.barry1990.skygrid.sql.SkyGridSQL;
import me.barry1990.skygrid.world.SkyGridWorld;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SkyGridDebugCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			final Player p = (Player) sender;
			// debug command 
			for (Player player : SkyGrid.sharedInstance().getServer().getOnlinePlayers()) {
				player.teleport(new Location(SkyGrid.sharedInstance().getServer().getWorld("world"), 7, 129, 7));
				SkyGridPlayerManager.unload(p);
			}
			
			SkyGridSQL.sharedInstance().resetDatabaseTables();
			SkyGridAchievements.deleteAllProgress();				
			SkyGrid.getLevelManager().reload();
			
			new SkyGridWorld() {
						
				@Override
				protected void worldLoaded(World world) {
				
					for (Player player : SkyGrid.sharedInstance().getServer().getOnlinePlayers()) {
						SkyGridOnPlayerJoin.registerAndLoadPlayer(player);
						player.teleport(SkyGrid.getLevelManager().getLevel().generateSkyGridSpawnLocation());
						SkyGridOnPlayerJoin.loadAfterPlayerJoin(player);
					}
					
				}
			}.recreate();
		}
		return true;
	}

}
