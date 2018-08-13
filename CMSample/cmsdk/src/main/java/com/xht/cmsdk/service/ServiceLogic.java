package com.xht.cmsdk.service;

import android.util.ArrayMap;

import com.xht.cmsdk.CMLog;
import com.xht.cmsdk.CMParams;
import com.xht.cmsdk.CMSDK;
import com.xht.cmsdk.callback.CMEventListener;
import com.xht.cmsdk.enums.NetworkType;
import com.xht.cmsdk.network.ConnParams;
import com.xht.cmsdk.network.IServerConnect;
import com.xht.cmsdk.network.ServerConnectFactory;
import com.xht.cmsdk.utils.CMUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by XIE on 2018/8/10.
 */

public class ServiceLogic {

    public static void getAccessToken(final CMParams cmParams, final CMEventListener listener){
        final String path = "https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String, String> paramMap = new ArrayMap<>();
        paramMap.put("appid", cmParams.getAppID());
        paramMap.put("secret", cmParams.getAppSecret());
        paramMap.put("code", cmParams.getCode());
        paramMap.put("grant_type", "authorization_code");


        IServerConnect connect = ServerConnectFactory.createConnect(NetworkType.HttpsUrlConnection);
        connect.getRequest(new ConnParams(path, CMUtil.map_to_string(paramMap, "utf-8")), new IServerConnect.Callback<String>(){

            @Override
            public void onSuccess(String jsonStr) {
                CMLog.log("ServiceLogic:getAccessToken", jsonStr);
                try {
                    JSONObject object = new JSONObject(jsonStr);

                    cmParams.setAccessToken("");
                    cmParams.setOpenId("");
                    getUserInfo(cmParams, listener);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String jsonStr) {
                listener.onEventFailed(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_LOGIN_TOKEN_FAIL);
            }
        });
    }

    private static void getUserInfo(final CMParams cmParams, final CMEventListener listener){
        final String path = "https://api.weixin.qq.com//sns/userinfo";
        Map<String, String> paramMap = new ArrayMap<>();
        paramMap.put("access_token", cmParams.getAccessToken());
        paramMap.put("openid", cmParams.getOpenId());

        IServerConnect connect = ServerConnectFactory.createConnect(NetworkType.HttpsUrlConnection);
        connect.getRequest(new ConnParams(path, CMUtil.map_to_string(paramMap, "utf-8")), new IServerConnect.Callback() {
            @Override
            public void onSuccess(String jsonStr) {
                listener.onEventSuccess(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_LOGIN_SUCCESS, jsonStr);
            }

            @Override
            public void onFailed(String jsonStr) {
                listener.onEventFailed(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_LOGIN_USERINFO_FAIL);
            }
        });
    }

}
