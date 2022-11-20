# Netty

## 异步和事件驱动

demo01中通过套接字实现简单通信，但是如果是大量用户需要通讯就面临三个问题

- 设置很多线程可能都在空等，浪费CPU资源
- 系统资源有限，为每个线程分配资源，空等线程显然就浪费者内存资源
- 线程切换涉及上下文切换，显然会占用很多性能

### Java NIO

通过<b>管道</b>来实现数据的输送，相较于轮询、中断、DMA具有更高的CPU利用率。

- 较少的线程可以处理许多连接，减少内存管理和上下文切换的开销
- 没有I/O操作需要处理的时候，线程可以用于其它任务

但是在高负载下可靠、高效处理比较繁琐且容易出错。

## Netty简介

特性：健壮、安全、高可用、高性能、更新快、易用

### 核心组件

这里不做过多解释了，如果熟悉过一两个项目，应该对这些不会陌生。

- channel：传入、传出的数据载体
- 回调：给请求的响应
- Feature：异步编程的的一个任务的开启
- 事件和ChannelHandler：很多框架，前端后端都包含这类"发布者订阅者"设计思想

## 第一个Netty案例

详见ch02.demo 

- channelActive() ——在到服务器务的连接已经被建立之后将被调用；
- channelRead0() ——服务器收到一条消息时被调用
- exceptionCaught() ——在处理过程引发异常及时被调用

## Netty的组件和设计

### Channel、EventLoop、ChannelFuture

- Channel ————Socket
- EventLoop ————控制流、多线程、并发
- ChannelFuture ————异步通知

#### Channel接口

基本的I/O(bind、connect、read、write)依赖于底层网络传输所提供的源于。基于Java网路编程，其基本构造是Socket。
Netty的Channel简化了Socket的复杂性。

- EmbeddedChannel
- LocalServerChannel
- NioDatagramChannel
- NioScptChannel
- NioSocketChannel

#### EventLoop接口

- 一个EventLoopGroup 包含一个或者多个EventLoop ；
- 一个EventLoop 它的生命周期内只与一个Thread绑定；
- 所有由EventLoop处理的I/O事件都将在它专有的Thread上被处理；
- 一个Channel 在它的生命周期内只注册一个EventLoop ；
- 一个EventLoop 可能会被分配给一个或者多个Channel 。

#### ChannelFuture接口

Netty的所有I/O操作都是异步的。一个操作不可能立即返回，所以要在某个时间点确定其结果的方法。
Netty的ChannelFuture中的addListener()方法注册了一个ChannelFutureListener，以便在某个操作完成时得到通知。

### ChannelHandler和ChannelPipeline

#### ChannelHandler接口

充当了所有处理入栈和出战数据的应用程序逻辑的容器。

#### ChannelPipeline接口

提供了ChannelHandler链的容器，定义了用于在该链上传播出入站事件流的API。当Channel被创建时，他会自动地被分配到它专属的ChannelPipeline。

ChannelHandler安装到ChannelPipeline的过程：

- 一个ChannelInitializer的实现被注册到ServerBootstrap中；
- 当ChannelInitializer.initChannel()方法被调用，就在Pipeline中安装一组自定义的ChannelHandler
- 移除ChannelInitializer

<b>入站：</b>服务器端到客户端；反之称为出站。

这里跟Spring Security的过程很相似，不做详细阐述。

### 引导

就是通过ip、端口监听应用或者绑定端口提供服务。

ServerBootstrap需要两个EventLoopGroup：一个用来监听绑定的应用程序，另一个用来处理已经收到的客户端的请求。

## 传输

### 传输API

核心是Channel

- ChannelConfig：包含了所有Channel的配置，支持热更新
- ChannelPipeline：持有出入站数据以及事件的ChannelHandler实例
- ChannelHandler：
    - 数据格式转换
    - 异常通知
    - 提供Channel变为活动或者非活动的通知
    - 提供当Channel注册到EventLoop或者从EventLoop注销时的通知
    - 提供用户自定义事件的通知
> 拦截过滤器 ChannelPipeline 实现一种常见的设计——拦截过滤器（Intercepting
>  Filter）。好似UNIX的管道

