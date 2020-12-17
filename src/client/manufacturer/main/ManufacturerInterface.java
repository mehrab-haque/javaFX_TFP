package client.manufacturer.main;

import org.json.JSONObject;

public interface ManufacturerInterface {
    void onError();
    void onSuccess(JSONObject data);
}
