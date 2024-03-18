package ru.alexanna.oikserver.services;

import ru.alexanna.oikserver.portreceiver.EquipmentOperationReceiver;
import ru.alexanna.oikserver.portreceiver.Receiver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReceptionService {
    protected final Set<Receiver> receivers = new HashSet<>();

    public void addReceiver(Receiver receiver) {
        receivers.add(receiver);
    }

    public void addReceivers(Set<Receiver> receivers) {
        this.receivers.addAll(receivers);
    }

    public void startAllReceivers() {
        Receiver receiver = new EquipmentOperationReceiver();
        receiver.setPortParams("CxOM2", 9600, false);
        receiver.setAddresses(List.of(9, 10, 11, 12, 13, 14, 15, 16));

        addReceiver(receiver);
        receivers.forEach(Receiver::startReceiving);
    }

    public void stopAllReceivers() {
        receivers.forEach(Receiver::stopReceiving);
    }
}
