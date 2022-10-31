package chapter12.demo05;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description 通过respond方法做出响应，子类需要覆盖这个方法来实现不同类型的而服务器，之后要shutDown设置关闭标志，主循环检查该标志位进行关闭
 * @date:2022/10/31 8:49
 * @author: qyl
 */
public abstract class UDPServer implements Runnable {
    private final int bufferSize; // 单位是字节
    private final int port;
    private final Logger logger = Logger.getLogger(UDPServer.class.getCanonicalName());
    private volatile boolean isShutDown = false;

    public UDPServer(int port, int bufferSize) {
        this.bufferSize = bufferSize;
        this.port = port;
    }

    public UDPServer(int port) {
        this(port, 8192);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[bufferSize];
        try (DatagramSocket socket = new DatagramSocket(port)) {
            socket.setSoTimeout(10000);
            while (true) {
                if (isShutDown) return;
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(incoming);
                    this.respond(socket, incoming);
                } catch (SocketTimeoutException e) {
                    if (isShutDown) return;
                } catch (IOException e) {
                    logger.log(Level.WARNING, e.getMessage(), e);
                }
            }
        } catch (SocketException e) {
            logger.log(Level.SEVERE, "could not bind to port " + port, e);
        }
    }

    protected abstract void respond(DatagramSocket socket, DatagramPacket incoming) throws IOException;

    public void shutDown() {
        this.isShutDown = true;
    }
}
