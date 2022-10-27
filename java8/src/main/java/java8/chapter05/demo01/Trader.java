package java8.chapter05.demo01;

import java8.chapter04.demo01.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;


/**
 * @description
 * @date:2022/10/26 22:22
 * @author: qyl
 */
@Getter
@AllArgsConstructor
public class Trader {
    private final String name;
    private final String city;

    @Override
    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}
