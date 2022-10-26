package chapter07.demo07;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CacheRequest;

/**
 * @date:2022/10/26 13:41
 * @author: qyl
 */
public class SimpleCacheRequest extends CacheRequest {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    /**
     * 通过OutputStream的形式要写入到缓存
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream getBody() throws IOException {
        return out;
    }

    @Override
    public void abort() {
        out.reset();
    }

    public byte[] getData(){
        if (out.size() == 0) return null;
        return out.toByteArray();
    }
}
