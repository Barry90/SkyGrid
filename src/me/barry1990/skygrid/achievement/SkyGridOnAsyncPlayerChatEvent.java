package me.barry1990.skygrid.achievement;

import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * SkyGridOnAsyncPlayerChatEvent - This class handles the chat color depending of the awarded achievements
 * 
 * @author Barry1990
 */
final class SkyGridOnAsyncPlayerChatEvent implements Listener {

	private static final String	f1	= "§6|";
	private static final String	f2	= "%1$s§6|§f %2$s";
	private static final String	c1	= "§f";			// begin
	private static final String	c2	= "§a";			// 10% achievements
	private static final String	c3	= "§6";			// 50%
	private static final String	c4	= "§c";			// 75%
	private static final String	c5	= "§5";			// all achievements
	private static final String	c6	= "§4";			// op

	@EventHandler
	public void SkyGridonAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {

		Player player = e.getPlayer();
		UUID playerUUID = e.getPlayer().getUniqueId();
		SkyGrid.getPlayerManager().setPlayerIsBack(playerUUID, player.getLocation());

		int numberOfAchievments = SkyGrid.getPlayerManager().getNumberOfAchievements(playerUUID);

		if (player.isOp()) {
			e.setFormat(f1 + c6 + f2);

		} else if (SkyGrid.getPlayerManager().getAchievementCountForPlayer(playerUUID) == numberOfAchievments) {
			e.setFormat(f1 + c5 + f2);

		} else if (SkyGrid.getPlayerManager().getAchievementCountForPlayer(playerUUID) > (numberOfAchievments * 0.75)) {
			e.setFormat(f1 + c4 + f2);

		} else if (SkyGrid.getPlayerManager().getAchievementCountForPlayer(playerUUID) > (numberOfAchievments * 0.5)) {
			e.setFormat(f1 + c3 + f2);

		} else if (SkyGrid.getPlayerManager().getAchievementCountForPlayer(playerUUID) > (numberOfAchievments * 0.1)) {
			e.setFormat(f1 + c2 + f2);

		} else
			e.setFormat(f1 + c1 + f2);

	}
}
