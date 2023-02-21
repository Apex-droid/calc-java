package com.example.demo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view1.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
       
        //scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("some calculator");
        stage.setScene(scene);
        stage.getMinWidth();
        stage.setHeight(640);
        stage.setWidth(483);
        stage.setMaxHeight(640);
        stage.setMinHeight(640);
        stage.setMinWidth(483);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

        //calc("(666.666+666)*(66+6)-5*cos(6)");
        //launch();

    }
}