package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


final class SGAGetRichOrDieTryin extends IAchievementNP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Get Rich Or Die Tryin";

	public SGAGetRichOrDieTryin(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.GET_RICH_OR_DIE_TRYIN;
	}

	@Override
	protected String getName() {
		return SGAGetRichOrDieTryin.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {
		return new ItemStack(Material.DIAMOND, 1);
	}
	
	private static class SGAListener implements Listener {
		
		@EventHandler
		public void SkyGridonBlockBreakEvent(BlockBreakEvent e) {
			if (e.getBlock().getType() == Material.DIAMOND_ORE) {
				SkyGridPlayerManager.awardAchievement(e.getPlayer(), SGAIDENTIFIER.GET_RICH_OR_DIE_TRYIN);
			}
		}
	}
	
}
