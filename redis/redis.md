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

1. Redis的键空间（存储所有的KV数据）
2. 哈希键的一种底层实现

#### 实现Redis键空间

Redis是一个键值对数据库，通过字典来存储所有键值对K-V（V是各种类型的value数据）

当用户添加键值对的时候，程序就把该键值对添加到Redis中；删除时，就会把键值对从Redis中删除。

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

```c++
typedef struct dict {
    // 特定于类型的处理函数
    dictType *type;
    // 类型处理函数的私有数据
    void *privdata;
    // 哈希表（2 个）
    dictht ht[2];
    // 记录 rehash 进度的标志，值为-1 表示 rehash 未进行
    int rehashidx;
    // 当前正在运作的安全迭代器数量
    int iterators;
} dict;
```

ht[0] 字典主要使用的哈希表， ht[1]对0号哈希表进行rehash时使用

dictht实现如下
```c++
typedef struct dictht {
    // 哈希表节点指针数组（俗称桶，bucket）
    dictEntry **table;
    // 指针数组的大小
    unsigned long size;
    // 指针数组的长度掩码，用于计算索引值
    unsigned long sizemask;
    // 哈希表现有的节点数量
    unsigned long used;
} dictht;
```

entry实现如下:
```c++
typedef struct dictEntry {
    // 键
    void *key;
    // 值
    union {
    void *val;
    uint64_t u64;
    int64_t s64;
    } v;
    // 链往后继节点
    struct dictEntry *next;
} dictEntry;
```

跟HashMap一样，table是一个数组，都指向链表的头节点，每个节点都是entry，冲突越多链表越长

<img src="./images/1680847379638.jpg">

#### 哈希算法

1. MurmurHash2 32bit算法：分布律和速度都非常好
2. 基于djb算法实现大小写无关的散列算法

命令表以及Lua脚本缓存都用到了算法2；Redis数据库、集群、哈希键、阻塞都用到了算法1

### 添加键值对到字典

- 如果字典的0号哈希table为空，就需要对0号hash进行初始化
- 插入时发生键碰撞，要处理碰撞
- 插入时字典满足rehash，需要启动rehash

<img src="./images/1680848903740.jpg">

### 添加元素到空白字典

初始ht[0]的table，默认大小是4

<img src="./images/1680849083335.jpg">

### 添加新键值对发生碰撞处理

通过链地址法来解决冲突

<img src="./images/1680851298699.jpg">

### 添加新键值对发生了rehash操作

哈希表的性能依赖于size（Hash表大小）和used（节点数量）的比率
- 1：1 性能最好
- 节点数量比哈希表大很多（很多碰撞），那么会退化成多个链表，性能差

为了让字典键值对不断增多的情况下保持良好性能，对ht[0]进行rehash：
不修改任何键值对，对哈希表扩容，尽量比率维持在1：1

每次添加之前，都会检查ratio = used / size
1. 自然rehash ： ratio >= 1 且dict_can_resize为真
2. 强制rehash ： ratio > dict_force_resize_ratio 

> dict_can_resize 为 false
> 1. Redis使用子进程对Redis数据库执行后台持久化任务（BGSAVE 、 BGREWRITEAOF），为了最大化copy on write禁止，会暂时设置dict_can_resize为假，避免执行自然rehash。
> 2. 当然如果满足强制rehash，那么不管怎么样都会被rehash

### Rehash执行过程

1. 创建一个ht[0] table 更大的ht[1] table
2. ht[0] table迁移到ht[1] table中
3. 将原有ht[0] 清空，并用ht[1]替换ht[0]


具体过程如下

#### 1. 开始rehash

字典的rehashidx为0，表示开始rehash；创建两倍used的ht[1]->table

<img src="./images/1680852495283.jpg">

#### 2. rehash进行中

迁移ht[0]->table 到 ht[1]->table，多次进行，通过rehashidx进路ht[0]->table中已经完成迁移的entry下标

<img src="./images/1680852612270.jpg">

#### 3. 节点迁移完毕

到了这个阶段，所有的节点都已经从 ht[0] 迁移到 ht[1] 了

<img src="./images/1680852705557.jpg">

#### 4. Rehash完毕

释放ht[0];用ht[1]代替ht[0];创建新的ht[1];rehashidx=-1,表示rehash结束

<img src="./images/1680852740107.jpg">

### 渐进式rehash

rehash在激活之后，分批次、渐进式完成（将rehash分散到多个步骤，避免集中计算），否则插入的键值对触发rehash，等待rehash结束再返回
就响应性太差了。同时阻塞服务器也不是好的行为

主要由 _dictRehashStep 和 dictRehashMilliseconds 两个函数进行
- _dictRehashStep

Redis数据库字典、哈希键字典被动rehash

- dictRehashMilliseconds

Redis服务器常规任务程序执行，用于对Redis数据库字典主动rehash

#### _dictRehashStep

每次执行 _dictRehashStep ，ht[0]->table 哈希表第一个不为空的索引上的所有节点就会全
部迁移到 ht[1]->table。rehash 开始进行之后（d->rehashidx 不为 -1），每次执行一次添加、查找、删除操作，
_dictRehashStep 都会被执行一次：

#### dictRehashMilliseconds

dictRehashMilliseconds 可以在指定的毫秒数内，对字典进行 rehash。
Redis 的服务器常规任务执行时,通过该函数尽可能rehash，加速rehash进程

#### 其他措施

rehash 过程中直接往ht[1]添加，同时查找删除在ht[0]、ht[1]上都能进行，确保rehash顺利开展

### 字典收缩

当可用 远大于 已用  就会rehash来收缩（shrink）字典，和扩展的rehash过程一样。

1. 创建一个比 ht[0]->table 小的 ht[1]->table ；
2. 将 ht[0]->table 中的所有键值对迁移到 ht[1]->table ；
3. 将原有 ht[0] 的数据清空，并将 ht[1] 替换为新的 ht[0] ；

收缩规则：被初始化过了且字典使用率小于系统允许的最小比率，默认10%

```c++
int htNeedsResize(dict *dict) {
    long long size, used;
    // 哈希表已用节点数量
    size = dictSlots(dict);
    // 哈希表大小
    used = dictSize(dict);
    // 当哈希表的大小大于 DICT_HT_INITIAL_SIZE
    // 并且字典的填充率低于 REDIS_HT_MINFILL 时
    // 返回 1
    return (size && used && size > DICT_HT_INITIAL_SIZE &&
    (used*100/size < REDIS_HT_MINFILL));
}
```

收缩是手动执行，扩展是自动触发。

