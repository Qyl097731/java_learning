package com.nju.factory.abstractfactory;

import com.nju.factory.domain.Dagger;
import com.nju.factory.domain.Fighter;
import com.nju.factory.domain.Weapon;

/**
 * 武器族工厂
 **/
public class WeaponFactory extends AbstractFactory{

    public Weapon getWeapon(String type){
        if (type == null || type.trim().length() == 0) {
            return null;
        }
        if ("Gun".equals(type)) {
            return new Fighter();
        } else if ("Dagger".equals(type)) {
            return new Dagger();
        } else {
            throw new RuntimeException("无法生产该武器");
        }
    }

    @Override
    public Fruit getFruit(String type) {
        return null;
    }
}