### 内置的传输

<img src="./images/1667699376244.jpg" />

#### NIO——非阻塞IO

是JDK1.4之后，基于选择器的API。NIO提供了所有IO操作的全异步实现。

选择器背后的基本概念是一个注册表，所有的Channel把自己关心的事情注册到Selector中
- 新Channel已被接收且已经就绪
- Channel连接已经完成
- Channel有已经就绪的可供读取的数据
- Channel可用于写数据

选择器运行在一个检查状态变化并且对其做出相应响应的线程上，在应用程序对状态的改变做出响应之后，选择器将会被重置，并重复这个过程。

> 零拷贝（zero-copy）目前只有使用NIO或者Epoll传输时才可使用的特性。可以快速高效地将数据从文件系统移动到网络接口，而不需要将其从内核オ间复制到用户空间，其在像FTP或者HTTP
> 这样的协议中可以显著提升性能。但是，并不是所有的操作都支持这一特性。特别对实现了数据加密或者压缩的文件系统是不可用的——只能传输文件的原始内容。反过来，传输已被加密的文件则不是问题。

#### Epoll——用于Linux的本地非阻塞传输

一个高度可扩展的IO事件通知特性。Netty为Linux提供了一组NIO API，以一种跟自身设计更加一致的方式来使用epoll，更加轻量使用中断，如果运行于Linux，高负载情况下性能由于JDK的NIO。

#### OIO——旧的阻塞IO

通过SO_TIMEOUT这个socket标志，指定等待一个IO操作完成的最大毫秒数，如果没有完成就会捕获这个异常并继续处理循环。在EventLoop下次运行时，继续尝试。

#### 用于JVM内部通信的Local传输

在同一个JVM中运行的客户端和服务器进行异步通信。

#### Embedded传输

将ChannelHandler传入到其他的ChannelHandler内部，可以扩展一个ChannelHandler，又不需要修改其内部代码。

### 传输的用例

<img src="./images/1667714675051.jpg />

## ByteBuf

作为NIO ByteBuffer在Netty中的替代品

### ByteBuf API
Netty的数据通过ByteBuf和ByteBufHolder进行暴露。

优点：
- 被用户自定义的缓冲区扩展
- 通过内置的复合缓冲区实现了透明的零拷贝
- 容量可以按需增长
- 在读和写两种模式间切换不需要调用ByteBuffer的flip
- 读和写使用了不同的索引
- 支持方法的链式调用
- 支持引用计数
- 支持池化

### ByteBuf类——Netty的数据容器
维护了读和写两个索引，读的索引值必然是写于写的索引值。也可设置写索引的最大值。

- 堆缓冲区

将数据存储在JVM堆空间中，这种模式成为支撑数组，能在没有池化的情况下提供快速的分配和释放

- 直接缓冲区
直接将数据存在内存中，避免每次调用本地IO之前将缓冲区的内容复制到一个中间缓冲区（或者从中间缓冲区把内容复制到缓冲区）。相对于堆缓冲区，直接缓冲区的分配和释放更昂贵。

- 复合缓冲区

为多个ByteBuf提供一个聚合视图，按需添加或者删除ByteBuf实例。

`CompositeByteBuf` 将多个缓冲区合并为单个缓冲区的虚拟表示。

考虑如下场景，可以重用消息体的时候，对于每个消息都会创建一个新的头部，为了不重新分配这两个缓冲区，可以使用CompositeByteBuf消除了没必要的复制的同时，暴露了通用的ByteBuf API。

```java
// 基于ByteBuffer 实现复合缓冲区
ByteBuffer[] message = new ByteBuffer[]{ header, body};
ByteBuffer message2 = ByteBuffer.allocate(header.remaining()+body.remaining());
message2.put(header)
message2.put(body)
message2.flip()

// 基于CompositeByteBuf
CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
ByteBuf headerBuf = ...
ByteBuf bodyBuf = ...
messageBuf.addComponents(headerBuf,bodyBuf);
// 移除头部
messageBuf.removeComponent(0);
// 循环遍历
for(ByteBuf buf:messageBuf){...}
```

