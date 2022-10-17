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
- 所有由EventLoop处理的I/O事件都将在它专有的Thread上被
处理；
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

