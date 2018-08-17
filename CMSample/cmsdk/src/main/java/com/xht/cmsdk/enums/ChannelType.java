package com.xht.cmsdk.enums;

/**
 * Created by XIE on 2018/8/9.
 */

public enum ChannelType {
    TypeWeChat(0),
    TypeQQ(1),
    TypeAli(1),
    TypeWeiBo(2),
    TypeUP(3);//银行卡（支付使用）

    int channelType;
    ChannelType(int type){
        channelType = type;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
