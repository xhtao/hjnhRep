package com.xht.cmsdk;

import com.xht.cmsdk.enums.ChannelType;
import com.xht.cmsdk.module.BaseModule;
import com.xht.cmsdk.module.strategy.StrategyQQ;
import com.xht.cmsdk.module.strategy.StrategyWeChat;

/**
 * Created by XIE on 2018/8/14.
 */

public class ModuleFactory {

    public static BaseModule createModule(ChannelType type){
        switch (type){
            case TypeWeChat:
                return StrategyWeChat.getInstance();
            case TypeQQ:
                return StrategyQQ.getInstance();
            case TypeAli:
                return null;
            case TypeWeiBo:
                return null;
            case TypeUP:
                return null;
            default:
                return null;
        }
    }

}
