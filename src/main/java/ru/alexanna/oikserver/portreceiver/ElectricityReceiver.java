package ru.alexanna.oikserver.portreceiver;

import jssc.SerialPortException;

public class ElectricityReceiver extends SerialPortReceiver {

    @Override
    byte[] createRequest(int chkPntAddress) {
        return new byte[0];
    }

    @Override
    void receive() throws Exception {

    }
}
