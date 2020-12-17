package client.manufacturer.view.logic;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

public class EditCar {
    private Stage parentStage;
    private JSONObject initialData;

    public EditCar(JSONObject initialData,Stage parentStage){
        this.parentStage=parentStage;
        this.initialData=initialData;
        loadEditor();
    }

    private void loadEditor(){
        Stage stage=new Stage();
        stage.setTitle("Edit Car Details");
        stage.setScene(new Scene(new Group()));
        stage.show();
    }
}
