package com.xht.cmsdk.network;

/**
 * Created by XIE on 2018/8/10.
 */

public interface IServerConnect {
    interface Callback<R>{
        void onSuccess(String jsonStr);
        void onFailed(String jsonStr);
    }

    void getRequest(ConnParams params, Callback callback);
    void postRequest(ConnParams params, Callback callback);
}
