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




    public Map<String, String> color_map = new HashMap<>();


    private List<Pokemon> pokemons = new ArrayList<>();
    private MyListener myListener;

    private List<Pokemon> getData(String query) throws SQLException {
        List<Pokemon> pokemons = new ArrayList<>();
        Pokemon pokemon;

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/poke_schema",
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

        return pokemons;
    }

    public void Search() throws SQLException {
        String keyword = searchBar.getText();
        List<Pokemon> pokemons_search;
//        page_info = "Search";
        if (Objects.equals(keyword, "")) {
             pokemons_search = new ArrayList<>(getData("SELECT * FROM POKEMON_LATEST LIMIT 50"));
        }
        else{
            keyword = Character.toUpperCase(keyword.charAt(0)) + keyword.substring(1);
            pokemons_search = new ArrayList<>(getData("SELECT * FROM POKEMON_LATEST WHERE NAME LIKE '%" + keyword + "%' OR TYPE1 LIKE '%" + keyword + "%' OR TYPE2 LIKE '%" + keyword + "%' LIMIT 50"));
        }
        if(!pokemons_search.isEmpty()){
            setChosenPokemon(pokemons_search.getFirst());
            myListener = new MyListener() {
                @Override
                public void onClickListener(Pokemon pokemon) {
                    setChosenPokemon(pokemon);
                }
            };
        }
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

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(pokemon.getImgSrc())));
        pokeImg.setImage(image);
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
            Search();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}