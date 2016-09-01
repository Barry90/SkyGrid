package me.barry1990.skygrid.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.skygrid.TitleManager;
import me.barry1990.skygrid.PlayerThreads.IPlayerThreads;
import me.barry1990.skygrid.PlayerThreads.NoHungerThread;
import me.barry1990.skygrid.achievement.SGAIDENTIFIER;
import me.barry1990.skygrid.world.SkyGridWorld;
import me.barry1990.utils.BarrysLogger;
import me.barry1990.utils.Converter;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Tree;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public final class SkyGridLevelWood extends ISkyGridLevel {
	
	private ISkyGridAlter altar;
	private Random random;
	
	private ItemStack magicGrass;
	private ItemStack smallMagicEssence;
	private ItemStack magicEssence;
	private ItemStack magicSapling;
	private ItemStack strongMagicEssence;
	private ItemStack magicWood;
	
	public SkyGridLevelWood() {
		this.random = new Random();
		this.altar = new SkyGridLevelWood.SkyGridWoodAltar();
		
		this.magicGrass = new ItemStack(Material.GRASS, 1); 
		ItemMeta meta = this.magicGrass.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Magic Grass");
		this.magicGrass.setItemMeta(meta);		
		
		this.smallMagicEssence = new ItemStack(Material.SUGAR, 1);
		meta = this.smallMagicEssence.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Small Magic Essence");
		this.smallMagicEssence.setItemMeta(meta);
		
		this.magicEssence = new ItemStack(Material.GLOWSTONE_DUST, 1);
		meta = this.magicEssence.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Magic Essence");
		this.magicEssence.setItemMeta(meta);
		
		this.magicSapling = new ItemStack(Material.SAPLING, 1);
		meta = this.magicSapling.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Magic Sapling");
		this.magicSapling.setItemMeta(meta);
		
		this.strongMagicEssence = new ItemStack(Material.NETHER_STAR, 1);
		meta = this.strongMagicEssence.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Strong Magic Essence");
		this.strongMagicEssence.setItemMeta(meta);
		
		this.magicWood = new ItemStack(Material.LOG_2, 1);
		meta = this.magicWood.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Magic Wood");
		this.magicWood.setItemMeta(meta);
		
		SkyGrid.registerEvent(new SkyGridLevelWoodListerner());
	}

	@Override
	public ChunkData fillChunkData(ChunkData chunkdata) {
		
		int layer = 0;		
		while (layer < 4) {
			int y = 134 + layer*5;

			if (layer < 4) {
				for (int x = 1; x < 16; x += 4) {				
					for (int z = 1; z < 16; z += 4) {					
						chunkdata.setBlock(x, y, z, this.randomMetaData.getTreeFullTexture(Material.LOG));
						chunkdata.setBlock(x, y, z + 1, this.randomMetaData.getTreeFullTexture(Material.LOG));
						chunkdata.setBlock(x, y + 1, z, this.randomMetaData.getTreeFullTexture(Material.LOG));
						chunkdata.setBlock(x, y + 1, z + 1, this.randomMetaData.getTreeFullTexture(Material.LOG));
						chunkdata.setBlock(x + 1, y, z, this.randomMetaData.getTreeFullTexture(Material.LOG));
						chunkdata.setBlock(x + 1, y, z + 1, this.randomMetaData.getTreeFullTexture(Material.LOG));
						chunkdata.setBlock(x + 1, y + 1, z, this.randomMetaData.getTreeFullTexture(Material.LOG));
						chunkdata.setBlock(x + 1, y + 1, z + 1, this.randomMetaData.getTreeFullTexture(Material.LOG));					
					}				
				}
			}
			
			layer++;
		}
		
		layer = 0;
		 while (layer < 4) {
			int y = 167 + layer*5; 
			
			for (int x = 1; x < 16; x += 4) {				
				for (int z = 1; z < 16; z += 4) {					
					if (this.random.nextFloat() < 0.2) {
						if (this.random.nextBoolean())
							chunkdata.setBlock(x, y, z, this.randomMetaData.getWood(Material.LOG));
						else
							chunkdata.setBlock(x, y, z, Material.DIRT);
						if (this.random.nextBoolean())
							chunkdata.setBlock(x, y, z + 1, this.randomMetaData.getWood(Material.LOG));
						else
							chunkdata.setBlock(x, y, z + 1, Material.DIRT);
						if (this.random.nextBoolean())
							chunkdata.setBlock(x, y + 1, z, this.randomMetaData.getWood(Material.LOG));
						else
							chunkdata.setBlock(x, y + 1, z, Material.DIRT);
						if (this.random.nextBoolean())
							chunkdata.setBlock(x, y + 1, z + 1, this.randomMetaData.getWood(Material.LOG));
						else
							chunkdata.setBlock(x, y + 1, z + 1, Material.DIRT);
						if (this.random.nextBoolean())
							chunkdata.setBlock(x + 1, y, z, this.randomMetaData.getWood(Material.LOG));
						else
							chunkdata.setBlock(x + 1, y, z, Material.DIRT);
						if (this.random.nextBoolean())
							chunkdata.setBlock(x + 1, y, z + 1, this.randomMetaData.getWood(Material.LOG));
						else
							chunkdata.setBlock(x + 1, y, z + 1, Material.DIRT);
						if (this.random.nextBoolean())
							chunkdata.setBlock(x + 1, y + 1, z, this.randomMetaData.getWood(Material.LOG));
						else
							chunkdata.setBlock(x + 1, y + 1, z, Material.DIRT);
						if (this.random.nextBoolean())
							chunkdata.setBlock(x + 1, y + 1, z + 1, this.randomMetaData.getWood(Material.LOG));
						else
							chunkdata.setBlock(x + 1, y + 1, z + 1, Material.DIRT);
					}				
				}				
			}
			
			layer++;
		}
		
		chunkdata.setRegion(0, 128, 0, 16, 129, 16, Material.COBBLESTONE);
		
		if (this.random.nextFloat() < 0.01) {
			int x = this.random.nextInt(16);
			int y = 98 - this.random.nextInt(30);
			int z = this.random.nextInt(16);
			chunkdata.setBlock(x, y, z, Material.EMERALD_ORE);
			chunkdata.setBlock(x, y-1, z, Material.COBBLESTONE);
		}

		return chunkdata;
	}

	@Override
	public Location generateSkyGridSpawnLocation() {

		double x = (this.random.nextInt(1500)-750) * 4 + 1.5;
		double z = (this.random.nextInt(1500)-750) * 4 + 1.5;
		double y = 134 + this.random.nextInt(4)*5;
		return new Location(SkyGridWorld.getSkyGridWorld(), x, y, z);
	}

	@Override
	public ISkyGridAlter getSkyGridAltar() {
		return this.altar;
	}

	@Override
	public IPlayerThreads getPlayerThreads(UUID playeruuid) {

		return new SkyGridLevelWood.PlayerThreads(playeruuid);
	}

	@Override
	List<Recipe> registerRecipes() {
		
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(new ShapedRecipe(this.magicSapling)
		.shape("LLL","LDL","LLL")
		.setIngredient('L', new Tree(TreeSpecies.GENERIC))
		.setIngredient('D', Material.DIRT));
		
		recipes.add(new ShapedRecipe(new ItemStack(Material.SAPLING))
		.shape("LLL","LDL","LLL")
		.setIngredient('L', new Tree(TreeSpecies.BIRCH))
		.setIngredient('D', Material.DIRT));
		
		recipes.add(new ShapedRecipe(new ItemStack(Material.SAPLING))
		.shape("LLL","LDL","LLL")
		.setIngredient('L', new Tree(TreeSpecies.JUNGLE))
		.setIngredient('D', Material.DIRT));
		
		recipes.add(new ShapedRecipe(new ItemStack(Material.SAPLING))
		.shape("LLL","LDL","LLL")
		.setIngredient('L', new Tree(TreeSpecies.REDWOOD))
		.setIngredient('D', Material.DIRT));
		
		recipes.add(new ShapedRecipe(this.magicGrass)
		.shape("EEE","EDE","EEE")
		.setIngredient('E', this.smallMagicEssence.getType())
		.setIngredient('D', Material.DIRT));
		
		recipes.add(new ShapedRecipe(this.strongMagicEssence)
		.shape("SMS","MMM","SMS")
		.setIngredient('S', this.smallMagicEssence.getType())
		.setIngredient('M', this.magicEssence.getType()));
		
		recipes.add(new ShapedRecipe(this.magicWood)
		.shape("ESE","SLS","ESE")
		.setIngredient('S', this.strongMagicEssence.getType())
		.setIngredient('E', Material.EMERALD_BLOCK)
		.setIngredient('L', Material.LOG));

		return recipes;
	}

	@Override
	public boolean isAchievementAvailable(SGAIDENTIFIER sga_id) {

		// TODO Auto-generated method stub
		return false;
	}
	
	private class SkyGridLevelWoodListerner implements Listener {
		
		private PotionType[] potiontypes;		
		
		public SkyGridLevelWoodListerner() {
			
			this.potiontypes = new PotionType[] {PotionType.INVISIBILITY, PotionType.JUMP, PotionType.SPEED,
					PotionType.SLOWNESS, PotionType.INSTANT_HEAL, PotionType.INSTANT_DAMAGE,
					PotionType.POISON, PotionType.STRENGTH, PotionType.WEAKNESS};			
			
		}
		
		@EventHandler
		public void onBlockPlaceEvent(BlockPlaceEvent e) {
			
			//  only set full textured log
			Block placed = e.getBlockPlaced();
			if (placed.getType() == Material.LOG || placed.getType() == Material.LOG_2) {
				
				Tree tree = ((Tree)placed.getState().getData());
				tree.setDirection(BlockFace.SELF);
				BlockState bs = placed.getState();
				bs.setData(tree);
				bs.update(true);				
			}
			
			if (placed.getType() == Material.SAPLING && placed.getRelative(BlockFace.DOWN).getType() != Material.GRASS) {
				e.setCancelled(true);
			}
		}
		
		@EventHandler
		public void onEntitySpawnEvent(EntitySpawnEvent e) {
			
			EntityType type = e.getEntityType();
			
			if (type != EntityType.DROPPED_ITEM && type != EntityType.SKELETON && type != EntityType.ENDERMAN && type != EntityType.SPIDER) {
				e.setCancelled(true);
			}
			
			if (type == EntityType.SKELETON && SkyGridLevelWood.this.random.nextFloat() < 1) {			
				((Skeleton) e.getEntity()).getEquipment().setItemInOffHand(this.createTippedArrowForSkeleton());;
			}
			
			if (type == EntityType.DROPPED_ITEM ) {		
				ItemStack i = ((Item) e.getEntity()).getItemStack();
				if (i.getType() == Material.SAPLING && !SkyGridLevelWood.this.magicSapling.getItemMeta().getDisplayName().equals(i.getItemMeta().getDisplayName())) {
					e.setCancelled(true);
				}
			}
		}

		@EventHandler
		public void onEntityDeathEvent(EntityDeathEvent e) {
			
			EntityType type = e.getEntityType();
			
			if (type == EntityType.SKELETON && SkyGridLevelWood.this.random.nextFloat() < 1) {
				e.getDrops().add(SkyGridLevelWood.this.smallMagicEssence);
			}
		}
				
		@EventHandler
		public void onPrepareItemCraftEvent(PrepareItemCraftEvent e) {
			
			if (e.getRecipe().getResult().equals(SkyGridLevelWood.this.magicGrass)) {
				for (ItemStack i : e.getInventory().getMatrix()) {
					if (i.getType() == SkyGridLevelWood.this.smallMagicEssence.getType()) {
						if (!SkyGridLevelWood.this.smallMagicEssence.getItemMeta().getDisplayName().equals(i.getItemMeta().getDisplayName())) {
							e.getInventory().setResult(new ItemStack(Material.AIR));
							return;
						}
					}
				}				
			}
			
			if (e.getRecipe().getResult().equals(SkyGridLevelWood.this.strongMagicEssence)) {
				for (ItemStack i : e.getInventory().getMatrix()) {
					if (i.getType() == SkyGridLevelWood.this.smallMagicEssence.getType()) {
						if (i.getAmount() != 9 || !SkyGridLevelWood.this.smallMagicEssence.getItemMeta().getDisplayName().equals(i.getItemMeta().getDisplayName())) {
							e.getInventory().setResult(new ItemStack(Material.AIR));
							return;
						}
					}					
					if (i.getType() == SkyGridLevelWood.this.magicEssence.getType()) {
						if (i.getAmount() != 9 || !SkyGridLevelWood.this.magicEssence.getItemMeta().getDisplayName().equals(i.getItemMeta().getDisplayName())) {
							e.getInventory().setResult(new ItemStack(Material.AIR));
							return;
						}
					}
				}				
			}	
			
			if (e.getRecipe().getResult().getType() == Material.GLOWSTONE) {
				e.getInventory().setResult(new ItemStack(Material.AIR));
				return;				
			}	
		}
		
		@EventHandler
		public void onCraftItem(CraftItemEvent e) {		
			if (e.getRecipe().getResult().getType() == Material.NETHER_STAR) {
				e.getInventory().setMatrix(new ItemStack[]{
						new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR),
						new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR),
						new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR)});
				if (e.getWhoClicked() instanceof Player) {
					e.getWhoClicked().setItemOnCursor(e.getRecipe().getResult());
				}
			}			
		}
		
		@EventHandler
		public void onLeavesDecayEvent(LeavesDecayEvent e) {
			if (SkyGridLevelWood.this.random.nextFloat() < 1) {
				Location leaves = e.getBlock().getLocation();
				leaves.getWorld().dropItem(leaves, SkyGridLevelWood.this.magicEssence);
			}
		}
				
		private ItemStack createTippedArrowForSkeleton() {
			
			ItemStack arrow = new ItemStack(Material.TIPPED_ARROW);
			arrow.setAmount(1);
			PotionType potiontype = this.potiontypes[SkyGridLevelWood.this.random.nextInt(this.potiontypes.length)];
			PotionMeta potionmeta = (PotionMeta) arrow.getItemMeta();
			potionmeta.setBasePotionData(new PotionData(potiontype));
			arrow.setItemMeta(potionmeta);
			
			return arrow;
		}
	
	}
	
	private class SkyGridWoodAltar extends ISkyGridAlter implements Listener {
		
		private ArrayList<Vector> blockSetAllowed;		
		private ArrayList<Vector> crystalposition;
		private Vector laserposition;
		private Vector soundsource;
		
		private ArrayList<ISkyGridRunnableWithDelay> altarAnimation;
		
		
		SkyGridWoodAltar() {
			super();
			BarrysLogger.info("SkyGridAltarWood Contructor called");
			this.blockSetAllowed = new ArrayList<Vector>();			
			this.crystalposition = new ArrayList<Vector>();
			this.altarAnimation = new ArrayList<ISkyGridRunnableWithDelay>();
			this.loadAltar();
			
		}
			

		@SuppressWarnings("deprecation")
		@Override
		public ChunkData getChunkData(World world, ChunkData chunkdata) {
			BarrysLogger.info(this, "getChunkData called");

			try {			
				
				JsonObject all = (JsonObject)this.getJsonFromResource("SkyGridWoodAltarData.data");
				
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
		protected void loadAltar() {
			
			BarrysLogger.info(this, "loading data...");

			JsonObject all = (JsonObject)this.getJsonFromResource("SkyGridWoodAltarData.data");
			
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
			
			// get position of laser
			JsonObject laserpos = all.getAsJsonObject("laserpos");
			int x = laserpos.get("x").getAsInt();
			int y = laserpos.get("y").getAsInt();
			int z = laserpos.get("z").getAsInt();
			this.laserposition = new Vector(x , y, z);
			
			
			// sound location
			JsonObject soundsource = all.getAsJsonObject("soundsource");
			x = soundsource.get("x").getAsInt();
			y = soundsource.get("y").getAsInt();
			z = soundsource.get("z").getAsInt();
			this.soundsource = new Vector(x, y, z);
								
			BarrysLogger.info(this, "Done");			
		}

		@Override
		boolean isBlockallowed(Block block) {
			return (block.getType() == SkyGridLevelWood.this.magicWood.getType());	
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
			final ArrayList<EnderCrystal> crytals = new ArrayList<EnderCrystal>(); 
			
			
			// activate all "egg laser"
			this.altarAnimation.add(new ISkyGridRunnableWithDelay(210) {
				
				@Override
				public void run() {
				
					world.playSound(SkyGridWoodAltar.this.soundsource.toLocation(world), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
					for (Vector allowed : SkyGridWoodAltar.this.blockSetAllowed) {
						if (allowed.getBlockY() >= 2) {
							final Block b = world.getBlockAt(allowed.toLocation(world).add(chunkloc).add(0, -2, 0));
							b.setType(Material.END_GATEWAY);	
						}
					}
									
				}
			});
			
			// spawn the crytals with beam
			this.altarAnimation.add(new ISkyGridRunnableWithDelay(200) {
				
				@Override
				public void run() {
				
					world.playSound(SkyGridWoodAltar.this.soundsource.toLocation(world).add(chunkloc), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
					
					for (Vector crystalpos : SkyGridWoodAltar.this.crystalposition) {
						EnderCrystal crystal = (EnderCrystal) world.spawnEntity(crystalpos.toLocation(world).add(chunkloc), EntityType.ENDER_CRYSTAL);
						crystal.setBeamTarget(SkyGridWoodAltar.this.laserposition.toLocation(world).add(chunkloc).add(0.5,5,0.5));
						crytals.add(crystal);
					}
					
				}
			});
						
			// activate the laser
			this.altarAnimation.add(new ISkyGridRunnableWithDelay(120) {
				
				@Override
				public void run() {
					world.playSound(SkyGridWoodAltar.this.soundsource.toLocation(world).add(chunkloc), Sound.ENTITY_IRONGOLEM_HURT, 1.0f, 1.0f);
					world.getBlockAt(SkyGridWoodAltar.this.laserposition.toLocation(world).add(chunkloc)).setType(Material.END_GATEWAY);					
				}
			});
			
			for (int i = 136; i < 155; i++) {
				final int y = i;
				this.altarAnimation.add(new ISkyGridRunnableWithDelay(20) {
					
					@Override
					public void run() {
						world.playSound(SkyGridWoodAltar.this.soundsource.toLocation(world).add(chunkloc), Sound.ENTITY_IRONGOLEM_HURT, 1.0f, 1.0f);
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								Block b = chunk.getBlock(x, y, z);
								if (b.getType() == Material.SOUL_SAND) {
									b.setType(Material.LOG);
									Tree tree = ((Tree)b.getState().getData());
									tree.setDirection(BlockFace.SELF);
									BlockState bs = b.getState();
									bs.setData(tree);
									bs.update(true);
								}
								if (b.getType() == Material.NETHERRACK) {
									b.setType(Material.LEAVES);
								}							
							}
							
						}
					}
				});
			}
						
			// messages
			this.altarAnimation.add(new ISkyGridRunnableWithDelay(200) {
				
				@Override
				public void run() {
					for (Player p :  SkyGrid.sharedInstance().getServer().getOnlinePlayers())
						TitleManager.sendTitles(p, "Congratulation", "", 60, 60, 20);
				}
			});

			this.altarAnimation.add(new ISkyGridRunnableWithDelay(240) {
				
				@Override
				public void run() {
					for (Player p :  SkyGrid.sharedInstance().getServer().getOnlinePlayers())
						TitleManager.sendTitles(p, "", "You finished Skygrid!", 60, 60, 20);
				}
			});

			this.altarAnimation.add(new ISkyGridRunnableWithDelay(240) {
				
				@Override
				public void run() {
					for (Player p :  SkyGrid.sharedInstance().getServer().getOnlinePlayers())
						TitleManager.sendTitles(p, "THANKS FOR PLAYING!", "", 60, 60, 20);
				}
			});
			
			// prepare next altar
			this.altarAnimation.add(new ISkyGridRunnableWithDelay(300) {
				
				@Override
				public void run() {
					SkyGridWoodAltar.this.preprareNextAltar();
				}
			});
			
			long delay = 0;
			for (ISkyGridRunnableWithDelay r : this.altarAnimation) {
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
	}
	
	private class PlayerThreads extends IPlayerThreads {
		
		private NoHungerThread nohunger;

		public PlayerThreads(UUID playeruuid) {

			super(playeruuid);
			this.nohunger = new NoHungerThread(playeruuid);
			this.nohunger.runTaskTimer(SkyGrid.sharedInstance(), 0, 100);
					
		}

		@Override
		public void invalidateThreads() {

			this.nohunger.cancel();
			
		}
		
		
	}

}
