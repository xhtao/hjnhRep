package com.xht.cmsdk.module.strategy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xht.cmsdk.CMLog;
import com.xht.cmsdk.CMSDK;
import com.xht.cmsdk.enums.ErrorCodes;
import com.xht.cmsdk.enums.ShareType;
import com.xht.cmsdk.error.CMError;
import com.xht.cmsdk.error.CMErrorFactory;
import com.xht.cmsdk.module.BaseModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by XIE on 2018/8/14.
 */

public class StrategyQQ extends BaseModule {
    private static StrategyQQ instance = null;
    /*是否在未安装QQ的情况下允许使用二维码：true允许 false不允许*/
    private boolean allowUseQRCode = true;

    private Context mContext = null;
    private Tencent mTencent = null;
    private UserInfo userInfo = null;

    private StrategyQQ(){}
    public static StrategyQQ getInstance(){
        if (instance == null){
            synchronized (StrategyQQ.class){
                if (instance == null){
                    instance = new StrategyQQ();
                }
            }
        }
        return instance;
    }

    @Override
    public BaseModule create() {
        mContext = cmParams.getActivity();
        initTencent();
        initBroadcast();
        return super.create();
    }

    private void initTencent(){
        mTencent = Tencent.createInstance(cmParams.getAppID(), mContext);
    }

    @Override
    protected void initBroadcast() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int type = intent.getIntExtra(WECHAT_RESULT_TYPE, -100);
                int errorCode = intent.getIntExtra(WECHAT_RESULT_EXTRA, -100);

                unRegisterResultBroadcast();
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IUiListener iUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                listener.onEventCancel(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Success, null, cmParams, o.toString()));
            }

            @Override
            public void onError(UiError uiError) {
                listener.onEventCancel(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Failed, uiError.errorMessage, cmParams, null));
            }

            @Override
            public void onCancel() {
                listener.onEventCancel(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Cancel, null, cmParams, null));
            }
        };
        Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, iUiListener);
            }
        }
    }

    private IUiListener universally = new IUiListener() {
        @Override
        public void onComplete(Object o) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };

    private void updateUserInfo(){
        if (mTencent != null && mTencent.isSessionValid()){
            userInfo = new UserInfo(mContext, mTencent.getQQToken());
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    listener.onEventSuccess(CMErrorFactory.createCMError(ErrorCodes.Error_Code_GetUserInfo_Success, null, cmParams, o.toString()));
                }

                @Override
                public void onError(UiError uiError) {
                    listener.onEventSuccess(CMErrorFactory.createCMError(ErrorCodes.Error_Code_GetUserInfo_Failed, uiError.errorMessage, cmParams, null));
                }

                @Override
                public void onCancel() {
                    listener.onEventSuccess(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Cancel, null, cmParams, null));
                }
            });
        }
    }

    private void initOpenIdAndToken(JSONObject object){
        try {
            String openId = object.getString(Constants.PARAM_OPEN_ID);
            String token = object.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = object.getString(Constants.PARAM_EXPIRES_IN);
            if (!TextUtils.isEmpty(openId) && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)){
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doLogin() {
        if (!checkedValid()){ return; }
        if (!mTencent.isSessionValid()){
            mTencent.login((Activity) mContext, "all", new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    initOpenIdAndToken((JSONObject) o);
                    updateUserInfo();
                }

                @Override
                public void onError(UiError uiError) {
                    CMLog.log("错误返回：" + uiError.errorCode, uiError.errorMessage, uiError.errorDetail);
                    listener.onEventSuccess(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Failed, uiError.errorMessage, cmParams, null));
                }

                @Override
                public void onCancel() {
                    CMLog.log("取消返回");
                    listener.onEventCancel(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Cancel, null, cmParams, null));
                }
            }, allowUseQRCode);
        }else{
            mTencent.logout(mContext);
            updateUserInfo();
        }
    }

    @Override
    public void doPay() {
        if (!checkedValid()){ return; }

    }

    @Override
    public void doShare() {
        if (!checkedValid()){ return; }

        IUiListener qqlistener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                listener.onEventSuccess(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Success, null, cmParams, o.toString()));
            }

            @Override
            public void onError(UiError uiError) {
                listener.onEventFailed(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Failed, uiError.errorMessage, cmParams, null));
            }

            @Override
            public void onCancel() {
                listener.onEventCancel(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Cancel, null, cmParams, null));
            }
        };

        final Bundle shareParam = getShareBundle();

        if (cmParams.getSharePosition() == 0){//分享给好友
            mTencent.shareToQQ((Activity) mContext, shareParam, qqlistener);
        }else if(cmParams.getSharePosition() == 1){//分享到朋友圈
            mTencent.shareToQzone((Activity) mContext, shareParam, qqlistener);
        }
    }

    private Bundle getShareBundle(){
        final Bundle shareParam = new Bundle();
        //////////////////////////////////////////////
        int shareType = -1;
        if (cmParams.getShareType().equals(ShareType.TextAndImage)){
            shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
        }else if (cmParams.getShareType().equals(ShareType.Image)){
            shareType = QQShare.SHARE_TO_QQ_TYPE_IMAGE;
        }else if (cmParams.getShareType().equals(ShareType.Music)){
            shareType = QQShare.SHARE_TO_QQ_TYPE_AUDIO;
        }else if (cmParams.getShareType().equals(ShareType.APP)){
            shareType = QQShare.SHARE_TO_QQ_TYPE_APP;
        }
        shareParam.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
        //////////////////////////////////////////////
        shareParam.putString(QQShare.SHARE_TO_QQ_TITLE, cmParams.getShareTitle());
        shareParam.putString(QQShare.SHARE_TO_QQ_SUMMARY, cmParams.getShareDescription());
        shareParam.putString(QQShare.SHARE_TO_QQ_TARGET_URL, cmParams.getShareUrl());
        //////////////////////////////////////////////
        if (cmParams.getSharePosition() == 0){//分享给好友
            ArrayList<String> imgList = cmParams.getShareImgUrl();
            shareParam.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgList.get(0));
        }else if(cmParams.getSharePosition() == 1){//分享到朋友圈
            shareParam.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL, cmParams.getShareImgUrl());
        }
//        shareParam.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL, cmParams.getShareImgUrl());
        //////////////////////////////////////////////
        shareParam.putString(QQShare.SHARE_TO_QQ_APP_NAME, cmParams.getAppName());
        shareParam.putString(QQShare.SHARE_TO_QQ_EXT_STR, "其他附加功能");
        return shareParam;
    }

    /**
     * 检查：
     * 1. qq sdk是否已经初始化
     * 2. 是否已经安装QQ应用（不允许使用二维码登陆的情况下）
     * @return
     */
    private boolean checkedValid(){
        if (mTencent == null) {
            listener.onEventFailed(CMErrorFactory.createCMError(ErrorCodes.Error_Code_UnRegister, null, cmParams, null));
            return false;
        }
        if (!allowUseQRCode && !mTencent.isQQInstalled(mContext)){
            listener.onEventFailed(CMErrorFactory.createCMError(ErrorCodes.Error_Code_UnInstall, null, cmParams, null));
            return false;
        }
        return true;
    }
}
