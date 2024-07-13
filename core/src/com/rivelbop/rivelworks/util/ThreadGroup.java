package com.rivelbop.rivelworks.util;

import com.esotericsoftware.minlog.Log;

/**
 * Used to handle multiple threads.
 *
 * @author David Jerzak (RivelBop)
 */
public class ThreadGroup {
    private static final String LOG_TAG = ThreadGroup.class.getSimpleName();

    /**
     * Stores all threads to handle.
     */
    public final Thread[] THREADS;

    /**
     * Creates a thread group with the specified thread count.
     *
     * @param count The number of threads.
     */
    public ThreadGroup(int count) {
        this.THREADS = new Thread[count];
    }

    /**
     * Creates a thread with the provided runnable at the provided index (does not start the thread).
     *
     * @param index    The index to set the thread to.
     * @param runnable The action for the thread to perform.
     * @throws InterruptedException Called if the thread to replace is unable to be joined.
     */
    public void set(int index, Runnable runnable) throws InterruptedException {
        set(index, new Thread(runnable));
    }

    /**
     * Sets a thread to the provided index (does not start the thread).
     *
     * @param index     The index to set the thread to.
     * @param newThread The new thread to set.
     * @throws InterruptedException Called if the thread to replace is unable to be joined.
     */
    public void set(int index, Thread newThread) throws InterruptedException {
        if (indexOutOfBounds(index)) {
            return;
        }

        Thread thread = THREADS[index];
        if (thread != null) {
            thread.join();
        }
        THREADS[index] = newThread;
    }

    /**
     * Starts the indexed thread.
     *
     * @param index The index of the thread to start.
     */
    public void start(int index) {
        if (indexOutOfBounds(index)) {
            return;
        }

        Thread thread = THREADS[index];
        if (thread != null && !thread.isAlive()) {
            thread.start();
            return;
        }
        Log.error(LOG_TAG, "Thread cannot be started at index {" + index + "}!");
    }

    /**
     * Starts all stored threads.
     */
    public void startAll() {
        for (Thread t : THREADS) {
            if (t != null && !t.isAlive()) {
                t.start();
            }
        }
    }

    /**
     * Loops through all threads and checks for available thread, if not found, it will wait for an opening in thread[0]
     * to execute the runnable.
     *
     * @param runnable The action for the thread to perform.
     * @throws InterruptedException The first thread is unable to join.
     */
    public void run(Runnable runnable) throws InterruptedException {
        run(new Thread(runnable));
    }

    /**
     * Loops through all threads and checks for available thread, if not found, it will wait for an opening in thread[0]
     * to execute the runnable.
     *
     * @param newThread The thread to insert and start.
     * @throws InterruptedException The first thread is unable to join.
     */
    public void run(Thread newThread) throws InterruptedException {
        for (int i = 0; i < THREADS.length; i++) {
            Thread thread = THREADS[i];
            if (thread == null || !thread.isAlive()) {
                THREADS[i] = newThread;
                THREADS[i].start();
                return;
            }
        }

        THREADS[0].join();
        THREADS[0] = newThread;
        THREADS[0].start();
    }

    /**
     * Joins the indexed thread.
     *
     * @param index The index of the thread to join.
     * @throws InterruptedException The indexed thread is unable to be joined.
     */
    public void join(int index) throws InterruptedException {
        if (indexOutOfBounds(index)) {
            return;
        }

        Thread thread = THREADS[index];
        if (thread != null) {
            thread.join();
            return;
        }
        Log.error(LOG_TAG, "Thread at index {" + index + "} is null!");
    }

    /**
     * Joins all stored threads.
     *
     * @throws InterruptedException A thread is unable to be joined.
     */
    public void joinAll() throws InterruptedException {
        for (Thread t : THREADS) {
            if (t != null) {
                t.join();
            }
        }
    }

    /**
     * Checks to see if the indexed thread is active.
     *
     * @param index The index of the thread to check.
     * @return Whether the thread is active.
     */
    public boolean isActive(int index) {
        if (indexOutOfBounds(index)) {
            return false;
        }

        Thread thread = THREADS[index];
        return thread != null && thread.isAlive();
    }

    /**
     * Checks to see if any of the threads are active.
     *
     * @return Whether any of the threads are active.
     */
    public boolean isActive() {
        for (Thread t : THREADS) {
            if (t != null && t.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the provided index is out of bounds.
     *
     * @param index The index to check.
     * @return Whether the index is out of bounds.
     */
    private boolean indexOutOfBounds(int index) {
        if (index < 0 || index >= THREADS.length) {
            Log.error(LOG_TAG, "Index {" + index + "} is out of range!");
            return true;
        }
        return false;
    }
}