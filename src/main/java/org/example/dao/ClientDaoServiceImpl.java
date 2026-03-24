package org.example.dao;

import org.example.Client;
import org.example.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoServiceImpl implements ClientDaoService {

    private Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    @Override
    public long create(String name) {

        String sql = "INSERT INTO client (name) VALUES (?)";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, name);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                    throw new RuntimeException("Cannot get ID");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getById(long id) {

        String sql = "SELECT name FROM client WHERE id = ?";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("name");
                    }
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setName(long id, String name) {

        String sql = "UPDATE client SET name = ? WHERE id = ?";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setLong(2, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {

        String sql = "DELETE FROM client WHERE id = ?";

        try {
            Connection conn = getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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
}