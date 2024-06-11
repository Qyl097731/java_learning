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
### 1.10 JEP 269: Convenience Factory Methods for Collections

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

### 1.11 JEP 271: Unified GC Logging
提供统一的GC日志API。`log_info(gc, heap, ergo)("Heap expanded");` 通过GC tag的来控制打印哪些内容。

### 1.12 JEP 280: Indify String Concatenation

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

### 1.13 JEP 285: Spin-Wait Hints
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

## JDK10

### 2.1 JEP 286: Local-Variable Type Inference
#### 简介
引入局部变量类型推断功能，允许在声明局部变量时使用 var 关键字来进行类型推断。

#### 案例
```java
public class VarExamples {
    public static void main(String[] args) {
        // 传统方式
        String message1 = "Hello, World!";
        List<String> list1 = new ArrayList<> ();

        // 使用局部变量类型推断
        var message2 = "Hello, World!";
        var list2 = new ArrayList<String>();

        // 传统方式
        Map<String, Integer> map = new HashMap<> ();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " => " + value);
        }

        // 使用局部变量类型推断
        for (var entry : map.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            System.out.println(key + " => " + value);
        }

        // 传统方式
        try (InputStream is = new FileInputStream ("file.txt");
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr)) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 使用局部变量类型推断
        try (var is = new FileInputStream("file.txt");
             var isr = new InputStreamReader(is);
             var reader = new BufferedReader(isr)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

### 2.2 JEP 307: Parallel Full GC for G1
G1垃圾收集器，可以使用多线程来进行Full GC（` -XX:ParallelGCThreads`）来提升G1的最坏的延迟。


## JDK11

### 3.1 JEP 181: Nest-Based Access Control
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
### 3.2 JEP 309: Dynamic Class-File Constants
引入了动态常量（Dynamic Constants）的概念，通过扩展 Java 类文件格式，使常量池能够包含动态计算的常量。动态常量的类型是 `CONSTANT_Dynamic`，它通过引导方法（Bootstrap Method）动态计算并初始化其值，这个过程就跟动态调用一样，允许在 Java 类文件中定义常量时进行延迟计算，以提高灵活性和效率。

### 3.3 JEP 318: Epsilon: A No-Op Garbage Collector (Experimental)
开发一个处理内存分配但不实现任何实际内存回收机制的GC。一旦可用的Java堆耗尽，JVM将关闭。

### 3.4 JEP 321: HTTP Client

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

### 3.5 JEP 323: Local-Variable Syntax for Lambda Parameters

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

### 3.6 JEP 330: Launch Single-File Source-Code Programs

引入了一种新的发布模式，使得可以直接运行包含 main 方法的单文件 Java 源代码程序，而不需要先编译成字节码，只需运行 `java <filename>.java` 即可。

### 3.7 JEP 333: ZGC: A Scalable Low-Latency Garbage Collector (Experimental)
ZGC是一个并发的、不分代、基于区域的垃圾收集器，基于标记整理算法。STD仅限于root扫描阶段，因此GC停顿时间不会随着堆或活动集的大小而增加。但是后面带了Experimental，说明还不建议用到生产环境。

旨在实现以下几点：
- GC暂停时间不会超过10ms；
- 即能处理几百兆小堆，也能处理几个T的大堆（OMG）；
- 和G1相比，应用吞吐能力不会下降超过15%；
- 为未来的GC功能和染色指针、读写屏障优化奠定基础；

需要通过`XX:+UnlockExperimentalVMOptions -XX:+UseZGC`进行设置

## 四、JDK12
### 4.1 JEP 189: Shenandoah: A Low-Pause-Time Garbage Collector (Experimental)
新增了一个名为 Shenandoah 的 GC 算法，通过与正在运行的 Java 线程进行并发处理来减少 GC 暂停时间。

使用 Shenandoah 的暂停时间与堆大小无关，这意味着无论堆是 200 MB 还是 200 GB，都将具有相同的暂停时间。

为每个Java对象添加了一个间接指针，使GC线程能够在Java线程运行时压缩堆。标记和压缩是同时执行的，因此只需要暂停Java线程足够长的时间来扫描线程堆栈以查找和更新对象图的根节点。

### 4.2 JEP 325: Switch Expressions (Preview)
#### 简介
扩展switch语句，使其既可以用作语句也可以用作表达式，并且这两种形式都可以使用“传统”或“简化”日常编码。

#### 案例
```java
public class SwitchExamples {

