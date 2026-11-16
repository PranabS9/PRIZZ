package streams;

import generics.BidFilter;
import models.Bid;

import java.util.*;
import java.util.stream.Collectors;

/**
 * [STREAMS] Business Intelligence & Stream Analytics Engine.
 * Purpose: Analyzes pricing, volume, and statistics across in-memory collections.
 * Syllabus: Stream API (filter, map, reduce, Collectors.groupingBy, Collectors.averagingDouble, summaryStatistics).
 */
public class AnalyticsEngine {

    // 1. Filtering using custom Functional Interface and Lambda
    public List<Bid> filterBidsUnderCeiling(List<Bid> bids, double maxBudget, BidFilter filter) {
        return bids.stream()
                .filter(b -> filter.evaluate(b, maxBudget))
                .collect(Collectors.toList());
    }

    // 2. Stream Reduction: Calculate total monetary volume of all bids
    public double calculateTotalBiddedVolume(List<Bid> bids) {
        return bids.stream()
                .map(Bid::getBidAmount)
                .reduce(0.0, Double::sum);
    }

    // 3. Complex Collector: Group bids by Request ID and calculate average bid per request
    public Map<Integer, Double> calculateAverageBidPerRequest(List<Bid> bids) {
        return bids.stream()
                .collect(Collectors.groupingBy(
                        Bid::getRequestId,
                        Collectors.averagingDouble(Bid::getBidAmount)
                ));
    }

    // 4. Numerical Reductions & Summary Statistics (Min, Max, Avg, Count)
    public DoubleSummaryStatistics getBidStatistics(List<Bid> bids) {
        return bids.stream()
                .mapToDouble(Bid::getBidAmount)
                .summaryStatistics();
    }

    // 5. Stream mapping, distinct filtering, sorting, and collecting
    public List<String> extractUniqueProposals(List<Bid> bids) {
        return bids.stream()
                .map(Bid::getProposalMessage)
                .filter(msg -> msg != null && !msg.trim().isEmpty())
                .distinct()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }
}

