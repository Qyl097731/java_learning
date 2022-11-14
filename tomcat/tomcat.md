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

#### 解析连接

看是否使用代理来设置不同端口
```java
    private void parseConnection(Socket socket)
        throws IOException, ServletException {

        if (debug >= 2)
            log("  parseConnection: address=" + socket.getInetAddress() +
                ", port=" + connector.getPort());
        ((HttpRequestImpl) request).setInet(socket.getInetAddress());
        if (proxyPort != 0)
            request.setServerPort(proxyPort);
        else
            request.setServerPort(serverPort);
        request.setSocket(socket);

    }
```

#### 解析请求头

采用字符数组来避免代价高昂的字符串操作,通过readHeader进行读取。

### 简单的Container容器

- SimpleContainer

因为默认连接器会接收到请求之后，会调用Servlet容器的invoke方法，该方法会载入相关servlet类，并调用servlet的service()方法。

- Bootstrap

通过创建连接器和Servlet容器，并向连接器注入该容器，之后进行连接器的初始化和启动，以此来监听请求。

## servlet容器

用来处理请求servlet资源，为客户端填充response对象的模块。

Tomcat含有四种不同层级的容器：
- Engine
整个Catalina servlet引擎
- Host
表示包含有一个或者多个Context容器的虚拟主机
- Context
表示一个Web应用程序，一个Context可以有多个Wrapper
- Wrapper
表示一个独立的servlet

每个高层级容器都包含有0或多个低层级子容器。可以通过addChild进行添加，添加的时候会进行类型检查。

容器支持的组件：载入器、记录器、管理器、领域和资源等。

可以通过`server.xml`进行指定使用哪一种容器。这是通过引入容器中的管道（pipeline）和阈（value）的集合实现的。

### 管道任务

旨在说明连接器调用了servlet容器的invoke之后会发生什么事情，并讨论Pipeline、Value、ValueContext、和Contained

管道中包含了Servlet将要调用的任务，一个阈表示一个具体的执行任务，阈的数量是额外添加的阈数量（本来只有一个基础阈）。可以同构server.xml进行动态添加阈。

管道就好像过滤器链，阈就好像过滤器。可以处理传递给他的request和response，基础阈总是最后一个执行。

跟网络编程中Channel、Selector类似，当监听到了请求之后，就会把请求给注册到Selector，之后就Selector慢慢处理。这里的管道就是Selector的职责，负责对阈（任务）的处理。

#### Pipeline接口

建议直接看源码：
- invoke调用管道中的阈
- add/removeValue增加/删除某个阈
- getBasic获取基础阈

#### Value接口
存储阈信息并真正处理阈

#### ValueContext接口

相当于一个iterator，不断遍历管道中的阈来执行Value（阈）

#### Contained接口

将阈和指定的servlet容器向关联。

### Wrapper接口

表示一个独立的servlet定义，继承自Container接口，负责servlet的init、service、destroy

- load 载入并初始化servlet
- allocate 分配一个已经初始化的servlet实例

### Context接口

表示一个Web应用程序，一个Context实例可以有一个或者多个Wrapper实例作为子容器。

### Wrapper应用程序

SimpleWrapper实现了Wrapper接口，是最小的servlet容器；包含了一个Pipeline实例，并使用Loader实例来载入类。其中Pipeline包含了一个基础阈和两个额外的阈。（一个Wrapper
也是一个Pipeline）
- ClientIPLoggerValve
- HeaderLoggerValve
- SimpleWrapperValve

#### SimpleLoader

包含一个ClassLoader，在构造函数中初始化。其中container指向了与该servlet容器相关的类加载器。

#### SimpleWrapper

两个变量：
- loader：加载servlet的载入器（Simple Class Loader)
- parent：wrapper的父容器

构造函数中会设置基础阈

#### SimpleWrapperValue类

是一个基础阈，专门用于处理SimpleWrapper类的请求。作为最后需要处理的阈，不需要调用ValueContext的invokeNext方法。

通过调用allocate来获取该Wrapper对应的servlet实例。

#### ClientIpLoggerValue类

该阈用来将客户端的IP地址输出到控制台。跟Spring Security中的职责链模式很像，是一种尾递归模式。

#### HeaderLoggerValue类

输出请求头到控制台。

### Context应用程序

包含了两个Wrapper实例的Context实例来构造Web应用程序，两个Wrapper包装了两个servlet类，有多个wrapper的时候需要一个映射器。映射器来选择合适的子容器处理制定的请求。

servlet容器通过多个映射器来支持不同的协议，一个映射器一个协议，如一个映射器对HTTP协议进行映射、一个映射器对HTTPS协议进行映射。

通过`getContainer`来获取映射器相关联的子servlet容器实例。

