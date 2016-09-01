package me.barry1990.skygrid.level;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

import me.barry1990.skygrid.SkyGrid;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


abstract class ISkyGridAlter implements Listener {
	
	
	ISkyGridAlter() {
		SkyGrid.registerEvent(this);
	}
	
	// Chunk	
	abstract public ChunkData getChunkData(World world, ChunkData chunkdata);		
	// Data
	abstract void loadAltar();	
	
	abstract boolean isBlockallowed(Block block);
	abstract boolean canBuildonLocation(Location location);
	abstract void buildOnLocationEvent(Location location);	
	abstract protected boolean isAltarComplete(Chunk chunk);
	abstract protected void doEndAnimation(Chunk chunk);		
	abstract void preprareNextAltar();
		
	public void alterChunkBlockPlaceEvent(BlockPlaceEvent e) {
		if (!this.isBlockallowed(e.getBlock())) {
			e.setCancelled(true);
			return;
		}

		final Location loc = e.getBlock().getLocation();
		loc.setX(loc.getBlockX() % 16);
		loc.setZ(loc.getBlockZ() % 16);
		
		if (!this.canBuildonLocation(loc)) {
			e.setCancelled(true);
			return;
		}
		
		this.buildOnLocationEvent(e.getBlock().getLocation());
		
		if (this.isAltarComplete(e.getBlock().getChunk())) {
			this.doEndAnimation(e.getBlock().getChunk());
		}
	}
	
	protected JsonElement getJsonFromResource(final String res) {
		JsonElement ret = null;
		try {
			InputStream resource_is = SkyGrid.sharedInstance().getResource(res);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	
			int readed;
			byte[] buffer = new byte[1024];	
			while ((readed = resource_is.read(buffer, 0, 1024)) != -1) 
				bos.write(buffer, 0, readed);			
			bos.flush();	
			String json = bos.toString(StandardCharsets.UTF_8.name());		
			JsonParser parser = new JsonParser();
			ret = parser.parse(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	@EventHandler
	public void skyGridAlterBlockExplodeEvent(EntityExplodeEvent e) {
		
		if (e.getEntityType() == EntityType.ENDER_CRYSTAL && SkyGrid.getLevelManager().isAltarChunk(e.getEntity().getLocation().getChunk())) {
			e.setCancelled(true);
			return;
		}
				
		e.blockList().removeIf(new Predicate<Block>() {
			
			@Override
			public boolean test(Block b) {
				return SkyGrid.getLevelManager().isAltarChunk(b.getChunk());
			}
		});
		
	}

}
