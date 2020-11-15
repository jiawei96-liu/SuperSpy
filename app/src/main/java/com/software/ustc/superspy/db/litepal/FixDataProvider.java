package com.software.ustc.superspy.db.litepal;

import java.util.ArrayList;
import java.util.List;

public class FixDataProvider {
    public static List<String> GetTenNumberList() {
// Create a List<String> for 0,1,2...9
        List<String> list = new ArrayList<String>();
//        list.add("请选择");
        list.add("父亲的姓名");
        list.add("母亲的姓名");
        list.add("最喜欢的食物");
        list.add("最喜欢的动物");
        list.add("高中的名称");
        return list;
    }
}