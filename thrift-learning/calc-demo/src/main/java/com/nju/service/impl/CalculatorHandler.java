package com.nju.service.impl;

import com.nju.exception.InvalidOperation;
import com.nju.model.SharedStruct;
import com.nju.model.Work;
import com.nju.service.Calculator;
import org.apache.thrift.TException;

import java.util.Map;

/**
 * @author qiuyiliang
 */
public class CalculatorHandler implements Calculator.Iface{
    private Map<Integer, SharedStruct> log;
    @Override
    public void ping() throws TException {
        System.out.println("ping()");
    }

    @Override
    public int add(int num1, int num2) throws TException {
        System.out.println("add(" + num1 + "," + num2 + ")");
        return num1 + num2;
    }

    @Override
    public int calculate(int logid, Work work) throws InvalidOperation, TException {
        System.out.println("calculate(" + logid + ", {" + work.op + "," + work.num1 + "," + work.num2 + "})");
        int val = 0;
        switch (work.op) {
            case ADD:
                val = work.num1 + work.num2;
                break;
            case SUBTRACT:
                val = work.num1 - work.num2;
                break;
            case MULTIPLY:
                val = work.num1 * work.num2;
                break;
            case DIVIDE:
                if (work.num2 == 0) {
                    InvalidOperation invalidOperation = new InvalidOperation();
                    invalidOperation.what = work.op.getValue();
                    invalidOperation.why = "Cannot divide by 0";
                    throw invalidOperation;
                }
                val = work.num1 / work.num2;
                break;
            default:
                InvalidOperation io = new InvalidOperation();
                io.what = work.op.getValue();
                io.why = "Unknown operation";
                throw io;
        }
        SharedStruct entry = new SharedStruct();
        entry.key = logid;
        entry.value = Integer.toString(val);
        log.put(logid, entry);

        return val;
    }

    @Override
    public void zip() throws TException {
        System.out.println("zip()");
    }

    @Override
    public SharedStruct getStruct(int key) throws TException {
        System.out.println("getStruct(" + key + ")");
        return log.get(key);
    }
}
