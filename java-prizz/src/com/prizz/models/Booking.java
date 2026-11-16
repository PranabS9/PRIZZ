package com.prizz.models;

import java.util.Date;

/**
 * [MODELS] Booking Entity.
 * Purpose: Represents an active or completed contract when a bid is accepted.
 * Syllabus: Domain relational modeling, entity relationships.
 */
public class Booking {
    private Integer bookingId;
    private Integer requestId;
    private Integer customerId;
    private Integer providerId;
    private Double finalAmount;
    private Boolean paymentCompleted;
    private Date bookedAt;

    public Booking(Integer bookingId, Integer requestId, Integer customerId, Integer providerId, Double finalAmount) {
        this.bookingId = bookingId;
        this.requestId = requestId;
        this.customerId = customerId;
        this.providerId = providerId;
        this.finalAmount = (finalAmount != null) ? finalAmount : 0.0;
        this.paymentCompleted = false;
        this.bookedAt = new Date();
    }

    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

    public Integer getRequestId() { return requestId; }
    public void setRequestId(Integer requestId) { this.requestId = requestId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Integer getProviderId() { return providerId; }
    public void setProviderId(Integer providerId) { this.providerId = providerId; }

    public Double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(Double finalAmount) { this.finalAmount = finalAmount; }

    public Boolean getPaymentCompleted() { return paymentCompleted; }
    public void setPaymentCompleted(Boolean paymentCompleted) { this.paymentCompleted = paymentCompleted; }

    public Date getBookedAt() { return bookedAt; }
    public void setBookedAt(Date bookedAt) { this.bookedAt = bookedAt; }

    @Override
    public String toString() {
        return String.format("Booking #%d [Req #%d, Customer #%d, Provider #%d] -> $%.2f (Paid: %s)",
                bookingId, requestId, customerId, providerId, finalAmount, paymentCompleted);
    }
}

