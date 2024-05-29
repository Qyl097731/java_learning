# JDK9-21新特性概览

## 引言

JDK更新速度相当快，后文将会对部分JDK新特性进行介绍，整体行文按照JDK发布顺序进行叙述，每个版本都尽量会给出概念简述以及使用代码，所有内容都源于官网以及现有博客。

## JDK9 

### 1.1 JEP 102: Process API Updates
#### 简介
该提议旨在改进 Java 程序对操作系统进程管理的支持，主要通过对` java.lang.Process` 和 `java.lang.ProcessHandle` 增强，方便更好管控操作系统进程资源。

#### 案例

```java
// 获取当前进程的信息
ProcessHandle currentProcess = ProcessHandle.current();
long currentPid = currentProcess.pid();
ProcessHandle.Info info = currentProcess.info();

System.out.printf("Current Process PID: %d%n", currentPid);
info.command().ifPresent(cmd -> System.out.printf("Command: %s%n", cmd));
info.startInstant().ifPresent(start -> System.out.printf("Start time: %s%n", start));
info.totalCpuDuration().ifPresent(cpu -> System.out.printf("CPU usage: %s%n", cpu));

// 获取并管理当前进程的所有子进程
currentProcess.children().forEach(child -> {
    System.out.printf("Child PID: %d%n", child.pid());
    child.destroy(); // 销毁子进程
    // 监听当前进程的状态变化
    child.onExit().thenRun(() -> System.out.println("Process has exited"));
});
```

#### 风险
跨平台的时候，需要考虑不同操作系统的差异。

### 1.2 JEP 193: Variable Handles

#### 简介

变量句柄（Variable Handles）是对Java内存模型进行的一次重要增强。变量句柄是对Java中的变量进行更灵活和高效访问的机制，主要是方便对一个类中的变量进行细粒度的管控，类似于安全的反射，具有更高的性能和类型安全。它们提供了一组方法来读取和写入变量，并且可以原子地操作这些变量。

变量句柄提供了一种统一的方式来访问各种类型的变量，包括字段、数组元素和静态变量。它们在并发编程中尤为有用，因为它们支持高级的同步操作和内存屏障。

由单个抽象类`java.lang.invoke.VarHandle`、`java.lang.invoke.MethodHandles`实现，`VarHandle`中包含了不同的访问模式，是多态的一种体现。

1. 读访问模式，读最新的变量值
2. 写访问模式，对变量的更新对其他线程可见
3. 原子更新访问模式
4. 数字原子更新访问模式，例如getAndAdd。
5. 位原子更新访问模式，如getAndbitwise

#### 案例
```java
public class VariableHandleExample {
    // 声明实例字段和静态变量句柄
    private int x;
    private static VarHandle X_HANDLE;

    // 声明数组和数组元素句柄
    private final int[] array = new int[10];
    private static VarHandle ARRAY_HANDLE;

    static {
        try {
            // 初始化实例字段的变量句柄
            X_HANDLE = MethodHandles.lookup ().findVarHandle (VariableHandleExample.class, "x", int.class);
            // 初始化数组元素的变量句柄
            ARRAY_HANDLE = MethodHandles.arrayElementVarHandle(int[].class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace ();
        }
    }

    // 使用变量句柄获取实例字段x的值
    public int getX() {
        return (int) X_HANDLE.get(this);
    }

    // 使用变量句柄设置实例字段x的值
    public void setX(int value) {
        X_HANDLE.set(this, value);
    }

    // 使用变量句柄进行实例字段x的原子比较并交换操作
    public boolean compareAndSetX(int expected, int newValue) {
        return X_HANDLE.compareAndSet(this, expected, newValue);
    }

    // 使用变量句柄获取数组元素的值
    public int getElement(int index) {
        return (int) ARRAY_HANDLE.get(array, index);
    }

    // 使用变量句柄设置数组元素的值
    public void setElement(int index, int value) {
        ARRAY_HANDLE.set(array, index, value);
    }

    public static void main(String[] args) {
        VariableHandleExample example = new VariableHandleExample();

        // 设置和获取实例字段x的值
        example.setX(42);
        System.out.println("Value of x: " + example.getX()); // 输出：Value of x: 42

        // 原子比较并交换操作
        boolean updated = example.compareAndSetX(42, 100);
        System.out.println("Updated: " + updated); // 输出：Updated: true
        System.out.println("Value of x: " + example.getX()); // 输出：Value of x: 100

        // 设置和获取数组元素的值
        example.setElement(0, 123);
        System.out.println("Value at index 0: " + example.getElement(0)); // 输出：Value at index 0: 123
    }
}

```
#### 风险
性能还需要更多的验证。

