package com.prizz.exceptions;

/**
 * [EXCEPTIONS] Invalid Bid Exception.
 * Purpose: Thrown when a submitted bid violates domain constraints (e.g. negative price).
 * Syllabus: Custom Checked Exception, parameter validation.
 */
public class InvalidBidException extends PrizzAppException {
    public InvalidBidException(String message) {
        super("PRIZZ_ERR_INVALID_BID", message, null);
    }
}

