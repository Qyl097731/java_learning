package chapter02.demo01;

import chapter01.demo01.HttpServer;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletResponse;

import java.io.*;
import java.net.URLConnection;
import java.util.Locale;

/**
 * @description
 * @date:2022/11/3 21:38
 * @author: qyl
 */
public class Response implements ServletResponse {
    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;
    PrintWriter writer;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        String filename = request.getUri();

        String mimeType = URLConnection.getFileNameMap().getContentTypeFor(filename);
        String header = "HTTP/1.1 200 OK\r\n"
                + "Server: OneFile 2.0\r\n"
                + "Content-type: " + mimeType + ";\r\n\r\n";
        output.write(header.getBytes());

        File file = new File(HttpServer.WEB_ROOT, filename);
        try (FileInputStream fis = new FileInputStream(file)) {
            int c;
            while ((c = fis.read(bytes, 0, BUFFER_SIZE)) != -1) {
                output.write(bytes, 0, c);
            }
        } catch (FileNotFoundException e) {
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length:23\r\n"+
                    "\r\n"+
                    "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        }
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        // println 自动清缓存; print不会清缓存
        // 如果service在最后一行调用print不会发送给浏览器
        writer = new PrintWriter(output,true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentLengthLong(long len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
