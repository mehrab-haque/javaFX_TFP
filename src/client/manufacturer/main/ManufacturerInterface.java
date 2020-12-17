package client.manufacturer.main;

import org.json.JSONObject;

import java.io.IOException;

public interface ManufacturerInterface {
    void onError();
    void onSuccess(JSONObject data) throws IOException;
}
