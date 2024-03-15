module ru.alexanna.oikserver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens ru.alexanna.oikserver to javafx.fxml;
    exports ru.alexanna.oikserver;
}