package com.xht.cmsdk.error;

import com.xht.cmsdk.enums.ErrorCodes;

/**
 * Created by XIE on 2018/8/17.
 */

public class CMError {
    private ErrorCodes errorCode;
    private String errorMessage;
    private String errorData;

    public CMError(final ErrorCodes code, final String message, final String data){
        this.errorCode = code;
        this.errorMessage = message;
        this.errorData = data;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorData() {
        return errorData;
    }
}
