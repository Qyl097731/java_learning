# JVM学习 

## 字节码

### 常见指令

https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5

### 常用工具

![image-20220816092516528](C:\Users\nsec\AppData\Roaming\Typora\typora-user-images\image-20220816092516528.png)

![image-20220816092600842](C:\Users\nsec\AppData\Roaming\Typora\typora-user-images\image-20220816092600842.png)



## 类加载及生命周期

### 加载阶段

##### 加载操作 

将字节码加载到内存，构建类模板对象。反射基于这一基础才得以实现。即加载二进制数据，生成class实例。

**-XX:+TraceClassLoading 追踪类加载轨迹**

##### 二进制获取方式

- 通过文件系统读入class后缀的文件
- 读入jar、zip等数据包，提取类文件
- 实现存放在数据库中的类的二进制数据
- 通过类似HTTP协议进行网络加载
- 运行时生成class的二进制信息

#####  类模型与class实例位置

- **类模型**

  1.8之前存放永久代；1.8之后存放元空间

- **class实例**

  存放在堆中，用来封装类的数据结构，在加载类的过程中创建

  构造方法是私有的，只有JVM能创建

##### 数组类的加载

不是类加载器负责创建，在运行时根据需要进行创建。数组元素通过类加载器创建。

### 链接阶段

##### 验证

- 保证加载的字节码是否合法

- 步骤
  1. 格式检查（加载阶段就开始了）
  2. 语义检查
  3. 字节码验证 （栈帧映射在此阶段，通过该监测也并非完全正确）
  4. 符号引用验证。（在解析环节执行）

##### 准备

- 为类的静态变量分配内存，初始化设置默认值，在初始化阶段赋值。
- **基本数据类型/String（不是new 出来）常量在编译时（载入字节码时）就分配初始化值，在准备阶段显式赋值**

**总结** 若static修饰 ：如果牵涉到需要new的都在clinit初始化赋值，如果确定不会new就直接准备阶段赋值，

##### 解析

- 将符号引用转化为直接引用

### 初始化阶段

- 执行类中定义的java程序代码，主要执行类的初始化操作<clinit>（线程安全）

  注意init是整个类初始化 clint是执行类中定义的方法

- 在加载类的时候，总是会试图加载父类，所以父类的clinit会咸鱼子类的clinit执行。即父类static先于子类static。

**主动使用 & 被动使用（不会被初始化）**

- 主动使用
  1. 当创建类实例，使用new、反射、克隆、反序列化
  2. 调用类的静态方法，即使用了invokestatic命令
  3. 使用类、接口的静态字段时
  4. 当初始化子类，发现父类还没初始化，要先触发父类初始化；接口除外，接口只有在调用其静态字段时，才会激活初始化；
  5. 但是一个接口定义了default，那么不管调用子类还是调用了接口中的静态字段，都会初始化。
  6. 虚拟机启动，用户指定一个执行类（含main方法），虚拟机会先初始化该类
  7. 初次调用MethodHadle实例时，初始化该MethodHadle指向的方法所在的类
- 被动使用
  1. 访问一个静态字段，只有真正声明这个字段的类才会被初始化，通过子类调用父类字段，不会导致子类初始化
  2. 通过数组定义类引用，不会引发类的初始化
  3. 引用常量不会触发类或者接口的初始化，因为在链接阶段就已经被显式赋值
  4. 调用ClassLoader类的loadClass()方法加载一个类，不是对类的主动使用，不会对类初始化

### 类的使用阶段

### 类的卸载阶段

##### 类、类的加载器、类的实例之间的引用关系

1. 类加载器内部，通过集合存放所加载类的引用，Class实例总是引用类加载器，通过Class.getClassLoader()获取类加载器
2. 一个类的实例可以通过Object的getClass方法获取该对象是哪个Class，同时所有类都有静态属性class表示对象所属的类Class。

##### 	类的生命周期

栈中存放引用地址，堆区存放对象，方法区（new 模板）存放类模板。

当类、类实例、类加载器都不再使用就回收。

### 	类加载器

#### 	作用

主要将类模板加载进来，交付给jvm进行连接初始化，是否可以运行由Execution Engine（执行引擎）决定

#### 	类加载的分类

**显示加载** ：通过ClassLoader加载，如Class.forName(name)或this.getClass().getClassLoader().loadClass()记载class对象

**隐式加载**：通过jvm自动加载

#### 	类的唯一性

