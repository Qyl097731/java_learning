package chapter18;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @description
 * @date:2022/8/20 23:09
 * @author: qyl
 */
public class MemoryMonitor {
    public static void main(String[] args) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("INIT HEAP: " + usage.getInit() / 1024 / 1024 + " m");
        System.out.println("MAX HEAP: " + usage.getMax() / 1024 / 1024 + " m");
        System.out.println("USE HEAP: " + usage.getUsed() / 1024 / 1024 + " m");
        System.out.println("\nFull Information: ");
        System.out.println("Heap Memory Usage: " + memoryMXBean.getHeapMemoryUsage());
        System.out.println("Non-Heap Memory Usage: " + memoryMXBean.getNonHeapMemoryUsage());

        System.out.println("==================通过java获取相关系统状态===================");
        System.out.println("当前堆内存大小totalMemory : " + (int) Runtime.getRuntime().totalMemory() / 1024 / 1024 + " m");
        System.out.println("空闲堆内存大小freeMemory : " + (int) Runtime.getRuntime().freeMemory() / 1024 / 1024 + " m");
        System.out.println("最大可用堆内存大小maxMemory : " + (int) Runtime.getRuntime().maxMemory() / 1024 / 1024 + " m");

    }
}
