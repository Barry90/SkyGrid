package me.barry1990.skygrid.achievement;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

final class SkyGridOnAsyncPlayerChatEvent implements Listener {
	
	private static final String f1 = "§6|";
	private static final String f2 = "%1$s§6|§f %2$s";
	private static final String c1 = "§f";	//begin
	private static final String c2 = "§a";	//3 achievements
	private static final String c3 = "§6";	//nether
	private static final String c4 = "§c";  //end
	private static final String c5 = "§5";  //all achievements
	private static final String c6 = "§4";  //op

	
	@EventHandler
	public void SkyGridonAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		SkyGridPlayerManager.setPlayerIsBack(player.getUniqueId(), player.getLocation());
		
		if (player.isOp()) {
			e.setFormat(f1 + c6 + f2);
			
		} else if (SkyGridPlayerManager.getAchievementCountForPlayer(player) == 16) {
			e.setFormat(f1 + c5 + f2);
	
		} else if (SkyGridPlayerManager.playerHasAchievementWithID(e.getPlayer(), SGAIDENTIFIER.GO_EVEN_DEEPER)) {
			e.setFormat(f1 + c4 + f2);
			
		} else if (SkyGridPlayerManager.playerHasAchievementWithID(e.getPlayer(), SGAIDENTIFIER.GO_DEEPER)) {
			e.setFormat(f1 + c3 + f2);
			
		} else if (SkyGridPlayerManager.getAchievementCountForPlayer(player) > 2) {
			e.setFormat(f1 + c2 + f2);
			
		} else 
			e.setFormat(f1 + c1 + f2);
		
	}
}
