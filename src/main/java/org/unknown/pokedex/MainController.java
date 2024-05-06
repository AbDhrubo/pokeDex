package org.unknown.pokedex;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.unknown.pokedex.main.MyListener;
import org.unknown.pokedex.models.Pokemon;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;


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

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchBtn;

    @FXML
    private HBox pokeBg;

    @FXML
    private HBox speedBar;

    @FXML
    private HBox hpBar;

    @FXML
    private HBox attackBar;

    @FXML
    private HBox defenceBar;
    @FXML
    private Label hpLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private Label attackLabel;

    @FXML
    private Label defenceLabel;




    private Map<String, String> color_map = new HashMap<>();


    private List<Pokemon> pokemons = new ArrayList<>();
    private MyListener myListener;

    private List<Pokemon> getData(String query) throws SQLException {
        List<Pokemon> pokemons = new ArrayList<>();
        Pokemon pokemon;

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/pokeschema",
                "dhrubo",
                "changeme"
        );

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getString("pokedex_number"));
            pokemon = new Pokemon();
            pokemon.setName(resultSet.getString("name"));
            pokemon.setClassification(resultSet.getString("classfication"));
            pokemon.setNumber(resultSet.getInt("pokedex_number"));
            System.out.println(pokemon.getImgSrc());
            pokemon.setType1(resultSet.getString("type1"));
            pokemon.setType2(resultSet.getString("type2"));
            pokemon.setHp(resultSet.getInt("hp"));
            pokemon.setAttack(resultSet.getInt("attack"));
            pokemon.setDefence(resultSet.getInt("defense"));
            pokemon.setSpeed(resultSet.getInt("speed"));
            pokemon.setGeneration(resultSet.getInt("generation"));
            pokemon.setLegendary(resultSet.getBoolean("is_legendary"));
            pokemon.setHeight(resultSet.getFloat("height_m"));
            pokemon.setWeight(resultSet.getFloat("weight_kg"));
            pokemon.setEvolution(resultSet.getInt("evolution"));
//            pokemon.setDesc(resultSet.getString("desc"));
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

    public void Search() throws SQLException {
        String keyword = searchBar.getText();
//        page_info = "Search";
        if (Objects.equals(keyword, "")) return;
        List<Pokemon> pokemons_search = new ArrayList<>(getData("SELECT * FROM POKEMON WHERE NAME LIKE ? OR TYPE1 LIKE ? OR TYPE2 LIKE ?"));
        if (pokemons_search.isEmpty()) return;
        grid.getChildren().clear();
        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < pokemons_search.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("pokemonCard.fxml"));
                AnchorPane anchorpane = fxmlLoader.load();
                ItemController itemcontroller = fxmlLoader.getController();
                itemcontroller.setData(pokemons_search.get(i), myListener);
                if (column == 3) {
                    column = 0;
                    row++;
                }
//                anchorpane.setStyle("-fx-background-color: " + color_map.get(pokemons_search.get(i).type1) + ";");
                grid.add(anchorpane, column++, row);
                GridPane.setMargin(anchorpane, new Insets(10));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setChosenPokemon(Pokemon pokemon){
        System.out.println(pokemon.getType1());
        String bgcolor = color_map.get(pokemon.getType1());
        System.out.println(bgcolor);
        chosenFruitCard.setStyle("-fx-background-color: " + bgcolor + ";");
        pokeName.setText(pokemon.getName());
        pokeNumber.setText("#"+pokemon.getNumber());
        pokeType.setText(pokemon.getType1());
        pokeCategory.setText(pokemon.getClassification() + " Pokemon");
        hpBar.setPrefSize(272, 22);
        speedBar.setPrefSize(272, 22);
        attackBar.setPrefSize(272, 22);
        defenceBar.setPrefSize(272, 22);
        hpBar.setPrefSize(hpBar.getPrefWidth()* (0.05 + ((double) pokemon.getHp() /350.0)), hpBar.getPrefHeight());
        hpLabel.setText(pokemon.getHp() + " ");
        speedBar.setPrefSize(speedBar.getPrefWidth()* (0.1 + ((double) pokemon.getSpeed() /350.0)), speedBar.getPrefHeight());
        speedLabel.setText(pokemon.getSpeed() + " ");
        attackBar.setPrefSize(attackBar.getPrefWidth()* (0.1 + ((double) pokemon.getAttack() /350.0)), attackBar.getPrefHeight());
        attackLabel.setText(pokemon.getAttack() + " ");
        defenceBar.setPrefSize(defenceBar.getPrefWidth()* (0.1 + ((double) pokemon.getDefence() /350.0)), defenceBar.getPrefHeight());
        defenceLabel.setText(pokemon.getHp() + " ");


        DecimalFormat df = new DecimalFormat("#.##");
        pokeWeight.setText(df.format(pokemon.getWeight()) + " kgs");
        pokeHeight.setText(df.format(pokemon.getHeight()) + "m");
//        descriptionLabel.setText(pokemon.getDesc());
//        pokeStrength.setText(pokemon.getStrength());
//        pokeWeakness.setText(pokemon.getWeakness());

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

    public void initColor() {
        color_map.put("normal", "#A8A77A");
        color_map.put("fire", "#EE8130");
        color_map.put("water", "#6390F0");
        color_map.put("electric", "#F7D02C");
        color_map.put("grass", "#7AC74C");
        color_map.put("ice", "#96D9D6");
        color_map.put("fighting", "#C22E28");
        color_map.put("poison", "#A33EA1");
        color_map.put("ground", "#E2BF65");
        color_map.put("flying", "#A98FF3");
        color_map.put("psychic", "#F95587");
        color_map.put("bug", "#A6B91A");
        color_map.put("rock", "#B6A136");
        color_map.put("ghost", "#735797");
        color_map.put("dragon", "#6F35FC");
        color_map.put("dark", "#705746");
        color_map.put("steel", "#B7B7CE");
        color_map.put("fairy", "#D685AD");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        int column = 0;
        int row = 1;
        initColor();

        try {
            pokemons.addAll(getData("SELECT * FROM POKEMON_LATEST LIMIT 50"));
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