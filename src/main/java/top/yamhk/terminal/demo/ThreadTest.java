package top.yamhk.terminal.demo;


/**
 * @author admin
 */
public class ThreadTest {
    boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();
        new Thread(threadTest::m, "t1").start();
        Thread.sleep(1000);
        threadTest.running = false;
    }

    void m() {
        System.out.println("m start...");
        while (running) {
        }
        System.out.println("m end...");
    }
}
