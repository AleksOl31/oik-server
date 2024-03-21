package ru.alexanna.oikserver.portreceiver;

public interface SerialPortEvent {
    void addPortEventListener(PortEventListener portEventListener);
    void removePortEventListener(PortEventListener portEventListener);
    void notifyListeners(String newLogString);
}
