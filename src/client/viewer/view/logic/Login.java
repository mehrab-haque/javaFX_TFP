package client.viewer.view.logic;


import javafx.stage.Stage;
import util.Util;

import java.io.IOException;

public class Login {

    private Stage stage;

    public Login(Stage stage) throws IOException {
        this.stage=stage;
        Util.jumpTo(stage,"src\\client\\viewer\\view\\fxml\\login.fxml");
    }
}
