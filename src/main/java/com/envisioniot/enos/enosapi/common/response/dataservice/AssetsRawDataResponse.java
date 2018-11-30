package com.envisioniot.enos.enosapi.common.response.dataservice;

import com.envisioniot.enos.enosapi.common.response.EnOSResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by jingqi.shi on 2018/11/21
 */
public class AssetsRawDataResponse extends EnOSResponse {

    private static final long serialVersionUID = -2817157091083930585L;

    @SerializedName("nextPageToken")
    private String nextPageToken;

    @SerializedName("data")
    private List<Map<String, String>> result;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Map<String, String>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, String>> result) {
        this.result = result;
    }
}
