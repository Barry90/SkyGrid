package me.barry1990.skygrid.commands;

import me.barry1990.skygrid.SkyGrid;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * SkyGridAfkCommand - This class handles the afk command
 * 
 * @author Barry1990
 */
public class SkyGridAfkCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			final Player p = (Player) sender;
			switch (args.length) {
			// no argument - toggle afk
				case 0: {
					SkyGrid.getPlayerManager().toggleAFK(p, p.getLocation());
					return true;
				}
				default:
					return false;
			}
		}
		return true;
	}

}
