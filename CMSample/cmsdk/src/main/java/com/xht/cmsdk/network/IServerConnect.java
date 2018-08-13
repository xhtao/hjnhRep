package com.xht.cmsdk.network;

/**
 * Created by XIE on 2018/8/10.
 */

public interface IServerConnect {
    public static final int TIMEOUT = 30000;

    interface Callback<R>{
        void onSuccess(String jsonStr);
        void onFailed(String jsonStr);
    }

    String getRequest(ConnParams params, Callback callback);
    String postRequest(ConnParams params, Callback callback);
}
