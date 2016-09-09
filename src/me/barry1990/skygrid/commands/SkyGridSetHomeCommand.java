package me.barry1990.skygrid.commands;

import me.barry1990.skygrid.sql.SkyGridSQL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * SkyGridSetHomeCommand - This class handles the sethome command
 * 
 * @author Barry1990
 */
public final class SkyGridSetHomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			final Player p = (Player) sender;
			switch (args.length) {
			// no argument - set default home "home"
				case 0: {
					SkyGridSQL.sharedInstance().addHome(p, p.getLocation(), "home");
					return true;
				}
				// one argument - set home
				case 1: {
					SkyGridSQL.sharedInstance().addHome(p, p.getLocation(), args[0]);
					return true;
				}
				default:
					return false;
			}
		}

		return true;
	}

}
