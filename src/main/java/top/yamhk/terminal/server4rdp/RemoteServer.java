package top.yamhk.terminal.server4rdp;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteServer {

    private ObjectInputStream objectInputStream;
    private OutputStream ous;

    public static void main(String[] args) {
        try {
            new RemoteServer().setupServer(66666);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setupServer(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream ins = socket.getInputStream();
            objectInputStream = new ObjectInputStream(ins);
            ous = socket.getOutputStream();
            DataOutputStream dous = new DataOutputStream(ous);
            EventReadThread eventReadThread = new EventReadThread(objectInputStream);
            eventReadThread.start();
            CaptureThread captureThread = new CaptureThread(dous);
            captureThread.start();
        }
    }
}
