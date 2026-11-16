package database;

import enums.RequestStatus;
import exceptions.DatabaseOperationException;
import generics.GenericDAO;
import models.ServiceRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * [DATABASE] ServiceRequest Data Access Object.
 * Purpose: Handles persistence and queries for customer service requests.
 * Syllabus: PreparedStatement CRUD, secure parameter binding, ResultSet mapping.
 */
public class RequestDAO implements GenericDAO<ServiceRequest, Integer> {
    private final DBConnectionManager connectionManager;

    public RequestDAO() {
        this.connectionManager = DBConnectionManager.getInstance();
    }

    @Override
    public void create(ServiceRequest req) throws DatabaseOperationException {
        String sql = "INSERT INTO service_requests (request_id, customer_id, title, description, budget, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, req.getRequestId());
            ps.setInt(2, req.getCustomerId());
            ps.setString(3, req.getTitle());
            ps.setString(4, req.getDescription());
            ps.setDouble(5, req.getBudget());
            ps.setString(6, req.getStatus().name());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to insert request #" + req.getRequestId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public ServiceRequest findById(Integer id) throws DatabaseOperationException {
        String sql = "SELECT request_id, customer_id, title, description, budget, status FROM service_requests WHERE request_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ServiceRequest req = new ServiceRequest(
                            rs.getInt("request_id"),
                            rs.getInt("customer_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDouble("budget")
                    );
                    req.setStatus(RequestStatus.valueOf(rs.getString("status")));
                    return req;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find request #" + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<ServiceRequest> findAll() throws DatabaseOperationException {
        String sql = "SELECT request_id, customer_id, title, description, budget, status FROM service_requests";
        List<ServiceRequest> list = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ServiceRequest req = new ServiceRequest(
                        rs.getInt("request_id"),
                        rs.getInt("customer_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("budget")
                );
                req.setStatus(RequestStatus.valueOf(rs.getString("status")));
                list.add(req);
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch all requests: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(ServiceRequest req) throws DatabaseOperationException {
        String sql = "UPDATE service_requests SET title = ?, description = ?, budget = ?, status = ? WHERE request_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, req.getTitle());
            ps.setString(2, req.getDescription());
            ps.setDouble(3, req.getBudget());
            ps.setString(4, req.getStatus().name());
            ps.setInt(5, req.getRequestId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update request #" + req.getRequestId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) throws DatabaseOperationException {
        String sql = "DELETE FROM service_requests WHERE request_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete request #" + id + ": " + e.getMessage(), e);
        }
    }
}

