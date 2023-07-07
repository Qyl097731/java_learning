package com.nju.factory.abstractfactory;

import com.nju.factory.domain.Weapon;

/**
 * 水果族工厂
 **/
public class FruitFactory extends AbstractFactory{
    @Override
    public Weapon getWeapon(String type) {
        return null;
    }

    public Fruit getFruit(String type){
        if (type == null || type.trim().length() == 0) {
            return null;
        }
        if ("Orange".equals(type)) {
            return new Orange();
        } else if ("Apple".equals(type)) {
            return new Orange();
        } else {
            throw new RuntimeException("我家果园不产这种水果");
        }
    }
}
