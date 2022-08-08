package chapter05;

/**
 * projectName:  jvm
 * packageName: chapter05
 * date: 2022-07-23 20:24
 * author 邱依良
 */
class Animal{
    public void eat(){
        System.out.println("动物进食");
    }
}

interface Huntable {
    void hunt();
}

class Dog extends Animal implements Huntable{

    @Override
    public void hunt() {
        System.out.println("汪汪汪");
    }

    @Override
    public void eat(){
        System.out.println("吃骨头");
    }
}

class Cat extends Animal implements Huntable{

    public Cat(){
        super();
    }

    public Cat(String name){
        this();
    }

    @Override
    public void hunt() {
        System.out.println("喵喵喵");
    }

    @Override
    public void eat(){
        System.out.println("吃老鼠");
    }
}

public class AnimalTest{
    //晚期绑定
    public void showAnimal(Animal animal){
        animal.eat();
    }
    public void showHunt(Huntable h){
        h.hunt();
    }
}
