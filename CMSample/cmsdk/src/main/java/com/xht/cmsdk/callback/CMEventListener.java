package com.xht.cmsdk.callback;

/**
 * Created by XIE on 2018/8/9.
 */

public interface CMEventListener {
    void onRequestStart();
    void onEventSuccess(int code, String jsonString);
    void onEventFailed(int code);
    void onEventCancel(int code);
}
