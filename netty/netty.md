# Netty

# 简单API介绍

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

如果被添加到多个Pipeline的时候要标注@Sharable，必须保证ChannelHandler是线程安全的。

> 通过共享ChannelHandler可以让多个ChannelPipeline来跨Channel统计信息。

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

##### 资源管理
在消耗或者无法传输消费时都要进行资源的释放。尤其是在消费之后，还要传回已经处理的通知，同时Netty提供了资源泄露检测机制。

### ChannelPipeline

链，每次ChannelHandler创建都被分配到一个ChannelPipeline，不受开发人员干预。

ChannelHandler可以同时实现出站和入站，在ChannelPipeline中，默认同一个方向的Handler先处理。

### ChannelHandlerContext

代表了ChannelHandler 和 ChannelPipeline的关联，每当ChannelHandler 添加到 ChannelPipeline之后，就会创建ChannelHandlerContext
，主要负责和下一个ChannelHandler的交互。而ChannelHandler和ChannelPipeline的方法则是负责整个Pipeline的交互。

可以通过该类来获取Channel和Pipeline的引用，也能获取ChannelHandler。

事件流更短，所以ChannelHandlerContext拥有最大的性能。即可以从ChannelPipeline的某个特定点开始传播事件，可以减少它不感兴趣的ChannelHandler
带来的开销。同时避免将事件传入可能对他感兴趣的ChannelHandler。

简而言之，就是减少对其他人的没必要的影响。

<img src="./images/1669035299947.jpg />

### 异常处理

#### 入站异常处理

默认将异常转发给pipeline中的下一个ChannelHandler，如果到达尾端，就记录为未处理，之后决定要不要将异常传播出去。

#### 出站异常处理

每次出战返回一个ChannelFuture，注册到其中的ChannelFutureListener在操作完成时被通知操作是否成功。

## EventLoop和线程模型

### EventLoop接口

基于Java.util.concurrent包，一个EventLoop可以回被指派用于服务多个Channel，同时根据核心的不同，能创建多个EventLoop实例。通过parent方法能获取当前EventLoop
实例所属的EventLoopGroup。

保证了在同一个线程中处理某个EventLoop中所产生的所有事件，解决了上下文切换开销的问题。

### 任务调度

#### JDK的任务调度

通过ScheduledExecutorService来实现

<img src="./images/1669041989679.jpg />

#### EventLoop调度任务

线程池管理会有额外线程的创建，这将会导致性能瓶颈，Netty通过Channel的EventLoop实现任务调度解决了这个问题。

```java
        ScheduledFuture<?> future=ch.eventLoop().schedule(()->{
            System.out.println ("5 s later" );
        },5, TimeUnit.SECONDS);
```
在5s后提交给Channel的EventLoop执行。

```java
        ScheduledFuture<?> future=ch.eventLoop().scheduleAtFixedRate(()->{
            System.out.println ("5 s later" );
        },5,5, TimeUnit.SECONDS);
```
在5s后每5s提交给Channel的EventLoop执行。

### 实现细节

#### 线程管理

通过检查Thread是否是分配给当前Channel以及他的EventLoop的那个线程，如果是就直接执行，否则就放入队列之后执行。

<img src="./images/1669044111993.jpg />

每个EventLoop都有自己的任务队列，相互之间独立，所以不需要额外在ChannelHandler中进行额外同步。

> 长时间的任务 通过EventExecutor进行执行，不要使用EventLoop，回导致饥饿

#### EventLoop线程的分配

EventLoop包含在EventLoopGroup中，不同的传输实现，EventLoop的创建和分配也不同。

1. 异步传输

少量被多个Channel共享的EventLoop。

<img src="./images/1669044804181.jpg />

EventLoopGroup 分配 固定的EventLoop（一个线程），之后每个Channel都会被EventLoopGroup分配一个EventLoop.之后通过轮询调用每个EventLoop队列中的Channel。

由于多个Channel共享一个EventLoop（线程），所以ThreadLocal用处大大减小。

2. 阻塞传输

<img src="./images/1669045379489.jpg />

一个Thread一个Channel，所以保证了Netty涉及的一致性。保证了Netty的可靠性和易用性。

## 引导

### Bootstrap类

引导层负责将所有的层次结构组装起来并运行。服务器致力于使用一个父Channel来接收客户端连接，子Channel来用于它们之间的通信。客户端需要一个单独的，没有父Channel的Channel来用于网络交互

> 引导类被标记为Cloneable，通过浅拷贝使得配置完成的引导类实例能够被另许多有相同配置的Channel来使用。

### 引导客户端

Bootstrap在bind方法之后就创建一个新的Channel，之后调用connect来建立连接。

在connect之后，Bootstrap会创建一个新的Channel

<img src="./images/1669105501138.jpg />

> 在引导的过程中，调用bind或者connect之前，必须调用以下方法来设置所需的组件
> group
> channel 或者 channelFactory
> handler

### 引导服务器

<img src="./images/1669107419968.jpg />

如网络编程的Selector思想由来一样，首先依旧绑定一个监听客户端请求的端口，之后有客户端请求到来之后，就分配新的Channel（即端口）来和客户端保持通信，在这个过程中该Channel分配到的EventLoop
（线程）在所有的创建了的Channel之间共享。通过group来进行线程共享，有点多线程Selector的味道了噢。

