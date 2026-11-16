package multithreading;

import models.Bid;
import java.util.Date;

/**
 * [MULTITHREADING] Concurrent Bid Generator.
 * Purpose: Simulates multiple independent service providers submitting bids concurrently.
 * Syllabus: Runnable interface, Thread lifecycle (sleep(), interrupt handling).
 */
public class BidProducer implements Runnable {
    private final BidBuffer buffer;
    private final int requestId;
    private final int totalQuotesToGenerate;

    public BidProducer(BidBuffer buffer, int requestId, int totalQuotesToGenerate) {
        this.buffer = buffer;
        this.requestId = requestId;
        this.totalQuotesToGenerate = totalQuotesToGenerate;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= totalQuotesToGenerate; i++) {
                double price = 120.0 + (Math.random() * 180.0);
                Bid bid = new Bid(i, requestId, 200 + i, price, 
                        "Instant Dispatch Service Quote #" + i, new Date());

                buffer.produceBid(bid);
                Thread.sleep(80); // Deliberation delay
            }
        } catch (InterruptedException e) {
            System.err.println("[PRODUCER INTERRUPTED] Shutting down producer: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

