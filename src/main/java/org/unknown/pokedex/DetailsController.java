package org.unknown.pokedex;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class DetailsController {
    @FXML
    private Text details;

    public void init(String details){
        this.details.setText(details);
    }
}
