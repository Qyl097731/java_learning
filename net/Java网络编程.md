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

