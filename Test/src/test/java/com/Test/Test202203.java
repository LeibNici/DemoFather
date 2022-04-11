package com.Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.awt.util.IdentityArrayList;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author chenming
 * @description
 * @create: 2022-03-22
 */
@SpringBootTest
@Slf4j
public class Test202203 {

    @Test
    public void test1() {
        ExecutorService pool = Executors.newFixedThreadPool(20);

        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            Future<List<Integer>> submit = pool.submit(new add(1, 2, latch));
            try {
                List<Integer> list = submit.get();
                log.info(String.valueOf(list.size()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    static class add implements Callable<List<Integer>> {

        private Integer a;
        private Integer b;
        private CountDownLatch latch;

        public add(Integer a, Integer b, CountDownLatch latch) {
            this.a = a;
            this.b = b;
            this.latch = latch;
        }

        @Override
        public List<Integer> call() throws Exception {
            try {
                return new LinkedList<>(Arrays.asList(a, b));
            } finally {
                latch.countDown();
            }
        }
    }

    @Test
    void test(){
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add(i);
        }

        list1.subList(9, 5);
    }

}
