package client.manufacturer.view.logic;

import client.manufacturer.main.Manufacturer;
import client.manufacturer.main.ManufacturerInterface;
import client.manufacturer.main.Profile;
import client.manufacturer.view.controller.EditorController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import util.Toast;
import util.Util;

import java.io.File;
import java.io.IOException;

public class EditCar {
    private Stage parentStage;
    private Stage editStage;
    private JSONObject initialData;
    private String image;

    public EditCar(JSONObject initialData,Stage parentStage) throws IOException, JSONException {
        this.parentStage=parentStage;
        this.initialData=initialData;
        loadEditor();
    }

    private void loadEditor() throws IOException, JSONException {
        editStage=new Stage();
        Manufacturer.getInstance().getStages().add(editStage);
        editStage.setTitle("Edit Car Details");
        editStage.setScene(new Scene(new Group()));
        editStage.show();

        editStage.setOnCloseRequest(event->{
            try {
                Manufacturer.getInstance().getStages().remove(editStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        EditorController editorController=Util.jumpTo(editStage,"src\\client\\manufacturer\\view\\fxml\\editor.fxml").getController();

        //Previous data
        editorController.reg.setText("Registration No: "+initialData.getInt("id"));
        if(initialData.has("image")){
            image=initialData.getString("image");
            editorController.image.setImage(new Image(image));
        }
        editorController.make.setText(initialData.optString("make"));
        editorController.model.setText(initialData.optString("model"));
        editorController.price.setText(initialData.optInt("price")+"");
        editorController.quantity.setText(initialData.optInt("quantity")+"");
        if(initialData.has("color"))editorController.color.setValue(Color.valueOf(initialData.getString("color")));

        //functions
        editorController.select.setOnMouseClicked(event->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select your car's image");
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            File file = fileChooser.showOpenDialog(null);
            image=file.toURI().toString();
            editorController.image.setImage(new Image(image));
            System.out.println(file.toURI().toString());
        });
        editorController.cancel.setOnMouseClicked(event->{
            editStage.close();
        });
        editorController.save.setOnMouseClicked(event->{
            if(!Util.isValidString(image))
                Toast.makeText(editStage,"Please select an image of your car.",1000,1000,500);
            else if(!Util.isValidString(editorController.make.getText()))
                Toast.makeText(editStage,"Please enter car's make.",1000,1000,500);
            else if(!Util.isValidString(editorController.model.getText()))
                Toast.makeText(editStage,"Please enter car's model.",1000,1000,500);
            else if(!Util.isValidInteger(editorController.price.getText()) || Integer.parseInt(editorController.price.getText())<1)
                Toast.makeText(editStage,"Please enter car's valid price.",1000,1000,500);
            else if(!Util.isValidInteger(editorController.quantity.getText()) || Integer.parseInt(editorController.quantity.getText())<1)
                Toast.makeText(editStage,"Please enter car's valid quantity.",1000,1000,500);
            else {
                try {
                    Manufacturer.getInstance().setManufacturerInterface(new ManufacturerInterface() {
                        @Override
                        public void onError() {
                            Toast.makeText(editStage,"Error",1000,1000,500);
                        }

                        @Override
                        public void onSuccess(JSONObject data) throws IOException, JSONException {
                            Toast.makeText(parentStage,"Car updated successfully",1000,1000,500);
                            editStage.close();
                        }
                    });
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("id", initialData.getInt("id"));
                    jsonObject.put("image",image);
                    jsonObject.put("make",editorController.make.getText().toLowerCase());
                    jsonObject.put("model",editorController.model.getText().toLowerCase());
                    jsonObject.put("color",editorController.color.getValue().toString());
                    jsonObject.put("price",Integer.parseInt(editorController.price.getText()));
                    jsonObject.put("quantity",Integer.parseInt(editorController.quantity.getText()));
                    Manufacturer.getInstance().editCar(jsonObject);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
