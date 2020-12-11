package server;

import util.NetworkUtil;

public class ClientType {
    private NetworkUtil networkUtil;
    private String type;
    private String timestampID;

    public ClientType(NetworkUtil networkUtil, String type, String timestampID) {
        this.networkUtil = networkUtil;
        this.type = type;
        this.timestampID = timestampID;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestampID() {
        return timestampID;
    }

    public void setTimestampID(String timestampID) {
        this.timestampID = timestampID;
    }
}
