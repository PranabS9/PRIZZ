package com.prizz.app;

import com.prizz.arrays.PricingMatrix;
import com.prizz.collections.BidComparators;
import com.prizz.collections.BidPriorityQueue;
import com.prizz.collections.RequestDispatcherQueue;
import com.prizz.exceptions.InsufficientFundsException;
import com.prizz.exceptions.InvalidBidException;
import com.prizz.fileio.CsvFileHandler;
import com.prizz.models.Bid;
import com.prizz.models.Customer;
import com.prizz.models.ServiceRequest;
import com.prizz.multithreading.AsyncAuditService;
import com.prizz.multithreading.BidBuffer;
import com.prizz.multithreading.BidConsumer;
import com.prizz.multithreading.BidProducer;
import com.prizz.streams.AnalyticsEngine;

import java.io.IOException;
import java.util.*;

/**
 * [APP] Main Application Driver for PRIZZ.
 * Purpose: Interactive console UI that orchestrates and demonstrates all 30 syllabus components.
 * Syllabus: Integration, Console I/O, Iterator and for-each traversal, Top-level exception handling.
 */
public class PrizzConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Core Components
        PricingMatrix pricingMatrix = new PricingMatrix();
        AnalyticsEngine analyticsEngine = new AnalyticsEngine();
        BidPriorityQueue bidPriorityQueue = new BidPriorityQueue();
        RequestDispatcherQueue dispatcherQueue = new RequestDispatcherQueue();
        AsyncAuditService auditService = new AsyncAuditService(2);

        // Collections Framework: List, Set, Map
        List<Bid> liveBids = new ArrayList<>();
        Set<String> uniqueCategories = new HashSet<>();
        Map<Integer, Customer> customerRegistry = new HashMap<>();

        // Seed initial data
        Customer c1 = new Customer(101, "Alice Walker", "alice@prizz.io", 1500.0, 250);
        Customer c2 = new Customer(102, "Bob Vance", "bob@vance.com", 850.0, 110);
        customerRegistry.put(c1.getUserId(), c1);
        customerRegistry.put(c2.getUserId(), c2);

        uniqueCategories.add("Logistics & Transport");
        uniqueCategories.add("Heavy Equipment Rigging");
        uniqueCategories.add("Emergency Warehousing");

        // Seed initial request
        ServiceRequest req1 = new ServiceRequest(501, 101, "Warehouse Pallet Transport", 
                "Move 150 pallets from Dock A to Central Warehouse", 600.0);
        dispatcherQueue.enqueueStandardRequest(req1);

        System.out.println("===============================================================");
        System.out.println("       PRIZZ: ENTERPRISE ON-DEMAND LOGISTICS MARKETPLACE       ");
        System.out.println("===============================================================");

        boolean running = true;

        while (running) {
            System.out.println("\nSelect an Operation to Test Syllabus Concepts:");
            System.out.println("  1. Core OOP & Tariff Pricing Matrix (2D Primitive Arrays)");
            System.out.println("  2. Request Dispatcher (Queue / Deque - FIFO & LIFO Emergency)");
            System.out.println("  3. Multithreaded Reverse-Auction Simulation (wait/notify & join)");
            System.out.println("  4. PriorityQueue & Comparator Sorting (Collections Framework)");
            System.out.println("  5. Stream API Reductions & Functional Interface Analytics");
            System.out.println("  6. Export & Import Records via CSV (Try-With-Resources File I/O)");
            System.out.println("  7. Test Exception Propagation (Custom Checked Exceptions)");
            System.out.println("  8. Exit PRIZZ");
            System.out.print("Enter choice (1-8): ");

            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        System.out.println("\n--- [1] CORE OOP & MULTI-DIMENSIONAL PRICING MATRIX ---");
                        // Traversal using for-each on Map entries
                        for (Map.Entry<Integer, Customer> entry : customerRegistry.entrySet()) {
                            System.out.println("  " + entry.getValue().getProfileSummary());
                        }

                        // Display 2D primitive array
                        pricingMatrix.displayMatrix();
                        double base = 500.0;
                        double adjusted = pricingMatrix.calculateAdjustedBudget(base, 2, 0); // Emergency in Metro
                        System.out.printf("  Sample Calculation: Base $%.2f -> Emergency Tier in Metro Zone: $%.2f\n", 
                                base, adjusted);
                        auditService.submitAuditTask("PRICING_CALCULATION", "Base: " + base + ", Adjusted: " + adjusted);
                        break;

                    case 2:
                        System.out.println("\n--- [2] REQUEST DISPATCHER (QUEUE / DEQUE) ---");
                        ServiceRequest emergencyReq = new ServiceRequest(999, 102, "CRITICAL: Chemical Spill Hazmat Cleanup",
                                "Immediate hazmat response containment required", 3500.0);
                        
                        // LIFO emergency enqueue
                        dispatcherQueue.enqueueEmergencyRequest(emergencyReq);
                        System.out.println("  Dispatched emergency request to HEAD of Deque.");
                        System.out.println("  Pending requests in queue: " + dispatcherQueue.pendingCount());

                        // Dispatch next (should be emergency request)
                        ServiceRequest dispatched = dispatcherQueue.dispatchNext();
                        System.out.println("  Dispatched Next: " + dispatched);
                        break;

                    case 3:
                        System.out.println("\n--- [3] MULTITHREADED CONCURRENCY (PRODUCER-CONSUMER) ---");
                        BidBuffer buffer = new BidBuffer(3); // Small capacity triggers wait/notify
                        BidProducer producer = new BidProducer(buffer, 501, 4);
                        BidConsumer consumer = new BidConsumer(buffer, liveBids);

                        Thread producerThread = new Thread(producer, "Marketplace-Producer-Thread");

                        // Lifecycle management
                        consumer.start();
                        producerThread.start();

                        // Join guarantees completion
                        producerThread.join();
                        Thread.sleep(400); // Allow consumer buffer to drain
                        consumer.interrupt(); // Interrupt consumer thread loop
                        consumer.join();

                        System.out.println("  Multithreaded intake complete. Total Live Bids: " + liveBids.size());
                        auditService.submitAuditTask("CONCURRENT_BIDDING", "Total bids captured: " + liveBids.size());
                        break;

                    case 4:
                        System.out.println("\n--- [4] PRIORITYQUEUE & COMPARATOR SORTING ---");
                        if (liveBids.isEmpty()) {
                            liveBids.add(new Bid(1, 501, 201, 420.0, "Standard freight van", new Date()));
                            liveBids.add(new Bid(2, 501, 202, 310.0, "Refrigerated lift-gate truck", new Date()));
                            liveBids.add(new Bid(3, 501, 203, 375.0, "Heavy-duty lowboy trailer", new Date()));
                        }

                        // Populate PriorityQueue
                        for (Bid b : liveBids) {
                            bidPriorityQueue.addBid(b);
                        }

                        System.out.println("  Bids Ranked in PriorityQueue (Iterator Traversal):");
                        bidPriorityQueue.printAllRankedBids();

                        Bid winningBid = bidPriorityQueue.pollWinningBid();
                        System.out.println("\n  Winning Lowest Bid Polled: " + winningBid);

                        // Sorting with Comparator
                        System.out.println("\n  Sorting Live Bids by Price Descending (Comparator Lambda):");
                        liveBids.sort(BidComparators.BY_PRICE_DESC);
                        for (Bid b : liveBids) {
                            System.out.println("    " + b);
                        }
                        break;

                    case 5:
                        System.out.println("\n--- [5] ADVANCED STREAM API & FUNCTIONAL INTERFACES ---");
                        if (liveBids.isEmpty()) {
                            liveBids.add(new Bid(1, 501, 201, 250.0, "Box truck delivery", new Date()));
                            liveBids.add(new Bid(2, 501, 202, 350.0, "Flatbed delivery", new Date()));
                            liveBids.add(new Bid(3, 502, 203, 180.0, "Courier van express", new Date()));
                        }

                        // 1. Functional Interface Lambda Filter
                        double budgetCeiling = 300.0;
                        List<Bid> affordableBids = analyticsEngine.filterBidsUnderCeiling(
                                liveBids, budgetCeiling, (b, ceiling) -> b.getBidAmount() <= ceiling);
                        System.out.println("  Bids under $" + budgetCeiling + ": " + affordableBids);

                        // 2. Stream Reduction
                        double totalVolume = analyticsEngine.calculateTotalBiddedVolume(liveBids);
                        System.out.printf("  Total Market Volume (reduce): $%.2f\n", totalVolume);

                        // 3. Collectors.groupingBy & averagingDouble
                        Map<Integer, Double> avgByReq = analyticsEngine.calculateAverageBidPerRequest(liveBids);
                        System.out.println("  Average Bid Price Per Request (groupingBy): " + avgByReq);

                        // 4. Stream Summary Statistics
                        DoubleSummaryStatistics stats = analyticsEngine.getBidStatistics(liveBids);
                        System.out.printf("  Statistics: Count=%d, Min=$%.2f, Max=$%.2f, Avg=$%.2f\n",
                                stats.getCount(), stats.getMin(), stats.getMax(), stats.getAverage());
                        break;

                    case 6:
                        System.out.println("\n--- [6] FILE HANDLING VIA TRY-WITH-RESOURCES ---");
                        String csvFile = "prizz_bids_export.csv";
                        CsvFileHandler.exportBidsToCsv(csvFile, liveBids);
                        System.out.println("  Exported " + liveBids.size() + " records to " + csvFile);

                        List<String> lines = CsvFileHandler.readCsvRawLines(csvFile);
                        System.out.println("  Read Back " + lines.size() + " data rows from disk:");
                        Iterator<String> lineIterator = lines.iterator();
                        while (lineIterator.hasNext()) {
                            System.out.println("    " + lineIterator.next());
                        }
                        break;

                    case 7:
                        System.out.println("\n--- [7] EXCEPTION HANDLING & PROPAGATION ---");
                        try {
                            validateAndProcessBid(new Bid(99, 501, 999, -50.0, "Negative price test", new Date()));
                        } catch (InvalidBidException ex) {
                            System.err.println("  Caught expected Custom Exception: " + ex);
                        }

                        try {
                            simulateWalletDeduction(c2, 5000.0);
                        } catch (InsufficientFundsException ex) {
                            System.err.println("  Caught expected Custom Exception: " + ex);
                        }
                        break;

                    case 8:
                        System.out.println("Shutting down PRIZZ Engine...");
                        auditService.shutdown();
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid selection. Please choose an option between 1 and 8.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Input Error: Please enter a valid numerical choice.");
            } catch (InterruptedException e) {
                System.err.println("Concurrency Interruption: " + e.getMessage());
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                System.err.println("File I/O Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected Exception Caught: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void validateAndProcessBid(Bid bid) throws InvalidBidException {
        if (bid.getBidAmount() <= 0) {
            throw new InvalidBidException("Bid amount must be positive. Provided: $" + bid.getBidAmount());
        }
    }

    private static void simulateWalletDeduction(Customer customer, double amount) throws InsufficientFundsException {
        if (customer.getWalletBalance() < amount) {
            throw new InsufficientFundsException(String.format(
                    "Customer #%d wallet balance ($%.2f) cannot cover payout ($%.2f).",
                    customer.getUserId(), customer.getWalletBalance(), amount));
        }
    }
}

