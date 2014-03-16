package me.dylan.NNL.Utils;

import java.util.HashMap;

/**
 * Utility used in the creation, usage, and modifying of threads for Neuroticz
 * 
 */
public class ThreadUtil {
	private static HashMap<String, Thread> threadPool = new HashMap<String, Thread>();

	/**
	 * Starts a new thread and then adds the string and thread to the thread
	 * pool
	 * 
	 * @param name
	 *            the string to be added
	 * @param threadRun
	 *            the thread to be executed and added
	 */
	public static void spinThreadForPool(String name, Runnable threadRun) {
		Thread spun = new Thread(threadRun);
		spun.start();
		threadPool.put(name, spun);
	}

	/**
	 * Checks if a thread in the pool has the key passed via string. If it does
	 * it interupts/stops the thread
	 * 
	 * @param threadName
	 *            The key to be checked against threadpool
	 * @throws IllegalArgumentException
	 *             The exception if the key is not found
	 */
	public static void endThread(String threadName)
			throws IllegalArgumentException {
		if (threadPool.containsKey(threadName)) {
			threadPool.get(threadName).interrupt();
		} else {
			throw new IllegalArgumentException("Invalid thread name provided, "
					+ threadName + " was not found.");
		}
	}

	/**
	 * Checks for threads that are still in the pool, but not being used, and
	 * removes them
	 * 
	 * @param threadName
	 *            the key to be checked against threadpool
	 */
	public static void untrackThread(String threadName) {
		if (threadPool.containsKey(threadName)) {
			threadPool.remove(threadName);
		} else {
			throw new IllegalArgumentException("Invalid thread name provided, "
					+ threadName + " was not found.");
		}
	}

	/**
	 * Interrupts/stops all threads that currently exist in threadpool
	 */
	// TODO: Craig: May want to check if they are active first? Will it cause an error
	// if you try and interrupt a thread that is already interrupted?
	public static void stopAll() {
		for (Thread th : threadPool.values()) {
			th.interrupt();
		}
	}
}
