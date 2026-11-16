package multithreading;

import models.Bid;
import java.util.List;

/**
 * [MULTITHREADING] Consumer Worker Thread.
 * Purpose: Polls and drains bids from the shared buffer into permanent memory storage.
 * Syllabus: Extending Thread class, thread join(), interruption handling.
 */
public class BidConsumer extends Thread {
    private final BidBuffer buffer;
    private final List<Bid> storageSink;

    public BidConsumer(BidBuffer buffer, List<Bid> storageSink) {
        super("BidConsumerWorker");
        this.buffer = buffer;
        this.storageSink = storageSink;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Bid bid = buffer.consumeBid();
                synchronized (storageSink) {
                    storageSink.add(bid);
                }
                Thread.sleep(60);
            }
        } catch (InterruptedException e) {
            System.out.println("[CONSUMER STOP] BidConsumer thread interrupted; finishing execution.");
            Thread.currentThread().interrupt();
        }
    }
}

