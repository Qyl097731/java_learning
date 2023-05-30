//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.3.2 生成的
// 请访问 <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2023.05.30 时间 09:55:13 PM CST 
//


package com.nju.mine.domain;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.nju.mine.domain package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.nju.mine.domain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStoresRequest }
     * 
     */
    public GetStoresRequest createGetStoresRequest() {
        return new GetStoresRequest();
    }

    /**
     * Create an instance of {@link GetStoresResponse }
     * 
     */
    public GetStoresResponse createGetStoresResponse() {
        return new GetStoresResponse();
    }

    /**
     * Create an instance of {@link Stores }
     * 
     */
    public Stores createStores() {
        return new Stores();
    }

}
