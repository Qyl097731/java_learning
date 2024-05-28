package com.nju.jdk9;

import java.io.IOException;

/**
 * @description
 * @date 2024/5/18 20:57
 * @author: qyl
 */
public class ProcessApi {
    public static void main(String[] args) throws IOException {
        // 获取当前进程的信息
        ProcessHandle currentProcess = ProcessHandle.current();
        long currentPid = currentProcess.pid();
        ProcessHandle.Info info = currentProcess.info();

        System.out.printf("Current Process PID: %d%n", currentPid);
        info.command().ifPresent(cmd -> System.out.printf("Command: %s%n", cmd));
        info.startInstant().ifPresent(start -> System.out.printf("Start time: %s%n", start));
        info.totalCpuDuration().ifPresent(cpu -> System.out.printf("CPU usage: %s%n", cpu));

        // 获取并管理当前进程的所有子进程
        currentProcess.children().forEach(child -> {
            System.out.printf("Child PID: %d%n", child.pid());
            child.destroy(); // 销毁子进程
            // 监听当前进程的状态变化
            child.onExit().thenRun(() -> System.out.println("Process has exited"));
        });
    }
}
