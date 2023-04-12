package com.nju.concurrent.ch07;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * @description 在Thread中，通过腹泻interrupt来封装非标准取消
 * @date:2022/12/22 16:35
 * @author: qyl
 */
public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream ( );
    }

    @Override
    public void interrupt() {

        try {
            socket.close ( );
        } catch (IOException e) {
            throw new RuntimeException (e);
        }finally {
            super.interrupt ();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buf = new byte[1024];
            int c;
            while((c = in.read (buf))!=-1){
                processBuffer(buf,c);
            }
        } catch (IOException e) {
//            throw new RuntimeException (e);
            // 允许线程退出
        }
    }

    private void processBuffer(byte[] buf, int c) {
        System.out.println (Arrays.toString (Arrays.copyOfRange (buf, 0, c)));
    }
}
