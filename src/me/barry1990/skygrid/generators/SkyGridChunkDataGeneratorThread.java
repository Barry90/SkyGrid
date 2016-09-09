package me.barry1990.skygrid.generators;

import java.util.LinkedList;
import java.util.Queue;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.ChunkData;

/**
 * SkyGridChunkDataGeneratorThread - This calls handles the asynchronous chunk generation
 * 
 * @author Barry1990
 */
class SkyGridChunkDataGeneratorThread extends Thread {

	/**
	 * IChunkDataProvider - The interface to implement
	 * 
	 * @author Barry1990
	 */
	interface IChunkDataProvider {

		/**
		 * This method returns allocated ChunkData
		 * 
		 * @param world The world for this chunk
		 * @return ChunkData that can be filled with data
		 */
		ChunkData getChunkData(World world);
	}

	private static final int	MAXQUEUE	= 15;
	private Queue<ChunkData>	chunkqueue	= new LinkedList<ChunkData>();
	private World				world;
	private boolean				softStop	= false;
	private IChunkDataProvider	provider;

	/**
	 * Creates a new instance of SkyGridChunkDataGeneratorThread
	 * 
	 * @param world The skygrid world
	 * @param provider The ChunkDataProvider
	 */
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

	/**
	 * This methed put a new chunk in the queue
	 * 
	 * @throws InterruptedException
	 */
	private synchronized void putChunk() throws InterruptedException {

		while (this.chunkqueue.size() == MAXQUEUE && !this.softStop) {
			wait();
		}
		if (this.softStop)
			return;
		this.chunkqueue.add(this.generateChunk());
		notify();
		// Later, when the necessary event happens, the thread that is running it calls notify() from a block synchronized on the same object.
	}

	// Called by Consumer
	/**
	 * Get new generated ChunkData
	 * 
	 * @return ChunkData
	 * @throws InterruptedException
	 */
	public synchronized ChunkData getChunk() throws InterruptedException {

		notify();
		while (this.chunkqueue.size() == 0) {
			BarrysLogger.info(this, "ChunkDate-Queue is empty. Waiting for generator...");
			wait();// By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
		}
		return this.chunkqueue.remove();
	}

	/**
	 * This method generates chunkData
	 * 
	 * @return the generated ChunkData
	 */
	private ChunkData generateChunk() {

		ChunkData data = this.provider.getChunkData(this.world);
		data = SkyGrid.getLevelManager().getLevel().fillChunkData(data);
		return data;
	}

	/**
	 * This method sends a softstop request to this thread
	 */
	public synchronized void softstop() {

		this.softStop = true;
		notify();
	}
}
