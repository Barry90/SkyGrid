package me.barry1990.skygrid.achievement;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;


final class SGAOhShit extends IAchievementNP {
	
	static {
		IAchievement.registerEvent(new SGAListener());
	}
	
	private static final String name = "Oh Sh**!";

	public SGAOhShit(HashMap<Byte, IAchievement> map, UUID playeruuid) {
		super(map, playeruuid);
	}
	@Override
	protected Byte getId() {
		return SGAIDENTIFIER.OH_SHIT;
	}
	@Override
	protected String getName() {
		return SGAOhShit.name;
	}
	
	@Override
	protected ItemStack getAchievementItem() {	
		return new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
	}
	
	private static class SGAListener implements Listener {
		
		private Random random;		
		
		public SGAListener() {
			this.random = new Random();
		}
		
		@EventHandler
		public void onPlayerEggThrowEvent(PlayerEggThrowEvent e) {			
			
			if (this.random.nextInt(250) == 0) {
				//0.4%
				e.setHatchingType(EntityType.CREEPER);
				e.setNumHatches(this.random.nextInt(2) == 0 ? (byte)2 : (byte)3);
				SkyGridPlayerManager.awardAchievement(e.getPlayer(), SGAIDENTIFIER.OH_SHIT);
			}
			
		}
		
		@EventHandler
		public void onEntitySpawnEvent(CreatureSpawnEvent e) {
			
			if (e.getSpawnReason() == SpawnReason.EGG) {
				if (e.getEntityType() == EntityType.CREEPER) {
					Creeper c = (Creeper) e.getEntity();
					c.setPowered(true);
				}
			}
			
		}
	}

}