一个SimpleContext实例，使用SimpleContextMapper作为映射器，SimpleContextValue作为基础阈，Context容器中添加了两个额外的阈ClientIpLoggerValue
和HeaderLoggerValue，并包含两个Wrapper作为子容器。

大体流程：
- 当前容器包含一条管道，先调用管道的invoke方法
- 该管道中所有阈都会被调用，最后是基础阈的invoke
- 在基础阈的invoke时，会通过映射器来找子容器，然后调用子容器的invoke方法。
- 子容器会调用自己管道中阈，基础阈负责载入相关的servlet类，并进行响应。

#### SimpleContextValue

SimpleContext的基础阈，通过映射器获取指定的Wrapper，并向response注入上下文容器（context）

#### SimpleContextMapper

映射器，为基础阈通过路径找子容器

#### SimpleContext

通过请求的URL阈Wrapper的实例名称来决定调用那个wrapper实例。如ModernServlet的实例名为Modern

做过web开发，应该就懂了！我们平时就是通过设置项目的context路径，然后浏览器发送请求的时候，就是项目名+指定的Servlet名来获取指定的服务,而这里可以设置mapping映射。

`public void addServletMapping(String pattern, String name);` 设置映射关系，不然获取不到servlet服务
`public String findServletMapping(String pattern);`获取请求路径所对应的Wrapper实例（就是最基础的servlet实例来提供服务）
`map()`返回负责处理请求的Wrapper实例

为了屏蔽子容器的实现，Loader现在注入在当前容器，子容器可以获取父容器的加载器进行使用。

参见SimpleContext，JSP开发过的话，整个很好懂

## 生命周期Lifecycle

Catalina包含很多组件，当Catalina启动的时候，组件也会一起启动，当Catalina关闭的时候，必须所有组件也关闭，如servlet容器关闭时，必须调用调用所有容器中的servlet的destroy方法，但是Session
会保存到辅助存储器中。这是通过实现Lifecycle接口实现的。

### Lifecycle 接口

允许一个组件包含其他组件，一旦父组件启动，那么子组件都能启动。

### LifecycleSupport

提供了一种简单的方法来触发某个组件的生命周期事件，并对事件监听器进行处理

### 应用程序

所有组件实现了Lifecycle接口，包含一个Context、两个Wrapper、一个Loader、一个映射器

单一启动关闭机制：让最高层级的组件启动\关闭，低层级会一起启动\关闭。

Wrapper分配的时候不会进行启动；Wrapper关闭的时候，会把实例直接回收掉；

## 日志记录器

一个servlet容器一个日志记录器。

### Logger接口

日志记录必须实现该接口，

### Tomcat的日志记录器

提供了三种日志记录器：
- FileLogger
- SystemErrorLogger
- SystemOutLogger

#### FileLogger

把日志消息写到一个日志文件中。在整个生命周期中可能打开、关闭多个日志文件。当日期变化时，log()关闭当前文件并打开一个新文件。

双重锁机制，防止多个线程都阻塞在1、2重if之间导致错误。

```java
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsString = ts.toString().substring(0, 19);
        String tsDate = tsString.substring(0, 10);

        // If the date has changed, switch log files
        if (!date.equals(tsDate)) {
            synchronized (this) {
                if (!date.equals(tsDate)) {
                    close();
                    date = tsDate;
                    open();
                }
            }
        }
```

## 载入器

一般性自定义载入器的原因：
- 限制载入器所能访问的范围，不应该能够访问所有的类
- 提供自动重载功能，当classes、lib目录下的类发生变化时，Web程序要重新载入这些类。

Tomcat自定义类载入器的原因
- 在载入器中指定某些规则
- 缓存已经载入的类
- 实现类的预载入，方便使用。
应用程序只能引用WEB-INF/classes目录以及子目录下的类，同时只能访问WEB-INF/lib目录下的库。

仓库：载入器从哪里加载类
资源：类载入器中的DirContext，上下文文件根路径。

### Java的类加载器

三种类加载器：
- 引导类载入器：载入所有的核心类。
- 扩展类载入器：载入标准扩展目录中的类，加载一些扩展目录中的JAR，加速开发
- 系统类载入器：搜索在环境变量CLASSPATH中指明的路径和JVR文件。
三者是继承关系，引导类载入器位于最上层，系统类载入器位于最下层。

## Loader接口

WEB-INF/classes 和 WEB-INF/lib目录作为仓库添加到载入器中.

- addRepository()添加新仓库
- findRepositories返回仓库数组对象

Tomcat的载入器和Context级别的servlet容器相关联，Context容器中的一个或者多个类被修改，能够对类自动重载，而不需要重启Tomcat。载入器自身需要Context的reload来进行重载。

