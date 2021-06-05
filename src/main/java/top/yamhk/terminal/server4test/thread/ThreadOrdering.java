package top.yamhk.terminal.server4test.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: YamHK
 * @Date: 2020/10/16 20:32
 */
@Slf4j
public class ThreadOrdering {

    private static int a;
    private static int b;
    private static int x;
    private static int y;

    @SneakyThrows
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            a = b = x = y = 0;
            CountDownLatch countDownLatch = new CountDownLatch(2);
            Thread t1 = new Thread(() -> {
                a = 1;
                x = b;
                countDownLatch.countDown();
            });
            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
                countDownLatch.countDown();
            });
            t1.start();
            t2.start();
            countDownLatch.await();
            if (x != 1 || y != 1) {
                if (x != 1 && y != 1) {
                    log.error("error {} with:x={},y={} ", i, x, y);
                    break;
                }
            }
        }
        Long end = System.currentTimeMillis();

        //2147483647/158s
        log.error("end with no 00 with {}/{}s", Integer.MAX_VALUE, (end - start) / 1000);
    }
}
