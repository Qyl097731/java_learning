package chapter2.pizza;

import java.util.Objects;

/**
 * @author qyl
 * @program NyPizza.java
 * @Description TODO
 * @createTime 2022-08-02 14:10
 */
public class NyPizza extends Pizza{
    public enum Size {SMALL,MEDIUM,LARGE}
    private final Size size;

    public static class Builder extends Pizza.Builder<Builder>{
        private final Size size;

        public Builder(Size size){
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build(){
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }
}
