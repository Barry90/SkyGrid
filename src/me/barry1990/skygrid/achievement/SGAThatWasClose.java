package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;


final class SGAThatWasClose extends IAchievementNP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "That was close";

	public SGAThatWasClose(HashMap<SGAIDENTIFIER, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}

	@Override
	protected SGAIDENTIFIER getId() {
		return SGAIDENTIFIER.THAT_WAS_CLOSE;
	}

	@Override
	protected String getName() {
		return SGAThatWasClose.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.ELYTRA, 1);
	}

	private static class SGAListener implements Listener {
		
		@EventHandler
		public void SkyGridonEntityDamageEvent(EntityDamageEvent e) {
			switch (e.getCause()) {
				case FALL: {
					if (e.getEntityType() == EntityType.PLAYER) {
						
						if  (e.getDamage() > 16) {						
							double finalhealth = ((Player)e.getEntity()).getHealth() - e.getFinalDamage();
							if (finalhealth > 0 && finalhealth <= 2 ) 
								SkyGridPlayerManager.awardAchievement((Player)e.getEntity(), SGAIDENTIFIER.THAT_WAS_CLOSE);					
						}
					}
					break;
				}
				default:
					return;
			}
		}
		
	}
}
