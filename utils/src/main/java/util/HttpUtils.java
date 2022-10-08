package util;

/**
 * projectName:  epidemicSystem
 * packageName: com.njxzc.utils
 * date: 2022-04-04 23:10
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *
 * 对HttpClient的封装，方便后续数据的爬取
 * @author 邱依良
 */
public abstract class HttpUtils {
    /** HttpClient连接池声明
     *  userAgent请求头声明
     *  config 请求配置声明
     **/
    private static PoolingHttpClientConnectionManager cm;
    private static String userAgent = null;
    private static RequestConfig config = null;

    static{
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(4);
        config = RequestConfig.custom()
                .setConnectionRequestTimeout(10000)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36";

    }


    public static String getHtml(String url){
        //1、创建HttpClient对象
        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(config)
                .build();
        //2、创建HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("user-Agent",userAgent);
        //3、执行请求
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //获取数据
            if(response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity(),"utf-8");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
