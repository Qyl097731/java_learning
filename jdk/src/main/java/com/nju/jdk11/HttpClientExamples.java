package com.nju.jdk11;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * @description
 * @date 2024/5/29 18:26
 * @author: qyl
 */
public class HttpClientExamples {
    public static void testSyncGet() throws URISyntaxException, IOException, InterruptedException {

        // 创建HttpClient请求
        HttpClient client = HttpClient.newHttpClient ();

        // 构建get请求
        HttpRequest request = HttpRequest.newBuilder ()
                .uri (new URI ("https://www.baidu.com"))
                .GET ()
                .build ();

        // 发送请求并接收响应
        HttpResponse<String> response = client.send (request, HttpResponse.BodyHandlers.ofString ());

        // 打印结果
        System.out.println ("Response Code : " + response.statusCode ());
        System.out.println ("Response Body : " + response.body ());
    }

    public static void testAsyncPost() throws URISyntaxException{

        // 创建HttpClient请求
        HttpClient client = HttpClient.newHttpClient ();

        // 构建Post请求
        HttpRequest request = HttpRequest.newBuilder ()
                .uri (new URI ("https://www.baidu.com"))
                .POST (HttpRequest.BodyPublishers.noBody ())
                .header("Content-Type", "application/json")
                .build ();

        // 发送异步请求
        CompletableFuture<HttpResponse<String>> response = client.sendAsync (request, HttpResponse.BodyHandlers.ofString ());

        // 异步结果处理
        response.thenApply (HttpResponse::body)
                .thenAccept (System.out::println);

        // 保证异步处理完成
        response.join ();
    }

    public static void testHttp2Get() throws URISyntaxException, IOException, InterruptedException {
        // 创建支持 HTTP/2 的 HttpClient 实例
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        // 构建 GET 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://www.baidu.com"))
                .GET()
                .build();

        // 发送请求并接收响应
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 输出响应状态码和响应体
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }


        public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
            testSyncGet();
            testAsyncPost();
            testHttp2Get();
    }
}
