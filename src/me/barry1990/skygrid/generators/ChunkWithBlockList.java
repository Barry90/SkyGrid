package me.barry1990.skygrid.generators;

import java.util.LinkedList;
import java.util.List;


class ChunkWithBlockList {
	
	public short [][] chunk;
	public List<ComplexBlock> list;
	
	public ChunkWithBlockList(int worldMaxHeight) {
		this.chunk = new short[worldMaxHeight / 16][4096];
		this.list = new LinkedList<ComplexBlock>();
	}

}
