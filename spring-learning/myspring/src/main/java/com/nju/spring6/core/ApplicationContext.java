package com.nju.spring6.core;

/**
 * ApplicationContext接口中提供一个getBean()方法，通过该方法可以获取Bean对象。
 * @author qiuyiliang
 */
public interface ApplicationContext {
    /**
     * 根据bean的id获取bean实例。
     * @param beanId bean的id
     * @return bean实例
     */
    Object getBean(String beanId);
}