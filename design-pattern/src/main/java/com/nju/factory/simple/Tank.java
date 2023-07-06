package com.nju.factory.simple;

/**
 * 坦克（具体产品角色）
 **/
public class Tank extends Weapon{
    @Override
    public void attack() {
        System.out.println("坦克开炮！");
    }
}
