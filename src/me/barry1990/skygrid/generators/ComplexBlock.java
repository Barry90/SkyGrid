package me.barry1990.skygrid.generators;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ComplexBlock {

	public int x;
	public int y;
	public int z;
	public Material material;
	public MaterialData materialData;
	
	public ComplexBlock(Material m,MaterialData md, int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.material = m;
		this.materialData = md;
	}
	
	public String toString(){
		return x+" "+y+" "+z+" "+material.toString();
	}
}
