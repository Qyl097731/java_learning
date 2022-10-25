package chapter04.demo10;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 尝试使用位于proxy.example.com的代理服务器完成所有HTTP连接，如果没有成功解析成特定的URL，就会使用直接连接
 * @date:2022/10/25 16:23
 * @author: qyl
 */
public class LocalProxySelector extends ProxySelector {
    private List<URI> failed = new ArrayList<>();

    @Override
    public List<Proxy> select(URI uri) {
        List<Proxy> result = new ArrayList<>();
        if (failed.contains(uri)
                || !"http".equalsIgnoreCase(uri.getScheme())) {
            result.add(Proxy.NO_PROXY);
        } else {
            // 建立连接
            SocketAddress address = new InetSocketAddress("proxy.example.com", 8000);
            // 指定代理
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            result.add(proxy);
        }
        return result;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        failed.add(uri);
    }

    @Test
    public void test() {
        // 每个虚拟机只有一个ProxySelector，通过ProxySelector.setDefault(selector)修改
        // 之后虚拟机打开的所有连接都将向此ProxySelector询问将要使用的正确代理
        ProxySelector selector = new LocalProxySelector();
        ProxySelector.setDefault(selector);
    }
}
