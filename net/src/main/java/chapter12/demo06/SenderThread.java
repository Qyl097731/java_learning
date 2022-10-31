package chapter12.demo06;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @date:2022/10/31 9:09
 * @author: qyl
 */
public class SenderThread extends Thread{
    private InetAddress server;
    private DatagramSocket socket;
    private int port;
    private volatile boolean stopped = false;

    public SenderThread(InetAddress server, DatagramSocket socket, int port) {
        this.server = server;
        this.socket = socket;
        this.port = port;
        this.socket.connect(server,port);
    }

    public void halt(){
        this.stopped = true;
    }

    @Override
    public void run() {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            if (stopped) return;
            String theLine = null;
            try {
                theLine = userInput.readLine();
                if (theLine.equals(".")) break;;
                byte[] data = theLine.getBytes();
                DatagramPacket output = new DatagramPacket(data,data.length,server,port);
                socket.send(output);
                Thread.yield();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
