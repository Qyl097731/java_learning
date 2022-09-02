package chapter6.enums;

/**
 * @author qyl
 * @program Fruit.java
 * @Description 用实例域取代位域
 * @createTime 2022-09-02 11:04
 */
public enum Fruit {
    APPLE(1),ORANGE(2);
    private final int indexOfFruitTypes;

    Fruit(int indexOfFruitTypes) {
        this.indexOfFruitTypes = indexOfFruitTypes;
    }

    public int indexOfFruitTypes() {
        return indexOfFruitTypes;
    }
}
