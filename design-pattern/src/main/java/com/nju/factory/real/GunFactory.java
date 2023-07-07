package com.nju.factory.real;

import com.nju.factory.domain.Dagger;
import com.nju.factory.domain.Weapon;

/**
 * 具体工厂角色
 **/
public class GunFactory extends AbstractWeaponFactory {
    @Override
    public Weapon get() {
        return new Dagger();
    }
}
