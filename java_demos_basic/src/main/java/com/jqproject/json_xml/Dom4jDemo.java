package com.jqproject.json_xml;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @author 姜庆
 * @create 2020-02-10 18:03
 * @desc Dom4j的简单使用
 **/
public class Dom4jDemo {

    public static void main(String[] args) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(Dom4jDemo.class.getClassLoader().getResourceAsStream("students.xml"));
        Element root = document.getRootElement();
        getNodesByDom(root);

        SAXReader saxReader = new SAXReader();
        //添加需要的xpath对应节点类型的hander
        saxReader.addHandler("/students/student", new ElementHandler() {
            @Override
            public void onStart(ElementPath elementPath) {
                System.out.println("start");
            }
            @Override
            public void onEnd(ElementPath elementPath) {
                Element current = elementPath.getCurrent();
                String id = current.attributeValue("id");
                System.out.println("----" + id);
                current.detach();
                System.out.println("end");
            }
        });
        saxReader.read(Dom4jDemo.class.getClassLoader().getResourceAsStream("students.xml"));
    }

    /**
     * 使用遍历的dom的方式
     *  dom4j不适合大文件的解析，因为它是一下子将文件加载到内存中，所以有可能出现内存溢出，sax是基于事件来对xml进行解析的，
     *  所以他可以解析大文件的xml，也正是因为如此，所以dom4j可以对xml进行灵活的增删改查和导航，而sax没有这么强的灵活性，
     *  所以sax经常是用来解析大型xml文件，而要对xml文件进行一些灵活（crud）操作就用dom4j。
     * @param root
     */
    public static void getNodesByDom(Element root) {
        System.out.println("当前节点的名称："+root.getName());
        List<Attribute> attributes = root.attributes();
        for (Attribute attribute : attributes) {
            System.out.println("属性："+attribute.getName()+"="+attribute.getText());
        }

        if(!root.getTextTrim().equals("")){
            System.out.println(root.getName()+"="+root.getText());
        }

        Iterator<Element> iterator = root.elementIterator();
        while(iterator.hasNext()){
            Element next = iterator.next();
            getNodesByDom(next);
        }
    }

    public static void getNodesBySax(Element root) {
        System.out.println("当前节点的名称："+root.getName());

    }

}
