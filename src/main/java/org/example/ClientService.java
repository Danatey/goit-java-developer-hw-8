package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;

    private Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    public long create(String name) {
        validateName(name);

        String sql = "INSERT INTO client (name) VALUES (?)";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, name);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new RuntimeException("Cannot get generated id");
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getById(long id) {
        validateId(id);

        String sql = "SELECT name FROM client WHERE id = ?";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("name");
                    } else {
                        throw new RuntimeException("Client not found, id=" + id);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(long id, String name) {
        validateId(id);
        validateName(name);

        String sql = "UPDATE client SET name = ? WHERE id = ?";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setLong(2, id);

                int updated = ps.executeUpdate();

                if (updated == 0) {
                    throw new RuntimeException("Client not found, id=" + id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(long id) {
        validateId(id);

        String sql = "DELETE FROM client WHERE id = ?";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);

                int deleted = ps.executeUpdate();

                if (deleted == 0) {
                    throw new RuntimeException("Client not found, id=" + id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> listAll() {

        List<Client> result = new ArrayList<>();

        String sql = "SELECT id, name FROM client";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    result.add(new Client(
                            rs.getLong("id"),
                            rs.getString("name")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private void validateName(String name) {
        if (name == null) {
            throw new RuntimeException("Name cannot be null");
        }

        String trimmed = name.trim();

        if (trimmed.length() < MIN_NAME_LENGTH || trimmed.length() > MAX_NAME_LENGTH) {
            throw new RuntimeException("Name length must be between "
                    + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH);
        }
    }

    private void validateId(long id) {
        if (id <= 0) {
            throw new RuntimeException("Invalid id: " + id);
        }
    }
}