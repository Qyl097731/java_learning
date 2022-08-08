package chapter05;

/**
 * projectName:  jvm
 * packageName: chapter05
 * date: 2022-07-23 21:09
 * author 邱依良
 */
interface Func {
    public boolean func(String str);
}

public class Lambda {
    public void lambda(Func func) {
        return;
    }

    public static void main(String[] args) {
        Lambda lambda = new Lambda();
        Func func = s -> {
            return true;
        };
        lambda.lambda(func);

        lambda.lambda(s -> {
            return true;
        });
    }
}
