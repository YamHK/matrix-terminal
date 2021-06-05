package top.yamhk.terminal.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;


/**
 * @author yingx
 */
public class Demo {


    public static void main(String[] args) {
        System.out.println("[args]>>>" + Arrays.toString(args));
        new Demo().work();
    }

    /**
     * collection cost
     *
     * @param collection collection
     */
    private static void getCost(Collection<String> collection) {
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < 400_0000; i++) {
            collection.add("id:" + i);
        }
        Long t2 = System.currentTimeMillis();
        long cost = t2 - t1;
        System.out.println("by:" + collection.getClass().getSimpleName() + ":" + cost + "");
    }

    private void work() {
        getCost(new ArrayList<>());
        getCost(new LinkedList<>());
        getCost(new Vector<>());
        getCost(new Stack<>());
        //collection
        Collection<String> collection;
        List<String> list;
        Map<String, String> map;
    }
}
