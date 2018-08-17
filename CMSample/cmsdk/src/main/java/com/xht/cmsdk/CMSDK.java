package com.xht.cmsdk;

import android.content.Intent;

import com.xht.cmsdk.callback.CMEventListener;
import com.xht.cmsdk.enums.ChannelType;
import com.xht.cmsdk.module.BaseModule;
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
        BaseModule login = ModuleFactory.createModule(mParams.getChannelType());
        login.setParams(mParams)
                .setListener(eventListener)
                .create()
                .doLogin();
        return this;
    }

    public CMSDK CMShare(){
        BaseModule share = ModuleFactory.createModule(mParams.getChannelType());
        share.setParams(mParams)
                .setListener(eventListener)
                .create()
                .doShare();
        return this;
    }

    public CMSDK CMPay(){
        BaseModule pay = ModuleFactory.createModule(mParams.getChannelType());
        pay.setParams(mParams)
                .setListener(eventListener)
                .create()
                .doPay();
        return this;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        BaseModule module = ModuleFactory.createModule(mParams.getChannelType());
        module.onActivityResult(requestCode, resultCode, data);
    }



    public class CMErrorCode{
        /*通用返回码：-1开始*/
        public static final int ERROR_CODE_DEFAULT = -1;//默认，未知错误
        public static final int ERROR_CODE_THIRD_SDK_UNREGISTER = -2;//第三方SDK未注册
        public static final int ERROR_CODE_THIRD_APP_UNINSTALL = -3;//应用未安装

        public static final int ERROR_CODE_SHARE_SUCCESS = 1;//分享成功
        public static final int ERROR_CODE_SHARE_FAILED = 2;//分享失败
        public static final int ERROR_CODE_SHARE_CANCEL = 3;//分享取消

        /*微信返回码：1001开始*/
        public static final int ERROR_CODE_WECHAT_SHARE_SUCCESS = 1001;//微信分享成功
        public static final int ERROR_CODE_WECHAT_SHARE_FAILED = 1002;//微信分享失败
        public static final int ERROR_CODE_WECHAT_SHARE_CANCEL = 1003;//微信分享取消

        public static final int ERROR_CODE_WECHAT_LOGIN_SUCCESS = 1004;//登陆成功
        public static final int ERROR_CODE_WECHAT_LOGIN_TOKEN_FAIL = 1005;//获取token失败
        public static final int ERROR_CODE_WECHAT_LOGIN_USERINFO_FAIL = 1006;//获取user info失败

        public static final int ERROR_CODE_WECHAT_PAY_SUCCESS = 1007;//微信支付成功
        public static final int ERROR_CODE_WECHAT_PAY_FAILED = 1008;//微信支付失败
        public static final int ERROR_CODE_WECHAT_PAY_CANCEL = 109;//微信支付取消
        public static final int ERROR_CODE_WECHAT_PAY_ORDER = 1010;//微信支付下单成功

        /*腾讯QQ返回码：2001开始*/
        public static final int ERROR_CODE_QQ_LOGIN_SUCCESS = 2001;//登陆成功
        public static final int ERROR_CODE_QQ_LOGIN_FAIL = 2002;//登陆失败
        public static final int ERROR_CODE_QQ_LOGIN_CANCEL = 2003;//登陆取消
        public static final int ERROR_CODE_QQ_LOGIN_USERINFO_FAIL = 2004;//获取user info失败

        public CMErrorCode(){}
    }
}
