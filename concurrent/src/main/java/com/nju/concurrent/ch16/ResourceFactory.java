package com.nju.concurrent.ch16;

import net.jcip.annotations.ThreadSafe;

/**
 * @description
 * @date:2023/1/13 19:14
 * @author: qyl
 */
@ThreadSafe
public class ResourceFactory {
    private static class ResourceHolder{
        public static Resource resource = new Resource();
    }

    public static Resource getResource(){
        return ResourceHolder.resource;
    }

    private static class Resource {
    }
}
