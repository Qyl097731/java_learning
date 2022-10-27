package chapter08.demo02;

import java.io.*;
import java.net.Socket;

/**
 * @description 连接到dict.org 把命令行输入的所有单词翻译成拉丁语，过滤所有响应码开头的数据
 * @date:2022/10/27 15:22
 * @author: qyl
 */
public class DictClient {
    public static final String SERVER = "dict.org";
    public static final int PORT = 2628;
    public static final int TIMEOUT = 15000;

    /**
     * reader 读取响应数据 writer向服务器写请求
     * @param args
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER, PORT)) {
            socket.setSoTimeout(TIMEOUT);
            Writer writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            for (String word : args) {
                define(word, writer, reader);
            }
            writer.write("quit\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void define(String word, Writer writer, BufferedReader reader) throws IOException {
        /**
         * 发送请求
         */
        writer.write("DEFINE eng-lat " + word + "\r\n");
        writer.flush();

        /**
         * 解析响应
         */
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (line.startsWith("250 ")){
                return;
            } else if (line.startsWith("552 ")) {
                System.out.println("No definition found for " + word);
            }else if (line.matches("\\d\\d\\d .*")) continue;
            else if (line.trim().endsWith(".")) continue;
            else System.out.println(line);
        }
    }
}
