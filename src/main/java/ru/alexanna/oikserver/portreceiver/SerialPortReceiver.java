package ru.alexanna.oikserver.portreceiver;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.*;

import static jssc.SerialPort.DATABITS_8;
import static jssc.SerialPort.STOPBITS_1;


@SuppressWarnings("unused")
public abstract class SerialPortReceiver implements Receiver {
    private SerialPort port;
    private int baudRate;
    private boolean parity;
    private List<Integer> addresses;
    private final Map<Integer, byte[]> receivedByteCollector = new HashMap<>();
    private volatile Map<Integer, byte[]> bytesReceived;
    private Thread receivingThread;
    private static final Logger log = LoggerFactory.getLogger(SerialPortReceiver.class);

    private final List<PortEventListener> listeners = new ArrayList<>();
    protected StringBuffer logStringBuffer;
    protected Timer logTimer;

    abstract byte[] createRequest(int chkPntAddress);

    abstract void receive() throws Exception;

    public SerialPortReceiver() {
    }

    public SerialPortReceiver(String portName, int baudRate, boolean parity) {
        this();
        setPortParams(portName, baudRate, parity);
    }

    @Override
    public void setPortParams(String portName, int baudRate, boolean parity) {
        this.port = new SerialPort(portName);
        this.baudRate = baudRate;
        this.parity = parity;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                logStringBuffer = new StringBuffer();
                receive();
                setReceivedBytes(receivedByteCollector);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        try {
            closePort();
        } catch (SerialPortException e) {
            log.error(e.getMessage());
        }
        log.debug("Thread state (run exit) {}", receivingThread.getState());
    }

    @Override
    public void stopReceiving() {
        if (receivingThread != null) {
            receivingThread.interrupt();
            logTimer.cancel();
            //TODO переделать через wait() в методе run()
            try {
                Thread.sleep(1000);
                if (receivingThread.getState() == Thread.State.RUNNABLE)
                    closePort();
                Thread.sleep(500);
                log.debug("Stop method closed port {}", receivingThread.getState());
            } catch (InterruptedException | SerialPortException e) {
                throw new RuntimeException(e);
            }
            log.debug("Stop method exit {}", receivingThread.getState());
        }
    }

    @Override
    public void startReceiving() throws Exception {
        openPort();
        receivingThread = new Thread(this);
        receivingThread.setName("Thread-" + port.getPortName());
        receivingThread.start();
        logTimer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                notifyListeners(logStringBuffer.toString());
            }
        };
        logTimer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public void setAddresses(List<Integer> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void addPortEventListener(PortEventListener portEventListener) {
        listeners.add(portEventListener);
    }

    @Override
    public void removePortEventListener(PortEventListener portEventListener) {
        listeners.remove(portEventListener);
    }

    @Override
    public void notifyListeners(String newLogString) {
        for (PortEventListener listener: listeners) {
            listener.updateLog(newLogString);
        }
    }

    public List<Integer> getAddresses() {
        return addresses;
    }

    public static List<String> getPortNames() {
        return Arrays.asList(SerialPortList.getPortNames());
    }

    public void openPort() throws SerialPortException {
        port.openPort();
        port.setParams(baudRate, DATABITS_8, STOPBITS_1, parity ? 1 : 0);
        log.debug("Port {} opened", port.getPortName());
    }

    public void closePort() throws SerialPortException {
        port.closePort();
        log.debug("Port {} closed", port.getPortName());
    }

    public void sendRequest(int address) throws SerialPortException {
        byte[] request = createRequest(address);
        port.writeBytes(request);
        Date date = new Date();
        String logStringRequest = date + " Запрос к ТМКП-" + address + " отправлен (" + request.length +
                "/13): " + getLogString(request) + "\n";
        logStringBuffer.append(logStringRequest);
    }

    public byte[] acceptAnswer(int byteNumber) throws SerialPortException {
        log.debug("Wait for read port {}...", port.getPortName());
        byte[] buffer = port.readBytes(byteNumber);
//        log.debug("Data read, from check point address {}: {}", buffer[1], getLogString(buffer));
        String logStringResponse = "Ответ ТМКП-" + buffer[1] + " (" + buffer.length + "/30): " +
                getLogString(buffer) + "\n\n";
        logStringBuffer.append(logStringResponse);
        return buffer;
    }

    public int calcCheckSum(byte[] byteArr, int byteNumber) {
        int result = 0;
        for (int i = 0; i < byteNumber; i++) {
            result += unsignedByte(byteArr[i]);
        }
        return result;
    }

    public String getLogString(byte[] byteArr) {
        StringBuilder builder = new StringBuilder();
        for (byte b : byteArr) {
            builder.append(String.format("%02X", unsignedByte(b)))
                    .append(" ");
        }
        return builder.toString();
    }

    public static int unsignedByte(byte b) {
        return b & 0xff;
    }

    public static byte loByte(int value) {
        return (byte) (value & 0xff);
    }

    public static byte hiByte(int value) {
        return (byte) ((value >> 8) & 0xff);
    }

    public void putAcceptedBytes(Integer key, byte[] acceptedArr) {
        receivedByteCollector.put(key, acceptedArr);
    }

    private void setReceivedBytes(Map<Integer, byte[]> map) {
        bytesReceived = map;
    }

    public Map<Integer, byte[]> getReceivedBytes() {
        return bytesReceived;
    }
}
