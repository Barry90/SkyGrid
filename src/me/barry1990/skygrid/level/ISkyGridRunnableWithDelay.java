package me.barry1990.skygrid.level;


abstract class ISkyGridRunnableWithDelay implements Runnable {

	long delay;
	
	ISkyGridRunnableWithDelay(long delay) {
		this.delay = delay;
	}
}
