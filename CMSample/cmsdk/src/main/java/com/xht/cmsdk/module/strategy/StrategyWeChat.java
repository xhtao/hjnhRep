package com.xht.cmsdk.module.strategy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xht.cmsdk.CMLog;
import com.xht.cmsdk.CMParams;
import com.xht.cmsdk.CMSDK;
import com.xht.cmsdk.callback.CMEventListener;
import com.xht.cmsdk.enums.ShareType;
import com.xht.cmsdk.module.BaseModule;

import java.io.ByteArrayOutputStream;

/**
 * Created by XIE on 2018/8/9.
 */

public class StrategyWeChat extends BaseModule {
    private static StrategyWeChat instance = null;
    private Context mContext = null;
    private IWXAPI iwxapi = null;

    private StrategyWeChat(final CMParams params, final CMEventListener iListener) {
        super(params, iListener);
        mContext = params.getActivity();
        initWXAPI();
        initBroadcast();
    }

    public static StrategyWeChat getInstance(final CMParams params, final CMEventListener iListener){
        if (instance == null){
            instance = new StrategyWeChat(params, iListener);
        }
        return instance;
    }

    private void initWXAPI(){
        iwxapi = WXAPIFactory.createWXAPI(mContext, cmParams.getAppID(), true);
        iwxapi.registerApp(cmParams.getAppID());
    }

    /**
     * 广播
     */
    private void initBroadcast(){
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int type = intent.getIntExtra(WECHAT_LOGIN_RESULT_TYPE, -100);
                int errorCode = intent.getIntExtra(WECHAT_LOGIN_RESULT_EXTRA, -100);

                CMLog.log("com.xht.cmsdk.module.strategy.StrategyWeChat", "onReceive", "授权成功返回", "获取access_token和user info", type+"");
                switch (type){
                    case ConstantsAPI.COMMAND_SENDAUTH://登陆
                        String code = intent.getStringExtra(WECHAT_LOGIN_RESULT_CODE);
                        onWeChatLoginResult(errorCode, code);
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX://分享
                        onWeChatShareResult(errorCode);
                        break;
                }
                unRegisterResultBroadcast();
            }
        };
    }

    /**
     * 登陆结果响应
     * @param errorCode
     * @param code
     */
    private void onWeChatLoginResult(final int errorCode, final String code){
        switch (errorCode){
            case BaseResp.ErrCode.ERR_OK://用户同意
                CMLog.log("onWeChatLoginResult", "用户同意");
                listener.onRequestStart();

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                CMLog.log("onWeChatLoginResult", "用户拒绝授权");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                CMLog.log("onWeChatLoginResult", "用户取消");
                break;
        }
    }

    /**
     * 分享结果响应
     * @param errorCode
     */
    private void onWeChatShareResult(final int errorCode){
        switch (errorCode){
            case BaseResp.ErrCode.ERR_OK://分享成功
                CMLog.log("onWeChatShareResult", "分享成功");
                listener.onEventSuccess(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_SHARE_SUCCESS, null);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://分享失败
                CMLog.log("onWeChatShareResult", "分享失败");
                listener.onEventFailed(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_SHARE_FAILED);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://分享取消
                CMLog.log("onWeChatShareResult", "分享取消");
                listener.onEventCancel(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_SHARE_CANCEL);
                break;
        }
    }

    @Override
    public void doLogin() {
        if (iwxapi == null) {
            listener.onEventFailed(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_UNREGISTER);
            return;
        }
        if (!iwxapi.isWXAppInstalled()){
            listener.onEventFailed(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_UNINSTALL);
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = cmParams.getUserSecret();
        req.state = cmParams.getWXState();
        iwxapi.sendReq(req);
        //注册本地广播
        registerResultBroadcast(mContext, WECHAT_LOGIN_RESULT_ACTION);
    }

    @Override
    public void doPay() {

    }

    @Override
    public void doShare() {
        if (iwxapi == null) {
            listener.onEventFailed(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_UNREGISTER);
            return;
        }
        if (!iwxapi.isWXAppInstalled()){
            listener.onEventFailed(CMSDK.CMErrorCode.ERROR_CODE_WECHAT_UNINSTALL);
            return;
        }

        WXMediaMessage.IMediaObject object = getShareObject();

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.title = cmParams.getShareTitle();
        mediaMessage.description = cmParams.getShareTitle();
        mediaMessage.mediaObject = object;

        if (cmParams.getShareType() == ShareType.Image || cmParams.getShareType() == ShareType.WebPage){
            Bitmap thumbBmp = Bitmap.createScaledBitmap(cmParams.getShareBitmap(), cmParams.getThumbSize(), cmParams.getThumbSize(), true);
            cmParams.getShareBitmap().recycle();
            mediaMessage.thumbData = bmpToByteArray(thumbBmp, true);
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(cmParams.getShareType().toString());
        req.message = mediaMessage;
        req.scene = cmParams.getSharePosition();
        iwxapi.sendReq(req);

        //注册本地广播
        registerResultBroadcast(mContext, WECHAT_SHARE_RESULT_ACTION);
    }

    private WXMediaMessage.IMediaObject getShareObject(){
        switch (cmParams.getShareType()){
            case Text:
                WXTextObject textObject = new WXTextObject();
                textObject.text = cmParams.getShareDescription();
                return textObject;
            case Image:
                WXImageObject imageObject = new WXImageObject(cmParams.getShareBitmap());
                return imageObject;
            case WebPage:
                WXWebpageObject webpageObject = new WXWebpageObject();
                webpageObject.webpageUrl = cmParams.getShareUrl();
                return webpageObject;
            case Music:
                return null;
            case Video:
                return null;
            default:
                return null;
        }
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