1. 何为类的唯一性

   通过类加载器和类本身一同确认在JVM中的唯一性。比较两个类是否相等，只有在两个类在通过一个类加载器加载的前提下才有意义。

2. 命名空间

   - 由该加载器以及所有父加载器所加载的类组成

   - 同一命名空间中，不会由两个相同的类

   - 在不同的命名空间中，可能会出现类的完整名字相同的两个类

#### 	类加载机制的三个基本特征

1. 双亲委派模型

   **优点**：安全、避免重复加载、保护程序、防止核心api被篡改

   **缺点**：高层的类加载器无法识别被用户程序自定义实现了的基础类的接口

   **执行过程**：

   1. 查看缓存中是否有所需要的类
   2. 如果没有就查看是否有父类，若有通过父类进行加载
   3. 若没有通过调用启动类加载器进行加载
   4. 若都没有获取所需要的类，就通过自身进行类的加载

   双亲委派集中体现在2-3两步

   **破坏双亲委派**

   1. 重写loadClass就会破坏2-3两步，建议重写findClass方法
   2. 高层的类加载器无法识别被用户程序自定义实现了的基础类的接口。若类库本就需要用户进行继承、实现，但是显然高层的类加载器不会识别。故设计了线程上下文类加载器。
   3. 用户追求程序动态性：代码热替换（修改的代码能不停止服务，立即生效）、模块热部署

2. 可见性

   子类加载器可以访问父类的加载器，反过来不可以，会不能实现容器的逻辑

3. 单一性

   父类加载器对于子加载器可见，所以父加载器加载过的类型，子类不会再重复加载。同级的类加载器，同一类型可以多次加载，互不可见。

#### 	类加载器的分类

1. 引导类加载器

   - 启动类加载器 

     只加载java、javax、sun开头的类，为自定义类加载器指定父类加载器。

2. 自定义类加载器 派生于ClassLoader 的类加载器

   - 扩展类加载器
   - 应用程序类加载器（系统类加载器）
   - 自定义类加载器

   下层都含有上层加载器的引用

3. 附加说明

   - 引用类型数组元素类加载 就是数组的类加载器
   - 基本数据类型元素类预定义 不会加载
   - **线程上下文类加载器** 是系统类加载器，高层次类加载器通过委托线程上下文类加载器来调用低层次的类加载器。

4. Class.forName & ClassLoader.loadClass()

   - Class.forName加载Class的同时会进行类的初始化
   - ClassLoader.loadClass只是加载，不会初始化，直到类的第一次使用时才会初始化。

#### 自定义类加载器

##### 为什么要自定义类加载器

- 隔离加载器
- 修改类加载器方式
- 扩展加载器
- 防止源码泄露

## JVM调优

### 虚拟机参数

#### 标准参数   （-开头的参数）

通过java -help查看所有标准参数

#### 非标准参数

##### -X开头的参数

java -X 查看所有以-X开头的参数

- -Xint 禁用JIT，解释执行所有的字节码，运行最慢
- -Xcomp 所有字节码首次使用都被编译成本地代码，然后再执行
- -Xmixed 混合模式，默认模式，让JIT根据程序运行情况，有选择地将代码编译
- -Xms<size> 设置初始堆大小
- -Xmx<size> 设置最大堆大小
- -Xss<size> 设置java线程堆栈大小

##### -XX开头的参数

- -XX:NewSize=1024m 设置新生代地初始大小为1024M
- -XX:MaxGCPauseMills=500 表示设置GC停顿时间500ms
- -XX:GCTimeRatio=19 设置吞吐量
- -XX:NewRatio=2 设置新生代与老年代比例
- -XX:HeapDumpPath= xxx.hprof 只当heap转存文件路径
- -XX:SurvivorRatio=8 设置新生代中 Eden 和 Survivor占比 8：1：1 默认是8
- -XX:+HeapDumpOnOutOfMemoryError 在程序OOM时，导出应用程序的当前堆快照
- -XX:PrintFlagFinal 输出所有参数名称和默认值

##### 常用JVM参数

- -XX:PrintCommandLineFlags 让程序运行前打印出用户手动设置或者JVM自动设置的XX选项

- -XX:PrintFlagsInitial 打印所有参数地默认值

- -XX:PrintFlagFinal 输出运行时所有参数名称和默认值

- -XX:PrintVMOptions 查看所有参数

- -Xmn<size> 设置年轻代初始大小以及最大大小，推荐为堆地3/8

