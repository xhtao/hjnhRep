package com.xht.cmsdk.error;

import com.xht.cmsdk.CMParams;
import com.xht.cmsdk.enums.ChannelType;
import com.xht.cmsdk.enums.ErrorCodes;
import com.xht.cmsdk.enums.OperateType;
import com.xht.cmsdk.utils.CMUtil;

/**
 * Created by XIE on 2018/8/17.
 */

public class CMErrorFactory {

    public static CMError createCMError(final ErrorCodes code, final String msg, final CMParams params, final String data){
        return new CMError(code, getErrorMessageWithCode(code, msg, params), data);
    }

    private static String getErrorMessageWithCode(final ErrorCodes code, final String msg, final CMParams params){
        StringBuilder sb = new StringBuilder();
        sb.append(CMUtil.getChannelName(params.getChannelType()))
                .append(CMUtil.getOperateName(params.getOperateType()));
        if (msg == null){
            sb.append(CMUtil.getErrorMessage(code));
        }else{
            sb.append(msg);
        }

        return sb.toString();
    }
}