- 当字典用于实现哈希键的时候，每次从字典中删除一个键值对，程序就会执行一次
htNeedsResize 函数，如果字典达到了收缩的标准，程序将立即对字典进行收缩；
- 当字典用于实现Redis数据库所有K-V键值对存储的时候，收缩的时机由
redis.c/tryResizeHashTables 函数决定，

### 字典的迭代

- 迭代器首先迭代字典的第一个哈希表，然后，如果 rehash 正在进行的话，就继续对第二
个哈希表进行迭代。
- 当迭代哈希表时，找到第一个不为空的索引，然后迭代这个索引上的所有节点。
- 当这个索引迭代完了，继续查找下一个不为空的索引，如此循环，一直到整个哈希表都迭
代完为止。

两种类型迭代器：
- 安全迭代器：在迭代进行过程中，可以对字典进行修改。
- 不安全迭代器：在迭代进行过程中，不对字典进行修改。

```c++
typedef struct dictIterator {
    dict *d; // 正在迭代的字典
    int table, // 正在迭代的哈希表的号码（0 或者 1）
    index, // 正在迭代的哈希表数组的索引
    safe; // 是否安全？
    dictEntry *entry, // 当前哈希节点
    *nextEntry; // 当前哈希节点的后继节点
} dictIterator;
```

## 跳表

一种随机化数据，层次化的链表中保存元素，效率可以和平衡树媲美。

<img src="./images/1680855186386.jpg">

Redis中的改版跳跃表

1. 允许重复的 score 值：多个不同的 member 的 score 值可以相同。
2. 进行对比操作时，不仅要检查 score 值，还要检查 member ：当 score 值可以重复时，
   单靠 score 值无法判断一个元素的身份，所以需要连 member 域都一并检查才行。
3. 每个节点都带有一个高度为 1 层的后退指针，用于从表尾方向向表头方向迭代：当执行
   ZREVRANGE 或 ZREVRANGEBYSCORE 这类以逆序处理有序集的命令时，就会用到
   这个属性。

修改版跳跃表：
```c++
typedef struct zskiplist {
    // 头节点，尾节点
    struct zskiplistNode *header, *tail;
    // 节点数量
    unsigned long length;
    // 目前表内节点的最大层数
    int level;
} zskiplist;
```

节点：
```c++
typedef struct zskiplistNode {
    // member 对象
    robj *obj;
    // 分值
    double score;
    // 后退指针
    struct zskiplistNode *backward;
    // 层
    struct zskiplistLevel {
    // 前进指针
    struct zskiplistNode *forward;
    // 这个层跨越的节点数量
    unsigned int span;
    } level[];
} zskiplistNode;
```

### 应用

实现有序集数据类型

跳跃表指向有序集合的score和member，以score为索引，对元素排序

```shell
ZADD s 6 x 10 y 15 z

ZRANGE s 0 -1 WITHSCORES
```

<img src="./images/1680856099834.jpg">

跳跃表要和另一个实现有序集的结构（字典）分享 member 和 score 值，所以跳跃
表只保存指向 member 和 score 的指针,所以实际上member和score不是在一个节点进行存储的

# 内存映射数据结构

为了解决Redis创建内部数据结构导致内存耗费的问题，Redis在条件允许的情况下，使用内存映射数据结构来代替内部数据结构

即通过特殊编码的字节序列来代替数据结构，减少内存开销，当然这一过程CPU会占用更多。

## 整数集合

intset用于有序、无重复复保存整数值，其整数类型通过该集合中的元素的最长元素来决定。

如果在一个 intset 里面，最长的元素可以用 int16_t 类型来保存，那么这个 intset
的所有元素都以 int16_t 类型来保存。

如果有一个新元素要加入到这个 intset ，并且这个元素不能用 int16_t 类型来保存
——比如说，新元素的长度为 int32_t ，那么这个 intset 就会自动进行“升级” ：先将集合中现
有的所有元素从 int16_t 类型转换为 int32_t 类型，接着再将新元素加入到集合中。

### 整数集合的应用

集合键的底层实现之一：只保存整数，元素数量不多

### 数据结构和主要操作

```c++
typedef struct intset {
    // 保存元素所使用的类型的长度
    uint32_t encoding;
    // 元素个数
    uint32_t length;
    // 保存元素的数组
    int8_t contents[];
} intset
```

contents数组读取和写入是要进行类型转换和指针计算来获取元素在内存中的正确位置。
分配的容量由encoding决定

#### 添加元素

<img src="./images/1680857323449.jpg">

### 升级

<img src="./images/1680859512380.jpg">

intsetUpgradeAndAdd过程如下：
1. 对新元素进行检测，看保存这个新元素需要什么类型的编码；
2. 将集合 encoding 属性的值设置为新编码类型，并根据新编码类型，对整个 contents 数
   组进行内存重分配。
3. 调整 contents 数组内原有元素在内存中的排列方式，让它们从旧编码调整为新编码。
<img src="./images/1680859800183.jpg">
4. 将新元素添加到集合中。

## 压缩列表

Ziplist由一系列特殊编码的内存块构成的列表，包含多个节点，每个节点可以保存一个长度受限的字符数组或者整数。

节约内存，被哈希键、列表键、有序集合键作为底层实现使用

### ziplist的构成

ziplist header 部分的长度总是固定的（4 字节 + 4 字节 + 2 字节），因此将指针移动到表
头节点的复杂度为常数时间；除此之外，因为表尾节点的地址可以通过 zltail 计算得出，因
此将指针移动到表尾节点的复杂度也为常数时间。

<img src="./images/1680866355866.jpg">

<img src="./images/1680867654515.jpg">

zltail 记录到尾部节点的起始地址的长度

### 节点构成

<img src="./images/1680866750896.jpg">

#### pre_entry_length

pre_entry_length 记录了前一个节点的长度，通过这个值，可以进行指针计算，从而跳转到
上一个节点

#### encoding 和 length

两者一起决定了content部分所保存的数据的类型及长度

encoding的长度为两个bit:
- 00 、01 和 10 表示 content 部分保存着字符数组。
- 11 表示 content 部分保存着整数。

#### content

保存着节点内容：

<img src="./images/1680867303567.png">

### 添加节点到末端

1. 记录到达 ziplist 末端所需的偏移量（因为之后的内存重分配可能会改变 ziplist 的地址，
因此记录偏移量而不是保存指针）
2. 根据新节点要保存的值，计算出编码这个值所需的空间大小，以及编码它前一个节点的
   长度所需的空间大小，然后对 ziplist 进行内存重分配。