### 1.3 JEP 200: The Modular JDK
#### 简介
将JDK划分为一组模块，这些模块可以在编译时、构建时和运行时组合成各种配置，更易于提高安全性和可维护性，提高应用程序性能，并为开发人员提供更好的大型编程工具。

整个流程大致分为三步：

1. 使用moudule-info.java来声明一个模块，一个模块只能有一个文件，且在顶层包同目录下
2. 使用exports来声明可以被外部引用的包可以有多个exports语句
3. 使用requires来声明依赖的外部的模块可以有多个requires语句

#### 案例

```java
module 被引用模块 {
	// 导出的子模块
    exports com.nju.jdk9;
}

module 引用模块{
	// 引用的子模块
    requires jdk9;
}
```

#### 风险
有些用例不能支持，还存在一些缺陷，留待后续迭代的时候进行完善。

### JEP 213: Milling Project Coin
#### 简介
主要是对语言的细微改进来提高Java的可读性和可维护性。
1. `try-with-resources`使用的简化
2. 允许接口中私有方法，只能在该接口内部使用，主要用于重用代码，减少重复。

#### 案例
##### 1.1 try-with-resources增强
```java
import java.io.*;

public class TryWithResourcesExample {
    public static void main(String[] args) throws IOException {
        // 在 try 块外部声明和初始化资源
        Reader reader = new BufferedReader(new FileReader("test.txt"));
        try (reader) {
            // 使用资源
            System.out.println(reader.read());
        }
    }
}

```
##### 1.2 接口中私有方法
```java
interface MyInterface {
    default void doSomething() {
        sayHello ();
        sayGoodBye();
    }

    void sayGoodBye();

    private void sayHello() {
        System.out.println ("hello");
    }
}

public class PrivateInterfaceImpl implements MyInterface{
    public static void main(String[] args) {
        PrivateInterfaceImpl impl = new PrivateInterfaceImpl ();
        impl.doSomething ();
    }

    @Override
    public void sayGoodBye() {
        System.out.println ("bye");
    }
}
```

### 1.5 JEP 222: jshell: The Java Shell (Read-Eval-Print Loop)
新增JShell控制台进行交互编程（就跟python一样，直接控制台编码，输出）

### 1.6 JEP 254: Compact Strings
之前的JDK版本采用char数组存储字符串，char默认两个字节存储，JDK9采用byte数组更加节约存储空间

### 1.7 JEP 248: Make G1 the Default Garbage Collector
使用G1作为默认的垃圾收集器，减少GC期间STW（Stop the World）的时间，以提供更好的用户体验。

### 1.8 JEP 264: Platform Logging API and Service

提供了更轻量级的默认的日志API`java.util.logging`。

### 1.9 JEP 270: Reserved Stack Areas for Critical Sections

#### 简介
给线程栈预留了更多的空间，减少并发情况下`StackOverflow`风险，避免进一步的并发问题，如死锁。

当一个临界区由几个方法组成时，例如一个方法a调用了一个方法b，可用的堆栈足以让方法a执行。方法a开始修改数据结构，然后调用方法b，但是剩余的堆栈不足以执行b，导致`StackOverflowError`。因为方法b和方法a的剩余部分没有执行，所以数据结构的一致性可能已经受到损害。

#### 案例
```java
// 无法保证原子操作
final void lock() {
    if (compareAndSetState(0, 1))
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}
```
### 2.0 JEP 269: Convenience Factory Methods for Collections

#### 简介
通过工厂方法，简化集合初始化。

