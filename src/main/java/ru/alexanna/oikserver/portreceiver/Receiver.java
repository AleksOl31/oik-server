package ru.alexanna.oikserver.portreceiver;


import java.util.List;

public interface Receiver extends Runnable {
    void startReceiving();
    void stopReceiving();
    void setPortParams(String portName, int baudRate, boolean parity);
    void setAddresses(List<Integer> addresses);

}
