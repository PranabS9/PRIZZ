package com.prizz.enums;

/**
 * [ENUMS] Request Lifecycle Status Enumeration.
 * Purpose: Defines all valid stages of a service request.
 * Syllabus: Java Enum types, state management.
 */
public enum RequestStatus {
    DRAFT,
    OPEN,
    BIDDING_ACTIVE,
    ACCEPTED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

