package chapter2.pizza;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author qyl
 * @program Pizza.java
 * @Description TODO
 * @createTime 2022-08-02 13:58
 */
public abstract class Pizza {
    public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}

    final Set<Topping> toppings;

    /**
     * 比如List<String>，对于这个类型来说，List并不能完整描述这个类型，必须加上类型参数String才是完整的类型。
     * 一个泛型类，不把所有类型都填满，就不是完整可用的类型。
     * T 只能是 Builder 的子类型。因为 Builder是个泛型类，在描述「某个具体的 Builder 类型」 时，就必须带上类型参数。对于这里来说，就是 T。
     * @param <T>
     */
    abstract static class Builder<T extends Builder<T>>{
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);          // 创建空的Topping类的枚举集合
        public T addTopping(Topping topping){
            toppings.add(topping);
            return self();
        }
        abstract Pizza build();

        protected abstract T self();
    }

    Pizza(Builder<?>builder){
        toppings = builder.toppings.clone();
    }

}
