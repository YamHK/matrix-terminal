package top.yamhk.terminal.server4test.thread;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.yamhk.core.base.utils.XcodeUtils;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ThreadCountTest {
    static int threadCount = 10;
    static CountDownLatch countDownLatch = new CountDownLatch(threadCount);
    static Queue number = Queues.newLinkedBlockingDeque();

    /**
     * main
     *
     * @param args args
     */
    public static void main(String[] args) throws InterruptedException {
        List<Runnable> runnableList = Lists.newArrayList();
        int[] sum = {0};
        int numberCount = 100;
        for (int i = 0; i < numberCount; i++) {
            number.add(i);
        }
        Long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            TaskClient command = new TaskClient(number, sum, i, runnableList);
            runnableList.add(command);
        }

        runnableList.forEach(XcodeUtils.getDefaultExecutor()::execute);
        countDownLatch.await();
        Long end = System.currentTimeMillis();

        log.error("sum:{}/{}", sum, (end - start));
    }

    /**
     * 模拟线程-
     * Object o=new Object();4byte+8byte=12byte
     *
     * @author xx252016
     */
    static class TaskClient implements Runnable {
        private final long taskNum;
        Logger log = LoggerFactory.getLogger(this.getClass());
        List<Runnable> runnableList;
        Queue<Integer> number;
        int[] sum;

        public TaskClient(Queue<Integer> number, int[] sum, int taskNum, List<Runnable> runnableList) {
            this.runnableList = runnableList;
            this.number = number;
            this.taskNum = taskNum;
            this.sum = sum;
        }

        @Override
        public void run() {
            log.warn("正在执行task:{}/{}", taskNum, runnableList.size());
            while (!number.isEmpty()) {
                sum[0] += number.poll();
            }
            //执行完毕
            runnableList.remove(this);
            countDownLatch.countDown();
            log.warn("执行完毕task:{}/{}--ending--{},sum:{}", taskNum, runnableList.size(), taskNum, sum[0]);
        }
    }
}
