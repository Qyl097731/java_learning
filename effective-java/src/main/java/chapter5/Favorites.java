package chapter5;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qyl
 * @program Favorites.java
 * @Description 类型参数化的实例
 * @createTime 2022-09-01 18:13
 */
public class Favorites{
    private Map<Class<?>,Object> favorites = new HashMap<>();
    public <T> void putFavorite(Class<T> type,T instance){
        //type.cast 来进行动态的类型转换，防止有人恶意的用Class的原生形态插入map，造成后面出现类型不安全，放入和键值不同的类型的值.
        favorites.put(type,type.cast(instance));
    }
    public <T> T getFavorite(Class<T> type){
        return type.cast(favorites.get(type));
    }
}


