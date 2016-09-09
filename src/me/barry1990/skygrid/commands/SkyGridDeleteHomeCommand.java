package me.barry1990.skygrid.commands;

import me.barry1990.skygrid.sql.SkyGridSQL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * SkyGridDeleteHomeCommand - This class handles the deletehome command
 * 
 * @author Barry1990
 */
public class SkyGridDeleteHomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			final Player p = (Player) sender;
			switch (args.length) {
			// no argument - delete default home "home"
				case 0: {
					SkyGridSQL.sharedInstance().deleteHome(p, "home");
					return true;
				}
				// one argument - delete home
				case 1: {
					SkyGridSQL.sharedInstance().deleteHome(p, args[0]);
					return true;
				}
				default:
					return false;
			}
		}
		return true;
	}

}
