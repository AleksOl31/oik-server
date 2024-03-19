package ru.alexanna.oikserver;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexanna.oikserver.entities.CheckPoint;
import ru.alexanna.oikserver.entities.Port;
import ru.alexanna.oikserver.models.MainModel;

import java.net.URL;
import java.util.*;


public class MainController implements Initializable {
    private final MainModel mainModel;

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @FXML
    private ListView<CheckPoint> checkPointsListView;
    @FXML
    private TableView<Port> portsTableView;
    @FXML
    private TableColumn<Port, String> portNameColumn;
    @FXML
    private TableColumn<Port, Integer> baudColumn;
    @FXML
    private TableColumn<Port, Boolean> parityColumn;
    @FXML
    private TableColumn<Port, String> ktmsColumn;
    @FXML
    private TableColumn<Port, String> dataTypeColumn;
    @FXML
    private Button startBtn;

    public MainController() {
        this.mainModel = new MainModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainModel.initialize();
        setup();
    }

    public void setup() {
        portsTableInitialize();
        checkPointsListViewInitialize();
    }

    private void portsTableInitialize() {
        portsTableView.setPlaceholder(new Label("Информация о портах отсутствует"));
        portsTableView.itemsProperty().bindBidirectional(mainModel.getPortsProperty());
        portsTableView.getSelectionModel().selectedItemProperty().addListener((
                (observableValue, oldPort, newPort) -> mainModel.setSelectedPort(newPort)));
        portsTableView.getSelectionModel().selectFirst();
    }

    private void checkPointsListViewInitialize() {
        checkPointsListView.setPlaceholder(new Label("ТМКП не зарегистрированы"));
        checkPointsListView.itemsProperty().bind(mainModel.getCheckPointsProperty());
        checkPointsListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<CheckPoint>() {
            @Override
            public String toString(CheckPoint checkPoint) {
                return checkPoint.getAddress().toString();
            }

            @Override
            public CheckPoint fromString(String s) {
                return null;
            }
        }));
    }

    public void startBtnClick() {
        try {
            mainModel.startReceiving();
        } catch (Exception e) {
            log.error("Start receiving error: {}", e.getMessage());
        }
    }

    public void stopBtnClick() {
        mainModel.stopReceiving();
    }
}