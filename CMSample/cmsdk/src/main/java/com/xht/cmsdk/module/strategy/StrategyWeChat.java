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
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xht.cmsdk.CMLog;
import com.xht.cmsdk.CMSDK;
import com.xht.cmsdk.enums.ErrorCodes;
import com.xht.cmsdk.enums.ShareType;
import com.xht.cmsdk.error.CMErrorFactory;
import com.xht.cmsdk.module.BaseModule;
import com.xht.cmsdk.service.ServiceLogic;
import com.xht.cmsdk.tasks.WeChatLoginTask;
import com.xht.cmsdk.tasks.taskrely.TaskParams;

import java.io.ByteArrayOutputStream;

/**
 * Created by XIE on 2018/8/9.
 */

public class StrategyWeChat extends BaseModule {
    private static StrategyWeChat instance = null;
    private Context mContext = null;
    private IWXAPI iwxapi = null;

    private StrategyWeChat() {
        super();
    }

    public static StrategyWeChat getInstance(){
        if (instance == null){
            instance = new StrategyWeChat();
        }
        return instance;
    }

    @Override
    public BaseModule create() {
        mContext = cmParams.getActivity();
        initWXAPI();
        initBroadcast();
        return super.create();
    }

    private void initWXAPI(){
        iwxapi = WXAPIFactory.createWXAPI(mContext, cmParams.getAppID(), true);
        iwxapi.registerApp(cmParams.getAppID());
    }

    /**
     * 广播
     */
    @Override
    protected void initBroadcast(){
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int type = intent.getIntExtra(WECHAT_RESULT_TYPE, -100);
                int errorCode = intent.getIntExtra(WECHAT_RESULT_EXTRA, -100);

                CMLog.log("com.xht.cmsdk.module.strategy.StrategyWeChat", "onReceive", "授权成功返回", "获取access_token和user info", type+"");
                switch (type){
                    case ConstantsAPI.COMMAND_SENDAUTH://登陆
                        String code = intent.getStringExtra(WECHAT_RESULT_CODE);
                        onWeChatLoginResult(errorCode, code);
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX://分享
                        onWeChatShareResult(errorCode);
                        break;
                    case ConstantsAPI.COMMAND_PAY_BY_WX:
                        onWeChatPayResult(errorCode);
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
                //请求access_token
                ServiceLogic.getAccessToken(cmParams, listener);
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
     * 支付结果响应
     * @param errorCode
     */
    private void onWeChatPayResult(final int errorCode){
        switch (errorCode){
            case BaseResp.ErrCode.ERR_OK://分享成功
                listener.onEventSuccess(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Success, null, cmParams, null));
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://分享失败
                listener.onEventFailed(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Failed, null, cmParams, null));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://分享取消
                listener.onEventCancel(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Cancel, null, cmParams, null));
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
                listener.onEventSuccess(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Success, null, cmParams, null));
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://分享失败
                listener.onEventFailed(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Failed, null, cmParams, null));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://分享取消
                listener.onEventCancel(CMErrorFactory.createCMError(ErrorCodes.Error_Code_Action_Cancel, null, cmParams, null));
                break;
        }
    }

    @Override
    public void doLogin() {
        if (!checkOperateValidity()){ return; }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = cmParams.getUserSecret();
        req.state = cmParams.getWXState();
        iwxapi.sendReq(req);
        //注册本地广播
        registerResultBroadcast(mContext, WECHAT_LOGIN_RESULT_ACTION);
    }

    @Override
    public void doPay() {
        if (!checkOperateValidity()){ return; }
        TaskParams params = new TaskParams();
        params.setAppID(cmParams.getAppID());
        params.setMchID(cmParams.getMchID());
        params.setTradeOutNum(cmParams.getOrderNum());
        params.setTradeName(cmParams.getItemName());
        params.setTradeDetail(cmParams.getItemDetail());
        params.setTradePrice(cmParams.getItemPrice());
        params.setSign(cmParams.getSignKey());
        params.setCreateIp("14.23.150.211");
        params.setNonceStr("1add1a30ac87aa2db72f57a2375d8fec");
        params.setNotifyUrl(cmParams.getNotifyUrl());

        new WeChatLoginTask(mContext).execute(params);
    }

    /**
     *
     * @param params
     */
    public void wxPayDoing(TaskParams params){
        PayReq request = new PayReq();
        request.appId = params.getAppID();
        request.partnerId = params.getMchID();
        request.prepayId= params.getPrepayID();
        request.packageValue = "Sign=WXPay";
        request.nonceStr=params.getNonceStr();
        request.timeStamp= params.getTimeStamp();
        request.sign= params.getSign();
        iwxapi.sendReq(request);
        //注册本地广播
        registerResultBroadcast(mContext, WECHAT_PAY_RESULT_ACTION);
    }

    @Override
    public void doShare() {
        if (!checkOperateValidity()){ return; }

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

    /**
     *
     * @return
     */
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

    /**
     *
     * @param type
     * @return
     */
    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
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

    private boolean checkOperateValidity(){
        if (iwxapi == null) {
            listener.onEventFailed(CMErrorFactory.createCMError(ErrorCodes.Error_Code_UnRegister, null, cmParams, null));
            return false;
        }
        if (!iwxapi.isWXAppInstalled()){
            listener.onEventFailed(CMErrorFactory.createCMError(ErrorCodes.Error_Code_UnInstall, null, cmParams, null));
            return false;
        }
        return true;
    }
}
