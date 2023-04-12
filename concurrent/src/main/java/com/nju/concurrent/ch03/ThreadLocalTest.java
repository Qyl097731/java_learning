package com.nju.concurrent.ch03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description ThreadLocal的简单使用
 * @date:2022/12/16 22:28
 * @author: qyl
 */
public class ThreadLocalTest {
    public static final String DB_URL = "test";
    /**
     * 保证了每个线程拥有属于自己的Connection
     */
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection> ( ) {
        @Override
        protected Connection initialValue() {
            try {
                return DriverManager.getConnection (DB_URL);
            } catch (SQLException e) {
                throw new RuntimeException (e);
            }
        }
    };

    private static Connection getConnection() {
        return connectionHolder.get ( );
    }
}