3. 设置新节点的各项属性：pre_entry_length 、encoding 、length 和 content 。
4. 更新 ziplist 的各项属性，比如记录空间占用的 zlbytes ，到达表尾节点的偏移量 zltail
   ，以及记录节点数量的 zllen 。

### 添加中间节点

先分配内存，插入ziplist，之后填入数据，这时候就要改变前驱后继节点的信息，最后更新ziplist各项属性。

后继节点更新的时候：
1. next 的 pre_entry_length 域的长度正好能够编码 new 的长度（都是 1 字节或者都是 5
   字节）
2. next 的 pre_entry_length 只有 1 字节长，但编码 new 的长度需要 5 字节
3. next 的 pre_entry_length 有 5 字节长，但编码 new 的长度只需要 1 字节

1、3直接更新，2需要对后继节点逐个检查，扩容，直到遇到一个能不需要扩容得节点。（连锁更新）

### 删除节点
1. 定位节点，节点左移覆盖被删除得节点内存，收缩多余空间。
2. 检查更新后继节点，和添加一样，可能会引起连锁更新。

# Redis数据类型

## 对象处理机制

键类型不同，能执行的命令也各不相同。
Redis必须让每个键带有类型信息，以选择合适的处理方式。即底层会根据不同的编码encoding进行不同操作，
但是用户只需要进行某指令而不需要关注底层的实现，即屏蔽底层实现。

为了解决以上问题，Redis 构建了自己的类型系统，这个系统的主要功能包括：
- redisObject 对象。
- 基于 redisObject 对象的类型检查。 
- 基于 redisObject 对象的显式多态函数。
- 对 redisObject 进行分配、共享和销毁的机制。

### redisObject数据结构、以及Redis的数据类型

redisObject是Redis本身处理的参数，Redis数据库中的键、值也都是这个数据类型

```c++
/*
* Redis 对象
*/
typedef struct redisObject {
   // 类型
   unsigned type:4;
   // 对齐位
   unsigned notused:2;
   // 编码方式
   unsigned encoding:4;
   // LRU 时间（相对于 server.lruclock）
   unsigned lru:22;
   // 引用计数
   int refcount;
   // 指向对象的值
   void *ptr;
} robj;
```
下面三个属性最为重要

- type：值的类型

```c++
#define REDIS_STRING 0 // 字符串
#define REDIS_LIST 1 // 列表
#define REDIS_SET 2 // 集合
#define REDIS_ZSET 3 // 有序集
#define REDIS_HASH 4 // 哈希表
```

- encoding：对象所保存的值的编码
```c++
#define REDIS_ENCODING_RAW 0 // 编码为字符串
#define REDIS_ENCODING_INT 1 // 编码为整数
#define REDIS_ENCODING_HT 2 // 编码为哈希表
#define REDIS_ENCODING_ZIPMAP 3 // 编码为 zipmap
#define REDIS_ENCODING_LINKEDLIST 4 // 编码为双端链表
#define REDIS_ENCODING_ZIPLIST 5 // 编码为压缩列表
#define REDIS_ENCODING_INTSET 6 // 编码为整数集合
#define REDIS_ENCODING_SKIPLIST 7 // 编码为跳跃表
```
- ptr：指向实际保存值的指针，由type和encoding决定

如 果 一 个 redisObject 的 type 属 性 为 REDIS_LIST ， encoding 属 性 为
REDIS_ENCODING_LINKEDLIST ，那么这个对象就是一个 Redis 列表，它的值保存在一个双
端链表内，而 ptr 指针就指向这个双端链表；

redisObject、Redis所有数据类型、Redis底层实现三者关系如下：
<img src="./images/1680971175708.jpg">

### 命令的类型检查和多态

1. 根据给定 key ，在数据库字典中查找和它对应的 redisObject ，如果没找到，就返回
   NULL 。
2. 检查 redisObject 的 type 属性和执行命令所需的类型是否相符，如果不相符，返回类
   型错误。
3. 根据 redisObject 的 encoding 属性所指定的编码，选择合适的操作函数来处理底层的
   数据结构。
4. 返回数据结构的操作结果作为命令的返回值。

### 对象共享

Flyweight模式：预分配一些常见对象，在多个数据结构之间共享，避免重复分配的麻烦，节约了CPU时间

- 各种命令的返回值，比如执行成功时返回的 OK ，执行错误时返回的 ERROR ，类型错误时
返回的 WRONGTYPE ，命令入队事务时返回的 QUEUED 等等。
- 包括0在内，小于redis.h/REDIS_SHARED_INTEGERS的所有整数
（REDIS_SHARED_INTEGERS 的默认值为 10000）

<img src="./images/1680972145146.jpg">

> 共享对象只能被带指针的数据结构使用：字典、双端链表；
> 整数集合、压缩列表就不适用

### 引用计数以及对象销毁

创建的时候1，共享就+1，释放就-1，最后refcount=0就销毁整个redisObject

## 字符串 String

除了GET、SET等命令的操作对象外，Redis中的所有Key以及执行命令时提供的参数，都是字符串类型的

### 字符串编码

- REDIS_ENCODING_INT使用long来保存long类型值
- REDIS_ENCODING_RAW用 sdshdr 保存sds、long long、double、long double类型值

### 编码的选择

字符串创建之后默认使用后者，但将保存到数据库的时候，会将字符串转化为前者。

## 哈希表 HASH

HSET、HLEN等命令的操作对象，使用REDIs_ENCODING_ZIPLIST和REDIS_ENCODING_HT两种编码方式

### 字典编码的哈希表

哈希表的key为字典的key，哈希表的值是字典的值；键、值都为字符串对象

<img src="./images/1681103273264.jpg">

### 压缩列表编码的哈希表

将键值一起压入压缩列表，新添加的键值对会被添加到压缩列表表尾，查找、删除也是先定位key，再根据key定位值的位置。

<img src="./images/1681103601435.jpg">

### 编码选择

默认是压缩表，若满足下列之一就会转换成REDIS_ENCODING_HT
- 哈希表中的某个key或者value长度大于server.hash_max_ziplist_value（默认64）
- 压缩列表中的节点数大于server.hash_max_ziplist_entries(默认512)

## 列表 LIST

REDIS_LIST是 LPUSH、LRANGE等命令的操作对象，使用ZIPLIST或者LINKEDLIST进行实现

### 编码选择

默认ZIPLIST，若满足下列之一就会转换成LINKEDLIST
- 向列表添加字符串值，且字符串长度超过server.list_max_ziplist_value(默认64)
- ziplist包含的节点超过server.list_max_ziplist_entries(默认512)

### 阻塞的条件

BLPOP、BRPOP、BRPOPLPUSH阻塞客户端，统称为阻塞原语。

