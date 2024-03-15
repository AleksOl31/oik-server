package ru.alexanna.lib.database;

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


    /*

        private Map<Integer, MonitoringData> safDBLookup() {
        Map<Integer, MonitoringData> monDataMap = new HashMap<>();
        Properties props = new Properties();
        props.setProperty("user", "SYSDBA");
        props.setProperty("password", "masterkey");
        props.setProperty("encoding", "WIN1251");

        Connection connection = DatabaseConnection.getConnection("jdbc:firebirdsql://10.70.0.150:3051/saf", props);
        System.out.println(connection + " " + new Date());
        String query = "SELECT id, locono, locotime, locodate, speed, latitude, longitude, " +
                "fuel_volume, fuel_dens, modify_dt, fuel_mass" +
                " FROM data_gps";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                MonitoringData monData = new MonitoringData();
                monData.setDate(new Date());
                monData.setLatitude(rs.getDouble("latitude"));
                monData.setLongitude(rs.getDouble("longitude"));
                monData.setSpeed(rs.getInt("speed") / 10);
                monData.setCourse(0);
                monData.setSats(10);
                monData.setFuelVolume(rs.getInt("fuel_volume"));
                monDataMap.put(rs.getInt("locono"), monData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(connection);
        }
        return monDataMap;
    }

     */
}
