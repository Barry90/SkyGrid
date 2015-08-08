package me.barry1990.skygrid.generators;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.bukkit.inventory.ItemStack;


class SkyGridInventoryGeneratorThread extends Thread {
	
	static final int chestsize = 27;
	static final int MAXQUEUE = 15;
	private Queue<ItemStack[]> itemStacksQueue = new LinkedList<ItemStack[]>();
	private Random random;
	
	public SkyGridInventoryGeneratorThread() {
		super();
		this.random = new Random();
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				putItemStacks();
				//sleep(5000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void putItemStacks() throws InterruptedException {
		while (itemStacksQueue.size() == MAXQUEUE) {
			wait();
		}
		itemStacksQueue.add(this.setRandomInventoryContent());
		notify();
		//Later, when the necessary event happens, the thread that is running it calls notify() from a block synchronized on the same object.
	}
	
	// Called by Consumer
	public synchronized ItemStack[] getItemStacks() throws InterruptedException {
		notify();
		while (itemStacksQueue.size() == 0) {
			wait();//By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
		}
		
		return itemStacksQueue.remove();
	}
	 
	private ItemStack[] setRandomInventoryContent() {
			
		ItemStack[] items = new ItemStack[chestsize];
		int range = 20;
		int pos = 0;
		
		while ((pos < chestsize) && ((this.random.nextInt(100)+1) > range)) {
			items[pos] = ItemList.getRandomBuildingBlock(random);
			pos++;
			range +=20;
		}
		
		range = 15;		
		while ((pos < chestsize) && ((this.random.nextInt(100)+1) > range)) {
			items[pos] = ItemList.getRandomMaterialItems(random);
			pos++;
			range +=15;
		}
		
		range = 50;		
		while ((pos < chestsize) && ((this.random.nextInt(100)+1) > range)) {
			items[pos] = ItemList.getRandomEquipmentItems(random);
			pos++;
			range +=22;
		}
		
		range = 70;		
		while ((pos < chestsize) && ((this.random.nextInt(100)+1) > range)) {
			items[pos] = ItemList.getRandomRareItems(random);
			pos++;
			range +=9;
		}
		
		range = 20;		
		while ((pos < chestsize) && ((this.random.nextInt(100)+1) > range)) {
			items[pos] = ItemList.getRandomFoodItem(random);
			pos++;
			range +=30;
		}
		
		range = 95;		
		while ((pos < chestsize) && ((this.random.nextInt(100)+1) > range)) {
			items[pos] = ItemList.getRandomLendaryItem(random);
			pos++;
			range +=1;
		}
		
		return items;		
	}

}
