package chapter01.demo01;

import java.io.*;
import java.net.URLConnection;

/**
 * @description
 * @date:2022/11/3 16:10
 * @author: qyl
 */
public class Response {
    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output){
        this.output = output;
    }

    public void setRequest(Request request){
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try{
            String filename = request.getUri();
            File file = new File(HttpServer.WEB_ROOT,filename);
            if (file.exists()){
                String mimeType = URLConnection.getFileNameMap().getContentTypeFor(filename);
                String header = "HTTP/1.1 200 OK\r\n"
                        + "Server: OneFile 2.0\r\n"
                        + "Content-type: " + mimeType + ";\r\n\r\n";
                output.write(header.getBytes());
                fis = new FileInputStream(file);
                int ch ;
                while ((ch = fis.read(bytes,0,BUFFER_SIZE)) != -1 ){
                    output.write(bytes,0,ch);
                }
            }else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length:23\r\n"+
                        "\r\n"+
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                fis.close();
            }
        }
    }
}