默认StandardContext是禁用自动重载，需要在server.xml中进行配置

<img src="./images/1667724851128.jpg />

可以通过`setDelegate`设置是否委托父类载入器。

Tomcat的类载入器继承自URLClassLoader

### WebappLoader类

Web应用程序的载入器，实现了Lifecycle接口，可以由其相关联的容器来启动，同时还实现了Runnable接口，可以指定线程调用modified方法。
如果是true就会通知关联的Context类实例，由该容器进行类重新载入。

一旦start，会执行如下任务：
- 创建一个载入器
- 设置仓库
- 设置类路径
- 设置访问权限
- 启动线程来支持自动重载

#### 设置仓库

将WEB-INF/classes目录传入倒类加载器的addRepository方法中，而WEB-INF/lib传入setJarPath中，这样可以从classes目录中和部署的lib目录载入相关类。

#### 设置新线程重新载入

通过新线程定期检查modified，如果是true，就通知相关容器进行载入。而Context容器会创建ContextNotifier在新线程进行载入。而Context容器继续监听是否还有modified为true之后发来的通知。

WebappLoader定期检查通知Context容器，Context通知Notifier进行更新

### WebappClassLoader

通过缓存已加载类来提升性能，同时某些特殊包或者子包中的类不允许载入。

#### 缓存类

通过Vector来把已经从classes、lib中加载的类作为ResourceEntry缓存起来，会保存其代表的class文件的字节流

#### 载入类

- 检查自身缓存、上层缓存
- 查看是否允许加载该类
- 查看是否能通过委托，让父类加载
- 从当前仓库载入类，查看是否能通过父类进行载入，若不存在父类，通过系统类加载器加载。
- 未找到，抛异常

## Session 管理

Manager：Session管理器，通过该管理器来实现管理Session，且必须与Context容器相关联。

一般将Session存在内存中，当然也可以持久化。

### Session对象

#### Session接口

Session对象存在于Session管理器中，而与Manager相关联的Context容器内，该Session对象具有唯一标识符。Manager会判断Session是否过期。

#### StandardSession类

实现了Session和HttpSession接口，构造函数传入Manager，使得Session拥有Manager。getSession会返回一个经过包装的Session实例

#### StandardSessionFacade

将Session变成外观类之后传出倒servlet实例，隐藏了实现细节。

### Manager

StandardManager:当Catalina运行时，Manager将Session存在内存中；Catalina关闭时，将Session放入文件中，之后重新启动Catalina时，会直接将Session对象从文件中加载回内存。

如果可以持久化：load()将存储介质中的Session对象重新载入到内存中，unload()将内存中的Session对象存入指定介质；


#### StandardManager

当stop的时候，会调用unload，将Session序列化到CATALINA_HOME指定的work目录下的SESSION.ser文件中。

同时实现了Runnable，新开线程处理已经过期的Session。

#### PersistentManagerBase

持久化Session管理器的父类，持有一个store的引用。

- 换出

超过了maxActiveSessions指定的上限，或者该Session限制了过长时间，才会换出。既能换出到内存，也能换出到存储器。查找一般都是先内存后存储器。

- 备份

空闲时间超过限制的Session进行备份。

#### PersistentManager

继承自PersistentManagerBase 新增了两个属性

#### DistributedManager

用于集群环境中，一台Tomcat服务器表示一个节点，集群环境中，必须使用DistributedManager作为Session管理器，来支持Session复制。

为了Session复制，会通过DistributedManager项其他节点发送消息。

实现了Runnable接口，通过新建的线程来检查session对象是否过期并从其他节点上接收消息

### 存储器

Session管理器为Session提供的持久化存储器的一个组件，即Store

#### StoreBase

周期性检查Session，移除过期的Session，在Tomcat5 改用backgroundProcess周期性调用processExpires

#### FileStore

将Session对象存储到某个.session的临时文件中，可以修改存放.session的临时目录。

#### JDBCStore 

Session存入数据库，setDriverName、setConnectionURL设置驱动和连接URL。

<b>servlet获取session的时候，其实是通过父容器中的SessionManager来获取的</b>

## 安全性

只有授权用户在提供了正确的用户名和密码之后才能查看他们，servlet通过web.xml(配置部署描述器)进行访问控制，通过将验证器阈添加到管道pipeline中，请求到来后都先经过authenticate()才能访问。

### 领域

对用户进行身份验证的组件，与Context容器相关联，通过setRealm进行设置。

有效用户名和密码默认存储在tomcat-user.xml中

<img src="./images/1667828369615.jpg />

### GenericPrincipal

始终与一个领域对象相关联，必须有一个用户名和密码对，该用户名对应的角色是可选的，通过hasRole(String role)来查看是否有指定角色。

