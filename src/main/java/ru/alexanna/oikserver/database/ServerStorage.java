package ru.alexanna.oikserver.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexanna.oikserver.entities.CheckPoint;
import ru.alexanna.oikserver.entities.Location;
import ru.alexanna.oikserver.entities.Port;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ServerStorage {
    private final Properties properties;
    private static final Logger log = LoggerFactory.getLogger(ServerStorage.class);

    public ServerStorage() {
        properties = getConnectionProperties();
    }

    private Properties getConnectionProperties() {
        Properties properties = new Properties();
//        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();

        try (InputStream in = Files.newInputStream(Paths.get("src/main/resources/database.properties"))) {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка инициализации базы данных! Не найден файл: " + e.getMessage());
        }
        return properties;
    }

    private Connection getConnection() {
        return DatabaseConnection.getConnection(properties.getProperty("jdbc.driver"), properties);
    }

    public Set<Port> findAllPorts() {
        HashSet<Port> ports = new HashSet<>();
        Connection connection = getConnection();
        String query = "SELECT id, name, baud_rate, parity, ktms, received_data FROM ports ORDER BY name";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Port port = new Port();
                port.setId(resultSet.getInt("id"));
                port.setName(resultSet.getString("name"));
                port.setBaudRate(resultSet.getInt("baud_rate"));
                port.setParity(resultSet.getBoolean("parity"));
                port.setKtms(resultSet.getString("ktms"));
                port.setReceivedData(resultSet.getString("received_data"));
                port.setCheckPoints(findCheckPointsBy(port.getId()));
                ports.add(port);
            }
        } catch (SQLException e) {
            log.error("findAllPorts {}", e.getMessage());
        } finally {
            DatabaseConnection.close(connection);
        }
        return ports;
    }

    public List<CheckPoint> findCheckPointsBy(int portId) {
        List<CheckPoint> checkPoints = new ArrayList<>();
        Connection connection = getConnection();
        String query = "SELECT id, name, address, location_id FROM check_points " +
                "WHERE port_id = ? ORDER BY address";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, portId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                CheckPoint checkPoint = new CheckPoint();
                checkPoint.setId(resultSet.getInt("id"));
                checkPoint.setName(resultSet.getString("name"));
                checkPoint.setAddress(resultSet.getInt("address"));
                checkPoint.setLocation(findLocationBy(resultSet.getInt("location_id")));
                checkPoint.setPort(portId);
                checkPoints.add(checkPoint);
            }
        } catch (SQLException e) {
            log.error("findCheckPointsByPortId {}", e.getMessage());
        } finally {
            DatabaseConnection.close(connection);
        }
        return checkPoints;
    }

    public Location findLocationBy(int id) {
        Location location = new Location();
        Connection connection = getConnection();
        String query = "SELECT id, name, tech_object_id FROM locations " +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                location.setId(resultSet.getInt("id"));
                location.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            log.error("findLocationById {}", e.getMessage());
        } finally {
            DatabaseConnection.close(connection);
        }
        return location;
    }

}
