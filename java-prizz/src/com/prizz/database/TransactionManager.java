package com.prizz.database;

import com.prizz.exceptions.DatabaseOperationException;
import com.prizz.exceptions.InsufficientFundsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * [DATABASE] Atomic Transaction Manager.
 * Purpose: Coordinates multi-table escrow payment operations using commit and rollback.
 * Syllabus: JDBC Transaction Management (setAutoCommit(false), commit, rollback, finally cleanup).
 */
public class TransactionManager {
    private final DBConnectionManager connectionManager;

    public TransactionManager() {
        this.connectionManager = DBConnectionManager.getInstance();
    }

    public boolean executeBookingPayment(int bookingId, int customerId, int providerId, double amount)
            throws DatabaseOperationException, InsufficientFundsException {

        String sqlCheckBalance = "SELECT wallet_balance FROM users WHERE user_id = ?";
        String sqlDeductCustomer = "UPDATE users SET wallet_balance = wallet_balance - ? WHERE user_id = ?";
        String sqlCreditProvider = "UPDATE users SET wallet_balance = wallet_balance + ? WHERE user_id = ?";
        String sqlUpdateBooking = "UPDATE bookings SET payment_completed = true WHERE booking_id = ?";

        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psDeduct = null;
        PreparedStatement psCredit = null;
        PreparedStatement psBooking = null;
        ResultSet rs = null;

        try {
            conn = connectionManager.getConnection();
            // Step 1: Start atomic transaction
            conn.setAutoCommit(false);

            // Step 2: Validate customer balance
            psCheck = conn.prepareStatement(sqlCheckBalance);
            psCheck.setInt(1, customerId);
            rs = psCheck.executeQuery();

            if (!rs.next()) {
                throw new DatabaseOperationException("Customer not found with ID: " + customerId);
            }

            double currentBalance = rs.getDouble("wallet_balance");
            if (currentBalance < amount) {
                throw new InsufficientFundsException("Insufficient funds: Available $" + currentBalance 
                        + ", Required $" + amount);
            }

            // Step 3: Deduct from customer
            psDeduct = conn.prepareStatement(sqlDeductCustomer);
            psDeduct.setDouble(1, amount);
            psDeduct.setInt(2, customerId);
            psDeduct.executeUpdate();

            // Step 4: Credit to provider
            psCredit = conn.prepareStatement(sqlCreditProvider);
            psCredit.setDouble(1, amount);
            psCredit.setInt(2, providerId);
            psCredit.executeUpdate();

            // Step 5: Update booking payment state
            psBooking = conn.prepareStatement(sqlUpdateBooking);
            psBooking.setInt(1, bookingId);
            psBooking.executeUpdate();

            // Step 6: Atomic commit
            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Rollback failed: " + ex.getMessage());
                }
            }
            throw new DatabaseOperationException("Transaction failed and was rolled back: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (psCheck != null) psCheck.close();
                if (psDeduct != null) psDeduct.close();
                if (psCredit != null) psCredit.close();
                if (psBooking != null) psBooking.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing transaction resources: " + e.getMessage());
            }
        }
    }
}

