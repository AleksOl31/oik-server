module ru.alexanna.oikserver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.slf4j;
    requires jssc;
    requires java.sql;

    opens ru.alexanna.oikserver to javafx.fxml;
    exports ru.alexanna.oikserver;

}