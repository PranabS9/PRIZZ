package com.prizz.fileio;

import com.prizz.models.Bid;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * [FILE I/O] CSV Import & Export File Manager.
 * Purpose: Persists and reads back market bid records from flat text/CSV files on disk.
 * Syllabus: File Handling using try-with-resources with BufferedReader, BufferedWriter, FileReader, and FileWriter.
 */
public class CsvFileHandler {

    public static void exportBidsToCsv(String filePath, List<Bid> bids) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("bidId,requestId,providerId,bidAmount,proposalMessage");
            writer.newLine();

            for (Bid bid : bids) {
                String sanitizedMsg = bid.getProposalMessage().replace("\"", "\"\"");
                String line = String.format("%d,%d,%d,%.2f,\"%s\"",
                        bid.getBidId(),
                        bid.getRequestId(),
                        bid.getProviderId(),
                        bid.getBidAmount(),
                        sanitizedMsg);
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static List<String> readCsvRawLines(String filePath) throws IOException {
        List<String> records = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return records;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip CSV column headers
                    continue;
                }
                if (!line.trim().isEmpty()) {
                    records.add(line);
                }
            }
        }
        return records;
    }
}