阻塞条件： 这些命令用于空列表

### 阻塞

阻塞的步骤
1. 将客户端的状态设为“正在阻塞” ，并记录阻塞这个客户端的各个键，以及阻塞的最长时限
   （timeout）等数据。
2. 将客户端的信息记录到 server.db[i]->blocking_keys 中（其中 i 为客户端所使用的数
   据库号码）。
3. 继续维持客户端和服务器之间的网络连接，但不再向客户端传送任何信息，造成客户端
   阻塞。

server.db[i]->blocking_keys 是一个字典，字典的键是那 些造成客户端阻塞的键，而字典的值是一个链表，链表里保存了所有因为这个键而被阻塞的客
户端（被同一个键所阻塞的客户端可能不止一个）：

<img src="./images/1681104854068.jpg">

脱离阻塞的方法：
1. 被动脱离：有其他客户端为造成阻塞的键推入了新元素。
2. 主动脱离：到达执行阻塞原语时设定的最大阻塞时间。
3. 强制脱离：客户端强制终止和服务器的连接，或者服务器停机。

### 被动脱离:lpush\rpush\linsert

<img src="./images/1681105201900.jpg">

当向一个空键推入新元素时，pushGenericCommand 函数执行以下两件事：
1. 将 readyList 添加到服务器。

```c++
typedef struct readyList {
   redisDb *db;
   robj *key;
} readyList;
```

2. 将新元素 value 添加到键 key3 。

Redis 的主进程在执行完 pushGenericCommand 函数之后，会继续调用 handleClientsBlockedOnLists 函数 来接触某一个客户端的阻塞

1. 如果 server.ready_keys 不为空，那么弹出该链表的表头元素，并取出表头元素中的
   readyList 值。
2. 根据 readyList 值所保存的 key 和 db ，在 server.blocking_keys 中查找所有因为 key
   而被阻塞的客户端（以链表的形式保存）。
3. 根据 readyList 值所保存的 key 和 db ，在 server.blocking_keys 中查找所有因为 key
   而被阻塞的客户端（以链表的形式保存）。
4. 删除 server.blocking_keys 中相应的客户端数据，取消客户端的阻塞状态。
5. 继续执行步骤 3 和 4 ，直到 key 没有元素可弹出，或者所有因为 key 而阻塞的客户端都 取消阻塞为止。
6. 继续执行步骤 1 ，直到 ready_keys 链表里的所有 readyList 结构都被处理完为止。

### FBFS先阻塞先服务策略

添加到blocking_keys的时候，默认添加在最后，而进行取消阻塞是队头开始取消阻塞，所以是一个FIFO链表，Redis中成为FBFS。

### 主动取消：超过最大等待时间被取消

每次 Redis 服务器常规操作函数（server cron job）执行时，程序都会遍历查询那些处于“正在阻塞”状态的客户端的最大阻塞时限，如果已经过期就会撤销阻塞，给客户端返回一个空白回复。

## 集合 SET

SADD 、 SRANDMEMBER 等 命 令 的 操 作 对 象， 它 使 用 INTSET 和 HT

### 编码的选择

第一个元素long long,就设置初始编码intset，否则设置为字典

### 编码的切换

默认intset，如果满足以下任意一个条件，就会被转换成字典:
- intset 保存的整数值个数超过 server.set_max_intset_entries （默认值为 512 ）。
- 试图往集合里添加一个新元素，并且这个元素不能被表示为 long long 类型。

### 交集 O(n^2)

SINTER、SINTERSTORE 

### 并集 O(n)

SUNION、SUNIONSTORE

### 差集 O(n^2)

SDIFF、SDIFFSTORE

## 有序集 ZSET

是 ZADD 、 ZCOUNT等命令的操作对象， 它使用ZIPLIST 和 SKIPLIST 两种方式编码：

### 编码选择

如果满足下述任一条件就创建压缩列表，否则就是跳跃表。
- 服务器属性 server.zset_max_ziplist_entries 的值大于 0 （默认为 128 ）。
- 元素的 member 长度小于服务器属性 server.zset_max_ziplist_value 的值（默认为 64 ）。

### ZIPLIST

每个有序集元素以两个相邻的 ziplist 节点表示，第一个节点保存元素的 member 域， 第二个元素保存元素的 score 域。
多个元素之间按 score 值从小到大排序，如果两个元素的 score 相同，那么按字典序对 member 进行对比，决定那个元素排在前面，那个元素排在后面。

<img src="./images/1681108349994.jpg">

### SKIPLIST

```c++
typedef struct zset {
   // 字典
   dict *dict;
   // 跳跃表
   zskiplist *zsl;
} zset;
```

<img src="./images/1681108715004.jpg">

过使用字典结构，并将 member 作为键，score 作为值，有序集可以在 O(1) 复杂度内：
- 检查给定 member 是否存在于有序集（被很多底层函数使用）；
- 取出 member 对应的 score 值（实现 ZSCORE 命令）。

通过使用跳跃表，可以让有序集支持以下两种操作：有点InnoDb非聚簇索引的味道
- 在 O(log N) 期望时间、O(N) 最坏时间内根据 score 对 member 进行定位；
- 范围性查找和处理操作是（高效地）实现 ZRANGE 、ZRANK 和 ZINTERSTORE
等命令的关键。

# 功能的实现

## 事务

MULTI、DISCARD、EXEC、WATCH

### 事务

一批命令打包顺序执行,MULTI开启，EXEC执行，三个阶段分别为
- 开始事务
- 命令入队

<img src="./images/1681114979140.jpg">

- 执行事务

```shell
redis> MULTI
OK
redis> SET book-name "Mastering C++ in 21 days"
QUEUED
redis> GET book-name
QUEUED
redis> SADD tag "C++" "Programming" "Mastering Series"
QUEUED
redis> SMEMBERS tag
QUEUED
redis> EXEC
```

1) OK
2) "Mastering C++ in 21 days"
3) (integer) 3
4) "Mastering Series"
   1) "C++"
   2) "Programming"

### 执行事务

EXEC DISCARD MULTI WATCH这四个命令即使处于事务状态，也会直接执行。

### 事务和非事务状态下执行命令

非事务状态下，Redis 命令都由同一个函数执行，所以它们共享很多服务器的一般设置，比如 AOF 的配置、RDB 的配置，以及内存限制

