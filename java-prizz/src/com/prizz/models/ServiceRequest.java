package com.prizz.models;

import com.prizz.enums.RequestStatus;
import java.util.Date;

/**
 * [MODELS] Service Request Entity.
 * Purpose: Represents a job posting with budget, status, and description.
 * Syllabus: Encapsulation, State modeling with Enums, Wrapper classes.
 */
public class ServiceRequest {
    private Integer requestId;
    private Integer customerId;
    private String title;
    private String description;
    private Double budget;
    private RequestStatus status;
    private Date createdAt;

    public ServiceRequest(Integer requestId, Integer customerId, String title, String description, Double budget) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.status = RequestStatus.OPEN;
        this.createdAt = new Date();
    }

    public Integer getRequestId() { return requestId; }
    public void setRequestId(Integer requestId) { this.requestId = requestId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("Request #%d: '%s' | Budget: $%.2f | Status: %s",
                requestId, title, budget, status);
    }
}