#### 案例

```java
public class CollectionsExample {
    public static void main(String[] args) {
        // 使用List.of()创建不可变的列表
        List<String> list = List.of("one", "two", "three");
        System.out.println("List: " + list); // 输出: List: [one, two, three]

        // 尝试修改列表会抛出UnsupportedOperationException
        // list.add("four"); // 这行代码会抛出异常

        // 使用Set.of()创建不可变的集合
        Set<String> set = Set.of("one", "two", "three");
        System.out.println("Set: " + set); // 输出: Set: [one, two, three]

        // 尝试修改集合会抛出UnsupportedOperationException
        // set.add("four"); // 这行代码会抛出异常

        // 使用Map.of()创建不可变的映射
        Map<String, Integer> map = Map.of("one", 1, "two", 2, "three", 3);
        System.out.println("Map: " + map); // 输出: Map: {one=1, two=2, three=3}

        // 使用Map.ofEntries()创建不可变的映射
        Map<String, Integer> mapEntries = Map.ofEntries(
                Map.entry("one", 1),
                Map.entry("two", 2),
                Map.entry("three", 3)
        );
        System.out.println("Map with Entries: " + mapEntries); // 输出: Map with Entries: {one=1, two=2, three=3}

        // 尝试修改映射会抛出UnsupportedOperationException
        // map.put("four", 4); // 这行代码会抛出异常
    }
}
```

### 2.1 JEP 271: Unified GC Logging
提供统一的GC日志API。`log_info(gc, heap, ergo)("Heap expanded");` 通过GC tag的来控制打印哪些内容。

### 2.2 JEP 280: Indify String Concatenation

#### 简介

旨在通过使用`invokedynamic`(`java.lang.invoke.StringConcatFactory`)指令来优化字符串连接操作。这种方法使用`invokedynamic`指令，并将连接逻辑推迟到运行时，以便可以更好地利用JVM的优化能力。

在Java 9之前，字符串连接通常是通过`StringBuilder`的隐式使用来实现的。

#### 案例
```java
String m(String a, int b) {
  return a + "(" + b + ")";
}
```

```java
// JDK8编译代码
java.lang.String m(java.lang.String, int);
       0: new           #2                  // class java/lang/StringBuilder
       3: dup
       4: invokespecial #3                  // Method java/lang/StringBuilder."<init>":()V
       7: aload_1
       8: invokevirtual #4                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      11: ldc           #5                  // String (
      13: invokevirtual #4                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      16: iload_2
      17: invokevirtual #6                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
      20: ldc           #7                  // String )
      22: invokevirtual #4                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      25: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      28: areturn

// JDK9 编译代码
        java.lang.String m(java.lang.String, int);
        0: aload_1
        1: ldc           #2                  // String (
        3: iload_2
        4: ldc           #3                  // String )
        6: invokedynamic #4,  0              // InvokeDynamic #0:makeConcat:(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;
        11: areturn
```

#### 优点
- 性能优化：通过将字符串连接推迟到运行时，JVM可以应用更高效的优化策略。
- 减少内存开销：避免了大量的StringBuilder对象的创建和销毁，降低了GC压力。
- 灵活性：运行时优化允许JVM根据具体情况选择最佳的连接策略，适应不同的硬件和JVM实现。

### 2.3 JEP 285: Spin-Wait Hints
#### 简介
引入了“旋转等待提示”（Spin-Wait Hints），即在多线程编程中提供一种机制，通过标准化的方法向底层处理器传达线程处于“自旋等待”（spin-wait）的状态，在 `java.lang.Thread.onSpinWait() `中实现自旋等待提示。

#### 案例

```java
public class SpinWaitExample {
    private volatile boolean condition = false;

    public void waitForCondition() {
        while(!condition) {
            Thread.onSpinWait ();
        }
        System.out.println ("Condition change to true");
    }

    public void setCondition() {
        condition = true;
    }

    public static void main(String[] args) throws InterruptedException {
        SpinWaitExample example = new SpinWaitExample ();
        Thread waiter = new Thread (example::waitForCondition);
        waiter.start ();

        Thread.sleep (1000);

        example.setCondition ();
        waiter.join ();
    }
}

```

