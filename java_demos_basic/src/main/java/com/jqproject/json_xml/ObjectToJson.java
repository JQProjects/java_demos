package com.jqproject.json_xml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jqproject.entity.Item;
import com.jqproject.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 姜庆
 * @create 2020-02-10 17:27
 * @desc Object转换成Json字符串
 **/
public class ObjectToJson {

    public static String json = "{\"id\":\"11\",\"name\":\"张三\",\"items\":[{\"itemId\":\"2\",\"itemName\":\"测试\"},{\"itemId\":\"3\",\"itemName\":\"火狐\"}]}";

    public static void main(String[] args) {

        User u = JSON.parseObject(json, User.class);
        System.out.println(u);


        Item item01 = new Item();
        item01.setItemId("0");
        item01.setItemName("猪肉");

        Item item02 = new Item();
        item02.setItemId("1");
        item02.setItemName("鸡肉");

        User user = new User();
        user.setId("0");
        user.setName("商品列表");
        List<Item> items = new ArrayList<>();
        items.add(item01);
        items.add(item02);
        user.setItems(items);
        System.out.println(user);

        System.out.println(JSON.toJSONString(user));

    }

}
