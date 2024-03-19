package ru.alexanna.oikserver.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexanna.oikserver.MainController;
import ru.alexanna.oikserver.entities.CheckPoint;
import ru.alexanna.oikserver.entities.Port;
import ru.alexanna.oikserver.services.DatabaseService;
import ru.alexanna.oikserver.services.ReceptionService;

import java.util.Set;


public class MainModel {
    protected final ReceptionService receptionService = new ReceptionService();
    protected final DatabaseService databaseService = new DatabaseService();
    protected final ListProperty<Port> ports = new SimpleListProperty<>();
    protected final ObjectProperty<Port> selectedPort = new SimpleObjectProperty<>();
    protected final ListProperty<CheckPoint> checkPoints = new SimpleListProperty<>();

    private static final Logger log = LoggerFactory.getLogger(MainModel.class);

    public MainModel() {
        this.selectedPort.addListener(((observableValue, port, selected) ->
                setCheckPoints(port.getCheckPoints())));
    }

    public void initialize() {
        setPorts(databaseService.findAllPorts());
        log.debug("Ports {}", getPorts());
//        setCheckPoints(ports.get(0).getCheckPoints());
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

    public void setSelectedPort(Port port) {
        selectedPort.setValue(port);
    }

    public Port getSelectedPort() {
        return selectedPort.getValue();
    }

    public ListProperty<CheckPoint> getCheckPointsProperty() {
        return checkPoints;
    }

    public void setCheckPoints(Set<CheckPoint> checkPoints) {
        ObservableList<CheckPoint> observableList = FXCollections.observableArrayList(checkPoints);
        this.checkPoints.setValue(observableList);
    }

    //==============================================================================================
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
