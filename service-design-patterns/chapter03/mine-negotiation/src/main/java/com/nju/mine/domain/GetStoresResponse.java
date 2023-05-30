//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.3.2 生成的
// 请访问 <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2023.05.30 时间 09:55:13 PM CST 
//


package com.nju.mine.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="stores" type="{http://www.qyl.com/ws}stores"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stores"
})
@XmlRootElement(name = "getStoresResponse")
public class GetStoresResponse {

    @XmlElement(required = true)
    protected Stores stores;

    /**
     * 获取stores属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Stores }
     *     
     */
    public Stores getStores() {
        return stores;
    }

    /**
     * 设置stores属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Stores }
     *     
     */
    public void setStores(Stores value) {
        this.stores = value;
    }

}