### 为引导过程添加多个ChannelHandler

将多个ChannelHandler添加到ChannelPipeline中，如果程序中在初始化引导类的时候需要多个Handler就可以自定义实现ChannelInitializerImpl来安装到ChannelPipeline；

ChannelInitializer就好像Tomcat中的RuleSet的思想（把所有的规则在一个地方统一配置）一样，把所有的Handler都在initChannel时进行添加。

### 使用Netty的ChannelOption和属性

通过ChannelOption能够让所有创建的Channel自动完成配置。
可用的ChannelOption 包括底层连接的详细信息，如keep-alive 或者超时属性以及缓冲区设置。就像SocketOption一样可以设置很多属性值。

通过AttributeMap抽象（一个由Chanel和引导类提供的集合）以及AttributeKey<T>（一个用于插入和获取属性值的泛型类）可以将任何类型的数据项阈客户端和服务器Channel相关联。

### 引导DatagramChannel

无连接的数据包进行传输需要该类。详情见com.nju.netty.ch08.Demo06

### 关闭

优雅地关闭。详情见com.nju.netty.ch08.Demo07

## 单元测试

### EmbeddedChannel 概述

这个实现提供了通过ChannelPipeline传播事件的简便方法。将入站或者出站数据写入到EmbeddedChannel中，检查是否有东西到达ChannelPipeline
的尾端，以此得知消息是否被编码或者解码以及触发了ChannelHandler动作。

常用方法：
<img src="./images/1669189235970.jpg />

流程图：
<img src="./images/1669186752212.jpg />

### 使用EmbeddedChannel测试ChannelHandler

#### 测试入站消息

详情见com.nju.netty.ch09.demo01.FixedLengthFrameDecoder

#### 测试出站消息
详情见com.nju.netty.ch09.demo02.AbsIntegerEncoder

### 测试异常处理

如果读取的字节数超出了某个特定的限制，就会抛出TooLongFrameException来防范资源被耗尽的方法。

# 编解码器

应用程序和网络之间的数据转换，需要编码器和解码器。

## 编解码器框架

### 什么是编解码器

编解码器由编码器和解码器组成，将字节流从一种格式转换为另一种格式。

编码器操作出站数据，解码器处理入站数据。

### 解码器

- 将字节解码为消息——ByteToMessageDecoder 和 ReplayingDecoder
- 将另一种消息类型解码为另一个种——MessageToMessageDecoder

为了处理入站数据，所以解码器实现了ChannelInboundHandler

#### ByteToMessageDecoder

将字节解码为消息，会对入站数据进行缓冲，直到准备好处理。

<img src="./images/1669207033689.jpg />

> 编解码器的引用计数
>
> 消息一旦被编码或者解码，就会被自动释放，如果想以后继续引用可以`ReferenceCountUtil.retain(message)` 来增加引用计数，防止释放。

#### 抽象类ReplayingDecoder

扩展了ByteToMessageDecoder，不必调用readableBytes，只需要使用其自定义的ReplayingDecoderByteBuf即可。

详情见com.nju.netty.ch10.demo01.ToIntegerDecoder2。不足会抛出Error，如果数据足够多会继续调用read。

> 更多类处理器：
>
> - LineBasedFrameDecoder：通过行尾控制字符(\n或者\r\n)来解析消息数据
> - HttpObjectDecoder：一个Http数据的解码器

#### MessageToMessageDecoder

在channel之间进行数据的转换

<img src="./images/1669208562210.jpg />

详情见com.nju.netty.ch10.demo01.IntegerToStringDecoder

其中整个Demo1的流程图：

<img src="./images/1669208817642.jpg />

#### TooLongFrameException

为了防止解码器缓冲大量数据以至于耗尽内存，通过TooLongFrameException类，可以使得帧超出指定大小限制时抛出。

当使用一个可变帧大小的协议的时候，这种保护措施尤为重要。

详情见com.nju.netty.ch10.demo02.SafeByteToMessageDecoder

### 编码器

#### MessageToByteEncoder

<img src="./images/1669209409528.jpg />

详情见com.nju.netty.ch10.demo03.ShortToByteEncoder

流程图如下：
<img src="./images/1669209815504.jpg />

#### 抽象类MessageToMessageEncoder

详情见com.nju.netty.ch10.demo03.IntegerToStringEncoder

### 抽象的编解码器类

复合了ChannelInboundHandler 和 ChannelOutboundHandler 接口。但是可用性和可扩展性差。

#### 抽象类的ByteToMessageCodec

将字节码进行解码，可能为POJO，之后再编码。

#### 抽象类MessageToMessageCodec

decode将 INBOUND_IN 类型的数据转化为 OUTBOUND_IN 类型的消息，encode则进行逆向操作，

INBOUND_IN就好似网络传送的类型，OUTBOUND_IN就好似是应用程序所处理的类型

> WebSocket : 能实现Web浏览器和服务器之间的双向通信。

#### CombinedChannelDuplexHandler

避免了一个独立部署解码器编码器损失的便利性和结合解码器、编码器的可重用性。

充当ChannelInboundHandler和ChannelOutboundHandler的容器，。

其实就是通过组合代替继承思想。

详情见com.nju.netty.ch10.demo05;

## 预置的ChannelHandler和编解码器

