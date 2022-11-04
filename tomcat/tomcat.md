# 《深入剖析Tomcat》 

源代码已经附上了，这里只记录思想。

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

详见ex01


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

- ex02

    servlet仅做如下几件事：
    - 等待HTTP请求
    - 创建一个ServletRequest和ServletResponse
    - 若请求是静态资源，则调用StaticResourceProcessor对象的process()方法，将ServletRequest和ServletResponse实例传入
    - 若请求的是servlet，则载入相应的servlet类，调用其service()方法，传入ServletRequest和ServletResponse
    
    <b>注意:</b>这个servlet容器，每次请求servlet都会载入相应的servlet类
    
    在demo1基础上，添加了一个Servlet容器，当HTTP请求不是对静态资源的请求的时候就会分发到servletProcessor实例。
    
    在后续ex02基础上添加了一些安全措施，外观模式防止接口对外暴露，其余未变。

## 连接器

Catalina 含有两个重要模块：连接器和容器。

### StringManager

利用单例模式，一个包对应一个StringManager，通过Map存储所有的StringManager。如果传入的包名在Map中还没有相应的StringManager，就会新建该实例。StringManager含有国际化功能，
会根据服务器的语言环境来选择使用哪个文件。

`getString(String key)`来获取文件中key对应的错误信息，SpringBoot获取配置类似。

### 应用程序

本章应用程序分为：连接器模块、启动模块、核心模块

- 启动模块：Bootstrap类，负责启动应用程序
- 连接器模块：支持以下类型
    - 连接器及其支持类
    - 表示HTTP请求的类及其支持类
    - 表示HTTP相应的类及其支持类
    - 外观类
    - 常量类
- 核心模块：ServletProcessor类和StaticResourceProcessor类

#### 具体阐释：
将HttpServer拆分成HttpConnector（等待HTTP请求工作）和HttpProcessor（创建Request和Response工作)

- 连接器
    - HTTP请求使用HttpRequest类，会作为参数传给Service，必须正确地设置HttpRequest的成员变量，但是连接器不知道service
会使用哪些变量，为了性能，在一些参数被真正调用前，默认不去解析他们。
具体这里使用socketInputStream来读取字节流，先readRequestLine()读取首部第一行，之后readHeader
()获取首部剩下的内容，以键值对返回。

- 处理器
    - 通过parse来解析传来的HTTP请求行和请求头。但是不会解析全部，懒解析。

总的来说两者都不全部解析参数，都是懒加载。

## Tomcat的默认连接器

Tomcat的默认连接器满足以下要求：
- 实现Connector
- 实现Request、Response

工作原理：等待HTTP请求，创建Request和Response，调用容器的invoke()方法，将request和response传递给servlet容器。
`public void invoke(Request request,Response response)`


使用了对象池来避免频繁创建对象带来的性能损耗。同时使用字符数组代替字符串。

### HTTP1.1新特性

- 持久连接
    建立连接，完成一次请求之后保持连接，不会跟之前一样立即关闭
- 块编码
    servlet容器可以在接收到一些字节之后就开始发送相应信息，不必等到收到所有信息。HTTP1.1通过transfer-encoding来标识分块发送，XX\r\n 其中XX标识还剩多少数据要传送。
- 状态码100的使用
   发送较长请求体之前，发送一个短的试探请求，来查看服务器响应与否。如果拒绝接收，那么较长的请求体也没必要再发了，自然减少了没必要的开销。

### Connector接口
最终要的四个方法：
- setContainer() 将服务器与servlet容器关联
- getContainer() 返回与当前连接器相关联的servlet容器
- createRequest()为引入的hTTP请求创建request对象
- createResponse()创建一个response对象
   
### HttpConnector类

HttpConnector实现了Connector接口（成为Catalina连接器）、Runnable接口（新开线程运行）、Lifecycle接口（维护每个实现了该接口的每个Catalina组件的生命周期）

#### 创建服务套接字
通过工厂模式(open)创建服务器套接字

#### 维护HttpProcessor

一HttpConnector对应一个HttpProcessor对象池，这样每个HttpProcessor可以运行在自己的线程中，可以让一个HttpConnector来同时处理多个Http
请求,同时也避免了每次创建HttpProcessor的操作。HttpProcessor的构造函数会调用HttpConnector来创建Request何Response

#### 提供HTTP请求服务

轮询式阻塞的监听请求，如果请求到来就从对象池中获取一个HttpProcessor,若池空了且当前已经创建的对象已达上限，就直接关闭请求，不进行处理。
创建成功就通过`assign`方法把socket分发给该`processor`

#### HttpProcessor 类

- `assign()`异步实现，可以HttpProcessor同时处理多个HTTP请求。
- `process()`负责解析HTTP请求，调用相应的servlet容器的invoke方法。

通过available的信号量、notifyAll()、wait()实现了生产者、消费者的同步。如果存在Socket未处理就等待Processor进行处理，如果没有Socket能处理就等待HttpConnector传入Socket

<img src="./images/1667569354528.jpg />

### 处理请求

着重讲述HttpProcessor的process()
- 解析连接
- 解析请求
- 解析请求头

这里注意设置HttpProcessor缓冲区大小是通过connector来获取的。因为对于connector用户，不应该知道底层怎么实现，所以应该只能设置connector的bufferSize
大小，底层直接获取该connector来进行设置缓冲区大小。

```java
   SocketInputStream input = null;
        OutputStream output = null;

        // Construct and initialize the objects we will need
        try {
            input = new SocketInputStream(socket.getInputStream(),
                                          connector.getBufferSize());
        } catch (Exception e) {
            log("process.create", e);
            ok = false;
        }

```





