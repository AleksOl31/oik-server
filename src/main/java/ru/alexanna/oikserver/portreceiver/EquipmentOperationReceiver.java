package ru.alexanna.oikserver.portreceiver;

import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class EquipmentOperationReceiver extends SerialPortReceiver {
    private static final Logger log = LoggerFactory.getLogger(EquipmentOperationReceiver.class);
    @Override
    final byte[] createRequest(int chkPntAddress) {
        byte[] request = new byte[13];
        request[0] = (byte) 0xfe;
        request[1] = loByte(chkPntAddress);
        request[2] = hiByte(chkPntAddress);
        request[3] = 0x06;
        for (int i = 4; i <= 9; i++)
            request[i] = (byte) 0xfd;
        int checkSum = calcCheckSum(request, 10);
        request[10] = loByte(checkSum);
        request[11] = hiByte(checkSum);
        request[12] = 0;
        return request;
    }

    @Override
    final void receive() throws Exception {
        final int  ANSWER_BYTE_NUMBER = 30;
        final int PAUSE = 20;
            for (Integer address : getAddresses()) {
                sendRequest(address);
                byte[] acceptBytes = acceptAnswer(ANSWER_BYTE_NUMBER);
                Thread.sleep(PAUSE);
                putAcceptedBytes(address, acceptBytes);
            }
    }

}