    private void chooseDayJdk8(int day) {
        int numLetters;
        switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                numLetters = 6;
                break;
            case TUESDAY:
                numLetters = 7;
                break;
            case THURSDAY:
            case SATURDAY:
                numLetters = 8;
                break;
            case WEDNESDAY:
                numLetters = 9;
                break;
            default:
                throw new IllegalStateException("Wat: " + day);
        }

        switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                System.out.println(6);
                break;
            case TUESDAY:
                System.out.println(7);
                break;
            case THURSDAY:
            case SATURDAY:
                System.out.println(8);
                break;
            case WEDNESDAY:
                System.out.println(9);
                break;
        }

    }

    private void chooseDayJdk12(int day) {
        int numLetters = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY                -> 7;
            case THURSDAY, SATURDAY     -> 8;
            case WEDNESDAY              -> 9;
            default -> throw new IllegalStateException ("Unexpected value: " + day);
        };

        System.out.println (numLetters);

        int j = switch (day) {
            case MONDAY  -> 0;
            case TUESDAY -> 1;
            default      -> {
                System.out.println ("yield test");
                break 2;
            }
        };

        switch (day) {
            case 1 -> System.out.println("one");
            case 2 -> System.out.println("two");
            case 3 -> System.out.println("many");
        }
    }
}
```
### 4.3 JEP 344: Abortable Mixed Collections for G1
G1在混合收集阶段，如果超出了停顿的预期时间，那么混合收集就可以取消。

如果G1一直选择错误的区域进行回收，那么会尝试进一步的混合回收：把回收集合分为强制回收和可选回收两部分。强制回收花费的时间少于预期回收的时间就会进行可选部分的回收。

强制部分大多数都是新生代对象，少部分的老年代对象。大约占了预测回收集合的80%。
可选部分只包含老年代，大约占预测回收集合的20%。

强制部分和可选部分会随着运行而不断变化。

## JDK 13 

### 5.1 JEP 350: Dynamic CDS Archives
#### 简介
扩展应用程序类数据共享，允许在Java应用程序执行结束时对所有加载的应用程序类和库类进行动态归档。

我们知道在同一个物理机／虚拟机上启动多个JVM时，如果每个虚拟机都单独装载自己需要的所有类，启动成本和内存占用是比较高的。所以Java团队引入了CDS的概念，通过把一些核心类在每个JVM间共享，每个JVM只需要装载自己的应用类，启动时间减少了，另外核心类是共享的，所以JVM的内存占用也减少了。

在JDK10中对CDS进行了扩展，不仅作用于 Boot Class Loader 加载的类，还能作用App Class Loader 或者自定义Class Loader 加载的类。但是需要定义**自定义Dump文件指定需要归档的类列表，整体流程繁琐**。

这次JEP350在JDK10基础上进行扩展，简化手动自定义Dump文件的流程。通过`-XX:ArchiveClassesAtExit `、`-XX:SharedArchiveFile`结合使用即可。

例如，创建hello.jsa:
```cmd
% bin/java -XX:ArchiveClassesAtExit=hello.jsa -cp hello.jar Hello
```
使用动态归档来进行应用程序启动:
```cmd
% bin/java -XX:SharedArchiveFile=hello.jsa -cp hello.jar Hello
```

### 5.2 ZGC: Uncommit Unused Memory
#### 简介
增强ZGC，将未使用的堆内存返回给操作系统。

ZGC当前不会将内存返回给操作系统，即使该内存已经长时间未使用。这种行为对于所有类型的应用程序和环境来说都不是最佳的，尤其是那些需要考虑内存占用的环境。例如：
- 那些需要根据使用量付费的容器
- 应用程序可能长时间处于空闲状态并与许多其他应用程序共享或竞争资源的环境。
- 应用程序在执行期间可能有非常不同的堆空间需求。例如，启动期间所需的堆可能大于稍后在稳定状态执行期间所需的堆。

暂时通过`-XX:ZUncommitDelay=<seconds>`启发式地控制什么时候将未使用的内存归还给操作系统。

### 5.3 JEP 354: Switch Expressions (Second Preview)
#### 简介
根据JEP 325发布期间用户的反馈，switch表达式使用yield语句取代了break进行value的返回。

#### 案例
```java
    private void chooseDayJdk13(int day) {
        int j = switch (day) {
            case MONDAY  -> 0;
            case TUESDAY -> 1;
            default      -> {
                System.out.println ("yield test");
                yield 2;
            }
        };

        int i = switch (day) {
            case MONDAY  : yield 0;
            case TUESDAY : yield 1;
            default :{
                System.out.println ("yield test");
                yield 2;
            }
        };
    }
