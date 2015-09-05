package me.barry1990.skygrid.eventlistener;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;


public final class SkyGridOnBlockDamageEvent implements Listener {

	@EventHandler
	public void SkyGridonBlockDamageEvent(BlockDamageEvent e) {
		if (e.getBlock().getType() == Material.ENDER_PORTAL_FRAME && e.getPlayer().getItemInHand().getEnchantments().keySet().contains(Enchantment.SILK_TOUCH)) {
			e.getBlock().breakNaturally();
			e.getPlayer().getWorld().dropItem(e.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.ENDER_PORTAL_FRAME, 1));
		}
		
	}
	
}
