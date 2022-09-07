package chapter11.Observer;

import org.junit.Test;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description
 * @date:2022/9/7 16:43
 * @author: qyl
 */
public class ObserverTest {
    @Test
    public void test(){
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver((s,e)-> System.out.println(e));
        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }

    @Test
    public void test1(){
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23){
                    set.removeObserver(this);
                }
            }
        });
        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }

    @Test
    public void test2(){
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23){
                    ExecutorService exec = Executors.newSingleThreadExecutor();
                    try {
                        exec.submit(()-> set.removeObserver(this)).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }finally {
                        exec.shutdown();
                    }
                }
            }
        });
        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }

}
