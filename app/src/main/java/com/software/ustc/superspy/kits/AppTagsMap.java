package com.software.ustc.superspy.kits;

import java.util.HashMap;

public class AppTagsMap {
    private HashMap<String, String> appTagsMap = new HashMap<String, String>();
    private String[] appTag={"聊天社交","金融理财","旅行交通","时尚购物","影音视听",
            "游戏","短视频","学习教育","新闻资讯","其他"};
    public AppTagsMap()
    {
        appTagsMap = new HashMap<String, String>();
        appTagsMap.put("微信", appTag[0]);
        appTagsMap.put("知乎", appTag[0]);
        appTagsMap.put("QQ", appTag[0]);
        appTagsMap.put("陌陌", appTag[0]);
        appTagsMap.put("百度贴吧", appTag[0]);
        appTagsMap.put("探探", appTag[0]);
        appTagsMap.put("微博", appTag[0]);

        appTagsMap.put("支付宝", appTag[1]);
        appTagsMap.put("同花顺", appTag[1]);
        appTagsMap.put("小米钱包", appTag[1]);

        appTagsMap.put("滴滴出行", appTag[2]);
        appTagsMap.put("高德地图", appTag[2]);
        appTagsMap.put("腾讯地图", appTag[2]);
        appTagsMap.put("百度地图", appTag[2]);
        appTagsMap.put("携程旅行", appTag[2]);

        appTagsMap.put("手机淘宝", appTag[3]);
        appTagsMap.put("京东", appTag[3]);
        appTagsMap.put("咸鱼", appTag[3]);
        appTagsMap.put("拼多多", appTag[3]);
        appTagsMap.put("小米商城", appTag[3]);
        appTagsMap.put("小米有品", appTag[3]);
        appTagsMap.put("网易有品", appTag[3]);
        appTagsMap.put("小米特价版", appTag[3]);
        appTagsMap.put("手机天猫", appTag[3]);
        appTagsMap.put("多点", appTag[3]);

        appTagsMap.put("哔哩哔哩", appTag[4]);
        appTagsMap.put("爱奇艺", appTag[4]);
        appTagsMap.put("QQ音乐", appTag[4]);
        appTagsMap.put("网易云音乐", appTag[4]);
        appTagsMap.put("小米视频", appTag[4]);
        appTagsMap.put("喜马拉雅", appTag[4]);
        appTagsMap.put("音乐", appTag[4]);
        appTagsMap.put("收音机", appTag[4]);
        appTagsMap.put("腾讯视频", appTag[4]);
        appTagsMap.put("咪咕音乐", appTag[4]);

        appTagsMap.put("王者荣耀", appTag[5]);
        appTagsMap.put("和平精英", appTag[5]);
        appTagsMap.put("炉石传说", appTag[5]);
        appTagsMap.put("三国杀", appTag[5]);
        appTagsMap.put("部落冲突", appTag[5]);
        appTagsMap.put("荒野乱斗", appTag[5]);

        appTagsMap.put("快手", appTag[6]);
        appTagsMap.put("快手极速版", appTag[6]);
        appTagsMap.put("皮皮虾", appTag[6]);
        appTagsMap.put("抖音", appTag[6]);
        appTagsMap.put("抖音火山版", appTag[6]);

        appTagsMap.put("百度翻译", appTag[7]);
        appTagsMap.put("超级课程表", appTag[7]);
        appTagsMap.put("扇贝单词英语版", appTag[7]);
        appTagsMap.put("学习强国", appTag[7]);
        appTagsMap.put("扇贝听力", appTag[7]);
        appTagsMap.put("不背单词", appTag[7]);
        appTagsMap.put("作业帮", appTag[7]);
        appTagsMap.put("猿辅导", appTag[7]);
        appTagsMap.put("小猿搜题", appTag[7]);

        appTagsMap.put("今日头条", appTag[8]);
        appTagsMap.put("今日头条极速版", appTag[8]);
        appTagsMap.put("一点资讯", appTag[8]);
        appTagsMap.put("搜狐新闻", appTag[8]);
        appTagsMap.put("央视新闻", appTag[8]);
        appTagsMap.put("百度", appTag[8]);
        appTagsMap.put("澎湃新闻", appTag[8]);
    }

    public HashMap<String, String> getAppTagsMap() {
        return appTagsMap;
    }

    public String[] getAppTag() {
        return appTag;
    }
}