- -XX:MaxNewSize=<size> 设置年轻代地最大值

- -XX:+UseAdaptiveSizePolicy 设置开启自动调整eden 和 survivor分配策略，默认开启

- -XX:PretenureSizeThreadshol=<size> 大于size的对象直接分配在老年代 单位为字节

- -XX:MaxTenuringThreshold=<num> 设置每次MinorGC 还存活的对象年龄加一，最大年龄

- -XX:+PrintTenuringDistribution JVM每次MinorGC之后都打印Survivor中的对象的年龄分布

- -XX:TargetSurvivorRatio 表示MinorGC之后Survivor占用的期望比例

- -XX:PemSize=<size> 设置永久代初始值

- -XX:MaxPerSize = <size> 设置永久代最大值

- -XX:MetaspaceSize 初始元空间大小

- -XX:MaxMetaspaceSize 最大空间，默认没限制

- -XX:+UseCompressedOops 压缩对象指针

- -XX:UseCompressedClassPointers 压缩类指针

- -XX:CompressedClassSpaceSize 设置Klass Metaspace的大小 默认1G

- -XX:MaxDirectMemorySize 指定直接内存容量，若未指定，默认和java堆最大值一样

- -XX:+HeapDumpOnOutOfMemoryError 表示内存出现OOM的时候，把Heap转存Dump到文件以便后续分析

- -XX:+HeapDumpBeforeFullGC 表示在出现FullGC之前生成Heap转储文件

- -XX:HeapDumpPath=<path> 指定heap转储文件路径

- -XX:OnOutOfMemoryError 指定一个可行性程序或脚本路径，发生OOM时执行该脚本

- -XX:ParallelGCThread=N 限制线程数量 默认开启和CPU数据相同的线程数

- -XX:+UseConcMarkSweepGC 指定使用CMS进行GC 会自动ParNew + CMS + Serial Old的组合

- -XX:CMSInitialtingOccupanyFraction  设置堆内存使用率的阈值，一大超过就回收

- -XX:+UseCMSCompactAtFullCollection 执行玩Full GC进行压缩，避免出现碎片，会延长停顿时间

- -XX:CMSFullGCsBeforeCompaction 设置多少Full GCs后要进行内存压缩

- -XX:ParallelCMSThreads 设置CMS线程数量

- -XX:MaxGCPauseMillis 设置期望达到的最大GC停顿时间，JVM并不保证达到

- -XX:ParallelGCThraed 设置STW GC线程数的值，最多8

- -XX:ConcGCThreads 设置并发标记线程数

- -XX:InitialtingHeapOccupancyPercent 设置触发并发GC周期的JAVA堆占用率阈值 默认45

- -XX:+UseG1GC 设置G1垃圾回收器

- -XX:G1HeapRegionSize 设置Region的大小，目标根据最小的java堆划分出2048个区域

- -verbose:gc 输出gc日志到控制台 等同于  -XX:+PrintGC

- -XX:+PrintGCDetails 打印GC时详细信息、进程结束也会打印一次

- -XX:+PrintGCTimeStamps 打印GC时间戳

- -XX:+PrintHeapAtGC 在GC前后时打印Heap

- -Xloggc<file> 输出gc日志到file中

  

### 虚拟机参数添加方式

- Eclipse

- IDEA

- 运行jar包

  ```bash
  java [jvm参数] -jar xxx.jar
  ```

- 通过Tomcat运行war包

  - linux中在tomcat/bin/catalina.sh中添加配置JAVA_OPTS="[JVM参数]"
  - windows在catalina.bat中添加配置set "JAVA_OPTS=[JVM参数]"

- 程序运行过程中

  - jinfo -flag <name> = <value> <pid>上设置非boolean参数
  - jinfo -flag [+|-]<name> <pid> 设置boolean类型参数

### 性能优化三部曲

1. 性能监控 

   非强制或者入侵方式收集、查看应用运营性能数据的活动，多数是预防或主动性活动

2. 性能分析 

   会影响应用的吞吐量或者响应，大多在系统测试或者开发环境进行

3. 性能调优

   更改代码、参数、属性配置，以较少内存获取较高吞吐以及响应时间

#### 性能指标

1. 响应时间

2. 吞吐量 

   -XX:GCTimeRatio = n

3. 并发数

4. 内存占用

5. 相互间关系

### 性能监控及调优工具

#### 虚拟机参数

