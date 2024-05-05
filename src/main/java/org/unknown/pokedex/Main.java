package org.unknown.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;


import java.io.IOException;
import java.net.HttpURLConnection;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        Connection con = DriverManager
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PokéDex");
        stage.setScene(scene);
        stage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));
//        primaryStage.setTitle("PokéDex");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}