package top.yamhk.terminal.server4test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSLock implements Lock {
    private final AtomicReference<QNode> tail;
    private final ThreadLocal<QNode> myNode;

    public MCSLock() {
        tail = new AtomicReference<>(null);
        myNode = ThreadLocal.withInitial(QNode::new);
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode preNode = tail.getAndSet(qnode);
        if (preNode != null) {
            qnode.locked = false;
            preNode.next = qnode;
            //wait until predecessor gives up the lock
            while (!qnode.locked) {
            }
        }
        qnode.locked = true;
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
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            //后面没有等待线程的情况
            if (tail.compareAndSet(qnode, null)) {
                //真的没有等待线程，则直接返回，不需要通知
                return;
            }
            //wait until predecessor fills in its next field
            // 突然有人排在自己后面了，可能还不知道是谁，下面是等待后续者
            while (qnode.next == null) {
            }
        }
        //后面有等待线程，则通知后面的线程
        qnode.next.locked = true;
        qnode.next = null;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private class QNode {
        /**
         * 是否被qNode所属线程锁定
         */
        private volatile boolean locked = false;
        /**
         * 与CLHLock相比，多了这个真正的next
         */
        private volatile QNode next = null;
    }
}