### LoginConfig

登陆配置存储了领域对象的名字和身份验证方法名。通过web.xml中的login-config元素的配置来创建LoginConfig对象。

### Authenticator接口

作为一个标记，其他组件可以通过instanceof关键字检查某个组件是不是一个验证器。

### 安装验证器阈

web.xml中，login-config元素仅能出现一次。login-config元素包含一个auth-method元素指定身份认证方法；即一个Context仅有一个LoginConfig实例和利用一个验证了的实现。

<img src="./images/1667827068191.jpg />

## StandardWrapper

处理HTTP请求的过程：
- 连接器创建request和response对象
- 连接器调用StandardContext的invoke
- context容器调用管道对象的invoke，管道对象调用StandardContextValue的invoke
- StandardContextValue的invoke获取Wrapper来处理HTTP请求，调用wrapper的invoke方法
- wrapper调用管道对象的invoke,分为基础阈和普通阈
- allocate获取servlet实例，调用load()方法获取servlet类，并进行初始化init
- StandardWrapperValve掉哦那个servlet的service

### SingleThreadModel

保证servlet实例只处理一个请求，但是仍旧会因资源共享而产生同步问题

### StandardWrapper

载入servlet类并进行实例化，通过标准阈（StandardWrapperValve）来调用servlet的service方法

#### 分配servlet
standardWrapperValve 的invoke调用Wrapper的allocate进行实例化
singleThreadModel 默认的初始为false，如果存在servlet进count++，如果count为0就创建再count++;

#### 载入servlet

检查是不是singleThreadModel，如果不是且servlet已经加载过了，就直接返回。

如果是singleThreadModel或者servlet未被加载过，那么loadServlet进行加载（再加载前，会先检查请求的servlet是不是JSP页面，loadServlet会返回JSP请求路径所代表的servlet类）。

```java
    String actualClass = servletClass;
    if ((actualClass == null) && (jspFile != null)) {
        Wrapper jspWrapper = (Wrapper)
            ((Context) getParent()).findChild(Constants.JSP_SERVLET_NAME);
        if (jspWrapper != null)
            actualClass = jspWrapper.getServletClass();
    }
```

如果不存在请求的servlet类就会加载默认 servletclass 类，但是如果之前没有设置servletclass就会抛出异常。

```java
    if (actualClass == null) {
        unavailable(null);
        throw new ServletException
            (sm.getString("standardWrapper.notClass", getName()));
    }
```

解析完类名，获取载入器
```java
    Loader loader = getLoader();
    if (loader == null) {
        unavailable(null);
        throw new ServletException
            (sm.getString("standardWrapper.missingLoader", getName()));
    }
```

通过载入器获取类载入器
```java
    ClassLoader classLoader = loader.getClassLoader();
```

加载之后判断是不是特殊的类，类载入器特殊化。
```java
if (isContainerProvidedServlet(actualClass)) {
                classLoader = this.getClass().getClassLoader();
                log(sm.getString
                      ("standardWrapper.containerServlet", getName()));
            }
```

载入servlet类
```java
Class classClass = null;
try {
    if (classLoader != null) {
        System.out.println("Using classLoader.loadClass");
        classClass = classLoader.loadClass(actualClass);
    } else {
        System.out.println("Using forName");
        classClass = Class.forName(actualClass);
    }
} catch (ClassNotFoundException e) {
    unavailable(null);
    throw new ServletException
        (sm.getString("standardWrapper.missingClass", actualClass),
         e);
}
```

实例化servlet类
```java
try {
    servlet = (Servlet) classClass.newInstance();
} catch (ClassCastException e) {
    unavailable(null);
    // Restore the context ClassLoader
    throw new ServletException
        (sm.getString("standardWrapper.notServlet", actualClass), e);
} catch (Throwable e) {
    unavailable(null);
    // Restore the context ClassLoader
    throw new ServletException
        (sm.getString("standardWrapper.instantiate", actualClass), e);
}
```

载入方法前检查，检查该servlet是否允许载入
```java
// Check if loading the servlet in this web application should be
// allowed
if (!isServletAllowed(servlet)) {
    throw new SecurityException
        (sm.getString("standardWrapper.privilegedServlet",
                      actualClass));
}
```

查看是否能访问底层的Catalina功能，从而设置ContainerServlet，即继续设置子容器
```java
if ((servlet instanceof ContainerServlet) &&
                isContainerProvidedServlet(actualClass)) {
System.out.println("calling setWrapper");                  
                ((ContainerServlet) servlet).setWrapper(this);
System.out.println("after calling setWrapper");                  
            }
```

