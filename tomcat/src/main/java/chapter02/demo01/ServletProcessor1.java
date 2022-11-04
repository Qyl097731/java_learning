package chapter02.demo01;

import chapter01.demo01.HttpServer;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import java.net.*;

/**
 * @description
 * @date:2022/11/3 21:51
 * @author: qyl
 */
public class ServletProcessor1 {

    public void process(Request request, Response response) {
        String uri = request.getUri();
        // 获取uri中的服务名字
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        // 通过URL类加载器来加载servlet类
        URLClassLoader loader  =null;
        try{
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            // 指定类载入器从来查找类，若URL以/结尾，表明是一个目录，否则是一个JAR文件
            File classPath = new File(HttpServer1.CLASS_ROOT);
            // 指定仓库路径
            String repository = (new URL("file",null,classPath.getCanonicalPath()+File.separator)).toString();
            urls[0] = new URL(null,repository,streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加载类
        Class myClass = null;
        try{
            myClass = loader.loadClass("chapter02.demo01." + servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Servlet servlet;
        // 创建Servlet实例，向下转型嗲用service
        try{
            servlet = (Servlet) myClass.newInstance();
            servlet.service(request,response);
        } catch (IllegalAccessException | InstantiationException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
