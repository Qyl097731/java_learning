package chapter07.demo07;

import chapter07.demo06.CacheControl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheResponse;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description CacheResponse的一个具体子类
 * 保存了过期时间、以及URLConnection的头部信息
 * @date:2022/10/26 14:58
 * @author: qyl
 */
public class SimpleCacheResponse extends CacheResponse {
    private final Map<String, List<String>> headers;
    private final SimpleCacheRequest request;
    private final Date expires;
    private final CacheControl control;

    public SimpleCacheResponse(SimpleCacheRequest request, URLConnection uc, CacheControl control) {
        this.request = request;
        this.control = control;
        this.expires = new Date(uc.getExpiration());
        this.headers = Collections.unmodifiableMap(uc.getHeaderFields());
    }


    @Override
    public Map<String, List<String>> getHeaders() throws IOException {
        return headers;
    }

    /**
     * 把缓冲中的数据放入到InputStream作为用户可操作的返回数据
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(request.getData());
    }

    public CacheControl getControl() {
        return control;
    }

    public boolean isExpired() {
        Date now = new Date();
        if (control.getMaxAge().before(now)) return true;
        else if (expires != null && control.getMaxAge() != null){
            return expires.before(now);
        }else{
            return false;
        }
    }
}
