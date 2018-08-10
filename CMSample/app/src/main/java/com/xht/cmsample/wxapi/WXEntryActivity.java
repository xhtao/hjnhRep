package com.xht.cmsample.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xht.cmsdk.CMSDK;
import com.xht.cmsdk.module.BaseModule;

/**
 * Created by XIE on 2018/8/8.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "com.xht.cmsample.wxapi.WXPayEntryActivity";
    private IWXAPI mWxApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWxApi = WXAPIFactory.createWXAPI(this, CMSDK.getInstance().getAppId());
        mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWxApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        int errorCode = baseResp.errCode;
        int type = baseResp.getType();
        String code = null;
        if (type == ConstantsAPI.COMMAND_SENDAUTH) { code = ((SendAuth.Resp) baseResp).code; }
        sendWeChatBroadcast(errorCode, type, code);
    }

    private void sendWeChatBroadcast(final int errorCode, final int type, final String code){
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        Intent weChatResult = new Intent();
        weChatResult.setAction(BaseModule.WECHAT_LOGIN_RESULT_ACTION);
        weChatResult.putExtra(BaseModule.WECHAT_LOGIN_RESULT_TYPE, type);
        weChatResult.putExtra(BaseModule.WECHAT_LOGIN_RESULT_EXTRA, errorCode);
        weChatResult.putExtra(BaseModule.WECHAT_LOGIN_RESULT_CODE, code);
        broadcastManager.sendBroadcast(weChatResult);
        finish();
    }
}
