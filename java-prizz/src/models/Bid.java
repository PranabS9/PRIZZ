package models;

import java.util.Date;

/**
 * [MODELS] Bid Entity.
 * Purpose: Represents a price proposal submitted by a service provider.
 * Syllabus: Encapsulation, Comparable interface (natural order by price ascending).
 */
public class Bid implements Comparable<Bid> {
    private Integer bidId;
    private Integer requestId;
    private Integer providerId;
    private Double bidAmount;
    private String proposalMessage;
    private Date submittedAt;

    public Bid(Integer bidId, Integer requestId, Integer providerId, Double bidAmount, String proposalMessage, Date submittedAt) {
        this.bidId = bidId;
        this.requestId = requestId;
        this.providerId = providerId;
        this.bidAmount = (bidAmount != null) ? bidAmount : 0.0;
        this.proposalMessage = proposalMessage;
        this.submittedAt = (submittedAt != null) ? submittedAt : new Date();
    }

    public Integer getBidId() { return bidId; }
    public void setBidId(Integer bidId) { this.bidId = bidId; }

    public Integer getRequestId() { return requestId; }
    public void setRequestId(Integer requestId) { this.requestId = requestId; }

    public Integer getProviderId() { return providerId; }
    public void setProviderId(Integer providerId) { this.providerId = providerId; }

    public Double getBidAmount() { return bidAmount; }
    public void setBidAmount(Double bidAmount) { this.bidAmount = bidAmount; }

    public String getProposalMessage() { return proposalMessage; }
    public void setProposalMessage(String proposalMessage) { this.proposalMessage = proposalMessage; }

    public Date getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Date submittedAt) { this.submittedAt = submittedAt; }

    // Natural ordering: Lowest price takes highest priority in reverse auction
    @Override
    public int compareTo(Bid other) {
        if (other == null) return -1;
        return Double.compare(this.bidAmount, other.bidAmount);
    }

    @Override
    public String toString() {
        return String.format("Bid #%d [Req #%d, Provider #%d] -> $%.2f (\"%s\")",
                bidId, requestId, providerId, bidAmount, proposalMessage);
    }
}