```

### 5.4 JEP 355: Text Blocks (Preview)

#### 简介
text block，文本块，是一个多行字符串文字，它避免了对大多数转义序列的需要，以可预测的方式自动格式化字符串，并在需要时让开发人员控制格式。

使用`"""`作为文本块的开始符合结束符，在其中就可以放置多行的字符串，不需要进行任何转义。

#### 案例
```java
// 旧版本
String query = "SELECT `EMP_ID`, `LAST_NAME` FROM `EMPLOYEE_TB`\n" +
               "WHERE `CITY` = 'INDIANAPOLIS'\n" +
               "ORDER BY `EMP_ID`, `LAST_NAME`;\n";
// 新版本               
String query = """
               SELECT `EMP_ID`, `LAST_NAME` FROM `EMPLOYEE_TB`
               WHERE `CITY` = 'INDIANAPOLIS'
               ORDER BY `EMP_ID`, `LAST_NAME`;
               """;
```


## JDK 14

### 6.1 JEP 305: Pattern Matching for instanceof (Preview)

#### 简介
通过模式匹配来增强`instanceof`操作，模式匹配能够让程序员更好地关注`if`断言之后的逻辑，使得这部分逻辑的编写更加安全、简介。

```java
if (obj instanceof String) {// 1 if断言
    String s = (String) obj; // 2 强制转换 3 临时变量创建
    // use s
}
```

JEP 305就相当于简化上述过程，让1、2、3三个步骤合在1中，让程序员更好关注后续的use部分。

```java
if (obj instanceof String s) {
    // can use s here
} else {
    // can't use s here
}
```

#### 案例

```java
// 14以前的版本equals
@Override public boolean equals(Object o) { 
    return (o instanceof CaseInsensitiveString) && 
        ((CaseInsensitiveString) o).s.equalsIgnoreCase(s); 
}

// 14版本的equals
@Override public boolean equals(Object o) { 
    return (o instanceof CaseInsensitiveString cis) && 
        cis.s.equalsIgnoreCase(s); 
}
```

### 6.2 JEP 345: NUMA-Aware Memory Allocation for G1

通过实现非统一的内存访问（NUMA）内存分配来提升G1在大型机器上的性能。
单一的JVM运行在不同的端口/核心上的时候，内存访问的性能不一致（比如本地内存的访问肯定是优于大多数全局内存访问），导致现在多端口的机器上内存访问出现了性能瓶颈——越远的端口有更大的延迟。

`+XX:+UseNUMA`添加该参数可以让JVM初始化的时候，G1的Region分布的更加均匀。

### 6.3 JEP 349: JFR Event Streaming

#### 简介
为了对JVM进行持续监控，会暴露JFR（JDK Flight Recorder）的数据。

JFR在之前的JDK版本已经存在了，JVM通过JFR暴露超过500项数据，但是大部分数据需要通过解析log日志才能获取，而不是实时获取。用户想要使用JFR的数据的话，用户必须先开启JFR进行记录，然后停止记录，再将飞行记录的数据Dump到磁盘上，然后解析这个记录文件。跟我们OOM日志一样，需要记录之后再Dump，最后进行解析。

> // 下面这条命令会立即启动JFR并开始使用templayte.jfc的配置收集60s的JVM信息，并输出到output.jfr中。
// 一旦记录完成之后，就可以复制jfr文件到你的工作环境使用jmc GUI来分析。
// 它几乎包含了排查JVM问题需要的所有信息，包括堆dump时的异常信息等。
jcmd <PID> JFR.start name=test duration=60s settings=template.jfc filename=output.jfr

对于JVM的持续监控没那么方便（比如大盘监控），为此进行了优化。

`jdk.jfr.consumer`中实现jfr事件的异步订阅，简单点说就是事件到来之后有一个专门的handler会进行处理。

#### 案例
`RecordingStream`类实现了接口`jdk.jfr.consumer.EventStream`，它提供了一种统一的方式来过滤和消费事件，而不管源是实时流还是磁盘上的文件。
```java
public interface EventStream extends AutoCloseable {
  public static EventStream openRepository();
  public static EventStream openRepository(Path directory);
  public static EventStream openFile(Path file);

  void setStartTime(Instant startTime);
  void setEndTime(Instant endTime);
  void setOrdered(boolean ordered);
  void setReuse(boolean reuse);

  void onEvent(Consumer<RecordedEvent> handler);
  void onEvent(String eventName, Consumer<RecordedEvent handler);
  void onFlush(Runnable handler);
  void onClose(Runnable handler);
  void onError(Runnable handler);
  void remove(Object handler);

  void start();
  void startAsync();

  void awaitTermination();
  void awaitTermination(Duration duration);
  void close();
}
```
```java
// 打印总体CPU使用情况和超过10毫秒的争用锁。
try (var rs = new RecordingStream()) {
  rs.enable("jdk.CPULoad").withPeriod(Duration.ofSeconds(1));
  rs.enable("jdk.JavaMonitorEnter").withThreshold(Duration.ofMillis(10));
  rs.onEvent("jdk.CPULoad", event -> {
    System.out.println(event.getFloat("machineTotal"));
  });
  rs.onEvent("jdk.JavaMonitorEnter", event -> {
    System.out.println(event.getClass("monitorClass"));
  });
  rs.start();
}
```

### 6.4 JEP 352: Non-Volatile Mapped Byte Buffers
增加JDK特有的文件映射模式，为了能够通过`FileChannel API`创建指向非易失性内存的`MappedByteBuffer`实例

### 6.5 JEP 358: Helpful NullPointerExceptions

#### 简介
能更准确地描述哪个变量是null，以此改进JVM生成的NPE的排查效率。

#### 案例
```java
// a[i]/a[i][j] is null?
a[i][j][k] = 99;
```
JDK 14以前
```cmd
Exception in thread "main" java.lang.NullPointerException
    at Prog.main(Prog.java:5
```

JDK 14开始
```cmd
Exception in thread "main" java.lang.NullPointerException:
        Cannot load from object array because "a[i][j]" is null
    at Prog.main(Prog.java:5)
```

### 6.6 JEP 359: Records (Preview)

#### 简介
`records`提供了一种紧凑的语法来声明类，这些类是浅层不可变数据的透明持有者。

JAVA经常被抱怨太繁琐了，很大一部分原因都是类导致的（只不过是充当简单聚合的普通“数据载体”），但是却要为他们编写很多重复的、低价值的、易错的代码（`constructors, accessors, equals(), hashCode(), toString()...`)。当然现在很多的注解（lombok的`@Data`的产生其实也有这部分原因）

`records`可以简单、清晰、简洁地声明不可变、行为良好的数据聚合。<b>但是`records`不能扩展其他类，不能声明其他实例字段（静态变量可以）</b>

如果`records`是内部类，则默认是`static`类型，避免实例给`records`偷偷加了一些参数。

#### 案例
```java
// JDK 14 以前
class Point{
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point range = (Point) o;
        return x == range.x && y == range.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Range{" +
                "x =" + x +
                ", y =" + y +
                "}";
    }
}
// JDK 14 
record Point(int x, int y) { }
```

`record Point(int x, int y) { }`根据里面的参数x、y做以下的事情：

- 生成x、y的访问方法
- 生成equals、hashCode、toString...
- x、y默认已经作为Point这个数据组件的私有字段
- 生成唯一的构造函数，全参构造函数。


### 6.7 JEP 361: Switch Expressions
把JDK13中的Switch Expression（Preview）变为正式版本了。

### 6.8 JEP 363: Remove the Concurrent Mark Sweep (CMS) Garbage Collector

CMS不再维护，并在JDK14之后移除

### 6.9 JEP 366: Deprecate the ParallelScavenge + SerialOld GC Combination

Parallel Scavenge 和 Serial Old 这一对垃圾回收算法组合被废弃。因为这种组合只适用于新生代非常大而老年代非常少的部署，这种组合的带来的优势远小于维护的成本。

即`-XX:+UseParallelGC -XX:-UseParallelOldGC`被废弃。

### 6.10 JEP 368: Text Blocks (Second Preview)

#### 简介
在JDK13预览版本的基础上增加了两种转义序列。

- 换行符（'\'）
- 空格（'\s'）

#### 案例
```java
\\ 换行符
String text = """
                Lorem ipsum dolor sit amet, consectetur adipiscing \
                elit, sed do eiusmod tempor incididunt ut labore \
                et dolore magna aliqua.\
                """

\\ 空格 对齐
String colors = """
    red  \s
    green\s
    blue \s
    """;
```

## JDK15
### 7.1 JEP 360: Sealed Classes (Preview)
#### 简介

密封类、接口能够指定哪些类或者接口负责实现它们，相较于访问修饰符的声明方式更加直接有力。

Java中超类并非都是为了复用，有些情况下可能只是简化部分子类的实现，但是Java假设复用总是最终目的。封装类和接口就是为了对这个假设进行一定的放宽，指定某些子类才需要实现该超类，其他类不能实现该超类。

是对超类访问和实现两种维度的解耦。

#### 案例
```java
// 密封类
public abstract sealed class Shape permits Circle, Rectangle, Square {
    // 抽象类的定义
}

public final class Circle extends Shape {
    // Circle 的定义
}

public sealed class Rectangle extends Shape permits FilledRectangle {
    // Rectangle 的定义
}

public final class Square extends Shape {
    // Square 的定义
}

public non-sealed class FilledRectangle extends Rectangle {
    // FilledRectangle 的定义
}

// 密封接口
public sealed interface Service permits DatabaseService, NetworkService {
    void perform();
}

public final class DatabaseService implements Service {
    @Override
    public void perform() {
        // 实现方法
    }
}

public sealed class NetworkService implements Service permits SecureNetworkService {
    @Override
    public void perform() {
        // 实现方法
    }
}

public non-sealed class SecureNetworkService extends NetworkService {
    @Override
    public void perform() {
        // 实现方法
    }
}

```

### 7.2 JEP 371: Hidden Classes
引入隐藏类，不能被任何其他类直接使用。主要是为了给框架层面使用的，可以独立于任何类被卸载。

### 7.3 JEP 374: Deprecate and Disable Biased Locking
默认情况下禁用偏向锁，并弃用所有相关的命令行选项。

偏向锁对于老的应用有较明显的性能优化。老应用会使用HashTable、Vector...等集合类进行同步访问控制，偏向锁会减少CAS竞争锁的执行，直到出现其他线程并发抢占锁的时候才会进行锁升级。

但是现在的应用都会有新的集合类：HashMap、ArrayList或者并发集合类等等，上述的性能优化其实不明显了。

应用升级后，伴随着线程池并废弃线程锁的情况下性能更优。加上偏向锁的维护成本，就废弃了偏向锁。

### 7.4 JEP 377: ZGC: A Scalable Low-Latency Garbage Collector (Production)
ZGC从实验阶段变为了正式版本了。

### 7.5 JEP 379: Shenandoah: A Low-Pause-Time Garbage Collector (Production)
Shenandoah 从实验阶段变为正式版本了。

### 7.6 JEP 378: Text Blocks
#### 简介
Text Block最终版，跟之前的Preview没区别。

#### 案例
String新增了几个API

- String::stripIndent()
- String::translateEscapes()
- String::formatted(Object... args)
```java
// 所有的String相关的参数都可以用
String code = """
              public void print($type o) {
                  System.out.println(Objects.toString(o));
              }
              """.replace("$type", type);
              
String code = String.format("""
              public void print(%s o) {
                  System.out.println(Objects.toString(o));
              }
              """, type);

String source = """
                public void print(%s object) {
                    System.out.println(Objects.toString(object));
                }
                """.formatted(type);

```

### 7.7 JEP 384: Records (Second Preview)
#### 简介
对`Records`的完善。新增对于局部`records`的使用。

中间变量有以下方法：一种选择是像许多程序今天声明辅助类那样，声明静态嵌套的 "辅助" 记录。更方便的一种选择是在方法内部声明一个记录，靠近操作这些变量的代码。这也是`Records`新增局部使用方式的原因。

<b>局部记录的是静态的，所以不允许访问非静态的参数、变量、方法等，但是局部记录很有用，为局部枚举和局部接口地搭配使用打开了大门</b>。
#### 案例
一个商人和每月销售额的聚合通过局部记录 MerchantSales 来建模。使用这个记录可以提高后续流操作的可读性：
```java
List<Merchant> findTopMerchants(List<Merchant> merchants, int month) {
    // 局部记录
    record MerchantSales(Merchant merchant, double sales) {}

    return merchants.stream()
        .map(merchant -> new MerchantSales(merchant, computeSales(merchant, month)))
        .sorted((m1, m2) -> Double.compare(m2.sales(), m1.sales()))
        .map(MerchantSales::merchant)
        .collect(toList());
}

```



