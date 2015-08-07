package me.barry1990.skygrid.eventlistener;

import me.barry1990.skygrid.achievement.SGAchievement;
import me.barry1990.skygrid.achievement.SkyGridAchievementManager;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;


public class SkyGridOnCraftItem implements Listener {

	@EventHandler
	public void OnCraftItem(CraftItemEvent e) {		
		switch (e.getRecipe().getResult().getType()) {
			case WATER_BUCKET: {
				e.getInventory().setMatrix(new ItemStack[e.getInventory().getMatrix().length]);
				if (e.getWhoClicked() instanceof Player) {
					SkyGridAchievementManager.addAchievementForPlayer((Player)e.getWhoClicked(), SGAchievement.INFINITE_WATER_SOURCE);
				}
				BarrysLogger.info(this, "cleared CraftInventoryMatrix");
				break;
			}
			case WOOD_SWORD:
			case WOOD_AXE:
			case WOOD_HOE:
			case WOOD_SPADE:
			case WOOD_PICKAXE: {
				if (e.getWhoClicked() instanceof Player) {
					SkyGridAchievementManager.addMaterialForWoodManiac((Player)e.getWhoClicked(), e.getRecipe().getResult().getType());
				}
				break;
			}
			
			default: 
				break;
		}
				
	}
}
