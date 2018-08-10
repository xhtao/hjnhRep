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


    public static final String WECHAT_LOGIN_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_LOGIN_RESULT_ACTION";
    public static final String WECHAT_LOGIN_RESULT_TYPE = "com.tencent.mm.opensdk.WECHAT_LOGIN_RESULT_TYPE";
    public static final String WECHAT_LOGIN_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_LOGIN_RESULT_EXTRA";
    public static final String WECHAT_LOGIN_RESULT_CODE = "com.tencent.mm.opensdk.WECHAT_LOGIN_RESULT_CODE";

    public static final String WECHAT_PAY_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_ACTION";
    public static final String WECHAT_PAY_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_EXTRA";

    public static final String WECHAT_SHARE_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_SHARE_RESULT_ACTION";
    public static final String WECHAT_SHARE_RESULT_TYPE = "com.tencent.mm.opensdk.WECHAT_SHARE_RESULT_TYPE";
    public static final String WECHAT_SHARE_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_SHARE_RESULT_EXTRA";
    public static final String WECHAT_SHARE_RESULT_CODE = "com.tencent.mm.opensdk.WECHAT_SHARE_RESULT_CODE";

    public BaseModule(final CMParams params, CMEventListener iListener){
        cmParams = params;
        listener = iListener;
    }

    protected void registerResultBroadcast(final Context context, final String action){
        if (mReceiver == null){ return; }
        mBroadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter filter = new IntentFilter(action);
        mBroadcastManager.registerReceiver(mReceiver, filter);
    }

    protected void unRegisterResultBroadcast(){
        if (mBroadcastManager != null && mReceiver != null) {
            mBroadcastManager.unregisterReceiver(mReceiver);
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
