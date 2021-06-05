package top.yamhk.terminal.server4test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TicketLock implements Lock {
    private final ThreadLocal<Integer> myNum = new ThreadLocal<>();
    private final AtomicInteger serviceNum = new AtomicInteger(0);
    private final AtomicInteger ticketNum = new AtomicInteger(0);
    private final int count = 0;

    @Override
    public void lock() {
        myNum.set(ticketNum.getAndIncrement());
        while (serviceNum.get() != myNum.get()) {
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        serviceNum.compareAndSet(myNum.get(), myNum.get() + 1);
        myNum.remove();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
