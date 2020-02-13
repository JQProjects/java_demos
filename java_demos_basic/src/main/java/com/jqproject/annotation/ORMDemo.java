package com.jqproject.annotation;

import lombok.Data;

import java.lang.reflect.Field;

@Table("t_student")
@Data
class User{
    @Property(value = "t_student_id", leng = 10)
    private int id;
    @Property(value = "t_student_name",leng = 40)
    private String name;
}

/**
 * @author 姜庆
 * @create 2020-02-12 21:58
 * @desc 模拟ORM进行类和表中字段的映射转换
 **/
public class ORMDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        //通过映射的属性名来拼接出SQL语句
        Class<?> aClass = Class.forName("com.jqproject.annotation.User");
        StringBuffer sf = new StringBuffer();
        sf.append(" select ");
        // 获取类的所有的属性
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            Property property = field.getDeclaredAnnotation(Property.class);
            sf.append(property.value()+",");
        }
        sf.deleteCharAt(sf.length()-1);

        Table table = aClass.getDeclaredAnnotation(Table.class);
        sf.append(" from "+table.value());

        System.out.println(sf.toString());
    }
}
