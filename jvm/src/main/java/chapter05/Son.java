package chapter05;

/**
 * projectName:  jvm
 * packageName: chapter05
 * date: 2022-07-23 20:46
 * author 邱依良
 */
class Father {
    public Father() {
        System.out.println("father的构造器");
    }

    public static void showStatic(String str) {
        System.out.println("father" + str);
    }

    public final void showFinal(){
        System.out.println("father show final");
    }

    public void showCommon(){
        System.out.println("father 普通方法");
    }
}

public class Son extends Father{
    public Son(){super();}

    public Son(int age){this();}

    public static void showStatic(String str){
        System.out.println("son" + str);
    }

    private void showPrivate(String str){
        System.out.println("son private " + str);
    }

    public void show(){
        showStatic("njxzc");
        Father.showStatic("static");
        showPrivate("hello");
        super.showCommon();
        showFinal();
        info();

        MethodInterface in = null;
        in.methodA();
    }

    public void info(){

    }

    public void display(Father f){f.showCommon();}

    public static void main(String[] args) {
        Son son = new Son();
        son.show();
    }
}

interface MethodInterface{
    void methodA();
}

