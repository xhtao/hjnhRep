package com.xht.cmsdk;

/**
 * Created by XIE on 2018/8/8.
 */

public final class CMSDK {
    private static CMSDK instance = null;

    private CMParams mParams = null;

    private CMSDK(final CMParams params){ mParams = params; }

    /**
     * Singleton mode
     * @return
     */
    public static CMSDK getInstance(final CMParams params){
        if (instance == null){
            instance = new CMSDK(params);
        }
        return instance;
    }

    public String getAppId(){
        return mParams.getAppID();
    }

    public void CMLogin(){

    }
}
