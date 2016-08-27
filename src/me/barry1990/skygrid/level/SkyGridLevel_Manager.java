package me.barry1990.skygrid.level;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.generators.SkyGridChunkGenerator;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public final class SkyGridLevel_Manager implements Listener {
	
	private int x;
	private int z;
	private int id;
	
	private static SkyGridLevel_Manager sharedinstance; 
	private SkyGridChunkGenerator chunkGenerator; 
	private ISkyGridLevel level;
	private String PATH = "data.dat";
	
	private SkyGridLevel_Manager() {
		SkyGridLevel_Manager.sharedinstance = this;
		SkyGrid.registerEvent(this);
		this.loadLevelInfos();
		this.loadLevel(this.id);
	}
	
	public static SkyGridLevel_Manager sharedInstance() {
		return (sharedinstance != null) ? sharedinstance : (sharedinstance = new SkyGridLevel_Manager());
	}
	// ////////////////////////////////////////////////////
	// LOAD INFOS
	// ////////////////////////////////////////////////////
	
	private void loadLevelInfos() {
		
		//TODO maybe read from SQL DB?
		File file = new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH);
		if (!file.exists()) {
			this.createDefault();
			return;
		}		
		
		try (FileReader freader = new FileReader(file)) {
			
			JsonParser parser = new JsonParser();
			JsonObject e = (JsonObject)parser.parse(freader);
			
			this.id = e.get("id").getAsInt();
			this.x = e.get("chunk_x").getAsInt();
			this.z = e.get("chunk_z").getAsInt();
			
			BarrysLogger.info(this, "level infos loaded");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	
	public void createDefault() {
		
		File file = new File(SkyGrid.sharedInstance().getDataFolder() + File.separator + PATH);
		
		Random r = new Random();
		this.id = 1;
		this.x = r.nextInt(5000) - 2500;
		this.z = r.nextInt(5000) - 2500;
		//TODO
		this.x = 0;
		this.z = 0;
		
		String json = String.format("{\"id\":%d,\"chunk_x\":%d,\"chunk_z\":%d}", this.id, this.x ,this.z);		
		file.getParentFile().mkdirs();
		
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(json.getBytes(StandardCharsets.UTF_8), 0, json.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BarrysLogger.info(this, "altar infos created");
	}
	
	// ////////////////////////////////////////////////////
	// SKYGRID LEVEL
	// ////////////////////////////////////////////////////
	
	private void loadLevel(int id) {
		
		try {
			this.level = SkyGridLevel.getClassFromID(id).newInstance();
			BarrysLogger.info(this, "level class created" + this.level.getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public ISkyGridLevel getLevel() {
		return this.level;
	}
	
	public void reload() {
		BarrysLogger.info(this, "Reloading...");
		this.chunkGenerator.dispose();
		
		this.loadLevelInfos();
		this.loadLevel(this.id);
		this.chunkGenerator = new SkyGridChunkGenerator();
		
		
	}
	
	// ////////////////////////////////////////////////////
	// CHUNK GENERATION
	// ////////////////////////////////////////////////////
	
	public SkyGridChunkGenerator getGenerator() {
		return (this.chunkGenerator != null) ? this.chunkGenerator : (this.chunkGenerator = new SkyGridChunkGenerator());
	}
	
	public ChunkData getAltarChunkData(World world, ChunkData chunkdata) {
		if (this.level.getSkyGridAltar() != null)
			return this.level.getSkyGridAltar().getChunkData(world, chunkdata);
		else return chunkdata;
	}
	
	public boolean isAltarChunk(int x, int z) {
		return ((this.x == x) && (this.z == z)); 
	}
	
	public boolean isAltarChunk(Chunk chunk) {
		return ((this.x == chunk.getX()) && (this.z == chunk.getZ())); 
	}
	
	// ////////////////////////////////////////////////////
	// EVENTHANDLER
	// ////////////////////////////////////////////////////

	@EventHandler
	private void skyGridAlterBlockBreakEvent(BlockBreakEvent e) {
		
		if (!this.isAltarChunk(e.getBlock().getChunk().getX(), e.getBlock().getChunk().getZ()))
			return;

		e.setCancelled(true);

	}

	@EventHandler
	private void skyGridAlterBlockPlaceEvent(BlockPlaceEvent e) {

		if (!this.isAltarChunk(e.getBlock().getChunk().getX(), e.getBlock().getChunk().getZ()))
			return;
	
		if (this.level.getSkyGridAltar() != null)
			this.level.getSkyGridAltar().alterChunkBlockPlaceEvent(e);
		 
	}


}
