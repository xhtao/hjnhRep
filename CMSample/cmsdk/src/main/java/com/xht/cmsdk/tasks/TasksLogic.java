package com.xht.cmsdk.tasks;

import com.xht.cmsdk.enums.NetworkType;
import com.xht.cmsdk.module.strategy.StrategyWeChat;
import com.xht.cmsdk.network.ConnParams;
import com.xht.cmsdk.network.IServerConnect;
import com.xht.cmsdk.network.ServerConnectFactory;
import com.xht.cmsdk.tasks.taskrely.TaskParams;
import com.xht.cmsdk.utils.CMUtil;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * 单例，异步任务逻辑类
 * Created by XIE on 2018/8/13.
 */

public class TasksLogic {
    private static TasksLogic instance = null;

    protected TasksLogic(){}

    protected static TasksLogic getInstance(){
        if (instance == null){
            synchronized (TasksLogic.class){
                if (instance == null){
                    instance = new TasksLogic();
                }
            }
        }
        return instance;
    }

    protected String getWeChatPayOrder(TaskParams params){
        try {
            Map<String, String> queryParas = new HashMap<String, String>();
            queryParas.put("appid", params.getAppID());
            queryParas.put("mch_id", params.getMchID());
            queryParas.put("body", params.getTradeName());
            queryParas.put("nonce_str", params.getNonceStr());
            queryParas.put("notify_url", params.getNotifyUrl());
            queryParas.put("out_trade_no", params.getTradeOutNum());
            queryParas.put("spbill_create_ip", params.getCreateIp());
            queryParas.put("total_fee", params.getTradePrice() + "");
            queryParas.put("trade_type", params.getPayType());
            queryParas.put("sign", params.getSign());
            //以下选填
            CMUtil.choseInput(queryParas, "attach", params.getAttach());
            CMUtil.choseInput(queryParas, "device_info", params.getDeviceInfo());
            CMUtil.choseInput(queryParas, "sign_type", params.getSignType());

            String orderParams = CMUtil.mapToXmlUseDocument(queryParas);

            IServerConnect connect = ServerConnectFactory.createConnect(NetworkType.HttpsUrlConnection);

            return connect.postRequest(new ConnParams("https://api.mch.weixin.qq.com/pay/unifiedorder", orderParams), null);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void startWeChatPay(TaskParams params){
        StrategyWeChat pay = StrategyWeChat.getInstance();
        pay.wxPayDoing(params);
    }
}
