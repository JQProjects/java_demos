package com.jqproject.entity;

import lombok.Data;

import java.awt.event.ItemEvent;
import java.util.List;

/**
 * @author 姜庆
 * @create 2020-02-10 17:14
 * @desc User实体类
 **/
@Data
public class User {
    private String id;
    private String name;
    private List<Item> items;

}
