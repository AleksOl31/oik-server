package ru.alexanna.oikserver;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexanna.oikserver.portreceiver.EquipmentOperationReceiver;

import java.net.URL;
import java.util.*;


public class MainController implements Initializable {
    @FXML
    private VBox rootVB;

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @FXML
    protected void onHelloButtonClick() {

        log.debug("Logger");
        EquipmentOperationReceiver receiver = new EquipmentOperationReceiver(30);
        List<String> portNames = receiver.getPortNames();
        log.debug("Ports {}", portNames);
        String portName = portNames.get(1);
        receiver.setPortParams(portName, 9600, false);
        receiver.setAddresses(List.of(9, 10, 11, 12, 13, 14, 15, 16));
        try {
            receiver.openPort();
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }
        Thread thread = new Thread(receiver);
        thread.setName("Thread-" + portName);
        thread.start();
        Timer timer = new Timer("Timer-" + portName);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Map<Integer, byte[]> bytes = receiver.getReceivedBytes();
                log.info("Map {}", bytes);
//                bytes.forEach((key, val) -> log.info("{} - {}", key, receiver.getLogString(val)));
            }
        }, 1000, 1000);
    }

    public MainController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootVB.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0))));
    }
}