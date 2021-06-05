/**
 *
 */
package top.yamhk.terminal.server4history;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * socket编程
 * 网络基础知识、InerAddress类、RUL、TCP编程、UDP编程
 * <p>
 * TCP/IP模型
 * 应用层,(HTTP、FTP、SMTP、TELNET)
 * 传输层,(TCP/IP协议)
 * 网络层,
 * 数据链路层,
 * 物理层,(网线、双绞线、网卡)
 * <p>
 * IP地址和端口号组成SOCKET
 * <p>
 * Socket通信实现步骤
 * 创建ServerSoket和Socket
 * 打开连接到socket的输入/输出流
 * 按照协议对Socket进行读写操作
 * 关闭输入输出流,关闭Socket
 * <p>
 * java -jar com.clientChat.jar
 */

/**
 * 2016年12月7日上午10:57:11
 */
public class LearnBaseSocket {

    public static void main(String[] args) {
        LearnBaseSocket bs = new LearnBaseSocket();
        bs.coldeURL();
        bs.coldeInetaddress();
        bs.UDPServer();
        bs.UDPClient();
    }

    /**
     * 2016年12月7日上午10:47:47
     */
    void coldeInetaddress() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address.getHostName());
            System.out.println(address.getHostAddress());
            System.out.println(Arrays.toString(address.getAddress()));
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2016年12月7日上午10:57:36
     */
    void coldeURL() {
        try {
            String urlString = "http://www.imooc.com/";
            URL jw = new URL(urlString);
            // System.out.println(UtilsOther.getHtml("http://www.jxjiang.xyz"));
            URL jxjw = new URL(jw, "/index.html?username=tom#test");
            // 协议、主机、端口号、路径、文件名、相对路径、查询字符串
            System.out.println(jxjw.getProtocol());
            System.out.println(jxjw.getHost());
            System.out.println(jxjw.getPort());
            System.out.println(jxjw.getPath());
            System.out.println(jxjw.getFile());
            System.out.println(jxjw.getRef());
            System.out.println(jxjw.getQuery());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2016年12月7日下午12:17:08
     */
    void UDPServer() {
        try {
            DatagramSocket socket = new DatagramSocket(55555);
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            System.out.println("chatServerRoom start...");
            socket.receive(packet);
            String info = new String(data, 0, packet.getLength());
            System.out.println("chatServerRoom" + info);
            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2016年12月7日下午12:21:53
     */
    private void UDPClient() {
        try {
            InetAddress add = InetAddress.getByName("localhost");
            int port = 55555;
            byte[] data = "usernaem zhangsan,userMsg lisi".getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, add,
                    port);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
