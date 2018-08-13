package com.xht.cmsdk.network;

import java.util.Map;

/**
 * Created by XIE on 2018/8/10.
 */

public class ConnParams {
    private String connUrl = null;
    private String params = null;

    public ConnParams(final String url, final String paramMap){
        connUrl = url;
        params = paramMap;
    }

    public String getConnUrl() {
        return connUrl;
    }

    public void setConnUrl(String connUrl) {
        this.connUrl = connUrl;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
