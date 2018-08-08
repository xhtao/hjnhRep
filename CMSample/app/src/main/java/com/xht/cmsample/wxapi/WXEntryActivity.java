package com.xht.cmsample.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xht.cmsdk.CMSDK;

/**
 * Created by XIE on 2018/8/8.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "com.xht.cmsample.wxapi.WXPayEntryActivity";
    private IWXAPI mWxApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWxApi = WXAPIFactory.createWXAPI(this, CMSDK.getInstance(null).getAppId());
        mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }
}
