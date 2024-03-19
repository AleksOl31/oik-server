package ru.alexanna.oikserver.services;

import ru.alexanna.oikserver.portreceiver.EquipmentOperationReceiver;
import ru.alexanna.oikserver.portreceiver.Receiver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReceptionService {
    protected final Set<Receiver> receivers = new HashSet<>();
    protected Receiver receiver;

    public void addReceiver(Receiver receiver) {
        receivers.add(receiver);
    }

    public void addReceivers(Set<Receiver> receivers) {
        this.receivers.addAll(receivers);
    }

    public void startAllReceivers() {
        Receiver receiver1 = createReceiver();
        addReceiver(receiver1);
//        receivers.forEach(Receiver::startReceiving);
    }

    public void stopAllReceivers() {
        receivers.forEach(Receiver::stopReceiving);
    }

    public void stopReceiving() {
        receiver.stopReceiving();
    }

    public void startReceiving() throws Exception {
        receiver = createReceiver();
        receiver.startReceiving();
    }

    private Receiver createReceiver() {
        Receiver receiver = new EquipmentOperationReceiver();
        receiver.setPortParams("COM2", 9600, false);
        receiver.setAddresses(List.of(9, 10, 11, 12, 13, 14, 15, 16));
        return receiver;
    }
}
