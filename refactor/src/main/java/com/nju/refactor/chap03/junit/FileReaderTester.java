package com.nju.refactor.chap03.junit;

import com.nju.refactor.utils.UrlUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.junit.jupiter.api.Assertions;

import java.io.*;

/**
 * @description Junit测试的实现
 * @date:2022/10/13 18:14
 * @author: qyl
 */
public class FileReaderTester extends TestCase {

    private FileReader input;
    private FileReader empty;

    public FileReaderTester(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        try {
            input = new FileReader(new File(UrlUtil.getClassUrl(this.getClass())) + "/data.txt");
            empty = newEmptyFile();
        } catch (IOException e) {
            throw new RuntimeException("unable to open test file");
        }
    }

    private FileReader newEmptyFile() throws IOException {
        File empty = new File(UrlUtil.getClassUrl(this.getClass()) + "/empty.txt");
        return new FileReader(empty);
    }

    @Override
    public void tearDown() {
        try {
            input.close();
        } catch (IOException e) {
            throw new RuntimeException("error on closing test file");
        }
    }

    public void testEmptyRead() throws IOException {
        assertEquals(-1, empty.read());
    }

    public void testRead() throws IOException {
        char ch = '$';
        for (int i = 0; i < 6; i++) {
            ch = (char) input.read();
        }
        assertEquals('1', ch);
    }

    public void testFailRead() throws IOException {
        char ch = '$';
        for (int i = 0; i < 6; i++) {
            ch = (char) input.read();
        }
        assertNotSame('3', ch);
    }

    public void testReadAfterClose() throws IOException {
        input.close();
        try {
            input.read();
            fail("no exception for read past end");
        } catch (IOException e) {
        }
    }

    /**
     * 直接通过名字反射测试函数
     */
    public static Test suits() {
        TestSuite suit = new TestSuite();
        suit.addTest(new FileReaderTester("testRead"));
        suit.addTest(new FileReaderTester("testFailRead"));
        return suit;
    }

    public static void main(String[] args) {
        /**
         * 通过名字反射
         */
//        TestRunner.run(suits());
        /**
         * 通过类名找出test*的方法进行测试
         */
        TestRunner.run(new TestSuite(FileReaderTester.class));
    }
}
