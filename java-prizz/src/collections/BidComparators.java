package collections;

import models.Bid;
import java.util.Comparator;

/**
 * [COLLECTIONS] Reusable Comparators for Bid Entities.
 * Purpose: Provides multiple sorting strategies for market bids.
 * Syllabus: Comparator interface, Lambda-based sorting, method references.
 */
public class BidComparators {
    // 1. Lowest price first (standard reverse auction ranking)
    public static final Comparator<Bid> BY_PRICE_ASC =
            Comparator.comparingDouble(Bid::getBidAmount);

    // 2. Highest price first
    public static final Comparator<Bid> BY_PRICE_DESC =
            (b1, b2) -> Double.compare(b2.getBidAmount(), b1.getBidAmount());

    // 3. Most recently submitted first
    public static final Comparator<Bid> BY_TIMESTAMP_DESC =
            Comparator.comparing(Bid::getSubmittedAt).reversed();

    // 4. Provider ID ascending
    public static final Comparator<Bid> BY_PROVIDER_ID =
            Comparator.comparingInt(Bid::getProviderId);
}

