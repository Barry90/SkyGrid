package me.barry1990.skygrid;

import java.util.ArrayList;
import java.util.List;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import org.bukkit.material.SmoothBrick;
import org.bukkit.plugin.java.JavaPlugin;


public final class SkyGridRecipes {

	private static List<Recipe> skygridrecipes;
	
	static {
		
		BarrysLogger.info("start to create SkyGrid recipes");
		SkyGridRecipes.skygridrecipes = new ArrayList<Recipe>();
		
		/** CRAFTING - SHAPELESS */
		
		//Water-Bucket
		SkyGridRecipes.skygridrecipes.add(
			new ShapelessRecipe(new ItemStack(Material.WATER_BUCKET, 3))
			.addIngredient(2, Material.WATER_BUCKET)
			.addIngredient(1, Material.BUCKET)
		);
		//Lava-Bucket
		SkyGridRecipes.skygridrecipes.add(
			new ShapelessRecipe(new ItemStack(Material.LAVA_BUCKET, 1))
			.addIngredient(1, Material.OBSIDIAN)
			.addIngredient(1, Material.BLAZE_ROD)
			.addIngredient(1, Material.FLINT_AND_STEEL)
			.addIngredient(1, Material.BUCKET)
		);
		//Blaze Rod
		SkyGridRecipes.skygridrecipes.add(
				new ShapelessRecipe(new ItemStack(Material.BLAZE_ROD, 1))
				.addIngredient(2, Material.BLAZE_POWDER)
				.addIngredient(1, Material.STICK)
		);
		//Sand
		SkyGridRecipes.skygridrecipes.add(
				new ShapelessRecipe(new ItemStack(Material.SAND, 1))
				.addIngredient(1, new MaterialData(Material.SAND, (byte)1))
				.addIngredient(1, new Dye(DyeColor.WHITE))
		);
		//Red Sand
		SkyGridRecipes.skygridrecipes.add(
				new ShapelessRecipe(new ItemStack(Material.SAND, 1, (short)1))
				.addIngredient(1, new MaterialData(Material.SAND, (byte)0))
				.addIngredient(1, new Dye(DyeColor.RED))
		);
		//Cobble -> Granit
		SkyGridRecipes.skygridrecipes.add(
				new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short)1))
				.addIngredient(1, Material.COBBLESTONE)
		);
		//Granit -> Diorit
		SkyGridRecipes.skygridrecipes.add(
				new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short)3))
				.addIngredient(1, new MaterialData(Material.STONE, (byte)1))
		);
		//Diorit -> Andesite
		SkyGridRecipes.skygridrecipes.add(
				new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short)5))
				.addIngredient(1, new MaterialData(Material.STONE, (byte)3))
		);
		//Andesite -> Cobble
		SkyGridRecipes.skygridrecipes.add(
				new ShapelessRecipe(new ItemStack(Material.COBBLESTONE, 1))
				.addIngredient(1, new MaterialData(Material.STONE, (byte)5))
		);
		//Beetrodseeds
		SkyGridRecipes.skygridrecipes.add(
				new ShapelessRecipe(new ItemStack(Material.BEETROOT_SEEDS, 2))
				.addIngredient(1, Material.NETHER_WARTS)
		);
		
		/** CRAFTING - SHAPED */
		
		//Obsidian
		SkyGridRecipes.skygridrecipes.add(
			new ShapedRecipe(new ItemStack(Material.OBSIDIAN, 1))
			.shape(" S ","SBS"," S ")
			.setIngredient('S', Material.SMOOTH_BRICK).setIngredient('S', new SmoothBrick(Material.COBBLESTONE))
			.setIngredient('B', Material.BLAZE_POWDER)
		);
		//Cobweb
		SkyGridRecipes.skygridrecipes.add(
			new ShapedRecipe(new ItemStack(Material.WEB, 1))
			.shape("W W"," W ","W W")
			.setIngredient('W', Material.STRING)
		);
		//Netherportalframe
		SkyGridRecipes.skygridrecipes.add(
			new ShapedRecipe(new ItemStack(Material.ENDER_PORTAL_FRAME, 1))
			.shape("BIB","ECE","EEE")
			.setIngredient('B', Material.BLAZE_ROD)
			.setIngredient('I', Material.ENDER_PEARL)
			.setIngredient('E', Material.ENDER_STONE)
			.setIngredient('C', Material.CAULDRON_ITEM)
		);
		
		/** FURNACE */
		
		//Blaze Powder
		SkyGridRecipes.skygridrecipes.add(
			new FurnaceRecipe(new ItemStack(Material.BLAZE_POWDER, 1), new MaterialData(Material.ENDER_STONE), 0.1f)
		);

		BarrysLogger.info("SkyGrid recipes created");
	}
	
	public static void addSkyGridRecipes(JavaPlugin plugin) {
		for (Recipe recipe : skygridrecipes) {
			plugin.getServer().addRecipe(recipe);
			BarrysLogger.infoEnum("SkyGrid recipes added for", recipe.getResult().getType());
		}
	}
}
