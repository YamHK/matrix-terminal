package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class HDU2034 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, m, temp;
        Set<Integer> a, b;
        while (in.hasNext()) {
            n = in.nextInt();
            m = in.nextInt();
            if (n == 0 && m == 0) {
                break;
            }
            a = new TreeSet<Integer>();
            b = new TreeSet<Integer>();
            for (int i = 1; i <= n; i++) {
                a.add(in.nextInt());
            }
            for (int i = 1; i <= m; i++) {
                b.add(in.nextInt());
            }
            Iterator<Integer> it = b.iterator();
            while (it.hasNext()) {
                temp = it.next();
                a.remove(temp);
            }
            if (a.size() == 0) {
                System.out.print("NULL");
            } else {
                it = a.iterator();
                while (it.hasNext()) {
                    System.out.print(it.next() + " ");
                }
            }
            System.out.println();
        }
        in.close();
    }

}
