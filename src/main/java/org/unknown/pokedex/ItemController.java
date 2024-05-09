package org.unknown.pokedex;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.unknown.pokedex.main.MyListener;
import org.unknown.pokedex.models.Pokemon;

import java.util.Map;
import java.util.Objects;

public class ItemController {
    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private VBox cardBg;

    @FXML
    private void click(MouseEvent e){
        myListener.onClickListener(pokemon);
    }
    private Pokemon pokemon;
    private Map<String, String> color_map;

    private MyListener myListener;
    public void setData(Pokemon pokemon, MyListener myListener, Map<String, String>color_map){
        this.pokemon = pokemon;
        this.myListener = myListener;
        this.color_map = color_map;
        nameLabel.setText(pokemon.getName());
        priceLabel.setText("#" + pokemon.getNumber());
        String bgcolor = color_map.get(pokemon.getType1());
        cardBg.setStyle("-fx-background-color: " + bgcolor + ";");

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(pokemon.getImgSrc())));
        img.setImage(image);
    }
}
