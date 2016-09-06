package me.barry1990.skygrid.level;


abstract public class ISkyGridRunnableWithDelay implements Runnable {

	private long delay;
	
	public ISkyGridRunnableWithDelay(long delay) {
		this.setDelay(delay);
	}

	public long getDelay() {

		return delay;
	}

	public void setDelay(long delay) {

		this.delay = delay;
	}
}
