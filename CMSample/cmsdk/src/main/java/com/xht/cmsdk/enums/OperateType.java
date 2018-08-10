package com.xht.cmsdk.enums;

/**
 * Created by XIE on 2018/8/9.
 */

public enum OperateType {
    TypeLogin(0),
    TypePay(1),
    TypeShare(2);

    int operateType;
    OperateType(int type){
        operateType = type;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
