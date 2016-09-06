package me.barry1990.skygrid.generators;

import java.util.LinkedList;
import java.util.Queue;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.ChunkData;

class SkyGridChunkDataGeneratorThread extends Thread {

	interface IChunkDataProvider {

		ChunkData getChunkData(World world);
	}

	private static final int	MAXQUEUE	= 15;
	private Queue<ChunkData>	chunkqueue	= new LinkedList<ChunkData>();
	private World				world;
	private boolean				softStop	= false;

	private IChunkDataProvider	provider;

	public SkyGridChunkDataGeneratorThread(World world, IChunkDataProvider provider) {

		super();
		this.world = world;
		this.provider = provider;
	}

	@Override
	public void run() {

		try {
			while (!this.softStop) {
				putChunk();
			}
		} catch (InterruptedException e) {
		}
		this.chunkqueue.clear();
	}

	private synchronized void putChunk() throws InterruptedException {

		while (chunkqueue.size() == MAXQUEUE) {
			wait();
		}
		if (this.softStop)
			return;
		chunkqueue.add(this.generateChunk());
		notify();
		// Later, when the necessary event happens, the thread that is running it calls notify() from a block synchronized on the same object.
	}

	// Called by Consumer
	public synchronized ChunkData getChunk() throws InterruptedException {

		notify();
		while (chunkqueue.size() == 0) {
			BarrysLogger.info(this, "queue is empty. waiting...");
			wait();// By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
		}
		return chunkqueue.remove();
	}

	private ChunkData generateChunk() {

		ChunkData data = this.provider.getChunkData(this.world);
		data = SkyGrid.sharedInstance().getLevelManager().getLevel().fillChunkData(data);
		return data;
	}

	public synchronized void softstop() {

		notify();
		softStop = true;
	}
}
