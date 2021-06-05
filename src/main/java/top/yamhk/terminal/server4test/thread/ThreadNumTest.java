package top.yamhk.terminal.server4test.thread;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;


public class ThreadNumTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("cpu:" + Runtime.getRuntime().availableProcessors());
        int threadCount = 100;
        List<Runnable> runnableList = Lists.newArrayList();
//        ArrayList<Integer> count = Lists.newArrayList();
        CopyOnWriteArrayList<Object> count = Lists.newCopyOnWriteArrayList();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    count.add(j);
                }
                System.out.println(Thread.currentThread().getName() + "--->" + count.size());
                countDownLatch.countDown();
            }, "trd:" + i);
            runnableList.add(thread);
        }
        runnableList.forEach(e -> ((Thread) e).start());
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "--->end" + count.size());
    }
}