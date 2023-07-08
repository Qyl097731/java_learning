package com.nju.spring6.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 从类路径下进行配置文件读取，类加载
 *
 * @author qiuyiliang
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    private Map<String, Object> singletonMap = new HashMap<> ();
    private String[] resources;

    public ClassPathXmlApplicationContext(String... resource) {
        this.resources = resource;
        loadBeanDefinitions ();
    }

    private void loadBeanDefinitions() {
        if (resources == null || resources.length == 0) {
            return;
        }
        for (String resource : resources) {
            SAXReader reader = new SAXReader ();
            try {
                Document document = reader.read (ClassLoader.getSystemClassLoader ().getResourceAsStream (resource));
                List<Node> nodes = document.selectNodes ("//bean");
                if (nodes != null && !nodes.isEmpty ()) {
                    for (Node node : nodes) {
                        Element beanNode = (Element) node;
                        String id = beanNode.attributeValue ("id");
                        String className = beanNode.attributeValue("class");
                        Class<?> clazz = Class.forName(className);
                        Constructor<?> defaultConstructor = clazz.getDeclaredConstructor();
                        Object bean = defaultConstructor.newInstance();
                        // 存储到Map集合
                        singletonMap.put (id,bean);
                    }

                    // 再重新遍历集合，这次遍历是为了给Bean的所有属性赋值。
                    // 思考：为什么不在上面的循环中给Bean的属性赋值，而在这里再重新遍历一次呢？
                    // 通过这里你是否能够想到Spring是如何解决循环依赖的：实例化和属性赋值分开。
                    for (Node node : nodes) {
                        Element beanNode = (Element) node;
                        String id = beanNode.attributeValue ("id");
                        List<Element> properties = beanNode.elements ("property");
                        if (properties != null && !properties.isEmpty ()) {
                            for (Element property : properties) {
                                // 获取属性名字
                                String propertyName = property.attributeValue ("name");
                                // 获取属性类型
                                Class<?> propertyType =
                                        singletonMap.get (id).getClass ().getDeclaredField (propertyName).getType ();
                                // 获取set方法名字
                                String setMethodName = "set" + propertyName.toUpperCase ().charAt (0) + propertyName.substring (1);
                                // 获取set方法
                                Method method = singletonMap.get (id).getClass ().getMethod (setMethodName, propertyType);
                                // 获取属性值
                                String propertyValue = property.attributeValue ("value");
                                String propertyRef = property.attributeValue ("ref");
                                Object propertyVal = null;
                                if (propertyValue != null) {
                                    String typeSimpleName = propertyType.getSimpleName ();
                                    switch (typeSimpleName) {
                                        case "byte":
                                        case "Byte":
                                            propertyVal = Byte.valueOf (propertyValue);
                                        case "short":
                                        case "Short":
                                            propertyVal = Short.valueOf (propertyValue);
                                            break;
                                        case "int":
                                        case "Integer":
                                            propertyVal = Integer.valueOf (propertyValue);
                                            break;
                                        case "long":
                                        case "Long":
                                            propertyVal = Long.valueOf (propertyValue);
                                            break;
                                        case "float":
                                        case "Float":
                                            propertyVal = Float.valueOf (propertyValue);
                                            break;
                                        case "double":
                                        case "Double":
                                            propertyVal = Double.valueOf (propertyValue);
                                            break;
                                        case "boolean":
                                        case "Boolean":
                                            propertyVal = Boolean.valueOf (propertyValue);
                                            break;
                                        case "char":
                                        case "Character":
                                            propertyVal = propertyValue.charAt (0);
                                            break;
                                        case "String":
                                            propertyVal = propertyValue;
                                            break;
                                    }
                                    method.invoke (singletonMap.get (id), propertyVal);
                                }
                                if (propertyRef != null){
                                    method.invoke (singletonMap.get (id),singletonMap.get (propertyRef));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace ();
            }

        }


    }

    @Override
    public Object getBean(String beanId) {
        return singletonMap.get (beanId);
    }
}
