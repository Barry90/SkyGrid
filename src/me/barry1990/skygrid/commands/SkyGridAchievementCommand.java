package me.barry1990.skygrid.commands;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class SkyGridAchievementCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			final Player p = (Player) sender;
			switch (args.length) {
				//no argument 
				case 0 : {
					Inventory inv = SkyGridPlayerManager.createAchievementGUIForPlayer(p);
					p.openInventory(inv);
					return true;
				}
				default:
					return false;
			}
		}
		return true;
	}

}
