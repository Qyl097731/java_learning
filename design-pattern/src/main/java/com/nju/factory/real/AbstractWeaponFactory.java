package com.nju.factory.real;

import com.nju.factory.domain.Weapon;

/**
 * 武器工厂接口(抽象工厂角色)
 **/
public abstract class AbstractWeaponFactory {
    protected abstract Weapon get() ;
}
