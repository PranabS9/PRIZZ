package com.prizz.models;

/**
 * [MODELS] Customer Entity.
 * Purpose: Represents a customer who creates requests and accepts bids.
 * Syllabus: Inheritance (extends User), Polymorphism, Comparable interface.
 */
public class Customer extends User implements Comparable<Customer> {
    private Integer loyaltyPoints;

    public Customer(Integer userId, String name, String email, Double walletBalance, Integer loyaltyPoints) {
        super(userId, name, email, "CUSTOMER", walletBalance);
        this.loyaltyPoints = (loyaltyPoints != null) ? loyaltyPoints : 0;
    }

    public Integer getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(Integer loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    @Override
    public String getProfileSummary() {
        return String.format("Customer Profile: %s (ID: %d) | Balance: $%.2f | Loyalty Points: %d",
                getName(), getUserId(), getWalletBalance(), loyaltyPoints);
    }

    @Override
    public int compareTo(Customer other) {
        if (other == null) return 1;
        return this.getUserId().compareTo(other.getUserId());
    }
}

