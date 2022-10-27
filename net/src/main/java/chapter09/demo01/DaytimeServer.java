package chapter09.demo01;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @description Daytime服务器
 * @date:2022/10/27 20:46
 * @author: qyl
 */
public class DaytimeServer {
    public final static int PORT = 13;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (Socket connection = server.accept()) {
                    Writer out = new OutputStreamWriter(connection.getOutputStream());
                    Date now = new Date();
                    out.write(now.toString() + "\r\n");
                    out.flush();
                }catch (IOException e){}
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
