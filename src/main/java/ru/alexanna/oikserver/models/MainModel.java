package ru.alexanna.oikserver.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.alexanna.oikserver.entities.Port;
import ru.alexanna.oikserver.services.DatabaseService;
import ru.alexanna.oikserver.services.ReceptionService;

import java.util.Set;


public class MainModel {
    protected final ReceptionService receptionService = new ReceptionService();
    protected final DatabaseService databaseService = new DatabaseService();

    protected final ListProperty<Port> ports = new SimpleListProperty<>();

    public void initialize() {
        setPorts(databaseService.findAllPorts());
    }

    public ListProperty<Port> getPortsProperty() {
        return ports;
    }

    public void setPorts(Set<Port> ports) {
        ObservableList<Port> observableList = FXCollections.observableArrayList(ports);
        this.ports.setValue(observableList);
    }

    public ObservableList<Port> getPorts() {
        return ports.getValue();
    }

    public void startAllReceivers() {
        receptionService.startAllReceivers();
    }

    public void stopAllReceivers() {
        receptionService.stopAllReceivers();
    }

    public void stopReceiving() {
        receptionService.stopReceiving();
    }

    public void startReceiving() throws Exception {
        receptionService.startReceiving();
    }
}
