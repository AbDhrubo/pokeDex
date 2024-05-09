package org.unknown.pokedex;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.unknown.pokedex.main.MyListener;
import org.unknown.pokedex.models.Pokemon;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class EvolutionController implements Initializable {
    @FXML
    private GridPane grid;

    private ArrayList<Pokemon> pokemons_search;
    private MyListener myListener;

    public void init (ArrayList<Pokemon> pokemons, Map<String, String> color_map){
        this.pokemons_search = pokemons;
        grid.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < pokemons_search.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorpane = fxmlLoader.load();
                ItemController itemcontroller = fxmlLoader.getController();
                itemcontroller.setData(pokemons_search.get(i), myListener, color_map);
                if (column == 3) {
                    column = 0;
                    row++;
                }
//                anchorpane.setStyle("-fx-background-color: " + color_map.get(pokemons_search.get(i).type1) + ";");
                grid.add(anchorpane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorpane, new Insets(10));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
