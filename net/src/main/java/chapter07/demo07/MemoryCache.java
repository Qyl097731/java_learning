package chapter07.demo07;

import chapter07.demo06.CacheControl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 在请求时存储和获取缓存，同时线程安全的HashMap保证线程安全
 * @date:2022/10/26 15:09
 * @author: qyl
 */
public class MemoryCache extends ResponseCache {

    private final Map<URI, SimpleCacheResponse> responses = new ConcurrentHashMap<>();
    private final int maxEntries;

    public MemoryCache() {
        this(100);
    }

    public MemoryCache(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    @Override
    public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException {
        if ("GET".equals(rqstMethod)) {
            SimpleCacheResponse response = responses.get(uri);
            // 检查过期时间
            if (response != null && response.isExpired()) {
                responses.remove(uri);
                return null;
            }
            return response;
        } else {
            return null;
        }
    }

    @Override
    public CacheRequest put(URI uri, URLConnection conn) throws IOException {
        if (responses.size() >= maxEntries) return null;
        CacheControl control = new CacheControl(conn.getHeaderField("Cache-Control"));
        if (control.isNoCache()) {
            return null;
        } else if (!conn.getHeaderField(0).startsWith("GET")) {
            // 之缓存GET
            return null;
        }

        SimpleCacheRequest request = new SimpleCacheRequest();
        SimpleCacheResponse response = new SimpleCacheResponse(request, conn, control);
        responses.put(uri, response);
        return request;
    }

    @Test
    public void test() {
        ResponseCache.setDefault(new MemoryCache());
    }
}
