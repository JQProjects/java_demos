package com.jqproject.json_xml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 姜庆
 * @create 2020-02-10 13:20
 * @desc Fastjson的简单用法
 **/
public class FastjsonDemo {
    public static String jsonStr = "{\"sites\":[{\"name\":\"深圳宝安\",\"url\":\"www.jqtest.com\"},{\"name\":\"张三\",\"url\":\"http://jqtest.com/\"}]}";

    public static void main(String[] args) {
        //最新版本可以去掉了
        //JSONObject jsonObject = new JSONObject();

        JSONObject object = JSON.parseObject(jsonStr);
        JSONArray sites = object.getJSONArray("sites");
        for (Object site : sites) {
            JSONObject s = (JSONObject) site;
            String name = s.getString("name");
            String url = s.getString("url");
            System.out.println(name+"---"+url);
        }

    }



}
