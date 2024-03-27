package ru.alexanna.oikserver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexanna.oikserver.entities.Port;
import ru.alexanna.oikserver.models.MainModel;
import ru.alexanna.oikserver.portreceiver.ElectricityReceiver;
import ru.alexanna.oikserver.portreceiver.EquipmentOperationReceiver;
import ru.alexanna.oikserver.portreceiver.PortEventListener;
import ru.alexanna.oikserver.portreceiver.Receiver;

import java.util.*;

public class ReceptionService implements PortEventListener {
    private final Map<Port, Receiver> receivers = new HashMap<>();
    private final MainModel mainModel;
    private static final Logger log = LoggerFactory.getLogger(ReceptionService.class);

    public ReceptionService(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void startAllReceivers(List<Port> ports) {
        ports.forEach(port -> {
            try {
                startReceiving(port);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка открытия порта " + port.getName(), e);
            }
        });
    }

    public void stopAllReceivers() {
        receivers.forEach(((port, receiver) -> stopReceiving(port)));
    }

    public Receiver stopReceiving(Port port) {
        receivers.get(port).stopReceiving();
        return receivers.remove(port);
    }

    public Receiver startReceiving(Port port) throws Exception {
        Receiver receiver = createReceiver(port.getReceivedData());
        setReceiver(receiver, port);
        receiver.startReceiving();
        receiver.addPortEventListener(this);
        return receivers.put(port, receiver);
    }

    public void startLogging(Port port) {
        if (receivers.get(port) != null)
            receivers.get(port).startLogging();
    }

    public void stopLogging(Port port) {
        if (receivers.get(port) != null)
            receivers.get(port).stopLogging();
    }

    private Receiver createReceiver(String receivedData) {
        Receiver receiver;
        if (Objects.equals(receivedData, "Оборудование"))
            receiver = new EquipmentOperationReceiver();
        else
            receiver = new ElectricityReceiver();
        return receiver;
    }

    private static void setReceiver(Receiver receiver, Port port) {
        receiver.setPortParams(port.getName(), port.getBaudRate(), port.isParity());
//        List<Integer> addresses = port.getCheckPoints().stream().map(CheckPoint::getAddress).collect(Collectors.toList());
        // FIXME тестовый hardcode
        List<Integer> addresses;
        if (Objects.equals(port.getName(), "COM2"))
            addresses = List.of(9, 10, 11, 12, 13, 14, 15, 16);
        else
            addresses = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        receiver.setAddresses(addresses);
    }

    @Override
    public void updateLog(String newLogString) {
        log.debug("{}", newLogString);
        mainModel.addStringToLog(newLogString);
    }
}
