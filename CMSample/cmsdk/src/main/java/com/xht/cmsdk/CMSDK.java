package com.xht.cmsdk;

import com.xht.cmsdk.callback.CMEventListener;
import com.xht.cmsdk.module.strategy.StrategyWeChat;

/**
 * Created by XIE on 2018/8/8.
 */

public final class CMSDK {
    private static CMSDK instance = null;

    private CMParams mParams = null;
    private CMEventListener eventListener = null;

    private CMSDK(){  }

    /**
     * Singleton mode
     * @return
     */
    public static CMSDK getInstance(){
        if (instance == null){
            instance = new CMSDK();
        }
        return instance;
    }

    public String getAppId(){
        return mParams.getAppID();
    }

    public CMSDK setParams(final CMParams params){
        mParams = params;
        return this;
    }

    public CMSDK setEventListener(CMEventListener eventListener) {
        this.eventListener = eventListener;
        return this;
    }

    public CMSDK CMLogin(){
        StrategyWeChat login = StrategyWeChat.getInstance(mParams, eventListener);
        login.doLogin();
        return this;
    }

    public CMSDK CMShare(){
        StrategyWeChat login = StrategyWeChat.getInstance(mParams, eventListener);
        login.doShare();
        return this;
    }

    public class CMErrorCode{
        /*通用返回码：-1开始*/
        public static final int ERROR_CODE_DEFAULT = -1;//默认，未知错误

        /*微信返回码：1000开始*/
        public static final int ERROR_CODE_WECHAT_UNREGISTER = 1000;//IWXAPI未注册
        public static final int ERROR_CODE_WECHAT_UNINSTALL = 1001;//微信未安装

        public static final int ERROR_CODE_WECHAT_SHARE_SUCCESS = 1002;//微信分享成功
        public static final int ERROR_CODE_WECHAT_SHARE_FAILED = 1003;//微信分享失败
        public static final int ERROR_CODE_WECHAT_SHARE_CANCEL = 1004;//微信分享取消

        public CMErrorCode(){}
    }
}
