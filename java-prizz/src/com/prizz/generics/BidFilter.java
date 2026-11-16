package com.prizz.generics;

import com.prizz.models.Bid;

/**
 * [GENERICS & FUNCTIONAL] Bid Filter Functional Interface.
 * Purpose: Allows passing dynamic lambda expressions to filter bids against budget criteria.
 * Syllabus: Functional Interfaces (@FunctionalInterface), Lambda expressions.
 */
@FunctionalInterface
public interface BidFilter {
    /**
     * Evaluates whether a bid meets criteria against a ceiling budget.
     *
     * @param bid       The candidate bid
     * @param maxBudget Maximum budget constraint
     * @return true if candidate qualifies, false otherwise
     */
    boolean evaluate(Bid bid, double maxBudget);
}

