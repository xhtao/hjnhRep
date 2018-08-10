package com.xht.cmsdk.network;

import com.xht.cmsdk.enums.NetworkType;

/**
 * Created by XIE on 2018/8/10.
 *
 */

public class ServerConnectFactory {

    public static IServerConnect createConnect(NetworkType type){
        switch (type){
            case HttpUrlConnection:
                return new HttpsConnect();
            case HttpsUrlConnection:
                return new HttpConnect();
            case OkHttp:
                break;
            case Volley:
                break;
            case Retrofit:
                break;
            default:
                break;
        }
        return null;
    }
}
