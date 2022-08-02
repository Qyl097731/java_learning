package chapter05;

/**
 * @author qyl
 * @program SafeDoubleCheckSingleton.java
 * @Description 双重检查锁定
 * @createTime 2022-08-01 14:10
 */
public class SafeDoubleCheckSingleton {
    // 通过volatile降低性能
    private volatile static SafeDoubleCheckSingleton singleton;

    private SafeDoubleCheckSingleton(){}
    // 双重锁设计
    public static SafeDoubleCheckSingleton getInstance(){
        if(singleton == null){
            // 多线程并发创建对象，通过加锁保证只有一个线程能创建对象
            synchronized (SafeDoubleCheckSingleton.class){
                if(singleton == null){
                    // 隐患 多线程环境下 由于重排序 该对象可能还没初始化就被其他线程读取
                    // 通过volatile进行一些屏障的设置 可以避免上述隐患
                    singleton = new SafeDoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }
}