事务中的命令和普通命令在执行上的区别：
1. 非事务状态下的命令以单个命令为单位执行，前一个命令和后一个命令的客户端不一定 是同一个； 
而事务状态则是以一个事务为单位，执行事务队列中的所有命令：除非当前事务执行完毕，否则服务器不会中断事务，也不会执行其他客户端的其他命令。
2. 在非事务状态下，执行命令所得的结果会立即被返回给客户端；
   而事务则是将所有命令的结果集合到回复队列，再作为 EXEC 命令的结果返回给客户端。

### DISCARD

- DISCARD 命令用于取消一个事务，它清空客户端的整个事务队列，然后将客户端从事务状态
调整回非事务状态，最后返回字符串 OK 给客户端，说明事务已被取消。

- Redis 的事务是不可嵌套的，当客户端已经处于事务状态，而客户端又再向服务器发送 MULTI
时，服务器只是简单地向客户端发送一个错误，然后继续等待其他命令的入队。

- WATCH 只能在客户端进入事务状态之前执行，在事务状态下发送 WATCH 命令会引发一个
错误（和前面处理 MULTI 的情况一样）

### 带 WATCH 的事务

如果被WATCH的键被其他客户端并发修改了，那么整个事务不再执行

<img src="./images/1681116298028.jpg">

通过一个 watched_keys字典，键保存Redis需要监视的Key，值则是监视这个Key的客户端。

当键被修改的时候（比如 FLUSHDB 、SET 、DEL 、LPUSH 、SADD 、ZREM ，诸如此类），就会检查这个key是不是在watched_keys字典中，
如果存在就会把所有监视的客户端的 REDIS_DIRTY_CAS 选项打开，之后EXEC的时候，客户端会检查当前状态，如果REDIS_DIRTY_CAS就表示事务不安全，就执行失败。

<img src="./images/1681116727210.jpg">

### ACID

保证了一致性和隔离性

#### 原子性

Redis不回滚或重试，不具备原子性

#### 一致性

入队错误、执行错误、Redis 进程被终结

- 入队错误：在命令入队的过程中，如果客户端向服务器发送了错误的命令，那么服务器将向客户端返回一个出错信息，并且将客户端的事务状态设为
  REDIS_DIRTY_EXEC 。之后EXEC执行就会失败。   意味着不会导致不一致性。

- 执行错误： 在事务执行的过程中发生错误，不会引起事务中断或者整个失败，不会影响已完成事务命令结果，也不会影响后续事务，所以不影响一致性。

- Redis进程被终结：根据持久化情况分类
  - 内存模式：如果 Redis 没有采取任何持久化机制，那么重启之后的数据库总是空白的，所
    以数据总是一致的。
  - RDB：执行事务的时候，不会中断事务去执行RDB工作。也就是事务执行之后，才会执行RDB，所以即使事务执行了一半，也不会影响一致性，以为RDB保存的是最近一次
    没有问题的数据库快照。
  - AOF：事务执行途中，也会有后台线程持续保存。
    - 如果事务执行过程中，还未被写入AOF、或者AOF没有被SYNC保存到磁盘，那么重启之后能恢复到最新的历史版本
    - 如果事务执行过程中，部分的AOF被保存，那么重启需要redis-check-aof来部分成功的事务移除，再次启动之后就能恢复到最新的历史版本。

#### 隔离性

单线程执行，所以Redis事务带有隔离性

#### 持久性

事务只是队列形式的Redis命令，没有提供额外的持久性功能，根据持久化模式决定持久性。
- 单纯的内存模式，事务不持久
- RDB模式下，如果写入前就宕机就不持久
- AOF下主线程持续执行命令，每次执行完毕都会有Sync后台线程进行AOF保存，但是后台线程不会阻塞主线程，也就是说命令保存到AOF可能受前面命令落盘的影响，所以也
不能说是持久的

## 订阅与发布

`PUBLISH` 、 `SUBSCRIBE`

### 订阅频道

每个Redis服务器进程都维持一个服务器状态redis.h/redisServer结构，结构的pubsub_channels 属性是一个字典，这个字典的键为正在被订阅的频道，而字典的值则是一个链表，链表中保存了所有订阅这个频道的客户端。

<img src="./images/1682575003328.jpg">

SUBSCRIBE channel1 channel2 channel3

<img src="./images/1682575070380.jpg">

### 发送消息到频道

其中模式的订阅和信息发送，不仅会通知所有订阅该频道的客户端，还能像所有匹配了该模式的频道的客户端进行发送

<img src="./images/1682575617526.jpg">

<img src="./images/1682575771360.jpg">

### 退订频道

UNSUBSCRIBE

### 订阅模式

pubsub_patterns 属性是一个链表，链表中保存着所有和模式相关的信息，链表中的每个节点都包含一个 redis.h/pubsubPattern 结构：
```c++
struct redisServer {
// ...
list *pubsub_patterns;
// ...
};
```
链表中的每个节点都包含一个 redis.h/pubsubPattern 结构：

```c++
typedef struct pubsubPattern {
redisClient *client;
robj *pattern;
} pubsubPattern;
```
client 属性保存着订阅模式的客户端，而 pattern 属性则保存着被订阅的模式

<img src="./images/1682581649189.jpg">

新添加一个后

<img src="./images/1682581682016.jpg">

### 发送消息到模式

`PUBLISH`

完整的publish如下
```python
def PUBLISH(channel, message):
    # 遍历所有订阅频道 channel 的客户端
    for client in server.pubsub_channels[channel]:
        # 将信息发送给它们
        send_message(client, message)
    # 取出所有模式，以及订阅模式的客户端
    for pattern, client in server.pubsub_patterns:
        # 如果 channel 和模式匹配
        if match(channel, pattern):
            # 那么也将信息发给订阅这个模式的客户端
            send_message(client, message)
```

### 退订模式

`PUNSUBSCRIBE`删除 redisServer.pubsub_patterns中的节点

## Lua脚本

高效地处理 CAS

### 脚本的安全性

LUA脚本复制到附属节点或者将AOF写入AOF文件的时候，可能AOF带有随即性质或者副作用，在附属节点或者AOF文件读出重新运行时，可能得到的结果
与之前不一致。破坏主从节点之间的一致性。

### EVAL

1. 先定义lua函数
2. 执行这个lua函数

### EVAHSHA

执行之前生成过的lua函数，通过sha值直接调用

## 慢查询日志

### 相关数据结构

慢查询日志以一个 slowlog.h/slowlogEntry 结构定义：
```c++
typedef struct slowlogEntry {
    // 命令参数
    robj **argv;
    // 命令参数数量
    int argc;
    // 唯一标识符
    long long id; /* Unique entry identifier. */
    // 执行命令消耗的时间，以纳秒（1 / 1,000,000,000 秒）为单位
    long long duration; /* Time spent by the query, in nanoseconds. */
    // 命令执行时的时间
    time_t time; /* Unix time at which the query was executed. */
} slowlogEntry;
```

