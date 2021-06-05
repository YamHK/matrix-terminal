package top.yamhk.terminal.server4test.thread;

import lombok.SneakyThrows;

/**
 * @Author: YamHK
 * @Date: 2020/10/16 20:41
 */
public class ThreadThisEscape {

    int x = 8;

    public ThreadThisEscape() {
        new Thread(() -> {
            System.out.println(this.x);
        }).start();
    }

    @SneakyThrows
    public static void main(String[] args) {
        ThreadThisEscape thisEscape = new ThreadThisEscape();
        System.in.read();
    }
}
