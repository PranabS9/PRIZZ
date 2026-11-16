package com.prizz.arrays;

/**
 * [ARRAYS] Dynamic Pricing & Tariff Calculator.
 * Purpose: Computes surge tariffs based on urgency tier and geographic zones.
 * Syllabus: Multi-dimensional primitive arrays (double[][]).
 */
public class PricingMatrix {
    // Rows: Urgency Tier (0: Standard, 1: Urgent, 2: Emergency 24/7)
    // Columns: Regional Zone (0: Metro Central, 1: Suburban Outer, 2: Remote Industrial)
    private final double[][] rateMultipliers;

    public PricingMatrix() {
        this.rateMultipliers = new double[][] {
            { 1.00, 1.15, 1.30 }, // Standard Tier
            { 1.25, 1.40, 1.65 }, // Urgent Tier
            { 1.75, 1.95, 2.25 }  // Emergency Tier
        };
    }

    public double calculateAdjustedBudget(double baseBudget, int urgencyTier, int zoneIndex) {
        if (urgencyTier < 0 || urgencyTier >= rateMultipliers.length) {
            throw new IllegalArgumentException("Invalid urgency tier: " + urgencyTier);
        }
        if (zoneIndex < 0 || zoneIndex >= rateMultipliers[0].length) {
            throw new IllegalArgumentException("Invalid zone index: " + zoneIndex);
        }
        return baseBudget * rateMultipliers[urgencyTier][zoneIndex];
    }

    public double[][] getRateMultipliers() {
        return rateMultipliers;
    }

    public void displayMatrix() {
        System.out.println("\n--- Dynamic Tariff & Surge Multiplier Matrix (2D Array) ---");
        System.out.println("Tier \\ Zone        Metro       Suburban      Remote");
        String[] tierLabels = { "0 (Standard)", "1 (Urgent)  ", "2 (Emergency)" };
        for (int r = 0; r < rateMultipliers.length; r++) {
            System.out.printf("%s:   ", tierLabels[r]);
            for (int c = 0; c < rateMultipliers[r].length; c++) {
                System.out.printf("  %6.2fx    ", rateMultipliers[r][c]);
            }
            System.out.println();
        }
    }
}