服务器状态的 redis.h/redisServer 结构里保存了几个和慢查询有关的属性：
```c++
struct redisServer {
    // ... other fields
    // 保存慢查询日志的链表
    list *slowlog; /* SLOWLOG list of commands */
    // 慢查询日志的当前 id 值
    long long slowlog_entry_id; /* SLOWLOG current entry ID */
    // 慢查询时间限制
    long long slowlog_log_slower_than; /* SLOWLOG time limit (to get logged) */
    // 慢查询日志的最大条目数量
    unsigned long slowlog_max_len; /* SLOWLOG max number of items logged */
    // ... other fields
};
```


## 慢查询日志

慢日志记录
```c++
typedef struct slowlogEntry {
    // 命令参数
    robj **argv;
    // 命令参数数量
    int argc;
    // 唯一标识符
    long long id; /* Unique entry identifier. */
    // 执行命令消耗的时间，以纳秒（1 / 1,000,000,000 秒）为单位
    long long duration; /* Time spent by the query, in nanoseconds. */
    // 命令执行时的时间
    time_t time; /* Unix time at which the query was executed. */
} slowlogEntry;
```

redis.h/redisServer 结构里保存了几个和慢查询有关的属性

```c++
struct redisServer {
    // ... other fields
    // 保存慢查询日志的链表
    list *slowlog; /* SLOWLOG list of commands */
    // 慢查询日志的当前 id 值
    long long slowlog_entry_id; /* SLOWLOG current entry ID */
    // 慢查询时间限制
    long long slowlog_log_slower_than; /* SLOWLOG time limit (to get logged) */
    // 慢查询日志的最大条目数量
    unsigned long slowlog_max_len; /* SLOWLOG max number of items logged */
    // ... other fields
};
```
<img src="./images/1682592860207.jpg">

### 慢查询日志的操作

- 查看 O(n)
- 删除 O(n)
- 获取链表的数量=获取slowlog链表的数量 O(1)

# 内部运作机制

## Redis数据库结构

```c++
typedef struct redisDb {
    // 保存着数据库以整数表示的号码
    int id;
    // 保存着数据库中的所有键值对数据
    // 这个属性也被称为键空间（key space）
    dict *dict;
    // 保存着键的过期信息
    dict *expires;
    // 实现列表阻塞原语，如 BLPOP
    // 在列表类型一章有详细的讨论
    dict *blocking_keys;
    dict *ready_keys;
    // 用于实现 WATCH 命令
    // 在事务章节有详细的讨论
    dict *watched_keys;
} redisDb
```

### 数据库的切换

服务器启动，会直接创建n个数据库，数据库通过select numbers 进行切换，内部程序是通过数组 db[number]进行进行切换的。

AOF程序、复制程序、RDB程序需要知道数据库的id才能进行，否则不知道是哪一个数据库

### 键空间

Redis就是键值对数据库，键空间就相当于表

- 键：字符串
- 值：字符串、列表、哈希表、集合、有序表

如下所示dict就是键空间：
<img src="./images/1682697864589.jpg"/> 

### 键空间的操作

- 更新键的命中率和不命中率，通过`INFO`查看
- 更新键的LRU，通过`OBJECT`查看
- 删除过期键
- 键被修改了的话，那么将键设为脏（用于事务监视），并将服务器设为脏（等待 RDB 保存）；
- 将对键的修改发送到 AOF 文件和附属节点，保持数据库状态的一致；

- FLUSHDB 命令：删除键空间中的所有键值对。
- RANDOMKEY 命令：从键空间中随机返回一个键。
- DBSIZE 命令：返回键空间中键值对的数量。
- EXISTS 命令：检查给定键是否存在于键空间中。
- RENAME 命令：在键空间中，对给定键进行改名。

### 键的过期时间

`EXPIRE` 、`PEXPIRE` 、`EXPIREAT`  `PEXPIREAT`

命令 `TTL` 和 `PTTL` 则用于返回给定键距离过期还有多长时间

键的过期时间都被保存在 redisDb 结构的 expires 字典里：
```c++
typedef struct redisDb {
    // ...
    dict *expires;
    // ...
} redisDb;
```

和dict的键的指向完全一样（公用键），只是value存long long 类型的过期时间

<img src="./images/1682698824193.jpg"/>

### 过期键判定

1. 检查键是否存在于 expires 字典：如果存在，那么取出键的过期时间；
2. 检查当前 UNIX 时间戳是否大于键的过期时间：如果是的话，那么键已经过期；否则，
   键未过期。

### 过期键的清除

1. 定时删除：在设置键的过期时间时，创建一个定时事件，当过期时间到达时，由事件处理
   器自动执行键的删除操作。
   - CPU不友好、内存友好；
   - 目前 Redis 事件处理器对时间事件的实现方式——无序链表，查找一个时间复杂度
     为 O(N) ——并不适合用来处理大量时间事件。
2. 惰性删除：放任键过期不管，但是在每次从 dict 字典中取出键值时，要检查键是否过
   期，如果过期的话，就删除它，并返回空；如果没过期，就返回键值。
   - 对CPU友好、内存不友好
   - log日志某个时间段之后大概率都不会频繁访问，存在于内存中显然不适合
3. 定期删除策略：参见后文

### 过期键的惰性删除策略

所有命令在读取或写入数据库之前，程序都会调用 expireIfNeeded 对输入键进行检查，并将过期键删除

### 过期键定期删除策略

当 Redis 的例行处理程序 serverCron 执行时，activeExpireCycle 都会被调用——这个函数在规定的时间限制内，尽
可能地遍历各个数据库的 expires 字典，随机地检查一部分键的过期时间，并删除其中的过期
键

### 过期键对 AOF 、RDB 和复制的影响

#### 更新后的RDB

新的 RDB 文件，程序会对键进行检查，过期的键不会被写入到更新后的 RDB 文件 中。

#### AOF

在键已经过期，但是还没有被惰性删除或者定期删除之前，这个键不会产生任何影响，AOF 文
件也不会因为这个键而被修改。

当过期键被惰性删除、或者定期删除之后，程序会向 AOF 文件追加一条 DEL 命令，来显式地
记录该键已被删除。

#### AOF重写

和 RDB 文件类似，当进行 AOF 重写时，程序会对键进行检查，过期的键不会被保存到重写
后的 AOF 文件。

#### 复制

