package com.nju.service.impl;

import com.nju.service.MultiplicationService;
import org.apache.thrift.TException;

/**
 * 乘法处理
 * @author qiuyiliang
 */
public class MultiplicationHandler implements MultiplicationService.Iface {
    @Override
    public int multiply(int n1, int n2) throws TException {
        System.out.println("Multiply(" + n1 + "," + n2 + ")");
        return n1 * n2;
    }
}
