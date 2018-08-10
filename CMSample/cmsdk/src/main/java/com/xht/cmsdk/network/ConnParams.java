package com.xht.cmsdk.network;

import java.util.Map;

/**
 * Created by XIE on 2018/8/10.
 */

public class ConnParams {
    private String connUrl = null;
    private Map<String, String> params = null;

    public ConnParams(final String url, final Map<String, String> paramMap){
        connUrl = url;
        params = paramMap;
    }

    public String getConnUrl() {
        return connUrl;
    }

    public void setConnUrl(String connUrl) {
        this.connUrl = connUrl;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
