package java8.chapter04.demo01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description
 * @date:2022/10/25 23:37
 * @author: qyl
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;
    public enum Type { MEAT, FISH, OTHER }
}
