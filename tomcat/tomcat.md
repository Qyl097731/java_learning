# 《深入剖析Tomcat》

## 一个简单的Web服务器

### HTTP

- 基于可靠TCP建立连接。
- 发送请求、响应请求
- 断开连接

#### HTTP请求

##### 请求：

- 请求方法、URI、协议版本
- 请求头
- 实体

请求头和请求体之间有一个空行。

##### 响应：

- 协议、状态码、描述
- 响应头
- 响应实体

### Socket类

不细说了，参见net模块的笔记。

### 应用程序

服务器主要是解析请求，响应请求，这里把请求Socket解析成Request，响应体封装成Response，其中HttpServer是服务器。

详见chapter01.demo01


## 一个简单的Servlet容器

### Servlet接口

```java
void init(ServletConfig var1) throws ServletException;

ServletConfig getServletConfig();

void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

String getServletInfo();

void destroy();
```

- init()：在实例化的时候调用init()进行初始化，只会使用该方法一次。可以编写载入数据库驱动、初始化默认值等。
- service():在可客户端请求到达后，servlet执行相应的service()方法，将servletRequest和servletResponse对象作为参数传入。在servlet生命周期内，service()会被多次调用。
- 在servlet实例移除前，servlet容器会调用servlet实例的destroy()方法。只有servlet容器关闭或者要释放内存时且必须所有的该实例的service已经退出或者超时。，才会将servlet实例移除。

### 应用程序

一个功能齐全的servlet容器应该：
- 首次调用servlet时，要载入该servlet，并调用init()
- 针对每个request，创建一个ServletRequest和ServletResponse
- 调用service方法，将ServletRequest和ServletResponse实例传入
- 关闭servlet，调用destroy方法，卸载servlet

### 案例

- chapter02.demo01

    servlet仅做如下几件事：
    - 等待HTTP请求
    - 创建一个ServletRequest和ServletResponse
    - 若请求是静态资源，则调用StaticResourceProcessor对象的process()方法，将ServletRequest和ServletResponse实例传入
    - 若请求的是servlet，则载入相应的servlet类，调用其service()方法，传入ServletRequest和ServletResponse
    
    <b>注意:</b>这个servlet容器，每次请求servlet都会载入相应的servlet类
    
    在demo1基础上，添加了一个Servlet容器，当HTTP请求不是对静态资源的请求的时候就会分发到servletProcessor实例。
    
    在后续chapter02.demo01基础上添加了一些安全措施，外观模式防止接口对外暴露，其余未变。
