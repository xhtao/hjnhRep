package com.xht.cmsdk.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.xht.cmsdk.CMParams;
import com.xht.cmsdk.callback.CMEventListener;

/**
 * Created by XIE on 2018/8/9.
 */

public abstract class BaseModule implements IModule{
    protected LocalBroadcastManager mBroadcastManager = null;
    protected BroadcastReceiver mReceiver = null;

    protected CMParams cmParams = null;
    protected CMEventListener listener = null;

    public static String weChat_result_action = null;

    public static final String WECHAT_LOGIN_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_LOGIN_RESULT_ACTION";
    public static final String WECHAT_PAY_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_ACTION";
    public static final String WECHAT_SHARE_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_SHARE_RESULT_ACTION";

    public static final String WECHAT_RESULT_TYPE = "com.tencent.mm.opensdk.WECHAT_RESULT_TYPE";
    public static final String WECHAT_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_RESULT_EXTRA";
    public static final String WECHAT_RESULT_CODE = "com.tencent.mm.opensdk.WECHAT_RESULT_CODE";


    public BaseModule(){
    }

    public BaseModule setParams(final CMParams params){
        cmParams = params;
        return this;
    }

    public BaseModule setListener(CMEventListener iListener){
        listener = iListener;
        return this;
    }

    public BaseModule create(){
        return this;
    }

    protected void registerResultBroadcast(final Context context, final String action){
        if (mReceiver == null){ return; }
        weChat_result_action = action;
        mBroadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter filter = new IntentFilter(action);
        mBroadcastManager.registerReceiver(mReceiver, filter);
    }

    protected void unRegisterResultBroadcast(){
        if (mBroadcastManager != null && mReceiver != null) {
            mBroadcastManager.unregisterReceiver(mReceiver);
            weChat_result_action = null;
            mBroadcastManager = null;
            mReceiver = null;
        }
    }

    @Override
    public abstract void doLogin();

    @Override
    public abstract void doPay();

    @Override
    public abstract void doShare();
}
