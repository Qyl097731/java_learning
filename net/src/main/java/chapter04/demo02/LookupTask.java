package chapter04.demo02;

import java.net.InetAddress;
import java.util.concurrent.Callable;

/**
 * @description IP ----> DNS解析成域名
 * @date:2022/10/24 16:59
 * @author: qyl
 */
public class LookupTask implements Callable<String> {
    private final String line;

    public LookupTask(String line) {
        this.line = line;
    }

    @Override
    public String call() throws Exception {
        int index = line.indexOf(' ');
        String ip = line.substring(0,index);
        String theRest = line.substring(index);
        String hostName = InetAddress.getByName(ip).getHostName();
        return hostName + "." + theRest;
    }
}
