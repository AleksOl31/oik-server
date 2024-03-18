package ru.alexanna.oikserver.services;

import ru.alexanna.oikserver.portreceiver.Receiver;

import java.util.HashSet;
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
        receivers.forEach(Receiver::startReceiving);
    }

    public void stopAllReceivers() {
        receivers.forEach(Receiver::stopReceiving);
    }
}
