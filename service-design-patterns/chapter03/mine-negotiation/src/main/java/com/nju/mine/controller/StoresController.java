package com.nju.mine.controller;


import com.nju.mine.domain.Stores;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * @description 查询库存
 * @date 2023/5/26 23:51
 * @author: qyl
 */
@Path("/stores")
public class StoresController {
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JAXBElement<Stores> getStores() {
        /**
         * 假装查询辣
         */
        Stores stores = new Stores ();
        stores.setName ("好丽友");
        stores.setNum (11);
        return new JAXBElement<Stores> (
                new QName ("Stores"), Stores.class, stores);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStoresAsText() {
        Stores stores = new Stores ();
        stores.setName ("好丽友");
        stores.setNum (11);
        return stores.toString ();
    }
}
