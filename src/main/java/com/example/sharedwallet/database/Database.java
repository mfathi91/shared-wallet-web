package com.example.sharedwallet.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Connection connect() {
        final String url = "jdbc:sqlite:" + System.getenv("DB_PATH");
        final Connection conn;
        try {
            conn = DriverManager.getConnection(url);
        } catch (final SQLException e) {
            throw new IllegalStateException(String.format("Unable to connect to the database. Reason: %s", e));
        }
        return conn;
    }

    public static List<User> getUsers() {

        final List<User> users = new ArrayList<>();
        try (final Connection conn = connect();
             final Statement stmt = conn.createStatement()) {
            final ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new User(rs.getString("name")));
            }
        } catch (final SQLException e) {
            throw new IllegalStateException(String.format("Unable to read from the database. Reason: %s", e));
        }
        return users;
    }

    public static List<Payment> getPayments() {

        final List<Payment> payments = new ArrayList<>();
        try (final Connection conn = connect();
             final Statement stmt = conn.createStatement()) {
            final ResultSet rs = stmt.executeQuery("SELECT * FROM payments");
            while (rs.next()) {
                payments.add(new Payment(rs.getInt("amount"), rs.getString("note"), rs.getString("dt")));
            }
        } catch (final SQLException e) {
            throw new IllegalStateException(String.format("Unable to read from the database. Reason: %s", e));
        }
        return payments;
    }
}
