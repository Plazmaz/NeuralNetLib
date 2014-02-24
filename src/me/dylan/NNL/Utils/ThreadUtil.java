package me.dylan.NNL.Utils;

import java.util.HashMap;

public class ThreadUtil {
    private static HashMap<String, Thread> threadPool = new HashMap<String, Thread>();

    public static void spinThreadForPool(String name, Runnable threadRun) {
	Thread spun = new Thread(threadRun);
	spun.start();
	threadPool.put(name, spun);
    }

    public static void endThread(String threadName)
	    throws IllegalArgumentException {
	if (threadPool.containsKey(threadName)) {
	    threadPool.get(threadName).interrupt();
	} else {
	    throw new IllegalArgumentException("Invalid thread name provided, "
		    + threadName + " was not found.");
	}
    }

    public static void untrackThread(String threadName) {
	if (threadPool.containsKey(threadName)) {
	    threadPool.remove(threadName);
	} else {
	    throw new IllegalArgumentException("Invalid thread name provided, "
		    + threadName + " was not found.");
	}
    }

    public static void stopAll() {
	for (Thread th : threadPool.values()) {
	    th.interrupt();
	}
    }
}