如果被请求的是JSP页面，就调用servlet的service方法。
```java
if ((loadOnStartup > 0) && (jspFile != null)) {
    // Invoking jspInit
    HttpRequestBase req = new HttpRequestBase();
    HttpResponseBase res = new HttpResponseBase();
    req.setServletPath(jspFile);
    req.setQueryString("jsp_precompile=true");
    servlet.service(req, res);
}
```

触发AFTER_INIT_EVENT事件
```java
instanceSupport.fireInstanceEvent(InstanceEvent.AFTER_INIT_EVENT,
```

如果是STM就添加到servlet实例池中
```java
 singleThreadModel = servlet instanceof SingleThreadModel;
    if (singleThreadModel) {
        if (instancePool == null)
            instancePool = new Stack();
    }
    fireContainerEvent("load", this);
```

记录错误日志
```java
finally {
    String log = SystemLogHandler.stopCapture();
    if (log != null && log.length() > 0) {
        if (getServletContext() != null) {
            getServletContext().log(log);
        } else {
            out.println(log);
        }
    }
}
```

返回servlet实例
```java
return servlet;
```

#### ServletConfig对象
loadServlet载入servlet并进行init方法的时候，需要向StandardWrapper传入ServletConfig实例。
- getServletContext()可以获取父容器
- getServletName()获取servlet名
- getInitParameter()
- getInitParameterNames()

### StandardWrapperFacade

传入init方法前将自己包装成 StandardWrapperFacade 再传入。其中StandardWrapperFacade中保存了对原始的StandardWrapper的引用

### StandardWrapperValve

- 执行与servlet相关联的全部过滤器
- 调用servlet实例的service方法

StandardWrapperValve 的invoke方法：
- allocate获取servlet
- 创建过滤器链
- 进行过滤doFilter（包括调用service方法）
- deallocate方法，若servlet不再使用，就调用wrapper的unload

### FilterDef类

过滤器定义类

### ApplicationFilterConfig类

用于管理Web应用程序首次启动时创建的所有的过滤器实例。通过context对象和FilterDef对象进行初始化。其中前者表示Web应用程序，后者表示过滤器定义。

### ApplicationFilterChain 类
基础阈（StandardWrapperValve）的invoke创建ApplicationFilterChain实例，调用其doFilter方法，该方法会通过调用过滤器链中的具体过滤器的doFilter
()。当调用了最后一个过滤器，就会调用servlet的service方法。

## StandardContext 类

### StandardContext 配置

- available属性来设置StandardContext对象是否可用。StandardContext的start()正确执行，表明该对象正确配置。正确配置才能读取位于%CATALINA_HOME%/conf下的web.xml
文件，将文件中的配置应用于tomcat的应用程序。
- configured属性表示 StandardContext 实例是否被正确设置。start时触发生命周期事件，配置成功就会将configured设置为true，不然无法启动。

#### 构造函数

向 StandardContext 设置基础阈，之后通过该基础阈从连接其中接收每个HTTP请求。

#### 启动StandardContext实例

start()方法初始化StandardContext对象，用生命周期监听器配置StandardContext，配置成功将configured设置为true，最后设置available设置为true
，表示子容器和组件都正确启动，能提供HTTP请求的服务了。

<img src="./images/1667921796008.jpg />

#### invoke方法

通过相关联的连接器调用，或者如果StandardContext时Host容器的子容器，就有Host实例的invoke调用。需要等待重载完成

### StandardContextMapper类

对于每个HTTP请求，都通过StandardContext的管道中的基础阈的invoke来处理，该方法首先会获取HTTP请求的Wrapper，需要借助该Mapper映射器。

每次都会根据HTTP请求的类型获取特定的mapper，然后再调用map来获取request对应的Wrapper

map方法会首先解析出请求路径中的相对路径:先精确匹配，后模糊匹配。

Tomcat5之后直接通过request对象各种获取适合的wrapper实例。

### 对重载的支持

如果开启了重载（reloadable），那么web.xml或者WEB-INF/classes目录下的文件被重新编译后，应用程序就会重载。

通过容器和应用加载器绑定，之后就能启动线程来持续检查是否有文件更新。

reloadable和容器是否允许重载有关，由容器决定。

### backgroundProcess 方法

Context容器的其他组件的运行都是通过其他线程在后台进行处理的。为了节省资源，tomcat5所有的后台处理共享一个线程。若某个组件需要周期性的执行操作，直接把代码放入backgroundProcess即可。。

通过ContainerBase的start创建线程来执行该方法。

## Host和Engine

当同一个Tomcat部署多个Context容器，才需要Host。但是实际部署中，总会使用一个Host容器，因为Context实例如果使用 ContextConfig 对象进行配置的时候，ContextConfig需要知道web.xml
，必须要通过Host。

Engine容器是最顶层容器，可以包含Host或者Context子容器。

