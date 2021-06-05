package top.yamhk.terminal.server4test.thread;

import lombok.extern.slf4j.Slf4j;
import top.yamhk.core.base.utils.XcodeUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: YamHK
 * @Date: 2020/10/14 19:29
 */
@Slf4j
public class ThreadWaitTest {
    static Thread t1, t2;
    static volatile ReadyToRun current = ReadyToRun.T1;
    static AtomicInteger threadNo = new AtomicInteger(1);

    public static void main(String[] args) {
//        impl();
        char[] ai = new char[26];
        char[] ac = new char[26];
        for (int i = 0; i < 26; i++) {
            ac[i] = (char) ('A' + i);
            ai[i] = (char) i;
        }
        implLockSupport(ai, ac);
        impCAS(ai, ac);
        implAtomic(ai, ac);
    }

    private static void implAtomic(char[] ai, char[] ac) {
        t1 = new Thread(() -> {
            for (int i : ai) {
                while (threadNo.get() != 1) {
                }
                log.warn(">>>:{}", i + 1);
                threadNo.set(2);
            }


        });

        t2 = new Thread(() -> {
            for (char c : ac) {
                while (threadNo.get() != 2) {
                }
                log.warn(">>>:\t\t\t{}", c);
                threadNo.set(1);
            }
        });
        t1.start();
        t2.start();
    }

    private static void impCAS(char[] ai, char[] ac) {
        t1 = new Thread(() -> {
            for (int i : ai) {
                while (current != ReadyToRun.T1) {
                }
                log.warn(">>>:{}", i + 1);
                current = ReadyToRun.T2;
            }


        });

        t2 = new Thread(() -> {
            for (char c : ac) {
                while (current != ReadyToRun.T2) {
                }
                log.warn(">>>:\t\t\t{}", c);
                current = ReadyToRun.T1;
            }
        });
        t1.start();
        t2.start();
    }

    private static void implLockSupport(char[] ai, char[] ac) {
        t1 = new Thread(() -> {
            for (int i : ai) {
                log.warn(">>>:{}", i + 1);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(() -> {
            for (char c : ac) {
                LockSupport.park();
                log.warn(">>>:\t\t\t{}", c);
                LockSupport.unpark(t1);
            }
        });
        t1.start();
        t2.start();
    }

    private static void impl() {
        boolean number = true;
        Integer count = 0;
        XcodeUtils.getDefaultExecutor().execute(() -> echo(true, count));
        XcodeUtils.getDefaultExecutor().execute(() -> echo(false, count));
    }

    private static void echo(boolean number, Integer count) {
        char begin = 'A';
        char end = 'Z';
        Integer current = 0;
        while (begin <= end) {
            log.warn("{}:{}", number, (number ? ((begin - 'A') + 1) : "\t\t\t" + begin));
            begin = (char) (begin + 1);
            while (!number && Math.abs(current++ - count) == 1) {
                XcodeUtils.sleepSecondThread(1);
            }
            count += number ? 1 : 0;
        }
    }

    enum ReadyToRun {T1, T2}
}
