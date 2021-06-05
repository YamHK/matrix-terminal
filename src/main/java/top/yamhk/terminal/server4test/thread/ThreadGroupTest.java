package top.yamhk.terminal.server4test.thread;

/**
 * @Author: YamHK
 * @Date: 2019/5/9 17:45
 */
public class ThreadGroupTest {


    public String serverStatus() {
        java.lang.ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        //此线程组中活动线程的估计数
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        //把对此线程组中的所有活动子组的引用复制到指定数组中。
        currentGroup.enumerate(lstThreads);
        //打印线程组
        String msgFromAsk = "";
        for (int i = 0; i < noThreads; i++) {
            msgFromAsk += "Thread:" + i + " = " + lstThreads[i].getName() + "\n";
        }
        System.out.println(msgFromAsk);
        return msgFromAsk;
    }
}
