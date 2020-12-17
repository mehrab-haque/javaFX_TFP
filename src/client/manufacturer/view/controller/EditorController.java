package client.manufacturer.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.*;

public class EditorController {
    @FXML public Label reg;
    @FXML public ImageView image;
    @FXML public Button select;
    @FXML public TextField make;
    @FXML public TextField model;
    @FXML public TextField price;
    @FXML public ColorPicker color;
    @FXML public Button cancel;
    @FXML public Button save;
}