### StandardHost
实现了Host和Deployer接口

初始化的时候添加基础阈。start的时候添加ErrorReportvalve和ErrorDispatcherValve。

每当引入Http请求，都会调用invoke，之后调用基础阈的invoke，通过基础阈来调用StandardHost的map获取context进行处理。

总的来说就是通过Host进行域名解析。

### Engine接口

Engine容器可以设置一个Host容器或者一个默认的Context容器，可以与一个服务实例关联

## 服务器组件和服务组件

### 服务器组件

表示整个Catalina的servlet引擎，囊括了所有组件。用来启动和关闭系统。启动之后就无限期等待关闭命令。通过服务组件来包含其他所有组件（如连接器组件和容器组件）

### StandardServer

await轮询阻塞接收关闭请求

start启动多有的服务组件

initialize初始化素有的服务组件

### Service接口

服务组件可以添加servlet容器和连接器实例，所有添加到service组件中的连接器都会与该servlet容器相关联。

### StandardService

start方法启动连接器和所有servlet容器

### Stopper

优雅关闭Catalina服务器，所有生命周期组建的stop都能调用。

## Digester库

前面所有章节都是手动在BootStrap中进行设置组件，每次调整都需要重新编译Bootstrap类。

通过server.xml进行配置，是的每个xml元素转换成Java对象，可以用于设置Java对象属性，即可以通过server.xml来修改Tomcat配置。

该Digester就是来解析XML文档的。通过相Digester中添加Rule对象来指定更多的解析规则。

begin()会创建响应的Rule实例存入栈中，end()弹出rule


### Rule

xml解析规则，Digester包含了Rule的集合。digester进行规则配置（如addObjectCreate)的时候，实际是委托Rule进行规则与Digester进行绑定配置的。

### RuleSet

代码更简介，隐藏了具体实现，通过digester.addRuleSet（RuleSet ruleset） 进行设置，而不用显示的(如addObjectCreate)进行规则添加。

RuleSet内部会进行指定的规则配置。

### ContextConfig

StandardContext必须有监听器，而监听器是ContextConfig的一个实例。会安装指定验证阈和许可阈到管道对象中。还可以读取和解析默认的web.xml文件和应用程序自定的web.xml文件，并转换成Java
对象。默认web.xml存放于CATALINA_HOME的conf目录下。

应用程序的web.xml存在WEB-INF下。

即使web.xml都不存在也能执行ContextConfig。

ContextConfig会对START_EVENT和STOP_EVENT事件进行响应(start(),stop())。其中start加载两个web.xml的内容。

createWebDigester方法会addRuleSet添加解析XML的规则。

## 关闭钩子

防止异常结束，通过钩子函数能够保证永远能够正常关闭应用程序。

Java虚拟机对两类事件进行响应:
- System.exit或者最后一个非守护线程的退出
- 用户突然强制虚拟机中断运行，如Ctrl+c

执行关闭的时候：
- 首先启动所有已经注册的关闭钩子（先前向当前运行时注册过的线程），所有钩子并发执行，直到结束。
- 虚拟机调用没有被调用过的终结器

## 启动Tomcat

主要介绍startup包下的Catalina和Bootstrap类，前者用于解析Tomcat配置文件：server.xml文件，后者是入口点，负责创建Catalina实例。并调用process方法。

### Catalina类

启动类，是一个Digester对象，用于解析%CATALINA_HOME%/conf目录下的server.xml文件，来配置Tomcat。

Catalina封装了Server对象，拥有一个Service对象，该服务对象包含一个Servlet容器和多个连接器。可以使用Catalina类来启动/关闭Server对象

一般情况下可以通过使用Bootstrap实例化Catalina调用process来运行Tomcat。

process方法设置了home和base值，默认都是user.dir（用户的工作目录）

#### start

process中调用start来创建一个Digester实例来解析server.xml。同时start方法会调用Digester的push方法将Catalina对象传入作为参数。解析server.xml之后，会使变量server
引用一个Server对象，调用该Server的initialize和start方法，最后Catalina调用await新建线程等待被关闭。

#### stop

stop创建Digester实例，然后将Catalina对象压入Digester的内部栈，使用Digester对象来解析配置文件，通过正在运行的Server对象来发送关闭命令，来关闭server对象。

#### 启动Digester对象

createStartDigester 创建Digester实例，并为其添加规则。

```java
    // 创建Server实例，如果该server有className的属性，那么className的属性值就是该类的名称 类似Spring @Service("xxxService")
    // Configure the actions we will be using
    digester.addObjectCreate("Server",
                             "org.apache.catalina.core.StandardServer",
                             "className");
    // 指明要对Server对象设置属性值
    digester.addSetProperties("Server");
    // 将Server对象押入到Digester对象的内部栈中，因为调用的Digester的createStartDigester之前就已经将Catalina push进栈了，
    // 所以addSetNext就调用digester的setServer将Server实例存入digester
    digester.addSetNext("Server",
                        "setServer",
                        "org.apache.catalina.Server");
```

