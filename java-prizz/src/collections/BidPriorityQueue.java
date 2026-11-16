package collections;

import models.Bid;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * [COLLECTIONS] Priority Queue for Ranked Bids.
 * Purpose: Automatically ranks bids according to price priority.
 * Syllabus: Collections Framework (PriorityQueue), Iterator traversal, Comparator ordering.
 */
public class BidPriorityQueue {
    private final PriorityQueue<Bid> priorityQueue;

    public BidPriorityQueue() {
        this.priorityQueue = new PriorityQueue<>(BidComparators.BY_PRICE_ASC);
    }

    public void addBid(Bid bid) {
        if (bid != null) {
            priorityQueue.offer(bid);
        }
    }

    public Bid pollWinningBid() {
        return priorityQueue.poll();
    }

    public Bid peekWinningBid() {
        return priorityQueue.peek();
    }

    public int size() {
        return priorityQueue.size();
    }

    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    public void printAllRankedBids() {
        Iterator<Bid> iterator = priorityQueue.iterator();
        int rank = 1;
        while (iterator.hasNext()) {
            Bid b = iterator.next();
            System.out.println("  [Rank Candidate #" + (rank++) + "] " + b);
        }
    }
}

