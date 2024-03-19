package ru.alexanna.oikserver.services;

import ru.alexanna.oikserver.database.ServerStorage;
import ru.alexanna.oikserver.entities.Port;

import java.util.Set;

public class DatabaseService {
    private final ServerStorage serverStorage = new ServerStorage();
    public Set<Port> findAllPorts() {
        return serverStorage.findAllPorts();
    }
}
