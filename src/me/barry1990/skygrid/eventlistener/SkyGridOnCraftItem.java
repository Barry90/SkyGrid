package me.barry1990.skygrid.eventlistener;

//import me.barry1990.skygrid.achievement.SGAchievement;
import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.skygridplayer.SkyGridPlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;


public final class SkyGridOnCraftItem implements Listener {

	@EventHandler
	public void onCraftItem(CraftItemEvent e) {		
		switch (e.getRecipe().getResult().getType()) {
		
			/////////////////////////////////////////////
			//	ACHIEVEMENT: INFINITE_WATER_SOURCE
			/////////////////////////////////////////////
		
			case WATER_BUCKET: {
				e.getInventory().setMatrix(new ItemStack[e.getInventory().getMatrix().length]);
				if (e.getWhoClicked() instanceof Player) {
					SkyGridPlayerManager.awardAchievement((Player)e.getWhoClicked(), SGAIDENTIFIER.INFINITE_WATER_SOURCE);
				}
				break;
			}
			
			/////////////////////////////////////////////
			//	ACHIEVEMENT: HOT BUCKET
			/////////////////////////////////////////////
			
			case LAVA_BUCKET: {
				if (e.getWhoClicked() instanceof Player) {
					SkyGridPlayerManager.awardAchievement((Player)e.getWhoClicked(), SGAIDENTIFIER.HOT_BUCKET);
				}
				break;
			}
			
			/////////////////////////////////////////////
			//	ACHIEVEMENT: WOOD MANIAC
			/////////////////////////////////////////////
			
			case WOOD_SWORD:
			case WOOD_AXE:
			case WOOD_HOE:
			case WOOD_SPADE:
			case WOOD_PICKAXE: {
				if (e.getWhoClicked() instanceof Player) {
					SkyGridPlayerManager.addMaterialForWoodManiac((Player)e.getWhoClicked(), e.getRecipe().getResult().getType());
				}
				break;
			}
			
			/////////////////////////////////////////////
			//	ACHIEVEMENT: STONE MANIAC
			/////////////////////////////////////////////
			
			case STONE_SWORD:
			case STONE_AXE:
			case STONE_HOE:
			case STONE_SPADE:
			case STONE_PICKAXE: {
				if (e.getWhoClicked() instanceof Player) {
					SkyGridPlayerManager.addMaterialForStoneManiac((Player)e.getWhoClicked(), e.getRecipe().getResult().getType());
				}
				break;
			}
			
			/////////////////////////////////////////////
			//	ACHIEVEMENT: IRON MANIAC
			/////////////////////////////////////////////
			
			case IRON_SWORD:
			case IRON_AXE:
			case IRON_HOE:
			case IRON_SPADE:
			case IRON_PICKAXE: 
			case IRON_HELMET:
			case IRON_CHESTPLATE:
			case IRON_LEGGINGS:
			case IRON_BOOTS: {
				if (e.getWhoClicked() instanceof Player) {
					SkyGridPlayerManager.addMaterialForIronManiac((Player)e.getWhoClicked(), e.getRecipe().getResult().getType());
				}
				break;
			}
			
			/////////////////////////////////////////////
			//	ACHIEVEMENT: GOLD MANIAC
			/////////////////////////////////////////////
			
			case GOLD_SWORD:
			case GOLD_AXE:
			case GOLD_HOE:
			case GOLD_SPADE:
			case GOLD_PICKAXE: 
			case GOLD_HELMET:
			case GOLD_CHESTPLATE:
			case GOLD_LEGGINGS:
			case GOLD_BOOTS: {
				if (e.getWhoClicked() instanceof Player) {
					SkyGridPlayerManager.addMaterialForGoldManiac((Player)e.getWhoClicked(), e.getRecipe().getResult().getType());
				}
				break;
			}
			
			/////////////////////////////////////////////
			//	ACHIEVEMENT: IRON MANIAC
			/////////////////////////////////////////////
			
			case DIAMOND_SWORD:
			case DIAMOND_AXE:
			case DIAMOND_HOE:
			case DIAMOND_SPADE:
			case DIAMOND_PICKAXE: 
			case DIAMOND_HELMET:
			case DIAMOND_CHESTPLATE:
			case DIAMOND_LEGGINGS:
			case DIAMOND_BOOTS: {
				if (e.getWhoClicked() instanceof Player) {
					SkyGridPlayerManager.addMaterialForDiamondManiac((Player)e.getWhoClicked(), e.getRecipe().getResult().getType());
				}
				break;
			}
			
			default: 
				break;
		}
				
	}
}
