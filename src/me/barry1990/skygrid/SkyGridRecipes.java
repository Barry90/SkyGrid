package me.barry1990.skygrid;

import java.util.ArrayList;
import java.util.List;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.SmoothBrick;
import org.bukkit.plugin.java.JavaPlugin;


public class SkyGridRecipes {

	private static List<Recipe> skygridrecipes;
	
	static {
		
		BarrysLogger.info("start to create SkyGrid recipes");
		SkyGridRecipes.skygridrecipes = new ArrayList<Recipe>();
		
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
		
		BarrysLogger.info("SkyGrid recipes created");
	}
	
	public static void addSkyGridRecipes(JavaPlugin plugin) {
		for (Recipe recipe : skygridrecipes) {
			plugin.getServer().addRecipe(recipe);
			BarrysLogger.info("SkyGrid recipes added");
		}
	}
}
