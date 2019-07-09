package com.xht.cmsdk.module.strategy;

import android.app.Activity;
import android.content.Context;

import com.alipay.sdk.app.PayTask;
import com.xht.cmsdk.module.BaseModule;

/**
 * Created by XIE on 2018/8/20.
 */

public class StrategyAli extends BaseModule {
    private static StrategyAli instance = null;

    private Context mContext = null;

    private StrategyAli(){}

    public static StrategyAli getInstance(){
        if (instance == null){
            synchronized (StrategyAli.class){
                if (instance == null){
                    instance = new StrategyAli();
                }
            }
        }
        return instance;
    }

    @Override
    public BaseModule create() {
        mContext = cmParams.getActivity();
        initBroadcast();
        return super.create();
    }

    @Override
    protected void initBroadcast() {
    }

    @Override
    public void doLogin() {

    }

    @Override
    public void doPay() {
        PayTask payTask = new PayTask((Activity) mContext);

    }

    @Override
    public void doShare() {

    }
}
