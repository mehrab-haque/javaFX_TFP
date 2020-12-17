package client.manufacturer.view.logic;

import client.manufacturer.view.controller.EditorController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;
import util.Util;

import java.io.IOException;

public class EditCar {
    private Stage parentStage;
    private Stage editStage;
    private JSONObject initialData;

    public EditCar(JSONObject initialData,Stage parentStage) throws IOException {
        this.parentStage=parentStage;
        this.initialData=initialData;
        loadEditor();
    }

    private void loadEditor() throws IOException {
        editStage=new Stage();
        editStage.setTitle("Edit Car Details");
        editStage.setScene(new Scene(new Group()));
        editStage.show();

        EditorController editorController=Util.jumpTo(editStage,"src\\client\\manufacturer\\view\\fxml\\editor.fxml").getController();


        editorController.reg.setText("hiiiiii");

    }
}
