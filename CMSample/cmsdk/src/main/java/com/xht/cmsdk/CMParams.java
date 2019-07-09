package com.xht.cmsdk;

import android.app.Activity;
import android.graphics.Bitmap;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.xht.cmsdk.enums.ChannelType;
import com.xht.cmsdk.enums.OperateType;
import com.xht.cmsdk.enums.ShareType;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by XIE on 2018/8/8.
 */

public class CMParams {
    private Activity        activity            = null;
    private String          appName             = null;
    private String          appID               = null;
    private ChannelType     channelType         = null;
    private OperateType     operateType         = null;
    private final int       THUMB_SIZE          = 150;//缩略图大小--只读
    //登陆
    private String          appSecret           = null;
    private final String    USERSECRET          = "snsapi_userinfo";//--只读
    private final String    WXSTATE             = "com_xht_cmsdk_state";//--只读
    private String          code                = null;//请求token的code
    private String          accessToken         = null;//token
    private String          openId              = null;//openId

    //支付
    private String          mchID               = null;
    private String          orderNum            = null;//未知支付的随机串，支付宝支付的支付订单信息
    private String          itemName            = null;
    private String          itemDetail          = null;
    private int             itemPrice           = 0;
    private String          notifyUrl           = null;
    private String          signKey             = null;//商户平台设置的密钥key


