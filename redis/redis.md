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

1. 数据库键空间
2. 哈希键的一种底层实现

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

命令表以及Lua脚本缓存都用到了算法2；数据库、集群、哈希键、阻塞都用到了算法1

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
> 1. Redis使用子进程对数据库执行后台持久化任务（BGSAVE 、 BGREWRITEAOF），为了最大化copy on write禁止，会暂时设置dict_can_resize为假，避免执行自然rehash。
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

数据库字典、哈希键字典被动rehash

- dictRehashMilliseconds

Redis服务器常规任务程序执行，用于对数据库字典主动rehash

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
- 当字典用于实现数据库键空间（key space）的时候，收缩的时机由
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

<img src="./images/1680867274858.jpg">

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

