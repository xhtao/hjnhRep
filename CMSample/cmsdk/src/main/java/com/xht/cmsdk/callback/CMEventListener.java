package com.xht.cmsdk.callback;

import com.xht.cmsdk.error.CMError;

/**
 * Created by XIE on 2018/8/9.
 */

public interface CMEventListener {
    void onRequestStart();
    void onEventSuccess(CMError cmError);
    void onEventFailed(CMError cmError);
    void onEventCancel(CMError cmError);
}
