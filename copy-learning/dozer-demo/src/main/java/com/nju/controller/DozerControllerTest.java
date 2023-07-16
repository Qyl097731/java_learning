package com.nju.controller;

import com.nju.domain.Address;
import com.nju.domain.AddressDTO;
import com.nju.domain.Student;
import com.nju.domain.StudentDTO;
import com.nju.service.CopyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @description
 * @date 2023/7/14 1:12
 * @author: qyl
 */
@Controller
@RequestMapping("/dozer/test")
public class DozerControllerTest {
    @Resource
    private CopyService copyServiceImpl;

    @RequestMapping("/full")
    public String dozerTestFull() {
        try {
            Student student = new Student ();
            student.setName ("yyds");
            student.setDate (null);
            student.setScore (100);
            student.setAddress (new Address ());
            StudentDTO studentDTO = new StudentDTO ();
            copyServiceImpl.copy (student, studentDTO);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return "FAIL";
    }

    @RequestMapping("/null")
    public String dozerTestNull() {
        try {
            Student student = new Student ();
            StudentDTO studentDTO = new StudentDTO ();
            copyServiceImpl.copy (student, studentDTO);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace ();
            return "FAIL";
        }
    }

    @RequestMapping("/simple")
    public String dozer() {
        try {
            ExecutorService threadPool = Executors.newFixedThreadPool (30);
            CyclicBarrier barrier = new CyclicBarrier(30,()-> System.out.println ("finish"));
            for (int i = 0; i < 30; i++) {
                CompletableFuture.runAsync (()->{
                    try {
                        barrier.await ();
                        Address address = new Address ();
                        Address copied = new Address ();
                        copyServiceImpl.copy(address, copied);
                    } catch (Exception e) {
                        e.printStackTrace ();
                    } finally {
                        try {
                            barrier.await ();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            throw new RuntimeException (e);
                        }
                    }
                },threadPool);
            }
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace ();
            return "FAIL";
        }
    }
}
