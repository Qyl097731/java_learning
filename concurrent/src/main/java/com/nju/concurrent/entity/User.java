package com.nju.concurrent.entity;

/**
 * @description
 * @date:2022/12/14 15:12
 * @author: qyl
 */
public class User implements Comparable<User>{
    public String name;
    public int age;

    public User(){}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(User o) {
        if (o.age == age){
            return name.compareTo (o.name);
        }
        return age - o.age;
    }

    @Override
    public int hashCode() {
        return super.hashCode ( );
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals (obj);
    }
}
