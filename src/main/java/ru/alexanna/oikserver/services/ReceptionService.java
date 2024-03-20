package ru.alexanna.oikserver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexanna.oikserver.entities.Port;
import ru.alexanna.oikserver.portreceiver.ElectricityReceiver;
import ru.alexanna.oikserver.portreceiver.EquipmentOperationReceiver;
import ru.alexanna.oikserver.portreceiver.Receiver;

import java.util.*;

public class ReceptionService {
    protected final Map<Port, Receiver> receivers = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(ReceptionService.class);

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

    public void stopReceiving(Port port) {
        receivers.get(port).stopReceiving();
    }

    public void startReceiving(Port port) throws Exception {
        Receiver receiver = createReceiver(port.getReceivedData());
        setReceiver(receiver, port);
        receiver.startReceiving();
        receivers.put(port, receiver);
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
        List<Integer> addresses = List.of(9, 10, 11, 12, 13, 14, 15, 16);
        receiver.setAddresses(addresses);
    }
}
