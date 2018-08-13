package com.xht.cmsdk.tasks.taskrely;

import com.xht.cmsdk.utils.CMUtil;

/**
 * 异步任务参数
 * Created by XIE on 2018/8/13.
 */

public class TaskParams {
///////////////////////////////////////////微信支付相关参数///////////////////////////////////////////
    //必填
    private String      appID                           = null;//
    private String      mchID                           = null;//微信支付分配的商户号
    private String      nonceStr                        = null;//随机字符串，不长于32位
    private String      sign                            = null;//签名
    private String      tradeOutNum                     = null;//商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
    private String      tradeName                       = null;//商品名称
    private int         tradePrice                      = 0;//商品价格（单位为分）
    private String      payType                         = "APP";//支付类型
    private String      createIp                        = null;//用户端实际ip
    private String      notifyUrl                       = null;//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
    //非必填
    private String      tradeDetail                     = null;//商品描述       ------非必须
    private String      signType                        = "MD5";//签名类型，目前支持HMAC-SHA256和MD5，默认为MD5 ------非必须
    private String      deviceInfo                      = "WEB";//终端设备号(门店号或收银设备ID)，默认请传"WEB"------非必须
    private String      sceneInfo                       = null;//该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。
    private String      limitPay                        = null;//指定支付方式:no_credit--指定不能使用信用卡支付
    private String      feeType                         = "CNY";//符合ISO 4217标准的三位字母代码，默认人民币：CNY------非必须
    private String      attach                          = null;//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据 ------非必须
    private String      timeStart                       = null;//订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010------非必须
    private String      timeExpire                      = null;//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010------非必须
    private String      goodsTag                        = null;//订单优惠标记，代金券或立减优惠功能的参数------非必须
    //动态设置，下单后返回参数
    private String      prepayID                        = null;//预支付交易会话标识,微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
    //使用时获取
    private String      timeStamp                       = null;//时间戳，只读，使用时临时获取

    public TaskParams(){}
///////////////////////////////////////////getter and setter////////////////////////////////////////

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTradeOutNum() {
        return tradeOutNum;
    }

    public void setTradeOutNum(String tradeOutNum) {
        this.tradeOutNum = tradeOutNum;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public int getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(int tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeDetail() {
        return tradeDetail;
    }

    public void setTradeDetail(String tradeDetail) {
        this.tradeDetail = tradeDetail;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getPrepayID() {
        return prepayID;
    }

    public void setPrepayID(String prepayID) {
        this.prepayID = prepayID;
    }

    public String getTimeStamp() {
        timeStamp = CMUtil.currDataToStamp();
        return timeStamp;
    }
}
