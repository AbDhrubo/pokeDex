package org.unknown.pokedex;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.unknown.pokedex.main.MyListener;
import org.unknown.pokedex.models.Pokemon;

import java.util.Objects;

public class ItemController {
    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private void click(MouseEvent e){
        myListener.onClickListener(pokemon);
    }
    private Pokemon pokemon;

    private MyListener myListener;
    public void setData(Pokemon pokemon, MyListener myListener){
        this.pokemon = pokemon;
        this.myListener = myListener;
        nameLabel.setText(pokemon.getName());
        priceLabel.setText("#" + pokemon.getNumber());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(pokemon.getImgSrc())));
        img.setImage(image);
    }
}