#### 优点
- 提高性能：自旋等待提示允许处理器优化资源利用和功耗管理，在高性能计算场景中特别有用。
- 降低延迟：适用于需要低延迟同步的场景，例如高频交易系统或实时处理系统。
- 标准化：提供了一个标准API，避免了使用特定于平台的低级指令，提高了代码的可移植性。

### 其他
#### InputStream增强
新增了transferTo方法，可以用来将数据直接传输到 OutputStream

#### Stream API的增强
```java
public class StreamApiExample {
    public static void main(String[] args) {
        testForTakeWhile();
        testForDropWhile();
        testForOfNullable();
        testForIterate();
    }

    //  takeWhile用于从 Stream 中获取一部分数据，接收一个 Predicate 来选择从开头开始的满足条件的元素。
    public static void testForTakeWhile(){
        List<Integer> list = Arrays.asList(10,20,30,40,30,20,10);
        list.stream().takeWhile(t->t<40).forEach(t -> System.out.print(t + " "));
        System.out.println ();

        List<Integer> list2 = Arrays.asList(1,2,3,4,5,6,7);
        list2.stream().takeWhile(t->t<7).forEach(t -> System.out.print(t + " "));
        System.out.println ();
    }

    // dropWhile 的行为与 takeWhile 相反，返回剩余的元素
    public static void testForDropWhile() {
        List<Integer> list = Arrays.asList(10,20,30,40,30,20,10);
        list.stream().dropWhile(t->t<40).forEach(t-> System.out.print(t + " "));
        System.out.println ();

        List<Integer> list2 = Arrays.asList(1,2,3,4,5,6,7);
        list2.stream().dropWhile(t->t<7).forEach(t -> System.out.print(t + " "));
        System.out.println ();
    }

    // ofNullable 方法允许我们创建一个Stream，可以所有元素均为空。
    public static void testForOfNullable() {
        // JDK8允许通过
        Stream<String> streams = Stream.of("AA","BB",null);
        System.out.println(streams.count());
        // JDK8不允许通过
        /*Stream<Object> stream2 = Stream.of(null);
        System.out.println(stream2.count());*/
        // JDK9允许通过
        Stream<Object> stream2 = Stream.ofNullable(null);
        System.out.println(stream2.count());
    }

    // iterate 可以让你提供一个 Predicate (判断条件)来指定什么时候结束迭代。
    public static void testForIterate() {
        // 原始方式
        Stream.iterate(1,i->i+1).limit(50).forEach(t -> System.out.print(t + " "));
        System.out.println ();

        // 增强方式
        Stream.iterate(1,i->i<60,i->i+1).forEach(t -> System.out.print(t + " "));
    }
}

```

## JDK11

### 2.1 JEP 181: Nest-Based Access Control
#### 简介
旨在解决 Java 中内部类与其外部类之间访问权限的问题。

传统上，Java 内部类和其外部类需要通过编译器生成桥接方法来访问私有成员，这样既增加了代码复杂性，又带来了安全隐患。JEP 181 通过在 JVM 中引入“嵌套”概念，允许嵌套类直接访问彼此的私有成员，简化了访问控制。

内部、外部类直观感受就是同源，属于一层的事物，对于上层不用过多感知两者区别，跟Tomcat这种层层加载，层间嵌套想法类似。

