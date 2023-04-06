《Redis设计与实现》

# 内部数据结构

Redis不仅支持K-V，还支持列表、哈希、有序集合、集合。

## 简单动态字符串 (SDS)

### sds的用途

1. 实现字符串对象
2. Redis中作为char*类型的替代品

Redis的字符串可以保存字符串值也可以其他的值，只有value保存字符串的值的时候，value才是sds值

> 在Redis中，客户端传入服务器的协议内容、aof缓存、返回给客户端的恢复这些重要内容都是sds保存。
> 能动态扩展、性能高、资源利用率高、安全性高（防止越界）

### Redis中的字符串

c语言中的char数组，不缓存长度、不能追加
- strlen(s) O(n)
- N次追加，N次realloc
```c++
typedef char *sds;

struct sdshdr{
    // buf 已占用长度
    int len;
    
    // buf 剩余长度
    int free;
    
    // 实际保存字符串数据的地方
    char buf[];
}
```

```c++
// 示例
struct sdshdr {
    len = 11;
    free = 0;
    buf = "hello world\0"; // buf 的实际长度为 len + 1
};

```

### 优化追加操作

SET方法会赋予刚好字符串的大小，APPEND会追加之后，把free字段变成len一样大小（即进行一倍扩展）
,只要APPEND不超过现在的free就不会再扩容。

> 执行过APPEND后的预分配空间不会释放，除非字符串对应的键被删除或者关闭Redis后重新载入字符串都西昂。
> 如果频繁大体积字符串APPEND,那么就需要定时让服务器进行内存释放

## 双端链表

### 双端链表的应用 

对列表执行RPUSH、LPOP、LLEN就是用双端链表，

```redis
RPUSH brans Apple Microsoft Google

LPOP brands

LLEN brands

LRANGE brands 0 -1
```

> Redis列表使用两种数据接口作为底层实现
> 1. 双端链表
> 2. 压缩列表
> 后者使用内存更少，默认后者；有需要的是偶才会转换成双端链表

#### Redis自身功能的构建 

- 事务模块使用双端链表来按顺序保存输入的命令
- 服务器模块使用双端链表来保存多个客户端
- 发布订阅模块使用双端链表来保存订阅模式的多个客户端
- 事件模块使用双端链表来保存时间事件

### 双端链表的实现

<img src="./images/1680797657441.jpg">

listNode是双端链表的节点
```c++
typedef struct listNode {
    // 前驱节点
    struct listNode *prev;
    // 后继节点
    struct listNode *next;
    // 值
    void *value;
} listNode;
```

list是双端链表本身
```c++
typedef struct list {
    // 表头指针
    listNode *head;
    // 表尾指针
    listNode *tail;
    // 节点数量
    unsigned long len;
    // 复制函数
    void *(*dup)(void *ptr);
    // 释放函数
    void (*free)(void *ptr);
    // 比对函数
    int (*match)(void *ptr, void *key);
} list;

```

行为和性能特征：
- listNode 带有前驱后继，能够双向操作
- 保留了头尾指针，O(1)实现头插、尾插，高校实现LPUSH RPOP RPOP LPUSH
- list带有len，所以LLEN函数也是O(1)

### 迭代器

为双端队列进行双向迭代

```c++
typedef struct listIter {
    // 下一节点
    listNode *next;
    // 迭代方向
    int direction;
} listIter;
```

## 字典（映射、关联数组）

K-V键值对

### 字典的应用

1. 是新数据库键空间
2. 用作hash的一种底层实现

#### 实现数据库键空间

Redis是一个键值对数据库，每个数据库都有一个与之对应的字典，该字典成为键空间

当用户添加键值对的时候，程序就把该键值添加到键空间；删除时，就会把键值从键空间删除。

FLUSHDB 清空键空间所有键值对数据

DBSIZE 返回键空间所有键值对

所有的操作都是在键空间上进行的

#### hash类型键的底层实现

Hash类型的底层实现
- 字典
- 压缩列表

后者更省空间，默认是后者；如有需要才会转成前者。

Hash键操作，底层大概率为hash表
```shell
HSET book name "The design and implementatios of Redis"

HSET book type "source code analysis"

HSET book release-date "2013.3.8"

HGETALL book
```

### 字典的实现

- 数组或链表，适用于元素不多
- hash表，高效且简单（Redis的字典底层实现）
- 复杂的平衡树，高效且稳定，且能排序






