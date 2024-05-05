module org.unknown.pokedex {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens org.unknown.pokedex to javafx.fxml;
    exports org.unknown.pokedex.main;
    exports org.unknown.pokedex.Controller;
    opens org.unknown.pokedex.Controller to javafx.fxml;
//    exports org.unknown.pokedex.main;
    opens org.unknown.pokedex.main to javafx.fxml;
    exports org.unknown.pokedex;
}