package chapter6.enums;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author qyl
 * @program GardenTest.java
 * @Description EnumMap代替序数索引
 * @createTime 2022-09-02 11:34
 */
public class GardenTest {

    public static void main(String[] args) {
        Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);
        Plant[] garden = {
            new Plant("flower", Plant.LifeCycle.ANNUAL),
            new Plant("grass", Plant.LifeCycle.ANNUAL)
        };
        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantsByLifeCycle.put(lc, new HashSet<>());
        }
        for (Plant p : garden) {
            plantsByLifeCycle.get(p.lifeCycle).add(p);
        }
    }

}

class Plant {
    enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL}

    final String name;
    final LifeCycle lifeCycle;

    public Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", lifeCycle=" + lifeCycle +
                '}';
    }
}