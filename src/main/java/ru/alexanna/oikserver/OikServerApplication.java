package ru.alexanna.oikserver;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;

public class OikServerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OikServerApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("ОИК сервер");
        InputStream iconStream = getClass().getResourceAsStream("/icons/free-icon-development-1237434.png");
        if (iconStream != null) {
            Image image = new Image(iconStream);
            stage.getIcons().add(image);
        }
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            stage.setIconified(true);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}