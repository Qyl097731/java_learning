# 流

## 简介

- java的I/O都建立于流（stream）之上：FileInputStream、TelnetOutputStream
- 过滤器（filter）流可以串联到输入流或输出流上。可以来实现加密或者压缩，或者只是将数据转换为其他格式。DataOutputStream
- 阅读器（reader）和书写器（writer）可以串联到输入流和输出流，允许程序读写的是字符。
- 流是同步的。
- 支持通道和缓冲区的非阻塞IO

## 输出流

OutputStream

- FileOutputStream把数据写入文件
- TalnetOutputStream把数据写入网络
- ByteArrayOutputStream 把数据写入可扩展的字节数组

### flush

java代码缓存的存在，会导致流不会直接发送，而是等到缓冲区满了之后才发送。那么试想一下，如果服务器发送了300字节数据，1024字节的缓冲区显然未满，还在等待服务器继续发送，可是服务器却在等待客户端响应（没收到响应就不发），那么显然已经产生了死锁了，flush()方法可以强制缓冲区发送数据，即使缓冲区未满。

在关闭流之前记得刷新输出流，否则容易造成缓冲区数据丢失。

## 输入流

InputStream

- FileInputStream 从文件中读数据
- TelnetInputStream从网络中读数据
- ByteArrayStream 从字节数组中读数据

## 标记和充值

InputStream 允许程序备份和重新读取已经读取的数据。

## 过滤器串链在一起

```java
DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("data.txt")));
```

## 缓冲流

- BufferedOutputStream把写入的数据存储在缓冲区（一个名为buf的保护字节数组字段），直到缓冲区满或者刷新输出流。
- BufferedInputStream会先从缓冲区的保护字节数组（buf)中读取数据，如果不存在才回去底层源中读取数据，且会读取的数据尽可能的多，方便之后读取。

## PrintStream

有害，应该尽可能避免。

- println()的输出与平台有关，转义字符可能有不同的意思
- PrintStream假定使用所在平台的默认编码，在网络传输的时候可能并不是服务器或者客户端所期望的。
- PrintStream吞掉了所有异常

可以通过checkError()检查错误标志，但是无法重置错误标志。

## 数据流

DataInputStream和DataOutputStream可以用二进制格式读写Java的基本数据类型和字符串。

## 阅读器和书写器

Reader和Writer最重要的具体子类时InputStreamReader 和 OutputStreamReader；将面向字节的接口改为面向字符的接口

## 过滤器阅读器和书写器

BufferedReader、BufferedWriter、LineNumberReader、PushbackReader、PrintWriter

BufferedReader和BufferedWriter对应于InputStreamReader 和 OutputStreamReader（内部字节数组作为缓冲区），是面向字符的，使用的是一个内部字符数组作为缓冲区

BufferedWriter()新增的newLine()解决了跨平台换行符的问题，通过line.separator获取换行符。

## PrintWriter

尽管能正确处理多字节字符集和管计划文本，但是还是存在着平台依赖性和错误报告信息量小的问题。

# 线程

每个进程都有独立的内存，但是太重了。线程可以共享进程的资源，提升性能，轻量级，但是增加了程序的复杂性，尤其是一致性问题。

## 运行线程

可以覆盖Thread.run()方法或者实现Runnable接口让线程执行指定的任务。

`public void run`：所有的工作都写在这个方法里，线程开始结束都在这里。

## 派生Thread

通过继承Thread，重写run()实现多线程

见char3.demo1

## 实现Runnable

通过实现Runnable接口中的run()方法，传递给Thread来运行

见char3.demo2

## 从线程返回信息

默认线程run不接受参数，不返回结果。需要变量或者回调来传递返回信息。

见char3.demo3

## 轮询 & 回调

见案例char3.demo3，这里不再展示

## Feture、Callable、Executor

见案例char3.demo4，这里不再展示

## 同步类、实例、方法

不同的粒度，通过synchronized同步锁控制程序并发执行，但是性能有所下降。

## 同步的替代方法

- 方法内部创建全新的局部变量
- 基本类型的方法参数也是安全的，因为Java按值传递
- 对象类型的方法除了String（不可变）其他都很麻烦
- 不包含对其他类型对象引用的构造函数是安全的；如果构造函数把正在创建的对象传递给另一个不同线程可能会出问题。
- 利用不可变性
- 把非线程安全的类作为线程安全类的一个私有字段，只要包含类线程安全的访问这个非安全类。