- 主节点删除，会通知附属节点DEL
- 附属节点遇到过期节点的时候，不会真正删除。因为程序只根据键是否已经过期、而不是键是否已经被
  删除来决定执行流程，所以这种处理并不影响命令的正确执行结果。当接到从主节点发来
  的 DEL 命令之后，附属节点才会真正的将过期键删除掉。

可能浪费附属节点空间，但是过期键通常很快会被主节点发现并删除，所以这实际上也算
不上什么大问题。

### 数据库空间的收缩和扩展

Redis本来就是键值对空间，其收缩和扩展符合字典的扩展和收缩，规则一样。

## RDB

就是当前内存Redis中存储的数据快照，只是落了盘。

主要是`SAVE` 和 `BGSAVE`两个命令

- SAVE 直接调用 rdbSave ，阻塞 Redis 主进程，直到保存完成为止。在主进程阻塞期间，
服务器不能处理客户端的任何请求。
- BGSAVE 则 fork 出一个子进程，子进程负责调用 rdbSave ，并在保存完成之后向主
进程发送信号，通知保存已完成。因为 rdbSave 在子进程被调用，所以 Redis 服务器在
BGSAVE 执行期间仍然可以继续处理客户端的请求。

### SAVE 、BGSAVE 、AOF 写入和 BGREWRITEAOF

- BGXXX都是后台子线程运行，如果SAVE进行中，阻塞所有其他命令执行。
- BGSAVE中，那么SAVE命令不会执行，而是返回一个错误信息，以此防止两个SAVE竞争
- BGREWRITEAOF 和 BGSAVE 不能同时执行，一个是记录RDB，一个是压缩AOF。并发出两个子进程，并且两个子进程都同时进行大量的磁盘写
  入操作，这怎么想都不会是一个好主意。

### 载入

Redis服务器启动的时候，会读取RDB，这时候只有发布订阅的功能能正常运行。因为发布订阅和其他数据库功能
是完全隔离的。

AOF在命令执行的时候进行写入，比RDB数据更新，只有在AOF没有打开的时候才会进行使用RDB。

### RDB文件结构

<img src="./images/1682751218801.jpg"/>

#### REDIS
文件的前五个字节，来快速地判断该文件是否有可能是 RDB 文件。

#### RDB-VERSION
不同版本的 RDB 文件互不兼容，所以在读入程序时，需要根据版本来选择不同的读入方式。

#### DB-DATA
出现任意多次，每个 DB-DATA 部分保存着服务器上一个非空数
据库的所有数据。

#### SELECT-DB

确保还原键值对到正确的数据库

#### KEY-VALUE-PAIRS

<img src="./images/1682751437364.jpg"/>

如果是32位以内的有符号整数就用整数存，long long 类型就用字符序列存储。

字符串超过20位，如果服务器开启了LZF压缩，那么就会存压缩后的数据。

#### EOF

表述数据的结尾

#### CHECK-SUM
RDB 文件所有内容的校验和，如果是0表示关闭校验功能

## AOF

- RDB即快照
- AOF即对数据库的写命令以及参数进行记录，实际通过网络通讯协议格式进行记录

同步命令到 AOF 文件的整个过程可以分为三个阶段：
1. 命令传播：Redis 将执行完的命令、命令的参数、命令的参数个数等信息发送到 AOF 程
   序中。
2. 缓存追加：AOF 程序根据接收到的命令数据，将命令转换为网络通讯协议的格式，然后
   将协议内容追加到服务器的 AOF 缓存中。
3. 文件写入和保存：AOF 缓存中的内容被写入到 AOF 文件末尾，如果设定的 AOF 保存
   条件被满足的话，fsync 函数或者 fdatasync 函数会被调用，将写入的内容真正地保存
   到磁盘中。

简单的说一下写命令的执行过程：
- 客户端以网络协议格式发到服务器端，服务器解析成命令执行；
- 一旦成功就将命令发到AOF程序，AOF编码成网络协议格式进行内容的追加到AOF缓存
- WRITE 将AOF缓存 写入AOF文件
- SAVE：根据条件，调用 fsync 或 fdatasync 函数，将 AOF 文件保存到磁盘中。

### AOF保存模式

目前支持三种 AOF 保存模式，它们分别是：
1. AOF_FSYNC_NO ：不保存。
2. AOF_FSYNC_EVERYSEC ：每一秒钟保存一次。
3. AOF_FSYNC_ALWAYS ：每执行一个命令保存一次。

#### AOF_FSYNC_NO
每次调用 flushAppendOnlyFile 函数，WRITE 都会被执行，但 SAVE 会被
略过。
在这种模式下，SAVE 只会在以下任意一种情况中被执行：
- Redis 被关闭
- AOF 功能被关闭
- 系统的写缓存被刷新（可能是缓存已经被写满，或者定期保存操作被执行）
这三种情况下的 SAVE 操作都会引起 Redis 主进程阻塞。

#### AOF_FSYNC_EVERYSEC

在这种模式中，SAVE 原则上每隔一秒钟就会执行一次，因为 SAVE 操作是由后台子线程调用
的，所以它不会引起服务器主进程阻塞。对 fsync 或 fdatasync 的调用并不是每秒一次，
它和调用 flushAppendOnlyFile 函数时 Redis 所处的 状态有关。

每当 flushAppendOnlyFile 函数被调用时，可能会出现以下四种情况：
- 子线程正在执行 SAVE ，并且：
  1. 这个 SAVE 的执行时间未超过 2 秒，那么程序直接返回，并不执行 WRITE 或新的
     SAVE 。
  2. 这个 SAVE 已经执行超过 2 秒，那么程序执行 WRITE ，但不执行新的 SAVE 。
     注意，因为这时 WRITE 的写入必须等待子线程先完成（旧的）SAVE ，因此这里
     WRITE 会比平时阻塞更长时间。
- 子线程没有在执行 SAVE ，并且：
  1. 上次成功执行 SAVE 距今不超过 1 秒，那么程序执行 WRITE ，但不执行 SAVE 。
  2. 上次成功执行 SAVE 距今已经超过 1 秒，那么程序执行 WRITE 和 SAVE 。

#### 每执行一个命令保存一次
在这种模式下，每次执行完一个命令之后，WRITE 和 SAVE 都会被执行。主进程会被阻塞，不能
接受命令请求。

### AOF 保存模式对性能和安全性的影响
它们对服务器主进程的阻塞情况如下：
1. 不保存（AOF_FSYNC_NO）：写入和保存都由主进程执行，两个操作都会阻塞主进程。
2. 每一秒钟保存一次（AOF_FSYNC_EVERYSEC）：写入操作由主进程执行，阻塞主进程。保存
   操作由子线程执行，不直接阻塞主进程，但保存操作完成的快慢会影响写入操作的阻塞
   时长。
