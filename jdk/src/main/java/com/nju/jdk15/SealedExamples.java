package com.nju.jdk15;

/**
 * @description
 * @date 2024/6/12 0:21
 * @author: qyl
 */
public class SealedExamples {
    public abstract sealed class Shape permits Circle, Rectangle, Square {
        // 抽象类的定义
    }

    public final class Circle extends Shape {
        // Circle 的定义
    }

    public sealed class Rectangle extends Shape permits FilledRectangle {
        // Rectangle 的定义
    }

    public final class Square extends Shape {
        // Square 的定义
    }

    public non-sealed class FilledRectangle extends Rectangle {
        // FilledRectangle 的定义
    }

    // 密封接口
    public sealed interface Service permits DatabaseService, NetworkService {
        void perform();
    }

    public final class DatabaseService implements Service {
        @Override
        public void perform() {
            // 实现方法
        }
    }

    public sealed class NetworkService implements Service permits SecureNetworkService {
        @Override
        public void perform() {
            // 实现方法
        }
    }

    public non-sealed class SecureNetworkService extends NetworkService {
        @Override
        public void perform() {
            // 实现方法
        }
    }
}