> List list = Collections.synchronizedList(new ArrayList())使用的时候，如果对list执行一系列原子操作的组合需要对list进行同步。如果对包含list的对象进行同步，因为如果其他对象也引用该list，可能会造成线程不安全。

## 优先级

Java中10最高优先级、0最低优先级、默认是5.与UNIX相反。

## 让线程暂停的8种方法

### 阻塞

等待获取暂时无法获取到的资源

### 放弃 

yield() 允许同级线程抢占CPU，但是不会释放上锁资源

### 休眠

sleep() 允许低级线程抢占CPU，但是不会释放上锁资源；可以通过interrupt()唤醒，继续执行catch块内的代码，但是如果是唤醒IO阻塞，则很大可能不起效果。

### 连接线程 

join() 阻塞当前线程，等待被连接线程运行结束。

### 等待一个对象

wait(),等待一个对象，直到

- 时间到期：但是如果仍旧没有获得等待对象的锁，可能仍要阻塞一段时间
- 线程被中断：interrupt()
- 对象得到通知：notify() \ notifyAll()

### 结束

线程正常结束，如果任务运行时间本来就极短，不建议为它开设线程。

## 线程池和Executor

线程启动和撤销都会有性能开销；线程间切换也会有开销；生成过多的线程，超过机器所能承载的极限时，只会导致更多的线程管理的开销。

## Internet地址

### 简述

- IPV4

四字节三十二位，每个无符号字节范围0-255，字节间通过.分割

- IPV6

16字节128位，通过:分隔成8个块，每个区块都是4个16进制数字。可以省略前导0，最早出现的多个连续0区块可以用::表示。
有时候最后四个字节表示成IPV4格式。

- DNS

域名解析器。如果一个域名对应多个IP，可以通过DNS随机选择一个主机进行响应。如果该服务移动到其他机器时，会重新做域名与IP之间的映射

### InetAddress

Java对Ip的封装,当IP相同时，两个InetAddress对象就相同，否则就是不相同。

### 缓存

暂存最近请求的域名和IP映射。由于第一次查找超时（其实存在该域名到IP的映射），可是由于缓存机制的原因，会把第一次超时返回的域名到IP的映射存的时间较短，可以通过

- `newworkaddress.cache.ttl` 指定成功的查找结果的缓存时间
- `newworkaddress.cache.negative.ttl` 指定不成功的查找结果的缓存时间
- -1 表示永久缓存

### 安全性

对不可信的applet不提供DNS查找服务。可以通过`public void checkConnect(String hostname,int port)`测试主机能否解析.

### 地址类型

- 127.0.0.1 \ ::1 本机地址 可以通过`isLoopbackAddress()`测试
- 224.0.0.0 - 239.255.255.255 \ FF 组播地址，可以通过`isMulticastAddress()`测试
- FF0E\FF1E开头的IPV6地址 全球组播地址
- FF08\FF18开头的IPV6地址 组织范围组播地址
- FF05\FF15开头的IPV6地址 网站组播地址
- FF02\FF12开头的IPV6地址 子网范围组播地址
- FF01\FF11开头的IPV6地址 本地接口组播地址
- 0.0.0.0 \  :: 通配地址 可以通过`isAnyLocalAddress()`测试
- FE80:0000.0000:0000开头的IPV6地址 本地链接地址（对比DHCP服务的IP自动分配） 可以通过`isLinkLocalAddress()`测试
- EFC0:0000.0000:0000开头的IPV6地址 本地哇那个站地址，可以通过`isSiteLocalAddress()`测试

### 测试可达性

`isReachable()`测试是否连接到某特定主机

### NetworkInterface

表示本地IP地址，可以是一个物理接口，也可以是一个虚拟接口。可以通过这个类来枚举所有本地方法，之后通过这些地址创建InetAddress。

1. `getByName(String name)`获取指定名字的网络接口
2. `getByInetAddress(InetAddress address)` 获取指定IP地址绑定的网络接口
3. `getNetWorkInterfaces()` 获取所有的本机的网络接口
4. `getInetAddresses()` 一个接口可能对应多个IP，列举某接口的所有IP
5. `getName()` 返回某个接口的名字
6. `getDisplayName()` 返回一个更友好的接口名字

