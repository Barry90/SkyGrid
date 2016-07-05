package me.barry1990.skygrid.level;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import me.barry1990.skygrid.SkyGrid;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


abstract class ISkyGridAlter {
	
	// Chunk	
	abstract public ChunkData getChunkData(World world, ChunkData chunkdata);	
	
	// Data
	abstract protected void loadAltarChunkData();	
	public void loadAltar() {
		this.loadAltarChunkData();
	}
	
	abstract boolean isMaterialAllowed(Material material);
	abstract boolean canBuildonLocation(Location location);
	abstract void buildOnLocationEvent(Location location);
	public void alterChunkBlockPlaceEvent(BlockPlaceEvent e) {
		if (!this.isMaterialAllowed(e.getBlock().getType())) {
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
		this.testIfAlterIsComplete(e.getBlock().getChunk());
	}
	
	
	// Cinematic
	abstract protected boolean isAltarComplete(Chunk chunk);
	abstract protected void doEndAnimation(Chunk chunk);	
	void testIfAlterIsComplete(Chunk chunk) {
		if (this.isAltarComplete(chunk)) {
			this.doEndAnimation(chunk);
		}
	}	
	
	//abstract void cleanup();
	
	abstract void preprareNextAltar();
	
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

}
