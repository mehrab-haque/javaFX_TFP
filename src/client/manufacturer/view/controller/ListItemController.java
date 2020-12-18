package client.manufacturer.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ListItemController {
    @FXML public ImageView image;
    @FXML public Label reg;
    @FXML public Label make;
    @FXML public Label model;
    @FXML public Label price;
    @FXML public Label timestamp;
    @FXML public Button edit;
    @FXML public Button delete;
}
