package com.njxzc;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * projectName:  epidemicSystem
 * packageName: PACKAGE_NAME
 * date: 2022-04-04 22:36
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class HttpClient {

    @Test
    public void testGet() throws IOException {
        //1.创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2、创建HttpGet请求并进行相关设置
        HttpGet httpGet = new HttpGet("http://www.itcast.cn/");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");

        //3、发起请求
        CloseableHttpResponse res = httpClient.execute(httpGet);
        if (res.getStatusLine().getStatusCode() == 200) {
            String html = EntityUtils.toString(res.getEntity(), "utf-8");
            System.out.println(html);
        }

        //4、关闭请求
        res.close();
        httpClient.close();
    }

    @Test
    public void testPost() throws IOException {
        //1、创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2、创建HttpPost并设置相关参数
        HttpPost httpPost = new HttpPost("http://www.itcast.cn/");
        //准备集合用来存放请求参数 设置请求体
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", "java"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
        httpPost.setEntity(entity);

        //设置请求头
        httpPost.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");

        //3、发起请求
        CloseableHttpResponse res = httpClient.execute(httpPost);
        //4、判断响应状态以及数据
        if (res.getStatusLine().getStatusCode() == 200) {
            String html = EntityUtils.toString(res.getEntity(), "utf-8");
            System.out.println(html);
        }
        //5、关闭资源
        res.close();
        httpClient.close();
    }

    @Test
    public void testPool() throws IOException {
        //1、创建HttpClient连接管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //2、设置参数
        //设置最大参数
        cm.setMaxTotal(200);
        //设置每个主机的最大并发
        cm.setDefaultMaxPerRoute(4);
        doGet(cm);
        doGet(cm);
    }

    private void doGet(PoolingHttpClientConnectionManager cm) throws IOException {
        //3、从连接池中获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //4、创建httpClient对象
        HttpGet httpGet = new HttpGet("http://www.itcast.cn/");
        //5、获取请求
        CloseableHttpResponse res = httpClient.execute(httpGet);
        //6、获取数据
        if (res.getStatusLine().getStatusCode() == 200) {
            String html = EntityUtils.toString(res.getEntity());
            System.out.println(html);
        }
        //7、释放连接
        res.close();
        //httpClient 需要还给连接池不能关闭
    }

    @Test
    public void testConfig() throws IOException {
        //0、创建请求配置对象
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(10000)    //设置连接超时
                .setConnectTimeout(10000)   //设置创建连接超时
                .setConnectionRequestTimeout(100000)    //设置请求超时
                .build();

        //1.创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2、创建httpGet
        HttpGet httpGet = new HttpGet("http://www.itcast.cn/");
        //3、发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //4、获取响应数据
        if (response.getStatusLine().getStatusCode() == 200) {
            String html = EntityUtils.toString(response.getEntity());
            System.out.println(html);
        }
        //5、关闭资源
        response.close();
        httpClient.close();
    }

}
