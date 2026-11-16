package multithreading;

import models.Bid;

import java.util.LinkedList;
import java.util.Queue;

/**
 * [MULTITHREADING] Synchronized Circular Bid Buffer.
 * Purpose: Safe shared resource between concurrent producers and consumers.
 * Syllabus: Synchronization, Inter-thread Communication (wait() / notifyAll()), race condition prevention.
 */
public class BidBuffer {
    private final Queue<Bid> buffer = new LinkedList<>();
    private final int capacity;

    public BidBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produceBid(Bid bid) throws InterruptedException {
        while (buffer.size() == capacity) {
            wait(); // Sleep while buffer is full
        }
        buffer.offer(bid);
        System.out.println("[PRODUCER] Put Bid #" + bid.getBidId() 
                + " ($" + bid.getBidAmount() + ") into buffer. (Current Buffer Size: " + buffer.size() + ")");
        notifyAll(); // Notify waiting consumers
    }

    public synchronized Bid consumeBid() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait(); // Sleep while buffer is empty
        }
        Bid bid = buffer.poll();
        System.out.println("[CONSUMER] Took Bid #" + bid.getBidId() 
                + " by Thread [" + Thread.currentThread().getName() + "]. (Remaining: " + buffer.size() + ")");
        notifyAll(); // Notify waiting producers
        return bid;
    }

    public synchronized int getQueueSize() {
        return buffer.size();
    }
}