- XX:+PrintFlagsInitial 显示jvm启动时的参数初始值
- XX:+PrintFlagsFinal 显示jvm参数的最终值
- XX:+PrintCommandLineFlags 查看被用户或者jvm设置过的详细的jvm参数
- -XX:+HeapDumpOnOutOfMemoryError 

#### 简单命令

- jps：查看正在运行的java进程

  - -q  只显示id 不显示主类的名称
  - -l   输出主类的全类名\jar包的完整路径
  - -m 输出虚拟机进程启动传递给主类main()的参数
  - -v  列出虚拟机启动时的JVM参数
  - hostid 查看服务器上的java进程 配合jstat使用

- jstat：查看JVM统计信息 主要用于监测垃圾回收和内存泄露问题

  ```bash
   jstat -<option> [-t] [-h<lines>] <vmid> [<interval> [<count>]]
  ```

  **option**

  | 命令             | 作用                                                         |
  | :--------------- | ------------------------------------------------------------ |
  | class            | 显示ClassLoader的相关信息，类的装载、卸载数量、总空间、类装载所消耗的时间 |
  | gc               | 显示GC相关的堆信息，老年代、新生代、元空间                   |
  | gccapacity       | 与gc相似，但主要关注java堆各个区域使用到的最大最小空间       |
  | gcutil           | 与gc相似，输出主要关注已使用空间占总空间的百分比             |
  | gccause          | 与gcutil类似，额外输出导致最后一次发生GC产生的原因           |
  | gcnew            | 显示新生代状况                                               |
  | gcnewcapaciy     | ~                                                            |
  | gcodl            | ~                                                            |
  | compiler         | 显示jit编译器编译过的方法、耗时等资源                        |
  | printcompilation | 输出已经被jit编译的方法                                      |

  **-t** 

  打印上次监控输出到本次监控输出想间隔时间

  **-h**

  隔多少行打印一次表头

  **internel** 

  用于输出统计数据的周期，单位为毫秒

  **count**

  用于指定查询的次数

- jinfo：查看\调整虚拟机的配置参数

  以下参数参数同样可以直接实现

  - 

  直接通过jinfo命令查看可选参数

- jmap：导出内存镜像文件&内存使用情况,获取dump文件（对转存快照文件，二进制文件）

  - -head 输出整个堆空间的详细信息
  - -histo[:live] 输出堆中对象的统计信息，包括类、实力数量、合计容量,(:live只显示存活对象)
  - -dump[:live],format=b,file=filename.hprof pid 生成堆[存活对象]转储快照

  **小结：**为防止对象不被应用线程干扰，jmap借助安全点机制来暂停线程，可能导致对快照中的分析结果存在偏差。而jstat会让垃圾回收期自动记录，jstat只需要读即可。

- jhat：启动jhat就会启动http服务，可以在浏览器里分析jmap生成的堆快照文件。

- jstack：生成指定进程当前时刻的进程快照，追踪堆栈集合。

- jcmd：实现除了jstat之外的所有命令，可以导出堆、内存使用，查看java进程、导出线程信息、执行GC、JVM运行时间等

  - jcmd pid help显示所有可以执行的指令
  - 命令行形式进行监控：方法级别的分析数据无法获取

#### 深堆&浅堆

- 浅堆：一个对象所消耗的内存，只包含引用，不包含具体（不包含所引用的对象的浅堆大小）
- 保留级：只被A对象持有，A回收时一起被释放的对象集合（包括A）
- 深堆：对象保留集中所有对象的浅堆大小之和，即可以回收的所有内存，递归的回收的所有内容大小
- 对象的实际大小：一个对象所能触及的所有对象的浅堆大小之和

### 内存泄漏&内存溢出

#### 内存泄漏

JVM误以为对象还在使用，无法回收，造成内存泄漏   ； 即还被使用 但是不被需要

#### 内存溢出

对象不再使用，GC不能回收即内存泄漏。

#### 区别

是否涉及GC，溢出是指内存泄漏导致了内存不够，必须GC却不能GC。

### 内存泄漏的情况

- 静态集合类	

  容器如果是静态的，生命周期同JVM一致，这个时候如果含某个对象的引用，就会导致对象无法被释放

- 单例模式

  静态类型同上述一致

- 内部类

  内部类被引用时，外部类就算不引用了，仍旧不能释放

- 各种连接未关闭

- 变量不合理的作用域

  尽量把创建对象的作用域尽量的小

