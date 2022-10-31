package chapter12.demo04;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @description poke发送空的outgoing，并读取相应，初始null，直到在指定时间内读取到response字段
 * @date:2022/10/31 7:56
 * @author: qyl
 */
public class UDPPoke {
    private int bufferSize;
    private int timeout;
    private InetAddress host;
    private int port;

    public UDPPoke(InetAddress host, int port, int bufferSize, int timeout) {
        this.bufferSize = bufferSize;
        this.host = host;
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port out of range");
        }
        this.port = port;
        this.timeout = timeout;
    }

    public UDPPoke(InetAddress host, int port, int bufferSize) {
        this(host, port, bufferSize, 30000);
    }


    public UDPPoke(InetAddress host, int port) {
        this(host, port, 8192, 30000);
    }

    public byte[] poke(){
        try(DatagramSocket socket = new DatagramSocket(0)) {
            DatagramPacket outgoing = new DatagramPacket(new byte[1],1,host,port);
            socket.connect(host,port);
            socket.setSoTimeout(timeout);
            socket.send(outgoing);
            DatagramPacket incoming = new DatagramPacket(new byte[bufferSize],bufferSize);
            // 阻塞 直到收到响应
            socket.receive(incoming);
            int numBytes = incoming.getLength();
            byte[] response = new byte[numBytes];
            System.arraycopy(incoming.getData(),0,response,0,numBytes);
            return response;
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        InetAddress host;
        int port =0;
        try{
            host = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        } catch (UnknownHostException e) {
            System.out.println("Usage: java UDPPoke host port");
            return;
        }

        UDPPoke poker = new UDPPoke(host,port);
        byte[] response = poker.poke();
        if (response == null){
            System.out.println("No response within allowed time");
            return;
        }
        String result =new String(response, StandardCharsets.US_ASCII);
        System.out.println(result);
    }
}
