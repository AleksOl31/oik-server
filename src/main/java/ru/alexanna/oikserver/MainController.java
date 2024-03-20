package ru.alexanna.oikserver;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
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
        portsTableView.requestFocus();
        StringConverter<Integer> stringConverter = new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return value == null ? "" : value.toString();
            }

            @Override
            public Integer fromString(String s) {
                try {
                    return Integer.valueOf(s);
                } catch (Exception e) {
                    return null;
                }
            }
        };
        portNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        baudColumn.setCellValueFactory(new PropertyValueFactory<>("baudRate"));
        baudColumn.setCellFactory(TextFieldTableCell.forTableColumn(stringConverter));
        parityColumn.setCellValueFactory(new PropertyValueFactory<>("parity"));
        parityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Boolean aBoolean) {
                return aBoolean ? "Да" : "Нет";
            }

            @Override
            public Boolean fromString(String s) {
                return Objects.equals(s, "Да");
            }
        }));
        ktmsColumn.setCellValueFactory(new PropertyValueFactory<>("ktms"));
        dataTypeColumn.setCellValueFactory(new PropertyValueFactory<>("receivedData"));
    }

    private void checkPointsListViewInitialize() {
        checkPointsListView.setPlaceholder(new Label("ТМКП не зарегистрированы"));
        checkPointsListView.itemsProperty().bind(mainModel.getCheckPointsProperty());
        checkPointsListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(CheckPoint checkPoint) {
                return checkPoint.getAddress().toString() + " - " + checkPoint.getLocation().getName();
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