#### 关闭Digester对象

只对xml的根元素Server感兴趣，解析之后就进行返回。

### Bootstrap类

1.设置home和base的路径.如果没有设置就用home，home没设置会返回用户工作目录
```java
    // Configure catalina.base from catalina.home if not yet set
    if (System.getProperty("catalina.base") == null)
        System.setProperty("catalina.base", getCatalinaHome());
```
2.同时有三个不同目的的类加载器，为了防止应用程序中类（servlet和Web应用程序中的其他辅助类）使用WEB-INF/classes 目录 和 WEB-INFO/lib
目录之外的类。部署到%CATALINA_HME%/common/lib下的jar文件也可以使用。
```java
    ClassLoader commonLoader = null;
    ClassLoader catalinaLoader = null;
    ClassLoader sharedLoader = null;
```

- commonLoader负责载入%CATALINA_HOME%/common/classes、%CATALINA_HOME%/common/endorsed、%CATALINA_HOME%/common/lib下的Java类
- catalinaLoader负责载入servlet类，%CATALINA_HOME%/servlet/classes、%CATALINA_HOME%/servlet/endorsed、%CATALINA_HOME%/servlet
/lib下的Java类、以及所有commonLoader可以访问的Java类
- sharedLoader 可以载入%CATALINA_HOME%/shared/classes和%CATALINA_HOME%/shared/lib以及commonLoader可以加载的所有类，Web应用程序中与Context
容器相关联的 每个类载入器的父类载入器都是 sharedLoader

3.创建完加载器之后，创建Catalina实例，同时将sharedLoader作为该实例的父类加载器。

4.最后通过Catalina来启动Tomcat

### 在Windows上运行Tomcat

- .bat：批处理文件
- rem：用于注释，解释器不会执行以rem命令开始的行
- pause：暂停正在执行的批处理文件，并提示用户按键之后继续执行
- echo:用于在中断显示一段文本
    - echo %os%：显示操作系统名字
    - echo off：防止将批处理文件中的具体命令输出，只输出执行结果
    - @echo off：在echo off的基础上将命令本身的隐藏
- set：设置用户定义或命名的环境变量。暂时存储在内存中，命令执行完就销毁。
    ```bash
        set THE_KING=Elvis
        echo %THE_KING%   
    ```
- label:使用冒号设置标签，可以作为goto的跳转位置
    `:end`设置名称为end的标签
- goto：强制批处理文件跳转到指定位置执行
    ```bash
        echo Start
        goto end
        echo I can guarantee this line will not be executed
        :end
        echo End
        pause
    ```
- if:
    - 测试变量的值
    - 测试文件是否存在
    - 测试错误值
- exist：测试文件是否存在
- 接收参数：通过%来传递参数 %1 表示第一个参数，%2表示第二个参数；
    - `echo %1` 就会打印出命令行中第一个传入的参数
- shift:将接收参数向后移动一位,如%2的值给%1,%3的值给%2,这时候可以用%0获取第一个参数
    <img src="./images/1668255833862.jpg />
- call:调用另一条命令。
- setLocal:批处理脚本中使用，对环境变量的修改只在当前批处理脚本中有效，遇到endLocal命令则在批处理文件末尾的环境变量的值会恢复成原来的值。
- start:新开启Windows一个控制台，并指定名字。
    - `start "Title" echo hello`
    <img src="./images/1668255537904.jpg />
    
## 部署器

使用Web应用程序，都需要将该应用程序的Context实例部署到Host中，可以通过WAR部署或者复制Web应用程序到Tomcat的webapp下。这些应用程序，可以包含在描述符文件（XML格式）中，包含该Context实例的配置信息。

tomcat中使用了manager和admin来管理Tomcat和部署在Tomcat中的Web应用程序。

部署器是Deployer接口的实例，来创建StandardContext实例，并将实例添加到Host实例中，这样子就能简化启动，父容器启动，子容器跟着启动。

### 部署Web应用程序

关键在于StandardHost对HostConfig类型的引用，通过StandardHost实例start触发START事件，其中HostConfig实例对事件进行响应，并逐步部署Web应用程序。

Catalina启动时，Digester解析server.xml进行配置时就会来设置HostRuleSet规则，这其中就会让HostConfig监听StandardConfig，对之后的事件进行处理

