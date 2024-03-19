package ru.alexanna.oikserver.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.Properties;

@SuppressWarnings("unused")
public final class DatabaseConnection {
    public static Connection getConnection(String url, Properties properties) {
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLInvalidAuthorizationSpecException e) {
            throw new RuntimeException(e.getMessage());
        }
        catch (SQLException e) {
            throw new RuntimeException("Something went wrong during getting a connection.", e);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong during closing a connection.", e);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong during a rollback operation.", e);
        }
    }
}
