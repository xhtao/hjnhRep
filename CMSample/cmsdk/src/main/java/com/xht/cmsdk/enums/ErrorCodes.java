package com.xht.cmsdk.enums;

/**
 * Created by XIE on 2018/8/17.
 */

public enum ErrorCodes {
    Error_Code_DEFAULT(-1)                  //通用默认错误码，未知错误
    ,Error_Code_UnRegister(-2)              //第三方sdk未注册
    ,Error_Code_UnInstall(-3)               //第三方应用未安装
    ,Error_Code_Action_Success(0)           //操作成功
    ,Error_Code_Action_Failed(1)            //操作失败
    ,Error_Code_Action_Cancel(2)            //操作取消
    //登陆使用：获取用户信息结果
    ,Error_Code_GetUserInfo_Success(1001)   //获取用户信息成功
    ,Error_Code_GetUserInfo_Failed(1002)    //获取用户信息失败
    ,Error_Code_GetToken_Failed(1003)       //获取用户token数据失败

    ;

    int errorCodes;
    ErrorCodes(int code){ errorCodes = code; }

    @Override
    public String toString() {
        return super.toString();
    }
}
