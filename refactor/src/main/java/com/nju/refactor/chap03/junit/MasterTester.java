package com.nju.refactor.chap03.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @description
 * @date:2022/10/13 18:59
 * @author: qyl
 */
public class MasterTester extends TestCase {
    public static void main(String[] args) {
        TestRunner.run(suits());
    }

    private static Test suits() {
        TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(FileReaderTester.class));
        return suite;
    }

}
