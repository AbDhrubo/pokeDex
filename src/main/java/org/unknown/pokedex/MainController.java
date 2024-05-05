package org.unknown.pokedex;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.unknown.pokedex.main.MyListener;
import org.unknown.pokedex.models.Pokemon;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private ImageView fruitImage;

    @FXML
    private Label pokeName;
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Label pokeHeight;

    @FXML
    private Label pokeNumber;

    @FXML
    private Label pokeStrength;

    @FXML
    private Label pokeType;

    @FXML
    private Label pokeCategory;

    @FXML
    private Label pokeWeakness;

    @FXML
    private Label pokeWeight;

    @FXML
    private Text descriptionLabel;

    @FXML
    private ImageView pokeImg;


    private List<Pokemon> pokemons = new ArrayList<>();
    private MyListener myListener;

    private List<Pokemon> getData() throws SQLException {
        List<Pokemon> pokemons = new ArrayList<>();
        Pokemon pokemon;

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/pokeschema",
                "dhrubo",
                "changeme"
        );

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM POKEMONS");

        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getString("id"));
            pokemon = new Pokemon();
            pokemon.setName(resultSet.getString("name"));
            pokemon.setImgSrc(resultSet.getString("imgsrc"));
            pokemon.setColor("6A7324");
            pokemon.setCategory(resultSet.getString("category"));
            pokemon.setType(resultSet.getString("type"));
            pokemon.setHeight(resultSet.getFloat("height"));
            pokemon.setWeight(resultSet.getFloat("weight"));
            pokemon.setNumber(resultSet.getInt("id"));
            pokemon.setEvolution(resultSet.getInt("evolution_id"));
            pokemon.setDesc(resultSet.getString("desc"));
            pokemon.setStrength(resultSet.getString("strength"));
            pokemon.setWeakness(resultSet.getString("weakness"));
            pokemons.add(pokemon);
        }

//        for(int i = 0; i < 20; i++){
//            pokemon = new Pokemon();
//            pokemon.setName("Balbasaur");
//            pokemon.setImgSrc("img/001.png");
//            pokemon.setColor("6A7324");
//            pokemon.setCategory("Seed");
//            pokemon.setType("Grass");
//            pokemon.setHeight(0.7);
//            pokemon.setWeight(6.9);
//            pokemon.setNumber(1);
//            pokemon.setEvolution(2);
//            pokemon.setDesc("Bulbasaur is a Grass/Poison type Pokémon introduced in Generation 1.\n" +
//                    "\n" +
//                    "Bulbasaur is a small, mainly turquoise amphibian Pokémon with red eyes and a green bulb on its back. It is based on a frog/toad, with the bulb resembling a plant bulb that grows into a flower as it evolves.\n" +
//                    "\n" +
//                    "Bulbasaur is notable for being the very first Pokémon in the National Pokédex. It is one of the three choices for a starter Pokémon in the original Game Boy games, Pokémon Red & Blue (Red & Green in Japan), along with Charmander and Squirtle.");
//            pokemons.add(pokemon);
//        }

//        for(int i = 0; i < 20; i++){
//            pokemon = new Pokemon();
//            pokemon.setName("Pikachu");
//            pokemon.setImgSrc("img/025.png");
//            pokemon.setColor("6A7324");
//            pokemon.setCategory("Mouse");
//            pokemon.setType("Electric");
//            pokemon.setHeight(0.4);
//            pokemon.setWeight(6.0);
//            pokemon.setNumber(25);
//            pokemon.setEvolution(26);
//            pokemon.setDesc("Pikachu is an Electric type Pokémon introduced in Generation 1.\n" +
//                    "\n" +
//                    "Pikachu has a Gigantamax form available in Pokémon Sword/Shield, with an exclusive G-Max move, G-Max Volt Crash.");
//            pokemons.add(pokemon);
//        }

        return pokemons;
    }

    private void setChosenPokemon(Pokemon pokemon){
        pokeName.setText(pokemon.getName());
        pokeNumber.setText("#"+pokemon.getNumber());
        pokeType.setText(pokemon.getType());
        pokeCategory.setText(pokemon.getCategory() + " Pokemon");

        DecimalFormat df = new DecimalFormat("#.##");
        pokeWeight.setText(df.format(pokemon.getWeight()) + " kgs");
        pokeHeight.setText(df.format(pokemon.getHeight()) + "m");
        descriptionLabel.setText(pokemon.getDesc());
        pokeStrength.setText(pokemon.getStrength());
        pokeWeakness.setText(pokemon.getWeakness());

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(pokemon.getImgSrc())));
        pokeImg.setImage(image);

//        String totalStrength = "";
//        for (String strength: pokemon.getStrength()){
//            totalStrength += (" "+strength);
//        }
//        pokeStrength.setText(totalStrength);
//
//        String totalWeakness = "";
//        for (String weakness: pokemon.getWeakness()){
//            totalWeakness += (" "+weakness);
//        }
//        pokeWeakness.setText(totalWeakness);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        int column = 0;
        int row = 1;

        try {
            pokemons.addAll(getData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(!pokemons.isEmpty()){
            setChosenPokemon(pokemons.getFirst());
            myListener = new MyListener() {
                @Override
                public void onClickListener(Pokemon pokemon) {
                    setChosenPokemon(pokemon);
                }
            };
        }
        for (Pokemon pokemon : pokemons) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("item.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ItemController itemController = fxmlLoader.getController();
            if (itemController != null) {
                itemController.setData(pokemon, myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }
            } else System.out.println("controller was not found");
            grid.add(anchorPane, column++, row);
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            //set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(10));
        }
    }
}