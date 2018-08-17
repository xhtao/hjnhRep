package com.xht.cmsdk.enums;

/**
 * 分享类型
 * Created by XIE on 2018/8/9.
 */

public enum ShareType {
    Text,               //分享文字
    Image,              //分享图片
    TextAndImage,       //图片和文字
    WebPage,            //分享链接
    Music,              //分享音乐
    Video,              //分享视频
    APP,                //分享app（qq分享）
    ////////以下为分享位置
    Session,            //分享给好友
    Timeline,           //分享到朋友圈
    Favorite            //添加到收藏
}
