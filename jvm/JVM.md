**自动方式**

-XX:+HeapDumpOnOutOfMemoryError 在程序OOM时，导出应用程序的当前堆快照

-XX:HeapDumpPath: 指定堆快照保存位置

jmap

小结：为防止对象不被应用线程干扰，jmap借助安全点机制来暂停线程，可能导致对快照中的分析结果存在偏差。而jstat会让垃圾回收期自动记录，jstat只需要读即可。

jhat：

启动jhat就会启动http服务，可以在浏览器里分析jmap生成的堆快照文件。

jstack

生成指定进程当前时刻的进程快照，追踪堆栈集合。

jcmd

实现除了jstat之外的所有命令，可以导出堆、内存使用，查看java进程、导出线程信息、执行GC、JVM运行时间等

jcmd pid help显示所有可以执行的指令

命令行形式进行监控：方法级别的分析数据无法获取



