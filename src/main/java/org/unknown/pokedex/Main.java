package org.unknown.pokedex;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;


import java.io.IOException;
import java.net.HttpURLConnection;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader tempLoader = new FXMLLoader(Main.class.getResource("temp-view.fxml"));
        Scene tempScene = new Scene(tempLoader.load(), 1273, 720);

        stage.setTitle("PokéDex");
        stage.setScene(tempScene);
        stage.show();

        // Delay transition to the main scene
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            try {
                loadMainScene(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        delay.play();
    }

    private void loadMainScene(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));
        Scene mainScene = new Scene(mainLoader.load(), 1280, 720);

        stage.setTitle("PokéDex");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}