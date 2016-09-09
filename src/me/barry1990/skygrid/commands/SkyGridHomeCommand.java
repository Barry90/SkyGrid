package me.barry1990.skygrid.commands;

import me.barry1990.skygrid.sql.SkyGridSQL;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * SkyGridHomeCommand - This class handles the home command
 * 
 * @author Barry1990
 */
public final class SkyGridHomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			final Player p = (Player) sender;
			switch (args.length) {
			// no argument - teleport to default home "home"
				case 0: {
					Location loc = SkyGridSQL.sharedInstance().getHome(p, "home");
					if (loc != null) {
						p.teleport(loc);
					}
					return true;
				}
				// one argument - teleport to home
				case 1: {
					Location loc = SkyGridSQL.sharedInstance().getHome(p, args[0]);
					if (loc != null) {
						p.teleport(loc);
					}
					return true;
				}
				// two arguments - teleport to other players home
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
		return true;
	}

}
