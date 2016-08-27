package me.barry1990.skygrid.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.TitleManager;
import me.barry1990.skygrid.PlayerThreads.EndWarningThread;
import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.PlayerThreads.NetherWarningThread;
import me.barry1990.skygrid.eventlistener.SkyGridOnPlayerJoin;
import me.barry1990.skygrid.world.SkyGridWorld;
import me.barry1990.utils.BarrysLogger;
import me.barry1990.utils.Converter;

import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragon.Phase;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sapling;
import org.bukkit.material.SmoothBrick;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class SkyGridLevelEnd extends ISkyGridLevel {
	
	private ISkyGridAlter altar;
	private BlockList blocklist;
	
	public SkyGridLevelEnd() {
		this.altar = new SkyGridEndAltar();
		this.blocklist = new BlockList();
	}

	@Override
	public ChunkData fillChunkData(ChunkData chunkdata) {
		
		for (int y = 1; y < chunkdata.getMaxHeight(); y=y+4) {
			
			if (y <= 45) {
				
				//////////////////////////////
				// generate End-layer
				//////////////////////////////
				
				for (int z = 1; z < 16; z=z+4) {
								
					for (int x = 1; x < 16; x=x+4) {					
						
						Material material = this.blocklist.getRandomMaterialForEnd();
						chunkdata.setBlock(x, y, z, material);
					}
					
				}
			} else if (y <= 117) {
				
				//////////////////////////////
				// generate Nether-layer
				//////////////////////////////
			
				for (int z = 1; z < 16; z=z+4) {
					
					for (int x = 1; x < 16; x=x+4) {
						
						Material material = this.blocklist.getRandomMaterialForNether();		
						
						chunkdata.setBlock(x, y, z, material);
						MaterialData materialdata = null;
						switch (material) {
							case JACK_O_LANTERN:
							case PUMPKIN : {
								materialdata = this.randomMetaData.getPumpkin();
								break;
							}						
							case ENDER_CHEST:
							case CHEST: {
								materialdata = this.randomMetaData.getChest();
								break;
							}				
							case SOUL_SAND: {
								if (this.random.nextInt(100) <= 2) {
									chunkdata.setBlock(x, y, z, Material.NETHER_WARTS);
								}
								break;
							}
							default:
								break;
						}
						
						if (materialdata != null) {
							chunkdata.setBlock(x, y, z, materialdata);
						}
						
					}  
					
				}
				
			} else {
				
				//////////////////////////////
				//generate Normal-layer
				//////////////////////////////
				
				for (int z = 1; z < 16; z=z+4) {
					
					for (int x = 1; x < 16; x=x+4) {
						
						Material material = this.blocklist.getRandomMaterial();
						
						chunkdata.setBlock(x, y, z, material);
						MaterialData materialdata = null;
						switch (material) {
							case WOOL : {				
								materialdata = this.randomMetaData.getWool();
								break;
							}
							case JACK_O_LANTERN:
							case PUMPKIN : {
								materialdata = this.randomMetaData.getPumpkin();
								break;
							}
							case LOG :
							case LOG_2 : {
								materialdata = this.randomMetaData.getTree(material);
								break;
							}						
							case FURNACE: {
								materialdata = this.randomMetaData.getFurnace();
								break;
							}
							case MONSTER_EGGS: {
								materialdata = this.randomMetaData.getMonsterEggs();
								break;
							}
							case ENDER_CHEST:
							case CHEST: {
								materialdata = this.randomMetaData.getChest();
								break;
							}				
							case SAND: {
								if (this.random.nextInt(100) <= 2) {
									chunkdata.setBlock(x, y+1, z, Material.SUGAR_CANE_BLOCK);
									switch (this.random.nextInt(4)) {
										case 0 : {chunkdata.setBlock(x+1, y, z, Material.STATIONARY_WATER); break;}
										case 1 : {chunkdata.setBlock(x-1, y, z, Material.STATIONARY_WATER); break;}
										case 2 : {chunkdata.setBlock(x, y, z+1, Material.STATIONARY_WATER); break;}
										case 3 : {chunkdata.setBlock(x, y, z-1, Material.STATIONARY_WATER); break;}
									}
								}
								break;
							}
							case MYCEL: {
								Material mushroom = this.random.nextBoolean() ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM;
								chunkdata.setBlock(x, y+1, z, mushroom);
								break;
							}
							case DIRT: {
								if (this.random.nextInt(100) <= 2) {
									chunkdata.setBlock(x, y+1, z, Material.SAPLING);
									chunkdata.setBlock(x, y+1, z, new Sapling(this.randomMetaData.getTreeSpecies(Material.SAPLING)));
								}
								break;
							}
							
							default:
								break;
						}
						if (materialdata != null) {
							chunkdata.setBlock(x, y, z, materialdata);
						}
					}
					
				}
			}
		}

		return chunkdata;
	}

	@Override
	public Location generateSkyGridSpawnLocation() {
		Random random = new Random();
		double x,y,z;
		x = (random.nextInt(1500)-750) * 4 + 1.5;
		z = (random.nextInt(1500)-750) * 4 + 1.5;
		y = random.nextInt(8) * 4 + 174;
		
		BarrysLogger.info(SkyGridOnPlayerJoin.class,"x",x);
		BarrysLogger.info(SkyGridOnPlayerJoin.class,"y",y);
		BarrysLogger.info(SkyGridOnPlayerJoin.class,"z",z);
		
		return new Location(SkyGridWorld.getSkyGridWorld(), x, y, z);
	};
	
	@Override
	public ISkyGridAlter getSkyGridAltar() {
		return this.altar;
	}
	
	@Override
	public IPlayerThreads getPlayerThreads(UUID playeruuid) {
		return new PlayerThreads(playeruuid);
	}
	
	@Override
	List<Recipe> registerRecipes() {
		
		BarrysLogger.info("start to create SkyGrid recipes");
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		/** CRAFTING - SHAPELESS */
		
		//Water-Bucket
		recipes.add(
			new ShapelessRecipe(new ItemStack(Material.WATER_BUCKET, 3))
			.addIngredient(2, Material.WATER_BUCKET)
			.addIngredient(1, Material.BUCKET)
		);
		//Lava-Bucket
		recipes.add(
			new ShapelessRecipe(new ItemStack(Material.LAVA_BUCKET, 1))
			.addIngredient(1, Material.OBSIDIAN)
			.addIngredient(1, Material.BLAZE_ROD)
			.addIngredient(1, Material.FLINT_AND_STEEL)
			.addIngredient(1, Material.BUCKET)
		);
		//Blaze Rod
		recipes.add(
				new ShapelessRecipe(new ItemStack(Material.BLAZE_ROD, 1))
				.addIngredient(2, Material.BLAZE_POWDER)
				.addIngredient(1, Material.STICK)
		);
		//Sand
		recipes.add(
				new ShapelessRecipe(new ItemStack(Material.SAND, 1))
				.addIngredient(1, new MaterialData(Material.SAND, (byte)1))
				.addIngredient(1, new Dye(DyeColor.WHITE))
		);
		//Red Sand
		recipes.add(
				new ShapelessRecipe(new ItemStack(Material.SAND, 1, (short)1))
				.addIngredient(1, new MaterialData(Material.SAND, (byte)0))
				.addIngredient(1, new Dye(DyeColor.RED))
		);
		//Cobble -> Granit
		recipes.add(
				new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short)1))
				.addIngredient(1, Material.COBBLESTONE)
		);
		//Granit -> Diorit
		recipes.add(
				new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short)3))
				.addIngredient(1, new MaterialData(Material.STONE, (byte)1))
		);
		//Diorit -> Andesite
		recipes.add(
				new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short)5))
				.addIngredient(1, new MaterialData(Material.STONE, (byte)3))
		);
		//Andesite -> Cobble
		recipes.add(
				new ShapelessRecipe(new ItemStack(Material.COBBLESTONE, 1))
				.addIngredient(1, new MaterialData(Material.STONE, (byte)5))
		);
		//Beetrodseeds
		recipes.add(
				new ShapelessRecipe(new ItemStack(Material.BEETROOT_SEEDS, 2))
				.addIngredient(1, Material.NETHER_WARTS)
		);
		
		/** CRAFTING - SHAPED */
		
		//Obsidian
		recipes.add(
			new ShapedRecipe(new ItemStack(Material.OBSIDIAN, 1))
			.shape(" S ","SBS"," S ")
			.setIngredient('S', Material.SMOOTH_BRICK).setIngredient('S', new SmoothBrick(Material.COBBLESTONE))
			.setIngredient('B', Material.BLAZE_POWDER)
		);
		//Cobweb
		recipes.add(
			new ShapedRecipe(new ItemStack(Material.WEB, 1))
			.shape("W W"," W ","W W")
			.setIngredient('W', Material.STRING)
		);
		//Netherportalframe
		recipes.add(
			new ShapedRecipe(new ItemStack(Material.ENDER_PORTAL_FRAME, 1))
			.shape("BIB","ECE","EEE")
			.setIngredient('B', Material.BLAZE_ROD)
			.setIngredient('I', Material.ENDER_PEARL)
			.setIngredient('E', Material.ENDER_STONE)
			.setIngredient('C', Material.CAULDRON_ITEM)
		);
		
		/** FURNACE */
		
		//Blaze Powder
		recipes.add(
			new FurnaceRecipe(new ItemStack(Material.BLAZE_POWDER, 1), new MaterialData(Material.ENDER_STONE), 0.1f)
		);
		
		return recipes;	
	}
	
	@Override
	public boolean isAchievementAvailable(byte sga) {
		return true;
	}
	
	private class SkyGridEndAltar extends ISkyGridAlter implements Listener {
		
		private ArrayList<Vector> blockSetAllowed;
		private Material allowedBlockMaterial;
		
		private ArrayList<Vector> crystalposition;
		private ArrayList<Vector> laserposition;
		private Vector dragonlocation;
		private Vector soundsource;
		
		private ArrayList<ISkyGridRunnableWithDelay> endAnimation;
		
		
		SkyGridEndAltar() {
			BarrysLogger.info("SkyGridAltarEnd Contructor called");
			this.blockSetAllowed = new ArrayList<Vector>();
			
			this.crystalposition = new ArrayList<Vector>();
			this.laserposition = new ArrayList<Vector>();
			this.endAnimation = new ArrayList<ISkyGridRunnableWithDelay>();
			
		}
			

		@SuppressWarnings("deprecation")
		@Override
		public ChunkData getChunkData(World world, ChunkData chunkdata) {
			BarrysLogger.info(this, "getChunkData called");

			try {			
				
				JsonObject all = (JsonObject)this.getJsonFromResource("SkyGridEndAltarData.data");
				
				for (int r = 0; r < 16; r++) {
					JsonObject reg = all.getAsJsonObject(String.format("%d", r));
					if (reg == null)
						continue;
					
					String _id = reg.get("id").getAsString();
					String _data = reg.get("data").getAsString();
		
					short[] id = Converter.hexToShort(_id);
					byte[] data = Converter.hexToHalfBytes(_data);
					int i = 0;
					for (int iy = 0; iy < 16; iy++) {
						for (int iz = 0; iz < 16; iz++) {
							for (int ix = 0; ix < 16; ix++, i++) {										
								chunkdata.setBlock(ix, r*16+iy, iz , id[i] , data[i]);
							}
						}
					}			
					
				}			

			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			BarrysLogger.info(this, "ChunkData created");
			
			return chunkdata;
		}

		@Override
		protected void loadAltarChunkData() {
			
			BarrysLogger.info(this, "loading data...");

			JsonObject all = (JsonObject)this.getJsonFromResource("test.data");
			
			// get positions where blockplace is allowed
			JsonArray blockPos = all.getAsJsonArray("blockPos");
			Iterator<JsonElement> blockPos_it =  blockPos.iterator();
			while (blockPos_it.hasNext()) {
				JsonObject loc = (JsonObject) blockPos_it.next();
				int x = loc.get("x").getAsInt();
				int y = loc.get("y").getAsInt();
				int z = loc.get("z").getAsInt();
				this.blockSetAllowed.add(new Vector(x, y, z));

			}
			
			// get spawning positions of endcrystals
			JsonArray crystals = all.getAsJsonArray("crystals");
			Iterator<JsonElement> crystals_it =  crystals.iterator();
			while (crystals_it.hasNext()) {
				JsonObject loc = (JsonObject) crystals_it.next();
				int x = loc.get("x").getAsInt();
				int y = loc.get("y").getAsInt();
				int z = loc.get("z").getAsInt();
				this.crystalposition.add(new Vector(x, y, z));
			}
			
			// get positions of "lasers"
			JsonArray laserpos = all.getAsJsonArray("laserpos");
			Iterator<JsonElement> laserpos_it =  laserpos.iterator();
			while (laserpos_it.hasNext()) {
				JsonObject loc = (JsonObject) laserpos_it.next();
				int x = loc.get("x").getAsInt();
				int y = loc.get("y").getAsInt();
				int z = loc.get("z").getAsInt();
				this.laserposition.add(new Vector(x , y, z));
			}
		
			// dragon spawn location
			JsonObject dragonpos = all.getAsJsonObject("dragonpos");
			int x = dragonpos.get("x").getAsInt();
			int y = dragonpos.get("y").getAsInt();
			int z = dragonpos.get("z").getAsInt();
			this.dragonlocation = new Vector(x, y, z);
			
			// sound location
			JsonObject soundsource = all.getAsJsonObject("soundsource");
			x = soundsource.get("x").getAsInt();
			y = soundsource.get("y").getAsInt();
			z = soundsource.get("z").getAsInt();
			this.soundsource = new Vector(x, y, z);
			
			this.allowedBlockMaterial = Material.getMaterial(all.get("material").getAsString());
			
			
			//add listener
			SkyGrid.registerEvent(this);
			
			BarrysLogger.info(this, "Done");
			
		}

		@Override
		boolean isMaterialAllowed(Material material) {
			return (material == this.allowedBlockMaterial);	
		}
		
		@Override
		boolean canBuildonLocation(Location loc) {

			boolean allowedLocation = false;		
			for (Vector allowed : this.blockSetAllowed) {
				if (loc.getBlockX() == allowed.getBlockX() && loc.getBlockY() == allowed.getBlockY() && loc.getBlockZ() == allowed.getBlockZ()) {				
					allowedLocation = true;				
					break;	
				}
			}
			return allowedLocation;
		}

		@Override
		void buildOnLocationEvent(Location loc) {

			if (loc.getBlockY() >= 2) {
				
				final Block b = loc.getWorld().getBlockAt(loc.add(0, -2, 0));
				b.setType(Material.END_GATEWAY);	
				new BukkitRunnable() {						
					@Override
					public void run() {						
						b.setType(Material.AIR);							
					}
				}.runTaskLater(SkyGrid.sharedInstance(), 200);
				
			}
			
		}

		@Override
		protected boolean isAltarComplete(Chunk chunk) {

			boolean fullaltar = true;
			for (Vector allowed : this.blockSetAllowed) {
				if (chunk.getBlock(allowed.getBlockX(), allowed.getBlockY(), allowed.getBlockZ()).getType() == Material.AIR) {
					fullaltar = false;
					break;
				}			
			}
			return fullaltar;
		}

		@Override
		protected void doEndAnimation(final Chunk chunk) {
			
			final World world = chunk.getWorld();
			final Location chunkloc = new Location(world, chunk.getX() << 4, 0, chunk.getZ() << 4);
			final ArrayList<EnderDragon> dragon = new ArrayList<EnderDragon>(); 
			final ArrayList<EnderCrystal> crytals = new ArrayList<EnderCrystal>(); 
			
			
			// activate all "egg laser"
			this.endAnimation.add(new ISkyGridRunnableWithDelay(210) {
				
				@Override
				public void run() {
				
					world.playSound(SkyGridEndAltar.this.soundsource.toLocation(world), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
					for (Vector allowed : SkyGridEndAltar.this.blockSetAllowed) {
						if (allowed.getBlockY() >= 2) {
							final Block b = world.getBlockAt(allowed.toLocation(world).add(chunkloc).add(0, -2, 0));
							b.setType(Material.END_GATEWAY);	
						}
					}
									
				}
			});
			
			// spawn the crytals with beam
			this.endAnimation.add(new ISkyGridRunnableWithDelay(200) {
				
				@Override
				public void run() {
				
					world.playSound(SkyGridEndAltar.this.soundsource.toLocation(world).add(chunkloc), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
					
					for (Vector crystalpos : SkyGridEndAltar.this.crystalposition) {
						EnderCrystal crystal = (EnderCrystal) world.spawnEntity(crystalpos.toLocation(world).add(chunkloc), EntityType.ENDER_CRYSTAL);
						//crystal.setInvulnerable(true);
						crystal.setBeamTarget(SkyGridEndAltar.this.dragonlocation.toLocation(world).add(chunkloc).add(0.5,0,0.5));
						crytals.add(crystal);
					}
					
				}
			});
			
			// spawn the dragon
			this.endAnimation.add(new ISkyGridRunnableWithDelay(200) {
				
				@Override
				public void run() {
					world.playSound(SkyGridEndAltar.this.soundsource.toLocation(world).add(chunkloc), Sound.ENTITY_ENDERDRAGON_GROWL, 1.0f, 1.0f);
					EnderDragon d = (EnderDragon) world.spawnEntity(SkyGridEndAltar.this.dragonlocation.toLocation(world).add(chunkloc).add(0.5,0,0.5), EntityType.ENDER_DRAGON);
					d.setPhase(Phase.HOVER);
					dragon.add(d);
				}
			});
			
			// activate the laser
			this.endAnimation.add(new ISkyGridRunnableWithDelay(120) {
				
				@Override
				public void run() {
					world.playSound(SkyGridEndAltar.this.soundsource.toLocation(world).add(chunkloc), Sound.ENTITY_IRONGOLEM_HURT, 1.0f, 1.0f);
					for (Vector laserpos : SkyGridEndAltar.this.laserposition) {
						world.getBlockAt(laserpos.toLocation(world).add(chunkloc)).setType(Material.END_GATEWAY);	
					}
				}
			});
			
			// kill the dragon
			this.endAnimation.add(new ISkyGridRunnableWithDelay(100) {
				
				@Override
				public void run() {
					dragon.get(0).setHealth(0);
				}
			});
			
			// messages
			this.endAnimation.add(new ISkyGridRunnableWithDelay(200) {
				
				@Override
				public void run() {
					for (Player p :  SkyGrid.sharedInstance().getServer().getOnlinePlayers())
						TitleManager.sendTitles(p, "Congratulation", "", 60, 60, 20);
				}
			});

			this.endAnimation.add(new ISkyGridRunnableWithDelay(240) {
				
				@Override
				public void run() {
					for (Player p :  SkyGrid.sharedInstance().getServer().getOnlinePlayers())
						TitleManager.sendTitles(p, "", "You finished Skygrid!", 60, 60, 20);
				}
			});

			this.endAnimation.add(new ISkyGridRunnableWithDelay(240) {
				
				@Override
				public void run() {
					for (Player p :  SkyGrid.sharedInstance().getServer().getOnlinePlayers())
						TitleManager.sendTitles(p, "THANKS FOR PLAYING!", "", 60, 60, 20);
				}
			});
			
			// prepare next altar
			this.endAnimation.add(new ISkyGridRunnableWithDelay(300) {
				
				@Override
				public void run() {
					SkyGridEndAltar.this.preprareNextAltar();
				}
			});
			
			long delay = 0;
			for (ISkyGridRunnableWithDelay r : this.endAnimation) {
				delay += r.delay;
				SkyGrid.sharedInstance().getServer().getScheduler().runTaskLater(SkyGrid.sharedInstance(), r, delay);
			}
			
		}

		@Override
		void preprareNextAltar() {

			BarrysLogger.info("SKYGRID COMPLETED - WILL RESET SERVER NOW");
			for (Player p :  SkyGrid.sharedInstance().getServer().getOnlinePlayers()) {
				p.sendMessage("  §6The \u06E9 server will restart now.");
				p.sendRawMessage("  §6The \u06E9 server will restart now.");
				p.sendMessage("  §6The progress and the world will be resetet.");
				p.sendMessage("  §6Thank you for playing. :-)");
			}
			
			//TODO
			
			//SkyGridLevel_Manager.sharedInstance().createDefault();
			//SkyGrid.resetEverything();
			
		}
		
		
		@EventHandler
		private void skyGridEndAlterBlockExplodeEvent(EntityExplodeEvent e) {
			
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
	
	private class BlockList {
		
		private Random random;
		
		//list of blocks used for the grid
		private Material[] overworld_common = null;
		private Material[] overworld_seldom = null;
		private Material[] overworld_rare = null;
		private Material[] overworld_epic = null;
		private Material[] overworld_legend = null;
		
		private Material[] nether_common = null;	
		private Material[] nether_seldom = null;	
		private Material[] nether_rare = null;	
		private Material[] nether_epic = null;
		private Material[] nether_legend = null;
		
		private Material[] end_common = null;	
		private Material[] end_rare = null;	
		private Material[] end_legend = null;
		
		public BlockList() {
			this.random = new Random();
			
			//////////////////////////
			// OVEWORLD-LAYER
			//////////////////////////
			
			this.overworld_common = new Material[] { 
					Material.STONE,
					Material.DIRT,
					Material.COBBLESTONE,
					Material.LOG,
					Material.LOG_2				
			};
			this.overworld_seldom = new Material[] { 
					Material.GRASS,
					Material.SAND,
					Material.GRAVEL,
					Material.SANDSTONE,
					Material.WOOL,
					Material.MONSTER_EGGS					
			};
			this.overworld_rare = new Material[] { 				
					Material.GOLD_ORE,
					Material.IRON_ORE,
					Material.COAL_ORE,
					Material.GLASS,
					Material.SNOW_BLOCK,
					Material.PRISMARINE,
					Material.SEA_LANTERN,
					Material.HAY_BLOCK,
					Material.CAKE_BLOCK,
					Material.MELON_BLOCK,
					Material.CHEST
			};
			this.overworld_epic = new Material[] { 
					Material.STATIONARY_LAVA,
					Material.STATIONARY_WATER,
					Material.LAPIS_ORE,
					Material.NOTE_BLOCK,
					Material.BOOKSHELF,
					Material.OBSIDIAN,				
					Material.DIAMOND_ORE,
					Material.FURNACE,
					Material.MYCEL,
					Material.SLIME_BLOCK,
					Material.COAL_BLOCK,
					Material.PUMPKIN,
					Material.WORKBENCH
			};
			this.overworld_legend = new Material[] { 
					Material.SPONGE,
					Material.LAPIS_BLOCK,
					Material.GOLD_BLOCK,
					Material.IRON_BLOCK,
					Material.TNT,
					Material.MOB_SPAWNER,
					Material.ENDER_CHEST
			};
			
			//////////////////////////
			// NETHER-LAYER
			//////////////////////////
			
			this.nether_common = new Material[] {
					Material.NETHERRACK,
					Material.NETHERRACK,
					Material.NETHERRACK,
					Material.QUARTZ_ORE,
					Material.NETHER_BRICK,
					Material.RED_SANDSTONE
			};
			this.nether_seldom = new Material[] {
					Material.SOUL_SAND,
					Material.GLOWSTONE,
					Material.GRAVEL,
					Material.PUMPKIN,
					
			};
			this.nether_rare = new Material[] {
					Material.REDSTONE_ORE,
					Material.STATIONARY_LAVA,
					Material.JACK_O_LANTERN
			};	
			this.nether_epic = new Material[] {
					Material.REDSTONE_BLOCK,
					Material.OBSIDIAN
			};
			this.nether_legend = new Material[] {
					Material.SOUL_SAND,
					Material.NETHERRACK,
					Material.MOB_SPAWNER
			};
			
			//////////////////////////
			// END-LAYER
			//////////////////////////
			
			this.end_common = new Material[] {
					Material.ENDER_STONE,
					Material.ENDER_STONE,
					Material.ENDER_STONE,
					Material.END_BRICKS,
					Material.PURPUR_BLOCK
			};	
			this.end_rare = new Material[] {
					Material.STATIONARY_LAVA,
					Material.OBSIDIAN
			};
			this.end_legend = new Material[] {
					Material.SOUL_SAND,
					Material.NETHERRACK,
					Material.MOB_SPAWNER
			};
			
		}

		
		public Material getRandomMaterial() {	

			// select a random materiallist
			
			int randomint = this.random.nextInt(1000);
			
			if (randomint < 590) 
				return this.overworld_common[this.random.nextInt(this.overworld_common.length)];
			if (randomint < 880) 
				return this.overworld_seldom[this.random.nextInt(this.overworld_seldom.length)];
			if (randomint < 975) 
				return this.overworld_rare[this.random.nextInt(this.overworld_rare.length)];
			if (randomint < 996) 
				return this.overworld_epic[this.random.nextInt(this.overworld_epic.length)];
			return this.overworld_legend[this.random.nextInt(this.overworld_legend.length)];
					
		}
		
		public Material getRandomMaterialForNether() {
			
			int randomint = random.nextInt(1000);		
			
			if (randomint < 700)
				return this.nether_common[this.random.nextInt(this.nether_common.length)];
			if (randomint < 900)
				return this.nether_seldom[this.random.nextInt(this.nether_seldom.length)];
			if (randomint < 980)
				return this.nether_rare[this.random.nextInt(this.nether_rare.length)];
			if (randomint < 998)
				return this.nether_epic[this.random.nextInt(this.nether_epic.length)];
			return this.nether_legend[this.random.nextInt(this.nether_legend.length)];
					
		}

		public Material getRandomMaterialForEnd() {
			
			//return Material.ENDER_STONE;
			
			int randomint = random.nextInt(1000);		
			
			if (randomint < 800)
				return this.end_common[this.random.nextInt(this.end_common.length)];
			if (randomint < 950)
				return this.end_rare[this.random.nextInt(this.end_rare.length)];
			return this.end_legend[this.random.nextInt(this.end_legend.length)];

		}
	}

	private class PlayerThreads extends IPlayerThreads {
		
		private NetherWarningThread netherwarning;
		private EndWarningThread endwarning;
		
		public PlayerThreads(UUID playeruuid) {
			super(playeruuid);
			this.netherwarning = new NetherWarningThread(playeruuid);
			this.netherwarning.runTaskTimer(SkyGrid.sharedInstance(), 0, 100);
			this.endwarning = new EndWarningThread(playeruuid);
			this.endwarning.runTaskTimer(SkyGrid.sharedInstance(), 0, 60);
		}

		@Override
		public void invalidateThreads() {

			this.netherwarning.cancel();
			this.endwarning.cancel();
			
		}
	}
	
}
