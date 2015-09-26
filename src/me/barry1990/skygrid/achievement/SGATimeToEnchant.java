package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;


final class SGATimeToEnchant extends IAchievementNP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Time To Enchant";

	public SGATimeToEnchant(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.TIME_TO_ENCHANT;
	}

	@Override
	protected String getName() {
		return SGATimeToEnchant.name;
	}
	
	private static class SGAListener implements Listener {
		
		@EventHandler
		public void SkyGridonEnchantItemEvent(EnchantItemEvent e) {
			if (!(e.getEnchantsToAdd().values().contains(4) || e.getEnchantsToAdd().values().contains(5)))
				return;		
			SkyGridPlayerManager.awardAchievement(e.getEnchanter(), SGAIDENTIFIER.TIME_TO_ENCHANT);
		}
		
	}

}