### 字节级操作

#### 随机访问索引

通过索引来随机读取字节数组不改变Index

#### 顺序访问索引

同时具有读索引、写索引，避免ByteBuffer使用flip的方法来转换读写模式

#### 可丢弃字节

将已经读取过的字节进行丢弃并回收空间，随着readIndex的增加而增加，即重用这部分空间进行写操作。

#### 可读字节

`isReadale()`
通过readXXX或者skipXXX操作都会改变readerIndex,增加已读数据。

#### 可写字节

`writableBytes()`
通过writeXXX写的时候会改变writerIndex，减少写空间。

#### 索引管理

通过XXXIndex进行设置索引，clear会将index变为0，可以重读，也可以覆盖写。

#### 查找操作

`forEachByte(ByteBufProcessor.FIND_NUL)` 将含有NULL结尾的内容的Flash套接字集成，用该方法可以高效的消费Flash数据，执行较少的边界检查。

#### 派生缓冲区

为ByteBuf提供专门的内容视图,返回新的ByteBuf实例：
- duplicate
- slice
- slice(int,int)
- Unpooled.unmodifiabelBuffer
- order
- readSlice

由于内部存储是共享的，所以视图修改也会对源数据的修改。

> ByteBuf复制：需要真实副本应该使用copy，返回一个独立的数据副本

### ByteBufHolder

用来存储各种属性值，状态码、Cookie等。

### ByteBuf分配

#### 按需分配

ByteBufAllocator 接口

降低了分配和释放内存的开销，通过池化，可以用来分配任意类型的的ByteBuf实例。

<img src="./images/1667720594903.jpg />

可以通过Channel或者ChannelHandlerContext进行获取
```java
Channel channel = ...;
ByteBufAllocator allocator = channel.alloc(); 

ChannelHandlerContext ctx = ...;
ByteBufAllocator allocator2 = ctx.alloc();
```

两种ByteBufAllocator实现方式:PooledByteBufAllocator和UnpooledByteBufAllocator,前者池化了ByteBuf以提高性能并且最大限度减少内存碎片，后者不池化ByteBuf
，每次调用返回一个新的实例。

#### Unpooled缓冲区

提供了静态的辅助方法来创建未池化的ByteBuf实例。

<img src="./images/1667720894393.jpg /> 

#### ByteBufUtil

一个有用的方法是boolean equals(ByteBuf, ByteBuf)，判断两个实例是否相等。

#### 引用计数

通过引用计数来堆某个对象持有的资源不再被其他对象引用时，释放该对象所持有的资源来优化内容使用和性能的技术。

release()方法会清空所有的活动引用。

> 一般来说最后都是由引用技术的对象那一方来负责释放。

## ChannelHandler 和 ChannelPipeline

### ChannelHandler

#### Channel的生命周期

<img src="./images/1668870008757.jpg />

注册之后接收请求，然后发送给ChannelPipeline的ChannelHandler中注册到EventLoop（线程），运行完成之后变成不活跃，最后取消注册。

#### ChannelHandler的生命周期

<img src="./images/1668870562166.jpg />

子接口：
- ChannelInboundHandler 处理入站数据
- ChannelOutboundHandler 处理出站数据集

#### ChannelInboundHandler接口

常用方法：

<img src="./images/1668870875714.jpg />

#### ChannelOutboundHandler 接口

按需推迟操作或者事件，如远程节点的写入被暂停了，那么可以推迟冲刷操作并在稍后继续。

<img src="./images/1668871324194.jpg />
<img src="./images/1668871358454.jpg />

> ChannelPromise 和 ChannelFuture 在数据出站完成之后，进行通知；ChannelPromise是ChannelFuture的子类，如setSuccess和setFailure

#### ChannelHandler适配器

就像Tomcat中好多容器都存在XXXXBase一样，这里ChannelInboundHandlerAdapter和ChannelOutboundHandler提供了ChannelInboundHandler接口和ChannelOutboundHandler 接口一些基本实现，

> @Sharable 表示可以被添加到多个容器中