3. 每执行一个命令保存一次（AOF_FSYNC_ALWAYS）：和模式 1 一样。

<img src="./images/1682753186115.jpg"/>

### AOF文件的读取和数据还原

通过fake client 进行所有命令重新执行来还原数据

### AOF重写（ BGREWRITEAOF 命令的工作原理）

压缩AOF，防止过大。

其实就是分析原始AOF，判断最终数据状态，然后插入一条命令且不改变原始的最终的数据状态。

为了防止AOF重写过长时间的阻塞服务，Redis 增加了一个 AOF 重写缓存，这个缓存在 fork 出子进程之后开始启
用，Redis 主进程在接到新的写命令之后，除了会将这个写命令的协议内容追加到现有的 AOF
文件之外，还会追加到这个缓存中：

当子进程完成 AOF 重写之后，它会向父进程发送一个完成信号，父进程在接到完成信号之后，会调用一个信号处理函数，并完成以下工作：
1. 将 AOF 重写缓存中的内容全部写入到新 AOF 文件中。
2. 对新的 AOF 文件进行改名，覆盖原有的 AOF 文件。

只有最后的写入缓存和改名操作会造成主进程阻塞，在其他时候，AOF 后台重写都不会对主进程造成阻塞，这将 AOF 重写对性能造成的影响降到了最低。

每次当 serverCron 函数执行时，它都会检查以下条件是否全部满足，如果是的话，就会触发
自动的 AOF 重写：
1. 没有 BGSAVE 命令在进行。
2. 没有 BGREWRITEAOF 在进行。
3. 当前 AOF 文件大小大于 server.aof_rewrite_min_size （默认值为 1 MB）。
4. 当前 AOF 文件大小和最后一次 AOF 重写后的大小之间的比率大于等于指定的增长百分
   比。
默认情况下，增长百分比为 100%

## 事件

1. 处理文件事件：在多个客户端中实现多路复用，接受它们发来的命令请求，并将命令的执
行结果返回给客户端。
2. 时间事件：实现服务器常规操作（server cron job）。

### 文件事件

多个客户端通过套接字连接到 Redis 服务器中，但只有在套接字可以无阻塞地进行读或者写时，服务器
才会和这些客户端进行交互。 Redis 将这类因为对套接字进行多路复用而产生的事件称为文件事件

#### 读事件

读事件在整个网络连接的生命期内，都会在等待和就绪两种状态之间切换：
- 当客户端只是连接到服务器，但并没有向服务器发送命令时，该客户端的读事件就处于
等待状态。
- 当客户端给服务器发送命令请求，并且请求已到达时（相应的套接字可以无阻塞地执行读
操作），该客户端的读事件处于就绪状态。

<img src="./images/1682754985609.jpg" />

当客户端 X 向服务器发送命令请求，并且命令请求已到达时，客户端 X 的读事件状态
变为就绪：

<img src="./images/1682755062269.jpg" />

#### 写事件

写事件标志着客户端对命令结果的接收状态。服务器只会在有命令结果要传回给客户端时，才会为
客户端关联写事件，并且在命令结果传送完毕之后，客户端和写事件的关联就会被移除。

一个写事件会在两种状态之间切换：
- 当服务器有命令结果需要返回给客户端，但客户端还未能执行无阻塞写，那么写事件处
于等待状态。
- 当服务器有命令结果需要返回给客户端，并且客户端可以进行无阻塞写，那么写事件处
于就绪状态。

服务器端等待客户端可写：
<img src="./images/1682755228515.jpg" />

客户端的套接字可以无阻塞接收：
<img src="./images/1682755352177.jpg" />

执行结果被传送回客户端之后，客户端和写事件之间的关联会被解除:
<img src="./images/1682755458523.jpg" />

>读事件只有在客户端断开和服务器的连接时，才会被移除。
这也就是说，当客户端关联写事件的时候，实际上它在同时关联读/写两种事件。

### 时间事件

多个时间事件以无序链表的形式保存在服务器状态中

每个时间事件主要由三个属性组成：
- when ：以毫秒格式的 UNIX 时间戳为单位，记录了应该在什么时间点执行事件处理函数。
- timeProc ：事件处理函数。
- next 指向下一个时间事件，形成链表。

根据 timeProc 函数的返回值，可以将时间事件划分为两类:
- 单次执行事件：该事件会在指定的时间被处理一次，之后该事件就会被删除，不再执行。
- 循环执行事件：该事件会在指定的时间被处理，之后它会按照事件处理函数的返回值，更新事件的 when 属
  性，让这个事件在之后的某个时间点再次运行，并以这种方式一直更新并运行下去。

### 时间事件应用实例：服务器常规操作

服务器需要定期对自身的资源和状态进行必要的检查和整理，从而让服务器维持在一个健康稳定的状态，这类操作被统称为常规操作

主要执行以下操作：
- 更新服务器的各类统计信息，比如时间、内存占用、数据库占用情况等。
- 清理数据库中的过期键值对。
- 对不合理的数据库进行大小调整。 
- 关闭和清理连接失效的客户端。 
- 尝试进行 AOF 或 RDB 持久化操作。 
- 如果服务器是主节点的话，对附属节点进行定期同步。 
- 如果处于集群模式的话，对集群进行定期同步和连接测试。

### 事件之间调度

1. 一种事件会等待另一种事件执行完毕之后，才开始执行，事件之间不会出现抢占。
2. 事件处理器先处理文件事件（处理命令请求），再执行时间事件（调用 serverCron）
3. 文件事件的等待时间（类 poll 函数的最大阻塞时间），由距离到达时间最短的时间事件
   决定。

## 服务器和客户端

初始化过程可以分为以下六个步骤：
1. 初始化服务器全局状态。
2. 载入配置文件。
3. 创建 daemon 进程。
4. 初始化服务器功能模块。
5. 载入数据。
6. 开始事件循环。

总的来说就是初始化Redis服务器所需要的默认的全局状态，然后载入用户自己定义的配置，实现定制化的
Redis服务，之后创建daemon，运行服务器。之后将服务器功能模块初始化，如
<img src="./images/1682757337179.jpg"/>
之后通过AOF或者RDB数据的读取来恢复数据。最后就是事件监听，即监听客户端请求了。

- 服务器为每个已连接的客户端维持一个客户端结构，这个结构保存了这个客户端的所有
状态信息。
- 客户端向服务器发送命令，服务器接受命令然后将命令传给命令执行器，执行器执行给
定命令的实现函数，执行完成之后，将结果保存在缓存，最后回传给客户端。