#### 案例
```java
public class Outer {
    private String outerField = "Outer Field";

    public class Inner {
        private String innerField = "Inner Field";

        public void accessOuterField() {
            System.out.println(outerField);  // 访问外部类的私有字段
        }
    }

    public void accessInnerField() {
        Inner inner = new Inner();
        System.out.println(inner.innerField);  // 访问内部类的私有字段
    }
}
```
JDK11以前编译之后：
```cmd
Compiled from "Outer.java"
public class com.nju.jdk11.Outer {
	  private java.lang.String outerField;
	  public com.nju.jdk11.Outer();
	  public void accessInnerField();
	  static java.lang.String access$000(com.nju.jdk11.Outer);
}
```
JDK11编译之后：
```cmd
Compiled from "Outer.java"
public class com.nju.jdk11.Outer {
	  private java.lang.String outerField;
	  public com.nju.jdk11.Outer();
	  public void accessInnerField();
}

```
### 2.2 JEP 309: Dynamic Class-File Constants
引入了动态常量（Dynamic Constants）的概念，通过扩展 Java 类文件格式，使常量池能够包含动态计算的常量。动态常量的类型是 `CONSTANT_Dynamic`，它通过引导方法（Bootstrap Method）动态计算并初始化其值，这个过程就跟动态调用一样，允许在 Java 类文件中定义常量时进行延迟计算，以提高灵活性和效率。

### 2.3 JEP 318: Epsilon: A No-Op Garbage Collector (Experimental)
开发一个处理内存分配但不实现任何实际内存回收机制的GC。一旦可用的Java堆耗尽，JVM将关闭。

### 2.4 JEP 321: HTTP Client

#### 简介
对JDK9引入的HTTP Client进行最终的定稿。

引入了一个新的 HTTP 客户端 API，该 API 取代了原有的 `HttpURLConnection`，支持 HTTP/1.1 和 HTTP/2 协议，具备异步编程模型，增强了对高性能和低延迟的支持。

#### 特性
- 支持 HTTP/2：新的 HTTP 客户端默认支持 HTTP/2，可以更有效地利用网络资源，提供更好的性能。
- 异步编程模型：支持异步请求和响应处理，使得非阻塞 I/O 操作更加容易。
- 简洁易用：提供了简洁的 API，使得发送 HTTP 请求和处理响应更加直观和容易。
- WebSocket 支持：集成了对 WebSocket 的支持，允许客户端和服务器之间的双向通信。
- 高效资源管理：通过内建的连接池和其他优化手段，更高效地管理网络资源。

#### 案例
```java
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

```

### 2.5 JEP 323: Local-Variable Syntax for Lambda Parameters

#### 简介
允许在 lambda 表达式的参数中使用局部变量类型推断。这意味着你可以在 lambda 参数列表中使用 `var `关键字，让编译器自动推断参数的类型。这一改进与 Java 10 引入的局部变量类型推断（即 `var `关键字）保持一致，提高了代码的简洁性和一致性

#### 案例
```java
public class VarExample {
    public static void main(String[] args) {
        // 示例 1：基本用法
        List<String> list = Arrays.asList("one", "two", "three");

        // 使用传统方式声明 lambda 参数类型
        list.forEach((String s) -> System.out.println(s));

        // 使用 var 声明 lambda 参数类型
        list.forEach((var s) -> System.out.println(s));

        // 示例 2：使用多个参数
        BiFunction<Integer, Integer, Integer> add = (var x, var y) -> x + y;
        System.out.println("Sum: " + add.apply(5, 3)); // 输出: Sum: 8

        // 示例 3：结合注解使用
        // 使用注解和 var 关键字
        list.forEach((@Deprecated var s) -> System.out.println(s));
    }
}
```

### 2.6 JEP 330: Launch Single-File Source-Code Programs

引入了一种新的发布模式，使得可以直接运行包含 main 方法的单文件 Java 源代码程序，而不需要先编译成字节码，只需运行 `java <filename>.java` 即可。

### 2.7 JEP 333: ZGC: A Scalable Low-Latency Garbage Collector (Experimental)
ZGC是一个并发的、不分代、基于区域的垃圾收集器，基于标记整理算法。STD仅限于root扫描阶段，因此GC停顿时间不会随着堆或活动集的大小而增加。但是后面带了Experimental，说明还不建议用到生产环境。

旨在实现以下几点：
- GC暂停时间不会超过10ms；
- 即能处理几百兆小堆，也能处理几个T的大堆（OMG）；
- 和G1相比，应用吞吐能力不会下降超过15%；
- 为未来的GC功能和染色指针、读写屏障优化奠定基础；

需要通过`XX:+UnlockExperimentalVMOptions -XX:+UseZGC`进行设置
