package com.prizz.database;

import com.prizz.exceptions.DatabaseOperationException;
import com.prizz.generics.GenericDAO;
import com.prizz.models.Customer;
import com.prizz.models.ServiceProvider;
import com.prizz.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * [DATABASE] User Data Access Object.
 * Purpose: Handles CRUD persistence for Customer and ServiceProvider entities.
 * Syllabus: PreparedStatement with parameter binding (?), ResultSet object mapping, GenericDAO implementation.
 */
public class UserDAO implements GenericDAO<User, Integer> {
    private final DBConnectionManager connectionManager;

    public UserDAO() {
        this.connectionManager = DBConnectionManager.getInstance();
    }

    @Override
    public void create(User user) throws DatabaseOperationException {
        String sql = "INSERT INTO users (user_id, name, email, role, wallet_balance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole());
            ps.setDouble(5, user.getWalletBalance());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to insert user #" + user.getUserId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public User findById(Integer id) throws DatabaseOperationException {
        String sql = "SELECT user_id, name, email, role, wallet_balance, rating, loyalty_points FROM users WHERE user_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to query user #" + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() throws DatabaseOperationException {
        String sql = "SELECT user_id, name, email, role, wallet_balance, rating, loyalty_points FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to query all users: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) throws DatabaseOperationException {
        String sql = "UPDATE users SET name = ?, email = ?, wallet_balance = ? WHERE user_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setDouble(3, user.getWalletBalance());
            ps.setInt(4, user.getUserId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update user #" + user.getUserId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) throws DatabaseOperationException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete user #" + id + ": " + e.getMessage(), e);
        }
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String role = rs.getString("role");
        double balance = rs.getDouble("wallet_balance");

        if ("CUSTOMER".equalsIgnoreCase(role)) {
            int points = rs.getInt("loyalty_points");
            return new Customer(id, name, email, balance, points);
        } else {
            double rating = rs.getDouble("rating");
            return new ServiceProvider(id, name, email, balance, rating);
        }
    }
}

