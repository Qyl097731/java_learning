package chapter02.demo01;

import java.io.IOException;

/**
 * @description
 * @date:2022/11/3 21:49
 * @author: qyl
 */
public class StaticResourceProcessor {
    public void process(Request request,Response response){
        try{
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
