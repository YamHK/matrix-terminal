package top.yamhk.terminal.server4test.thread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.yamhk.core.base.utils.XcodeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ThreadCountTestNature {
    static List<Integer> number = Lists.newArrayList();
    static Integer[] sum;
    static List<Runnable> runnableList = Lists.newArrayList();
    static CountDownLatch countDownLatch;

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * main
     *
     * @param args args
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 12; i++) {
            int numberCount = 100_0000;
            for (int j = 0; j < numberCount; j++) {
                number.add(j);
            }
            testWithMultiThread(i, number);
            XcodeUtils.sleepSecondThread(3);
        }
    }

    private static void testWithMultiThread(int threadCount, List<Integer> number) throws InterruptedException {
        countDownLatch = new CountDownLatch(threadCount);
        sum = new Integer[threadCount];
        Long start = System.currentTimeMillis();
        List<List<Integer>> lists = averageAssign(number, threadCount);
        for (int i = 0; i < threadCount; i++) {
            sum[i] = 0;
            runnableList.add(new TaskClient(i, lists.get(i)));
        }
        runnableList.forEach(e -> new Thread(e, "1").start());
        countDownLatch.await();
        Long end = System.currentTimeMillis();
        log.error("sum:{}/{},cost:{}", sum, threadCount, (end - start));
    }

    /**
     * 模拟线程-
     * Object o=new Object();4byte+8byte=12byte
     *
     * @author xx252016
     */
    static class TaskClient implements Runnable {
        private final Integer taskNum;
        Logger log = LoggerFactory.getLogger(this.getClass());
        List<Integer> number;

        public TaskClient(int taskNum, List<Integer> number) {
            this.taskNum = taskNum;
            this.number = number;

        }

        @Override
        public void run() {
            number.forEach(e -> sum[taskNum] += e);
            countDownLatch.countDown();
        }
    }
}
