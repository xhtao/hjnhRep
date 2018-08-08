package com.xht.cmsdk;

import android.app.Activity;

/**
 * Created by XIE on 2018/8/8.
 */

public class CMParams {
    private Activity        activity           = null;
    private String          appID               = null;
    private String          appSecret           = null;
    private final String    USERSECRET          = "snsapi_userinfo";

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUserSecret() {
        return USERSECRET;
    }

    public static class Builder{
        private Activity        activity            = null;
        private String          appID               = null;
        private String          appSecret           = null;

        public Builder(Activity mActivity){
            activity = mActivity;
        }

        public CMParams.Builder appID(final String appid){
            appID = appid;
            return this;
        }

        public CMParams.Builder appSecret(final String secret){
            appSecret = secret;
            return this;
        }

        public CMParams build(){
            CMParams params = new CMParams();

            params.setActivity(activity);
            params.setAppID(appID);
            params.setAppSecret(appSecret);

            return params;
        }
    }
}
