package top.yamhk.terminal.server4test.thread;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ThreadCountTest4Collection {
    static int threadCount = 10;
    static CountDownLatch countDownLatch = new CountDownLatch(threadCount);
    static Queue number = Queues.newLinkedBlockingDeque();

    /**
     * main
     *
     * @param args args
     */
    public static void main(String[] args) throws InterruptedException {
        List<Collection<String>> list = Lists.newArrayList();
        list.add(Lists.newArrayList());
//        list.add(Lists.newLinkedList());
//        list.add(Queues.newArrayDeque());
//        list.add(new Vector<>());
        //
        list.forEach(ThreadCountTest4Collection::testCollectionCost);
//       set
        HashSet<String> hashSet = Sets.newHashSet();
        ArrayList<String> result = Lists.newArrayList();
        int numberCount = 40_0000;
        int preCount = 0;
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < numberCount; i++) {
            preCount = hashSet.size();
            hashSet.add("id:" + i);
            if (preCount != hashSet.size()) {
                result.add("id:" + i);
            }
        }
        Long t2 = System.currentTimeMillis();
        log.error("sum:{}/{}", hashSet.getClass().getSimpleName(), (t2 - t1));
    }

    private static void testCollectionCost(Collection collection) {
        int numberCount = 40_0000;
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < numberCount; i++) {
            collection.add("id:" + i);
            if (collection.contains(i)) {
                log.error("error");
            }
        }
        Long t2 = System.currentTimeMillis();
        log.error("sum:{}/{}", collection.getClass().getSimpleName(), (t2 - t1));
    }
}
