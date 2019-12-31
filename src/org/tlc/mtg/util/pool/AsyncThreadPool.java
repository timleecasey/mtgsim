package org.tlc.mtg.util.pool;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Fire and forget thread runner, runs one at a time, calls join in order.
 * Does not care about the number of concurrent threads, buyer beware.
 */
public class AsyncThreadPool {

    public static AsyncThreadPool POOL = new AsyncThreadPool();

    private List<Thread> cleaner = Collections.synchronizedList(new LinkedList<Thread>());
    private Joiner joiner = new Joiner(cleaner);

    AsyncThreadPool() {
        Thread t = new Thread(joiner, "JOINER");
        t.setDaemon(true);
        t.start();
    }

    /**
     * Add a runnable by creating a thread, starting and adding to join list.
     * @param r runnable to wait on.
     */
    public void start(Runnable r) {
        Thread t = new Thread(r);
        t.start();
        cleaner.add(t);
    }

    /**
     * Starts thread and adds to join list.
     * @param t thread to run
     */
    public void start(Thread t) {
        t.start();
        cleaner.add(t);
    }

    private static class Joiner implements Runnable {

        List<Thread> running;

        private Joiner(List<Thread> running) {
            this.running = running;
        }

        @Override
        public void run() {
            //noinspection InfiniteLoopStatement
            while( true ) {

                //
                // If I have something to join, join.  Else wait a bit to cycle around and recheck.
                //
                if( running.size() > 0 ) {
                    Thread t = running.get(0);
                    try {
                        t.join();
                        running.remove(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