    //分享
    private ShareType       shareType           = null;
    private ShareType       sharePosition       = null;
    private String          shareTitle          = null;
    private String          shareDescription    = null;
    private Bitmap          shareBitmap         = null;
    private String          shareUrl            = null;//地址，可能是：音乐、视频、web的url
    private ArrayList<String> shareImgUrl         = null;//分享图片的地址

////////////////////////////////////////////////////////////////////////////////////////////////////
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public OperateType getOperateType() {
        return operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public ShareType getShareType() {
        return shareType;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public ArrayList<String> getShareImgUrl() {
        return shareImgUrl;
    }

    public void setShareImgUrl(ArrayList<String> shareImgUrl) {
        this.shareImgUrl = shareImgUrl;
    }

    /**
     * 默认分享到朋友圈
     * @return
     */
    public int getSharePosition() {
        switch (sharePosition){
            case Session:
                return SendMessageToWX.Req.WXSceneSession;
            case Timeline:
                return SendMessageToWX.Req.WXSceneTimeline;
            case Favorite:
                return SendMessageToWX.Req.WXSceneFavorite;
            default:
                return SendMessageToWX.Req.WXSceneTimeline;
        }
    }

    public void setSharePosition(ShareType sharePosition) {
        this.sharePosition = sharePosition;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }

    public Bitmap getShareBitmap() {
        return shareBitmap;
    }

    public void setShareBitmap(Bitmap shareBitmap) {
        this.shareBitmap = shareBitmap;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void setShareType(ShareType shareType) {
        this.shareType = shareType;
    }
//////////////////////////////////////////////以下为只读/////////////////////////////////////////////
    public String getUserSecret() {
        return USERSECRET;
    }

    public String getWXState() {
        return WXSTATE;
    }

    public int getThumbSize() {
        return THUMB_SIZE;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 支付数据builder
     */
    public static class PayBuilder{
        private Activity        activity            = null;
        private String          appID               = null;
        private String          appSecret           = null;
        private ChannelType     channelType         = null;
        private OperateType     operateType         = null;
        private String          mchID               = null;
        private String          orderNum            = null;
        private String          itemName            = null;
        private String          itemDetail          = null;
        private int             itemPrice           = 0;
        private String          notifyUrl           = null;
        private String          signKey             = null;//商户平台设置的密钥key

        public PayBuilder(Activity mActivity){
            activity = mActivity;
        }

        public CMParams.PayBuilder appID(final String appid){
            appID = appid;
            return this;
        }

        public CMParams.PayBuilder appSecret(final String secret){
            appSecret = secret;
            return this;
        }

        public CMParams.PayBuilder channelType(final ChannelType type){
            channelType = type;
            return this;
        }

        public CMParams.PayBuilder operateType(final OperateType type){
            operateType = type;
            return this;
        }

        public CMParams.PayBuilder mchID(final String mchid){
            mchID = mchid;
            return this;
        }

        public CMParams.PayBuilder orderNum(final String ordernum){
            orderNum = ordernum;
            return this;
        }

        public CMParams.PayBuilder itemName(final String itemname){
            itemName = itemname;
            return this;
        }

        public CMParams.PayBuilder itemDetail(final String itemdetail){
            itemDetail = itemdetail;
            return this;
        }

        public CMParams.PayBuilder itemPrice(final int itemprice){
            itemPrice = itemprice;
            return this;
        }

        public CMParams.PayBuilder notifyUrl(final String notifyurl){
            notifyUrl = notifyurl;
            return this;
        }

        public CMParams.PayBuilder signKey(final String signkey){
            signKey = signkey;
            return this;
        }


        public CMParams build(){
            CMParams params = new CMParams();

            params.setActivity(activity);
            params.setAppID(appID);
            params.setAppSecret(appSecret);
            params.setChannelType(channelType);
            params.setOperateType(operateType);
            params.setMchID(mchID);
            params.setOrderNum(orderNum);
            params.setItemName(itemName);
            params.setItemDetail(itemDetail);
            params.setItemPrice(itemPrice);
            params.setNotifyUrl(notifyUrl);
            params.setSignKey(signKey);

            return params;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 登陆数据builder
     */
    public static class LoginBuilder{
        private Activity        activity            = null;
        private String          appID               = null;
        private String          appSecret           = null;
        private ChannelType     channelType         = null;
        private OperateType     operateType         = null;
        private String          code                = null;//请求token的code
        private String          accessToken         = null;//token
        private String          openId              = null;//openId

        public LoginBuilder(Activity mActivity){
            activity = mActivity;
        }

        public CMParams.LoginBuilder appID(final String appid){
            appID = appid;
            return this;
        }

        public CMParams.LoginBuilder appSecret(final String secret){
            appSecret = secret;
            return this;
        }

        public CMParams.LoginBuilder channelType(final ChannelType type){
            channelType = type;
            return this;
        }

        public CMParams.LoginBuilder operateType(final OperateType type){
            operateType = type;
            return this;
        }

        public CMParams.LoginBuilder code(final String _code){
            code = _code;
            return this;
        }

        public CMParams.LoginBuilder accessToken(final String _token){
            accessToken = _token;
            return this;
        }

        public CMParams.LoginBuilder openId(final String _openid){
            openId = _openid;
            return this;
        }

        public CMParams build(){
            CMParams params = new CMParams();

            params.setActivity(activity);
            params.setAppID(appID);
            params.setAppSecret(appSecret);
            params.setChannelType(channelType);
            params.setOperateType(operateType);
            params.setCode(code);
            params.setAccessToken(accessToken);
            params.setOpenId(openId);

            return params;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 分享数据builder
     */
    public static class ShareBuilder{
        private Activity        activity            = null;
        private String          appID               = null;
        private String          appName             = null;
        private ChannelType     channelType         = null;
        private OperateType     operateType         = null;

        private ShareType       shareType           = null;
        private ShareType       sharePosition       = null;
        private String          shareTitle          = null;
        private String          shareDescription    = null;
        private Bitmap          shareBitmap         = null;
        private String          shareUrl            = null;//地址，可能是：音乐、视频、web的url
        private ArrayList<String> shareImgUrl         = null;//分享图片的地址

        public ShareBuilder(Activity mActivity) {
            activity = mActivity;
        }

        public CMParams.ShareBuilder appID(final String appid){
            appID = appid;
            return this;
        }

        public CMParams.ShareBuilder appName(final String appname){
            appName = appname;
            return this;
        }

        public CMParams.ShareBuilder channelType(final ChannelType type){
            channelType = type;
            return this;
        }

        public CMParams.ShareBuilder operateType(final OperateType type){
            operateType = type;
            return this;
        }

        public CMParams.ShareBuilder shareType(final ShareType type){
            shareType = type;
            return this;
        }

        public CMParams.ShareBuilder sharePosition(final ShareType type){
            sharePosition = type;
            return this;
        }

        public CMParams.ShareBuilder shareTitle(final String type){
            shareTitle = type;
            return this;
        }

        public CMParams.ShareBuilder shareDescription(final String type){
            shareDescription = type;
            return this;
        }

        public CMParams.ShareBuilder shareBitmap(final Bitmap type){
            shareBitmap = type;
            return this;
        }

        public CMParams.ShareBuilder shareUrl(final String type){
            shareUrl = type;
            return this;
        }

        public CMParams.ShareBuilder shareImgUrl(final ArrayList<String> imgUrl){
            shareImgUrl = imgUrl;
            return this;
        }

        public CMParams build(){
            CMParams params = new CMParams();

            params.setActivity(activity);
            params.setAppID(appID);
            params.setAppName(appName);
            params.setChannelType(channelType);
            params.setOperateType(operateType);
            params.setShareType(shareType);
            params.setSharePosition(sharePosition);
            params.setShareTitle(shareTitle);
            params.setShareDescription(shareDescription);
            params.setShareBitmap(shareBitmap);
            params.setShareUrl(shareUrl);
            params.setShareImgUrl(shareImgUrl);

            return params;
        }
    }
}