- 改变哈希值

  对象存进hashset之后，修改了需要参与哈希值计算的字段，那么当前对象无法被删除

- 缓存泄露

  把对象引入缓存，被遗忘。可以利用弱引用WeakReference去替换强引用。

- 监听器和回调

  客户端在你实现的API中注册回调，没显示取消，就会积聚，需要确保回调被立即当作垃圾回收（用弱引用）

### OSL语言

```bash
select * from 类全名
select Object v.elementData from ***.ArrayList
select as retained set * form 类全名
select * from 引用地址

select * from char[] s where s.@length > 10
select * from java.lang.String s where s.value != null
select toString(f.path.value) from java.io.File f
```

### 	工具

#### Jprofile

**数据采集方式**

- Sampling样本采集

  每隔一定时间抽取堆栈的信心进行分析

  **优点**:对CPU的影响很小

  **缺点**：功能没重构强大

- Instrumentation重构模式

  全功能模式，在class加载之前把Jprofile相关功能代码写入到需要分析的class的字节码中，对运行的JVM有一定影响。

  **优点**：监控准确。

  **缺点**：若需要分析的class较多，会较大的影响性能


#### 	Arthas

优点： 可视化的JVM监控器都不适用于linux环境，Arthas适用于linux环境

启动：java -jar arthas-root.jar [pid]

**命令**: https://arthas.gitee.io/doc/commands.html

- dashboard 
- thread
- quit   退出客户端
- stop\shotdown 关闭arthas服务端以及客户端
- web console 通过http://127.0.0.1:8563/进行访问
- cat ~/logs/arthas/arthas.log 查看日志
- java -jar arthas-boot.jar -h   查看帮助
- jvm
- **monitor** 方法执行监控
- watch 方法执行的观测（返回值、参数）
- trace 方法内部调用路径，输出方法路径上的每个节点上耗时
- stack 输出当前方法被调用的调用路径
- tt 方法执行数据的时空隧道，记录下指定方法每次调用的入参和返回信息，对这些在不同时间调用进行观测
- jprofile 启动jprofile

#### 	JMS

对性能影响很小，非嵌入，可以开着做压力测试

##### 需要参数

- -XX:+UnlockCommercialFeatures
- -XX:+FlightRecorder

##### 事件类型

- 瞬时事件（Instant Event),用户关心发生与否，异常、线程启动事件
- 持续时间（Duration Event），用户关心的是他们的持续时间，例如垃圾回收事件
- 计时事件（Timed Event），用户关心的是他们的持续时间，如垃圾回收事件
- 取样事件（Simple Event),是周期性取样的事件

##### 启动方式

- 图形化结合JVM参数
  - -XX:StartFlightRecording=delay=5,duration=20s,filename=xxx.jfr,settings=profile MyApp
  - java -XX:StartFlightRecording=maxage=10m,maxsize=100m,name=Somlabel MyApp （不对JFR进行限制，会导致JFR很快的填满硬盘空间。）超过10分钟或者100m就不再手机
- 使用jcmd
- 使用插件

#### 火焰图

追求极致性能情况下，可以了解cpu在干什么

#### 	Visual  VM 

eclipse 插件

#### TProfiler

展示某段时间内的top method

#### Btrace

动态追踪

## GC日志

### GC类型

#### Minor GC

回收新生代

####  Major GC

回收老年代，会先触发Minor GC

#### Full GC

回收堆和方法区

老年代空间不足、方法区空间不足、显示调用System.gc()

#### Mixed GC 

收集整个新生代和部分老年代 仅G1支持

### GC日志

- minor gc : [区域 回收前大小,回收后大小（总大小）] 回收前大小,回收后大小（总大小）) ]

- full gc :  [区域 回收前大小,回收后大小（总大小）] [区域  回收前大小,回收后大小（总大小）) ] [区域  回收前大小,回收后大小（总大小）) ] 回收前大小,回收后大小（总大小）) ]

- <u>若是新生代总大小指的是Eden + 一个survivor</u>

- 时间

  - user：执行用户态代码所用时间，即执行此进程所使用的实际CPU时间，不包括其他进程或者此进程阻塞的时间，在GC时指GC线程的占用的CPU时间	

  - sys：执行内核态所消耗的CPU时间，即在内核执行系统调用或等待系统时间所使用的CPU时间

  - rea：程序从开始到结束所用的时间

### GC日志分析工具

- GCeasy

  https://gceasy.io/

- GCViewer

- GChisto

- HPjmeter
