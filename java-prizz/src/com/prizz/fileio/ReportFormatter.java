package com.prizz.fileio;

import com.prizz.models.Bid;
import com.prizz.models.ServiceRequest;

import java.util.List;

/**
 * [FILE I/O & FORMATTING] Text and Report Formatter.
 * Purpose: Assembles multi-line ASCII receipts, tables, and audit logs.
 * Syllabus: String manipulation, StringBuilder buffer optimization.
 */
public class ReportFormatter {

    public static String buildRequestCard(ServiceRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================================\n");
        sb.append(String.format("  SERVICE REQUEST #%d: %s\n", request.getRequestId(), request.getTitle().toUpperCase()));
        sb.append("====================================================\n");
        sb.append(String.format("  Customer ID : %d\n", request.getCustomerId()));
        sb.append(String.format("  Base Budget : $%.2f\n", request.getBudget()));
        sb.append(String.format("  Status      : %s\n", request.getStatus()));
        sb.append(String.format("  Details     : %s\n", request.getDescription()));
        sb.append("----------------------------------------------------\n");
        return sb.toString();
    }

    public static String buildBidSummaryTable(List<Bid> bids) {
        StringBuilder sb = new StringBuilder();
        sb.append("+--------+------------+-------------+------------+--------------------------------------+\n");
        sb.append("| Bid ID | Request ID | Provider ID | Price ($)  | Proposal Description                 |\n");
        sb.append("+--------+------------+-------------+------------+--------------------------------------+\n");

        for (Bid b : bids) {
            String shortMsg = b.getProposalMessage();
            if (shortMsg.length() > 36) {
                shortMsg = shortMsg.substring(0, 33) + "...";
            }
            sb.append(String.format("| %-6d | %-10d | %-11d | %-10.2f | %-36s |\n",
                    b.getBidId(), b.getRequestId(), b.getProviderId(), b.getBidAmount(), shortMsg));
        }
        sb.append("+--------+------------+-------------+------------+--------------------------------------+\n");
        return sb.toString();
    }
}

