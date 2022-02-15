package com.ThreadTest;

import com.ThreadPool.ThreadPoolApplication;
import com.ThreadPool.task.TaskTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author chenming
 * @description
 * @create: 2022-02-11
 */
@SpringBootTest(classes = ThreadPoolApplication.class)
public class test {

    @Autowired
    private TaskTest taskTest;

    @Test
    public void sdg() {
        for (int i = 0; i < 200; i++) {
            taskTest.ss(i);
        }
    }

    class TestThread1 extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("TestThread----run  " + i);
            }
        }
    }

    @Test
    public void test() {

        TestThread1 testThread1 = new TestThread1();
        testThread1.start();

        for (int i = 0; i < 200; i++) {
            System.out.println("mianThread-----run  " + i);
        }
    }

    class TestThread2 implements Runnable {

        private volatile int ticketNum = 10;
        boolean flag = true;

        @Override
        public void run() {
            while (flag) {

                try {
                    buyTicket();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        void buyTicket() throws InterruptedException {
            if (ticketNum <= 0) {
                flag = false;
                return;
            }
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + "-->第" + ticketNum-- + "张");

        }


    }

    @Test
    public void test2() throws InterruptedException {
        TestThread2 testThread2 = new TestThread2();

        Thread t1 = new Thread(testThread2, "first");
        Thread t2 = new Thread(testThread2, "second");
        Thread t3 = new Thread(testThread2, "third");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("main  end");

    }

    @Test
    public void test3() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                System.out.println("t1 running");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        Thread t2 = new Thread(()->{
            try {
                System.out.println("t2 running");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        t1.join();
        t2.join();

        System.out.println("main end");
    }

    @Test
    public void test4() throws IOException {

    }

}
