package top.yamhk.terminal.server4history;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

/*DOS指令的批量执行,以及信息的读取*/
public class LearnBaseIPCMD {

    public static void main(String[] args) throws Exception {
        // 输出IPv4地址
        InetAddress ipv4Address1 = InetAddress.getByName("1.2.3.4");
        System.out.println("ipv4Address1: " + ipv4Address1.getHostAddress());
        InetAddress ipv4Address2 = InetAddress.getByName("www.ibm.com");
        System.out.println("ipv4Address2: " + ipv4Address2.getHostAddress());
        // 输出IPv6地址
        InetAddress ipv6Address1 = InetAddress.getByName("abcd:123::22ff");
        System.out.println("ipv6Address1: " + ipv6Address1.getHostAddress());
        // 输出本机全部的IP地址
        InetAddress[] Addresses = InetAddress.getAllByName("1.2.3.4");
        for (InetAddress address : Addresses) {
            System.out.println("本机地址：" + address.getHostAddress());
        }
        new LearnBaseIPCMD().xip();
    }

    public void xip() {
        InetAddress netAddress = null;
        try {
            netAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("unknown host!");
            e.printStackTrace();
        }
        String hostIP = netAddress.getHostAddress();
        String hostName = netAddress.getHostName();
        System.out.println("host ip:" + hostIP);
        System.out.println("host userMsg:" + hostName);
        System.out.println(hostIP.length());
        /* 局域网用户 */
        System.out.println(hostIP.split(".").length);
        System.out.println(Arrays.toString(hostIP.split(".")));
        ipseek(hostIP);

        /* 获取java虚拟机和系统的信息 */
        Properties properties = System.getProperties();
        Set<String> set = properties.stringPropertyNames();
        for (String name : set) {
            System.out.println(name + ":\n\t" + properties.getProperty(name));
        }
    }

    void ipseek(String ipex) {
        System.out.println(ipex);
        System.out.println(ipex.split(".").length);
        System.out.println(Arrays.toString(ipex.split(".")));
        String hostwd = "";
        hostwd += ipex.split(".")[0];
        hostwd += ipex.split(".")[1];
        hostwd += ipex.split(".")[2];
        hostwd += ".";

        /* 网段:255 */
        System.out.println(hostwd);
        String order = "";
        String ipx = "ping " + hostwd;
        for (int i = 1; i < 5; i++) {
            order = ipx + i;
            new OrderThread(order, "GBK").start();
            System.out.println(order);
            // if (i % 10 == 0)
            try {
                Thread.sleep(2048 * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class OrderThread extends Thread {
    String order;
    String charset;
    String str_res = "", str_tem = "";
    InputStreamReader isr;

    public OrderThread(String order, String charset) {
        this.order = order;
        this.charset = charset;
    }

    @Override
    public void run() {
        try {
            Process process = Runtime.getRuntime().exec(order);
            isr = new InputStreamReader(process.getInputStream(), charset);
            BufferedReader br = new BufferedReader(isr);
            /* UTF-8,GBK */
            while ((str_tem = br.readLine()) != null) {
                str_res += str_tem + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        order += "-->" + str_res;
        System.out.println(order);
    }
}
