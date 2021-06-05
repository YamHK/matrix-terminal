package top.yamhk.terminal.server4test.thread;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.yamhk.core.base.utils.XcodeUtils;

import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2019/4/20 22:32
 */
public class ThreadPoolTest {
    /**
     * main
     *
     * @param args args
     */
    public static void main(String[] args) {
        List<Runnable> runnableList = Lists.newArrayList();
        int threadCount = 10;
        for (int i = 0; i < threadCount; i++) {
            TaskClient myTask = new TaskClient(runnableList, i + 1);
            runnableList.add(myTask);
            XcodeUtils.getDefaultExecutor().execute(myTask);
        }
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

        public TaskClient(List<Runnable> runnableList, int num) {
            this.runnableList = runnableList;
            this.taskNum = num;
        }

        @Override
        public void run() {
            log.warn("正在执行task:{}/{}", taskNum, runnableList.size());
            if (taskNum % 3 == 0) {
                while (true) {
                    runnableList.add(() -> {
                        log.error("freeMemory:{}", Runtime.getRuntime().freeMemory() / 1024 / 1024);
                    });
                }
            }
            XcodeUtils.sleepSecondThread(taskNum * 3);
            //执行完毕
            runnableList.remove(this);
            log.warn("执行完毕task:{}/{}--ending--{}-freeMemory:{}", taskNum, runnableList.size(), taskNum, Runtime.getRuntime().freeMemory());
        }
    }
}
