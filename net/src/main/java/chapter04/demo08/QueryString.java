package chapter04.demo08;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;

/**
 * @description 通过键值对来传递请求参数，把key - value 进行编码。如果前端封装过get post请求应该不陌生
 * @date:2022/10/25 15:19
 * @author: qyl
 */
public class QueryString {
    private StringBuilder query = new StringBuilder();

    public QueryString() {

    }

    public synchronized void add(String name, String value) {
        query.append('&');
        encode(name, value);
    }

    private synchronized void encode(String name, String value) {
        try {
            query.append(URLEncoder.encode(name, "UTF-8"));
            query.append("=");
            query.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    public synchronized String getQuery() {
        return query.toString();
    }

    @Override
    public String toString() {
        return getQuery();
    }

    @Test
    public void test() {
        QueryString qs = new QueryString();
        qs.add("h1", "en");
        qs.add("as_q", "Java");
        qs.add("as_epq", "I/O");
        String url = "https://www.google.com/search?" + qs;
        System.out.println(url);
    }
}
