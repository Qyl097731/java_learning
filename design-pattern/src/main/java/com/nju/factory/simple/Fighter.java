package com.nju.factory.simple;

/**
 * 战斗机（具体产品角色）
 **/
public class Fighter extends Weapon{
    @Override
    public void attack() {
        System.out.println("战斗机投下原子弹！");
    }
}
