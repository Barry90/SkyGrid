package me.barry1990.skygrid.commands;

import me.barry1990.skygrid.sql.SkyGridSQL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SkyGridListHomesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			final Player p = (Player) sender;
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
		return true;
	}

}
