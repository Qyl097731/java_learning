package com.nju.refactor.utils;

/**
 * @description
 * @date:2022/10/13 19:05
 * @author: qyl
 */
public class UrlUtil {
    public static String getClassUrl(Class<?> clazz){
        return clazz.getResource("/").getPath();
    }
}
