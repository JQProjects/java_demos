package com.jqproject.json_xml;

import com.jqproject.entity.UserEntity;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author 姜庆
 * @create 2020-02-10 21:00
 * @desc 模拟Spring对XML文件进行解析
 **/
public class ClassPathXmlApplicationContext {

    private String xmlPath = null;

    public ClassPathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
    }


    public Object getBean(String beanId) throws Exception {
        Objects.requireNonNull(beanId);
        if ("".equals(beanId) || " ".equals(beanId)) {
            throw new IllegalArgumentException("beanId should not null");
        }

        SAXReader saxReader = new SAXReader();
        //采用dom的方式一次性加载了所有的xml节点信息
        Document doc = saxReader.read(this.getClass().getClassLoader().getResource(xmlPath));
        Element root = doc.getRootElement();
        List<Element> elements = root.elements();

        for (Element element : elements) {
            String id = element.attributeValue("id");
            if ("".equals(beanId) || " ".equals(beanId) || beanId == null) {
                continue;
            }
            //非查找的节点跳过
            if (!id.equals(beanId)) {
                continue;
            }
            //获取class
            String aClass = element.attributeValue("class");
            Class<?> clazz = Class.forName(aClass);
            //新生成一个类
            Object instance = clazz.newInstance();
            List<Element> list = element.elements();
            //填充对象属性
            for (Element e : list) {
                //获取每个字元素里每个属性的值
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                field.set(instance, value);
            }
            return instance;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserEntity user = (UserEntity)context.getBean("user2");
        System.out.println(user);
    }

}