```java
/**
 * Process a "start" event for this Host.
 */
protected void start() {

    if (debug >= 1)
        log(sm.getString("hostConfig.start"));

    if (host.getAutoDeploy()) {
        deployApps();
    }

    if (isLiveDeploy()) {
        threadStart();
    }
}
```

```java
    protected void deployApps() {

        if (!(host instanceof Deployer))
            return;
        if (debug >= 1)
            log(sm.getString("hostConfig.deploying"));
        // 默认是webapps的值
        File appBase = appBase();
        if (!appBase.exists() || !appBase.isDirectory())
            return;
        // 将所有的webapp下的目录都视作Web应用进行部署，包括所有War和描述符文件
        String files[] = appBase.list();
        // 将%CATALINA_HOME%/webapps下的XML文件进行部署
        deployDescriptors(appBase, files);
        // 将%CATALINA_HOME%/webapps 下的WAR文件进行部署
        deployWARs(appBase, files);
        // 部署目录，也可以直接将Web应用程序的整个目录复制到%CATALINA_HOME%/webapps目录下来完成Web应用程序的部署
        deployDirectories(appBase, files);
    }
```

- deployDescriptors其实就是部署所有Web应用根路径
- deployWARs其实就是部署通过WAR包部署应用所提供的服务（具体的请求路径）
- deployDirectories其实就是部署通过WEB-INF下的应用所提供的服务（具体的请求路径）


#### 动态部署

如果liveDeploy为true时，start时会启动一个新线程来定期检查是否有新的应用要部署或者已经部署的web应用的web.xml是否有修改。

### Deploy接口

StandardHost也是一个不失其，同时也是一个容器，那么Web应用可以部署到其中或者消除Web应用

### StandardHostDeployer

帮助Web应用程序部署到StandardHost实例，由StandardHost对象调用，
```java
    public StandardHostDeployer(StandardHost host) {

        super();
        this.host = host;

    }
```

#### 安装一个描述符
install方法进行安装

```java
  // Install the new web application
        this.context = null;
        this.overrideDocBase = docBase;
        InputStream stream = null;
        try {
            stream = config.openStream();
            Digester digester = createDigester();
            digester.setDebug(host.getDebug());
            digester.clear();
            digester.push(this);
            digester.parse(stream);
            stream.close();
            stream = null;
        } catch (Exception e) {
```

#### 安装一个War包

通过反射来进行install

#### 启动Context实例

通过StandardHostDeployer的start来启动Context实例,其实就是启动host

## Manager 应用程序的servlet类

Manager用来管理已经部署的Web应用程序，随Tomcat一起启动，会通过manager.xml完成部署

### 使用Manager应用程序

作为WEB-INF/lib目录下的一个JAR文件部署的，通过描述符文件进行部署,指定了上下文路径/manager
```xml

<Context path="/manager" docBase="../server/webapps/manager"
        debug="0" privileged="true">

  <!-- Link to the user database we will get roles from -->
  <ResourceLink name="users" global="UserDatabase"
                type="org.apache.catalina.UserDatabase"/>

</Context>
```
指明了访问路径/manager 即 localhost:8080/manager/xxxx

同时指明了security-constraint来对所有的请求进行校验，之后manager角色的用户才能访问manager，同时需要Basic验证拥有manger角色的用户的张皓淼吗，
只有通过验证才能访问manager资源（需要在%CATALINA_HOME%/conf的tomcat-users.xml添加用户、角色）
<img src="./images/1668353903699.jpg />

### Containerservlet接口

实现了该接口的servlet类可以访问表示该servlet实例的StandardWrapper对象，通过该wrapper访问当前web应用程序的Context实例，以及Context实例内的部署器（StandardHost
等）。其中ManagerServlet就实现了ContainerServlet接口

### 初始化 ManagerServlet
建立与StandardServlet的关系，后面可以对其进行引用，从而访问Web资源
```java
if ((servlet instanceof ContainerServlet) &&
                isContainerProvidedServlet(actualClass)) {
System.out.println("calling setWrapper");                  
                ((ContainerServlet) servlet).setWrapper(this);
System.out.println("after calling setWrapper");                  
            }
```
其中isContainerProvidedServlet会判断时不是ManagerServlet或者是实现了ContainerServlet的了，如果是就返回true
```java
        if (classname.startsWith("org.apache.catalina.")) {
            return (true);
        }
        try {
            Class clazz =
                this.getClass().getClassLoader().loadClass(classname);
            return (ContainerServlet.class.isAssignableFrom(clazz));
        } catch (Throwable t) {
            return (false);
        }
```
xxx.class.isAssignableFrom(Class clazz) 如果xxx所表示的类和clazz表示的类都表示相同的接口或者是clazz的父类、父接口就会返回true

## 附录

- 描述符文件: ****.xml
- 通过将变化无限往下压，来减小变化带来的影响范围
