高性能MYSQL

# 创建高性能索引

## 索引基础

### 索引类型

#### B-Tree索引

MyIsam都是非聚簇索引，节点存数据，节点的数据存储的是数据的地址，需要根据这个地址去查内存；Innodb如果是主键那么就是聚簇索引，存的是key+数据，其余的都是非聚簇索引，存的是key+主键index，需要根据主键index去回表获取索要查询的数据。

B-Tree都是叶子节点存数据，叶子节点之间有顺序，如果索引有顺序，那么就可以range查询，直接遍历叶子的后续判断是不是也符合查询条件。

- 全值匹配：根据索引的所有列来进行匹配
- 最左前缀：依次根据索引列的左部进行匹配，索引下推，减少回表，直到不匹配为止
- 匹配列前缀：根据索引列的开头左边部分进行匹配。
- 匹配范围值
- 精确匹配到某一列，并且范围匹配另一列
- 索引覆盖：查询的字段正好是索引，减少回表

#### 哈希索引

缺点：

- 不支持部分索引列匹配，始终使用列的全部内容进行hash计算；如（A，B）索引，如果只查询A就不能使用该索引
- 不支持顺序查询以及排序
- 索引只包含哈希值和行指针，不存字段值，需要根据行指针去比较数据行是否相等并读数据行
- 只能用于等值查询
- 访问数据很快，如果有哈希冲突，需要遍历链表中的所有行指针，直到找到符合条件的行
- 哈希冲突很多，维护索引的代价会很高。如果选择过滤性很差的列建立索引，那么删除的时候，需要遍历链表。

数据仓库星形结构就适合hash索引。

Innodb自适应哈希索引：如果发现某些索引值被使用非常频繁，就会B+索引上创建哈希索引

```mysql
#法1.导致非常慢，且存储内容会非常大
select id
from url
where url = "http://www.mysql.com"

#法2 用 哈希
# 利用触发器创建hash，hash函数的选择尽量不要太过费时间且复杂
DELIMITER //
CREATE TRIGGER tablename_crc_ins
    BEFORE INSERT
    ON tablename
    FOR EACH ROW
BEGIN
    SET NEW.url_crc = crc32(NEW.url)
END;
//

CREATE TRIGGER tablename_crc_upd
    BEFORE UPDATE
    ON tablename
    FOR EACH ROW
BEGIN
    SET NEW.url_crc = crc32(NEW.url)
        EDN;
    //
DELIMITER;
# and后面的语句必须存在为了防止hash冲突
select id
from url
where url_crc = CRC32("http://www.mysql.com")
  and url = "http://www.mysql.com"

```

#### 空间数据索引

MyISQM支持空间索引，无须前缀匹配。必须使用Mysql的GIS相关函数如MBRCONTAINS()来维护数据。推荐使用PostgreSQL的postGIS。

#### 全文索引

查找文本中的关键词

#### 其他

TokuDB使用分形树索引

## 索引的优点

- 减少数据量扫描
- 将随机IO变为顺序IO
- 帮助服务器避免排序和临时表

## 高性能索引策略

### 独立的列

- 非表达式一部分

```mysql
SELECT actor_id FROM actor WHERE actor_id + 1 = 5;
```

- 非函数的参数

```mysql
  SELECT …… FROM TO_DAYS(CURRENT_DATE) - TO_DAYS(date_col) <= 10
```

### 前缀索引和索引选择性

前缀索引的选择必须是过滤程度高的，<b>对于BLOB、TEXT或者很长的VARCHAR，必须使用前缀索引，MSYQL不允许索引这些列的完整长度。关键点在于长度适当且能够高过滤性。

<b>缺点</b> 不能order by 和 group by，不能作为覆盖索引

```mysql
# 首先查询所有的 城市名字 前十名
SELECT COUNT(*) AS cnt, city_name
FROM city
GROUP BY city_name
ORDER BY cnt DESC
LIMIT 10
# 法1 逐步查找合适的区分度
SELECT COUNT(*) AS cnt, LEFT(city_name, 3) AS prefix
FROM city
GROUP BY city_name
ORDER BY cnt DESC
LIMIT 10

SELECT COUNT(*) AS cnt, LEFT(city_name, 7) AS prefix
FROM city
GROUP BY city_name
ORDER BY cnt DESC
LIMIT 10

# 法2 逐步计算不同前缀长度的选择性 相较于1 不精确
SELECT COUNT(DISTINCT LEFT(city_name, 3) / COUNT(1) AS sel3),
       COUNT(DISTINCT LEFT(city_name, 4) / COUNT(1) AS sel4),
       COUNT(DISTINCT LEFT(city_name, 5) / COUNT(1) AS sel5),
       COUNT(DISTINCT LEFT(city_name, 6) / COUNT(1) AS sel6),
       COUNT(DISTINCT LEFT(city_name, 7) / COUNT(1) AS sel7)

# 创建前缀索引
ALTER TABLE CITY
    ADD KEY (city(7))
```

#### 案例

将电子邮件反向存储之后，建立前缀索引可以快速找到某个域名的电子邮件

### 多列索引

多个单列索引最后查询优化使用了索引合并不是一个好的操作，如

```mysql
SELECT film_id, actor_id
FROM film_actor
WHERE actor_id = 1
   OR film_id = 1

# 会被优化
SELECT film_id, actor_id
FROM film_actor
WHERE actor_id = 1
UNION ALL
SELECT film_id, actor_id
FROM film_actor
WHERE film_id = 1
  AND actor_id <> 1
```

AND 或者 OR 多个索引列的时候考虑 建立联合索引。

### 合适的索引列顺序

过滤性能高的放在左边,但是注意要所有group by ，order by中出现的字段，都是极度影响性能的。

```mysql
SELECT COUNT(*),
       SUM(groupId = 10137),
       SUM(userId = 1288826),
       SUM(anunymous = 0)
FROM Message
```

### 聚簇索引

#### 优点

相关数据保存在一起，减少回表

#### 缺点

- 提高IO密集型应用性能，但是数据都在内存，那么聚簇索引没有什么优势
- 插入速度严重依赖插入顺序。如果按照主键插入速度很快，否则最好使用OPTIMISE TABLE 重新组织一下表。
- 聚簇索引插入新行或者主键被更新导致要移动行的时候，需要面临”页分裂“。如一行数据插入已经满的页，存储引擎会将页分成两个页，这就是页分裂，导致更多的磁盘空间。
- 可能导致全表扫描变慢，尤其是行稀疏或者页分裂导致数据存储不连续的时候。
- 二级索引需要找到叶子节点中的主键行之后去聚簇索引中查找真正的数据行，也就是两次IO。对于InnoDB，自适应hash索引能减少这样的重复工作。

#### 按照主键顺序插入

- 主键是顺序的，每一条记录都存储在上一条记录的后面。当达到页的最大填充因子（默认15/16)后会插入新的数据页，最后数据页会被顺序填满。
- 非顺序插入，因为存储的数据无序，要找到合适的位置进行插入，可能导致如下缺点
    - 目标页不在内存，需要从磁盘加载到内存，导致大量IO
    - 写入是乱序的，InnoDB不得不频繁页分裂，需要移动大量数据
    - 频繁页分裂，导致页变得稀疏并且不规则，最终有碎片

> 顺序的主键在高并发的情况下，InnoDB按主键顺序插入可能造成明显的争用，对间隙锁竞争；同时可能造成AUTO_INCREMENT锁竞争。这时候需要考虑表重新设计或者更改innodb_autoinc_lock_mode配置。

### 覆盖索引

#### 优点

- 索引条目通常远小于数据行大小，只需要读取索引，就会减少数据访问量
- 索引按列顺序存储，这样使得IO密集型范围查询比随机从磁盘读取一行数据的IO要少很多
- 像MyISAM内存中只缓存索引，数据访问需要系统调用。如果每次访问数据都需要一次系统调用，很可能造成性能问题。
- InnoDB的覆盖索引保证二级索引不去回表，提升性能

#### 延迟关联

```mysql
SELECT *
FROM products
         JOIN
     (SELECT prod_id
      FROM products
      WHERE actor = "ABC"
        AND title LIKE '%APOLLO%') t1 ON (t1.prod_id = products.prod_id)
```

先利用覆盖索引将少量prod_id查出来之后，在进行关联回表

### 使用索引扫描来做排序

如果覆盖索引有序，则可以顺序查找且不需要回表，否则多次回表不如顺序全表扫描。

> order by 需要满足最左前缀原则，多表连接进行order by的时候需要满足第一张表的最左前缀匹配，否则不嫩那个使用索引排序。除非where
> 或者 join字句对最左前缀的列指定了常量，然后紧接着后续的索引列进行order by

```mysql
# 联合索引 rental_date,inventory_id,customer_id
SELECT rental_date, staff_id
FROM rental
WHERE rental_date = '2005-05-25'
ORDER BY inventory_id,
         customer_id

# 最左前缀多个等于条件 也能使用索引
             ... WHERE rental_date = '2005-05-25' ADN inventory_id IN(1,2)
ORDER BY customer_id
```

下面这个无法使用索引进行范围查找

```mysql
# 索引列第一列用来范围查询
.
.. WHERE rental_date > '2005-05-25' ORDER BY inventory_id, customer_id;
```

### 压缩（前缀压缩）索引

MyISAM使用前缀压缩来减少索引的大小，在内存中存放更多的索引，以此提升性能。如perform，performance，那么performance会压缩成7,ance

压缩虽然使用空间更少，但是查找时无法使用二分查找。如果IO密集型就会减少很多成本，但是如果是CPU密集型就会导致随机读浪费很多性能，倒序扫描尤其。

删除冗余索引，可以减少修改时间。

二级索引进行修改的时候需要注意，如二级索引A，其实存的是（A，ID），对于`WHERE A = 5 ORDER BY ID`
能命中，但是变成（A，B）就变成（A，B，ID）了反而会变成文件排序了，不能利用索引了。

### 未使用的索引

删除不经常使用的索引，通过MariaDB打开userstates让服务器运行一段时间之后查看`INFORMATION_SCHEMA.INDEX_STATISTICS查看索引使用频率。

也可以使用Percona Toolkit的pt-index-usage来获取查询日志并对每个查询就行EXPLAIN。

### 索引和锁

索引使得锁定更少的行，性能更好；虽然行锁效率高，但是锁终究有额外开销，锁定超过需要的行会增加锁争用并减少并发性。

一旦InnoDb检索到数据并返回到服务器之后，MYSQL服务器再进行WHERE这段时间都是上锁的，只有过滤后才能释放锁。

```mysql
SET AUTOCOMMIT = 0;
BEGIN;
SELECT actor_id
FROM actor
WHERE actor_id < 5
  AND actor_id <> 1 FOR
UPDATE 
```

上述语句会获取1-4之间的排他锁，返回后才会进行WHERE进一步过滤。如下的语句就会被挂起

```mysql
SET AUTOCOMMIT = 0;
BEGIN;
SELECT actor_id
FROM actor
WHERE actor_id = 1 FOR
UPDATE;
```

InnoDb在二级索引使用读锁，主键索引使用写锁。消除了覆盖索引的可能性，使得SELECT FOR UPDATE 比 LOCK IN SHARE MODE 慢很多。

## 索引案例学习

### 支持多种过滤条件

如sex、country虽然选择性不高，但是每次都会用到，所以创建(sex,country)
作为前缀，虽然sex过滤性很差，但是可以通过`AND SEX IN('m','f')`来命中索引，并且不过滤任何行。
应该考虑同时优化查询和索引来达到最佳平衡。尽可能将范围查询字段放在索引最后，因为范围查询后的字段不再匹配。并且用IN()
来覆盖不再WHERE子句中的列，如果IN过多会导致组合暴增，降低性能。

```mysql
# 优化器会变成4 * 3 * 22 = 24种组合
WHERE eye_color     IN('brown','blue','hazel')
AND   hair_color    IN('black','red','blonde','brown')
AND   sex           IN('M','F')
```

### 优化排序

```mysql
# 翻页到很后面会非常慢，会花费大量实践去扫描需要丢弃的数据
SELECT <clos>
FROM profiles
WHERE sex='M'
ORDER BY rating
LIMIT 1000000,10
```

可以通过反范式或者预先缓存计算解决这个问题，也可以延迟关联，利用覆盖索引只返回主键，再根据主键获得需要的行。

```mysql
# 索引 (sex,rating) 默认带了id
SELECT <cols>
FROM profiles INNER JOIN (
    SELECT id from profiles
    WHERE x.sex='M' ORDER BY rating LIMIT 1000000, 10
    ) AS x using (id)
```

## 维护索引和表

创建表并建立索引还需要维护表和索引

- 找到并修复损坏的表
- 维护准确的索引统计信息
- 减少碎片

### 找到并修复损坏的表

表损坏通常是系统崩溃导致，会导致查询返回错误的结果或者莫须有的主键冲突问题，甚至会导致数据库崩溃。
`check table` 来检查是否表损坏。`repair table` 来修复损坏的表。
<img src="./images/1679931827833.jpg">
<img src="./images/1679931901860.jpg">

### 更新索引统计信息

可以通过两个API来了解存储引擎的索引分布信息，决定如何使用索引

- records_in_range() 来判断这个范围有多少记录。
- info() 返回各种类型数据，包括索引的基数（每个键有多少记录）
  存储引擎向优化器提供的扫描行数如果不准确或者执行计划太复杂以至于无法准确获取各个阶段的行数，那么会使用索引统计信息来估算扫描的行数，优化器就是基于需要扫描的行数进行优化的。
  可以通过ANALYZE TABLE 来重新生成统计信息解决这个问题。不同引擎运行的成本也不同
- Memory 不存储索引统计信息
- MyISAM存在磁盘，全表扫描会锁表
- InnoDB随机索引访问进行评估并存储在内存中

> 表首次打开、或者执行ANALYZE TABLE 或者 表的大小发生巨大变化（超过1/16或者插入了超过20亿数据)就会计算索引的统计信息
> 打开INFORMATION_SCHEMA表或者使用SHOW TABLE STATUS 和 SHOW INDEX或者在MYSQL客户端打开自动不全都会出发索引统计信息更新，会加剧服务器压力，
> 可以关闭`innodb_stats_on_metadata`来避免

```mysql
# 查看索引的基数Cardinality
SHOW INDEX FROM t_order
```

<img src="./images/1679931316859.jpg">

像Percona版本已经可以使用系统表来持久化索引统计信息，防止启动时进行收集

### 减少索引和数据的碎片

碎片化的索引可能会很差或者无序的方式存在磁盘上，降低查询效率；B-Tree需要随机磁盘访问才能定位叶子页，所以随机访问是不可避免的，如果叶子页物理上顺序且紧密，那么查询性能会更好，否则对于范围查询、索引覆盖扫描效率会下降；

表数据碎片化更加复杂
- 行碎片
数据行被存储在多个地方的多个片段中，即使只访问一行记录，行碎片浪费很多寻址时间
- 行间碎片
逻辑上顺序存储，但是在磁盘上不是顺序存储，如果无序会导致浪费很多寻址时间
- 剩余空间碎片
数据页有大量空余空间，导致磁盘数据读到内存浪费很多内存

InnoDB不会发生第三种，会进行移动重写。

可以通过OPTIMIZE TABLE 或者 ALTER TABLE tablename ENGINE=enginename 进行优化。

percona的XtraBackup有个--stats以非备份方式运行，只是打印索引和表的统计情况，包括页中的数据量和空余空间，来确定碎片化程度。

需要判断是否数据是稳定的，否则压缩之后导致页分裂重组，反而降低性能。

## 总结

1. 单行访问很慢，主要是太多次IO，尽量读取块数据，且包含多行数据的块
2. 按顺序访问很快：不需要多次磁盘寻道，比随机访问块；按顺序读取不需要额外的排序（order by、 group by）
3. 索引覆盖可以减少回表，减少IO

判断索引合理与否：按照响应时间进行分析，找出时间最长、造成最大压力的查询，检查schema、SQL和索引结构，判断是否扫描了太多的行，是否做了额外的排序或者用了临时表、是否使用随机IO、是否太多回表

# 查询性能优化

## 为什么查询速度会慢

查询看作一个响应，是由多个子任务组成，为了减少响应时间，其实就是减少子任务的执行次数，优化子任务。

查询消耗时间的地方：网络、CPU计算、生成统计信息和执行计划、锁等待等，向底层存储引擎检索数据调用的时候，还会进行上下文切换和系统调用。

## 慢查询基础：优化数据访问
排查步骤：
- 查看是否程序检索了过多不需要的数据行、或者列
- MYSQL服务器是否在分析大量超过需要的数据行

### 是否请求了不需要的数据

请求了过量非必须的数据，最后再丢弃，不仅给MYSQL服务器带来额外负担和网络开销，还会浪费CPU和内存。

- 查询不需要的记录
通过LIMIT进行限制，否则会把所有数据都进行返回之后才进行筛选丢弃
- 多表连接返回过多的列
- 总是取全部列 
  
  导致额外IO、内存、CPU开销；如果有缓存，那么取全部列可能更有好处
- 重复查询相同数据

  如获取用户头像URL可以缓存，避免再获取用户的评论的时候再次查询。

### MYSQL是否再扫描额外记录

重点关注是否扫描了过多数据，评判指标：
- 响应时间
- 扫描行数
- 返回行数
#### 响应时间
服务时间+排队阻塞时间（锁或者IO）

#### 扫描行数或者返回的行数

关联查询就使得扫描的行数和返回的行数之比可能1：1和10：1之间

#### 扫描的行数和访问类型

扫描表 >> 扫描索引 >> 范围访问 >> 单值访问

三种WHERE从好到坏依次为：
- 索引中使用WHERE，直接在存储引擎处完成
- 使用索引覆盖扫描（Using index），在索引中过滤不需要记录并返回命中的结果。在MYSQL服务器完成，无需回表。
- 从数据表返回数据，过滤不满足条件的记录。MYSQL服务器层完成，MYSQL需要先读出记录后过滤。

如果发现扫描很多行只返回有限行，可以通过
- 索引覆盖扫描，减少回表
- 使用单独的汇总表
- 重写复杂SQL

## 重构查询方式

### 一个复杂查询还是多个简单查询

将一个大查询分解为多个小查询是有必要的，仔细检查是否会减少工作量。

### 切分查询

定期清除大量数据，如果一个大语句一次性完成，会锁住很多数据，占满事务日志、耗尽系统资源、阻塞很多其他查询。

拆分之后可以减少MYSQL复制的延迟，同时每次做完之后如果还能进行停顿一下，那么会把服务器压力平坦到很长时间段，减少锁持有。

### 分解关联查询
```mysql
SELECT * FROM tag 
JOIN tag_post ON tag_post.tag_id = tag.id
JOIN post ON tag_post.post_id = post.id
WHERE tag.tag = 'mysql'

# 拆分
SELECT * FROM tag WHERE tag='mysql'
SELECT * FROM tag_post WHERE tag_id = 1234
SELECT * FROM post WHERE post.id in(123,456,9098)
```
好处
单个查询方便缓存、分库分表以至于有更好的性能和扩展性、查询效率可能提升、减少冗余查询，同样数据只要查一次，减少网络和内存消耗、手动实现了HASH关联

## 查询执行的基础

- 查询到达服务器
- 服务器查缓存，未命中就进行语法语义解析
- 优化器生成执行计划
- 返回结果

### MYSQL客户端\服务器通信

不会进行数据分段，半双工，用一个单独数据包进行传送，所以`max_allowed_packet`就很重要。通常MYSQL客户端会一次性接收所有数据并缓存到内存以此减少服务器压力，但是当结果集很大就会导致消耗过多内存。

- SLEEP
等待客户端请求
- QUERY 
将正在执行查询或者将结果发给客户端
- Locked
等待表锁
- Analyzing and statistics
收集存储引擎的统计信息并生成执行计划
- coping to tmp table [on disk]
正在执行查询，group by、 文件排序 、 union 复制数据到临时表，放到磁盘
- Sorting result
数据结果排序
- sending data
响应数据或者生成结果集
  
可以通过查看Last_query_cost来知晓MYSQL的查询成本

```java
SELECT SQL_NO_CACHE COUNT(1) FROM t_order;

# value 表示需要读取多少个数据页的随机查找才能完成上面查询
        
SHOW STATUS LIKE 'Last_query_cost';
```

- 编译时优化：静态优化后不依赖特别的数值，如WHERE中有常数。
- 运行时优化：和查询的上下文有关，如WHERE张取值、索引条目对应的数据行数

EXPLAIN 查看的时候如果看到`select tables optimized away`就是已经从执行计划中移除了该表，以常数取而代之。

IN() 会 将 IN()列表中的数据进行排序，然后二分判断是否相等，O(logn)级别，但是与之相当的or就是 O(n),当列表很大的时候，IN效率就高

存储引擎负责存储统计信息，MYSQL查询优化器需要首先获取这些统计信息才能进行优化，如每个表或者索引有多少个页面、每个表的每个索引的基数锁多少、数据行和索引长度、索引的分布信息等。

MYSQL中不管是单表还是多表都是关联查询，都是从外表获取一条数据，再去内表匹配，称之为嵌套循环关联查询。
如FROM 子句遇到子查询，就会把子查询的结果放到临时表，作为一张普通表（派生表）进行连接。UNION也是使用临时表。一旦使用了临时表，这些临时表都是没有索引的。

#### 排序优化

如果不能根据索引进行排序，将会导致filesort，性能下降，尽量避免排序或者对大量数据排序

内存如果不够对数据进行排序，将数据分块，对各独立的块进行“快排”，将排序结果存在磁盘之后进行合并，最后返回结果

MYSQL外部排序策略：
- 老版本两次传输

读取行指针和需要排序的字段，排序后再根据排序结果读取所需要的数据行，这时候由于排序后的数据读取会导致大量随机IO，性能会很差。
排序的时候会使用较少的内存，以至于缓冲区能容纳更多的行。

- 新版本一次传输

查询所需要的所有列，然后对这些列排序，最后直接返回排序结果。但是单条数据很大可能导致更多的排序快需要合并。

当查询的所有列总长度不超过参数max_length_for_sort_data时，可以使用”单词传输排序“

关联查询需要排序的时候
- 所有字段都是第一张表，那么直接关联处理前就进行文件排序 Using filesort
- 否则会把关联数据放在临时表，关联结束后进行文件排序 Using temporary：Using filesort 
即使有LIMIT也会先抛弃一部分数据后排好序。

### 返回数据结果

服务器将结果集返回客户端是一个增量、逐步返回的过程：服务器不需要存太多的结果，也能减少响应时间，尽快给客户端看到数据。

结果集通过TCP传送，传输的时候可能中途就数据包进行缓存后进行批量传输。

## MYSQL查询优化器的局限性

### 关联子查询

MYSQL的子查询实现非常糟糕，如WHERE……IN
```mysql
SELECT * FROM film
WHERE film_id IN(SELECT film_id FROM file_actor WHERE actor_id = 1); 
```
被优化成:把所有的film_id全表扫描出来之后挨个去子查询，性能很差
```mysql
SELECT * FROM film
WHERE EXISTS(SELECT * FROM film_actor WHERE actor_id = 1
    AND film_actor.film_id = film.film_id)
```

### UNION限制

主要是union的临时表中读取数据的时候，不一定有序，需要自己进行ORDER BY 和 LIMIT操作

### 松散索引扫描

MYSQL不支持，Oracle支持（跳表索引扫描）

如存在索引(a,b)

```mysql
SELECT ... FROM tb1 WHERE b BETWEEN 2 AND 3
```
因为最左匹配不满足，显然就不能使用整个索引，只能通过全表扫描。如果通过调表如下所示，效率更高，不再需要where筛选

<img src="./images/1680109718589.jpg/>

不过MYSQL5.6之后，松散扫描索引的一些限制会通过”索引下推“解决。

### 最大值和最小值优化

```mysql
SELECT MIN(actor_id) FROM actor WHERE first_name = 'PENELOPE'
```
理论上索引的最小值，在第一条数据被读取的时候就已经找到了，因为主键有序，但是MYSQL会进行全表扫描.

可以通过LIMIT曲线救国，但是难以理解,以此减少扫描的行数

```mysql
SELECT actor_id FROM actor USE INDEX(PRIMARY)
WHERE first_name = 'PENELOPE' LIMIT 1
```
### 在同一个表上查询和更新

MYSQL不允许对同一张表同时查询和更新。

```mysql
UPDATE tb1 AS outer_tb1
    SET cnt = (
        SELECT count(*) FROM tb1 AS inner_tb1
        WHERE inner_tb1.type = outer_tb1.type
      )
```

可以通过临时表来查询的时候更新，临时表在进行update之前就已经创建

```mysql
UPDATE tb1
    INNER JOIN(
        SELECT type, count(*) AS cnt
        FROM tb1
        GROUP BY type
    ) AS der USING(tyoe)
SET tb1.cnt = der.cnt
```

## 查询优化器的提示（hint)

#### HIGH_PRIORITY 和 LOW_PRIORITY
 
HIGH_PRIORITY用于Select、INSERT，把SELECT、INSERT放到所有语句最前面

LOW_PRIORITY用于SELECT、INSERT、UPDATE、DELETE之中，只要有语句，就一直等待，饥饿问题。

#### DELAYED

对INSERT、REPLACE有效，会将该提示的语句理解返回，并将插入的行数放入缓冲区，一旦表空闲就写入。适用于数据大，但不需要等待完成。

不是所有的都支持该函数

#### STRAIGHT_JOIN

SELECT前后，或者关联表之间。前者是让查询的表按照语句顺序进行关联，后者是固定前后两个表的关联顺序。

在关联表过多，数据库优化的时间花很多，可以使用该函数减少优化时间。

#### SQL_SMALL_RESULT 和 SQL_BIG_RESULT

告诉SELECT在GROUP 和 DISTINCT如何使用临时表和排序。SQL_SMALL_RESULT将结果集放在内存中的索引临时表，避免排序；SQL_BIG_RESULT将结果在磁盘临时表排序

#### SQL_BUFFER_RESULT
将结果放入临时表，趁块释放表锁，无所耗费太多内存。

#### SQL_CHCHE 和 SQL_BO_CACHE

结果集是否缓存起来

#### FOR UPDATE 和 LOCK IN SHARE MODE

尽量别乱用 

#### USE INDEX、IGNORE INDEX 和 FORCE INDEX

强制使用索引，如保证数据有序返回

optimizer_search_depth、optimizer_prune_level、optimizer_switch ：控制琼剧深度、是否跳过某些执行计划、控制禁用索引合并

## 优化特定类型的查询

### 优化COUNT()查询

count(列)统计指定列非NULL的行数，

如下经典优化
```mysql
select count(*) from city where id > 5

# 可以只扫描5行，同时前者是const级别
select (select count(*) from city) - count(*) from city where id <= 5
```

```mysql
# 统计多种颜色
select sum(if(color='blue',1,0)) as blue , sum(if(color='red',1,0)) as red from items

select sum(color='blue') as blue , sum(color='red') as red from items

select count(color='blue' or null) as blue,count(color='red' or null) as red from items
```

#### 使用近似值

跟EXPLAIN一样，不需要真正执行，而是直接估计大概需要的行数。在不需要精确数量的场景，可以使用近似值，如在线有多少人(本来需要很多的条件)，这里不进行这些特殊约束进行筛出，就能进一步提升性能，只是略微不精确。

#### 更复杂的优化

可以通过覆盖索引减少回表、或者增加汇总表或者类似的Memcached的外部缓存，进一步加速。但是快速、精确和实现简单，三者永远只能满足两个。

### 优化关联查询

- 确保ON 或者 USING列上有索引，一般都只要在关联的第二张表建立索引
- 确保任何的GROUP BY 和 ORDER BY都只涉及到一个表中的列，以此使用索引

### 优化子查询

尽量使用关联而不是子查询

### 优化GROUP BY 和 DISTINCT

尽量使用索引。

GROUP BY 会通过：临时表或者文件排序来分组，尽量使用唯一性高、最好是索引进行分组。

```mysql
SELECT first_name,last_name,count(*)
FROM actor
INNER JOIN actor using(actor_id)
group by first_name, last_name

# 优化,但是可能最后索引列变化，对应关系一变动就会查询出现错误结果，后面已经通过ONLY_FULL_GROUP_BY进行避免，就是需要将分组的字段返回
SELECT first_name,last_name,count(*)
FROM actor
INNER JOIN actor using(actor_id)
GROUP BY actor_id
```

#### 优化 GROUP BY WITH ROLLUP

分组结果再做一次超级聚合，可以使用WITH ROLLUP来实现这种逻辑，但是最好在应用程序中处理。

### 优化LIMIT分页

MYSQL 在 LIMIT 10000,20的时候会查询10020条记录但是只返回20条，其他的10000都会被抛弃，代价很高。

可以通过限制页数进行优化、或者延迟关联进行优化
```mysql
SELECT id,description
FROM file
INNER JOIN (SELECT id FROM film ORDER BY title LIMIT 1000,10) AS lim USING(id)
```

LIMIT 和 OFFSET其实是OFFSET的问题，导致大量行被丢弃。可以使用书签记录上次获取数据的位置，下次直接翻页，避免OFFSET，主键需要单调增长
```mysql
# 返回16049 - 16030
SELECT * FROM rental ORDER BY id DESC LIMIT 20

SELECT * FROM rental 
WHERE id < 16030         
ORDER BY id DESC LIMIT 20
```

### 优化SQL_CALC_FOUND_ROWS

会把所有的满足的行数都扫一遍统计行数，来获取总页数，而不是LIMIT行数就中止，所以代价可能很高。

如 缓存1000条数据，后面的数据再查询

### 优化UNION

为了让优化器进行优化，需要把WHERE ORDER LIMIT都下推到UNION中，会导致很冗余。

尽量使用UNION ALL，因为UNION会默认使用DISTINCT，代价很高，

### 使用用户自定义变量

不能使用自定义变量场景
- 自定义变量，无法使用查询缓存
- 不能再使用常量或者标识符的地方使用自定义变量，如表名、列名、LIMIt子句
- 仅在一个连接中有效，不能做连接间通信
- 连接池或者持久化连接，可能让毫无关系的代码发生交互
- 自定义变量是一个动态类型
- 复制的顺序和时间点不固定
- 赋值符号优先级很低，建议明确括号
- 不会产生语法错误，容易犯错

#### 优化排名语句

```mysql
SET @curr_cnt := 0, @prev_cnt := 0, @rank := 0;
SELECT id
    @curr_cnt := cnt AS cnt,
    @rank     := if(@prev_cnt <> @curr_cnt, @rank+1,@rank) AS rank,
    @prev_cnt := @curr_cnt AS dummy
FROM(
    SELECT id, count(*) AS cnt
    FROM film_actor
    GROUP BY actor_id
    ORDER BY cnt DESC
    LIMIT 10
  ) as der;
```

#### 避免重复查询刚刚更新的数据

使用变量记录时间戳，下次访问不需要扫描表。但是两次网络来回是无法避免的

```mysql
UPDATE t1 SET lastUpdated = NOW() WHERE id = 1 AND @now := NOW();
SELECT @now;
```

#### 统计更新和插入的数量

使用INSERT ON DUPLICATE KEY UPDATE 的时候，想知道到底多少是更新操作，可以如下

```mysql
SET @x := 1;

INSERT INTO t1(c1,c2) VALUES(4,4),(2,1),(3,1)
ON DUPLICATE KEY UPDATE
c1 = VALUES(c1) + (0 * (@x := @x + 1));

SELECT @x;
```

#### 确定取值顺序

WHERE 和 SELECT 执行的阶段不同，加入ORDER BY可能更不同

```mysql
SET @rownum := 0;

# 先进行WHERE 后进行 赋值
SELECT id , @rownum := @rownum + 1 AS cnt
FROM actor
WHERE @rownum <= 1;

# ORDER BY 引入文件排序，而WHERE实在文件排序之前取值，所以会返回所有记录
SELECT id ,@rownum := @rownum + 1 AS cnt
FROM actor
WHERE @rownum <= 1
ORDER BY first_name

# 修复方法：让赋值在WHERE的时候发生
SELECT id,@rownum AS rownum
FROM actor
WHERE (@rownum := @rownum + 1) <= 1

# 加入ORDER BY
SELECT id,first_name,@rownum AS rownum
FROM actor
WHERE @rownum <= 1
ORDER BY first_name,LEAST(0,@rownum := @rownum + 1)
```

> LEAST()函数可以在不改变排序的时候完成赋值,LEAST(0,@rownum := @rownum + 1)就是每次都返回0
> 还有 GREATEST() LENGTH() ISNULL() NULLIF() IF() COALESCE()c都已

#### 编写偷懒UNION

UNION 包含两部分，一个子查询先执行，如果未命中再执行第二个分支。如热数据未找到就找冷数据，以此提高缓存命中率

如长期未登录用户一张表作为冷数据，长期活跃的一张表热数据。

```mysql
# 此查询会每次都进行两张表遍历
SELECT id FROM user WHERE id = 123
UNION ALL
SELECT id FROM user_archived WHERE id = 123;

# 通过@found进行懒UNION
SELECT GREATEST(@found := -1,id) AS id,'users' AS which_tb1
FROM users WHERE id = 1
UNION ALL
    SELECT id, 'users_achived'
    FROM users_archived WHERE id = 1 AND @found IS NULL
UNION ALL
    SELECT 1,'reset' FROM DUAL WHERE (@found := NULL) IS NOT NULL
```

#### 用户自定义变量的其他用处

- 查询运行时计算总数和平均值
- 模拟GROUP语句的函数FIRST() 和 LAST()
- 对大量数据做数据计算
- 计算一个大表的MD5散列值
- 编写一个样本处理函数，当样本中的数值超过边界就会变为0
- 模拟读/写游标
- SHOW中WHERE加入变量值

## 案例学习

### 使用MYSQL构建一个对列表

通过MYSQL实现队列表是一个取巧做法，高并发、高流量下系统表现不好。

典型模式：一个表包含多种类型记录：未处理、已处理、待处理。一个或者多个线程在消费这些记录，声称正在处理，处理完成后，再将记录更新为已处理。
如邮件发送、多命令处理、评论修改等。

```mysql
# SELECT FOR UPDATE的优化 不需要上锁直接进行批量更新
UPDATE unsent_emails
SET status = 'claimed', owner = CONNECTION_ID()
WHERE owner = 0 AND status = 'unset'
LIMIT 10

SELECT id FROM unsent_emails
WHERE owner = CONNECTION_ID() AND status = 'claimed'
```

尽量少轮询；尽量用UPDATE 代替SELECT FOR UPDATE,减少锁时间，将已经处理和未处理的数据分开，保证数据集大小。

可以将任务队列从数据库迁移出去。Redis、memcached都能使用。以及各种消息队列都可以。

### 计算两点之间距离

两点之间的记录公式
```mysql
ACOS(COS(latA) * COS(latB) * COS(lonA - lonB) + SIN(latA) * SIN(latB)) * 地球半径
```
地球半径 3959 英里 或者 6371 千米。

复杂计算可以考虑用近似值先过滤一大批数据。

### 使用用户自定义函数

3500万数据进行XOR计算，来知晓两个数值是否相匹配，直接写好程序，以后台程序的方式运行在分布式服务器，假装是MYSQL完成

也就是通过业务来进行转换，避免技术难点。

# MYSQL高级特性

## 分区表

实现方式：对底层封装，索引也是按照分区的子表定义，没有全局索引，Oracle就可以定义索引和表是否进行分区。

应用场景：
- 表非常大无法全部放在内存，或者一部分是热点数据、其他是历史数据
- 分区表的数据更容易维护，可以清楚一个分区数据实现批量删除，排错也更容易定位
- 分区在不同的物理设备上，更高效利用硬件设备
- 分区可以避免锁竞争
- 备份和恢复独立分区

限制：
- 一个表最多1024个分区
- 分区必须包含所有的主键和唯一索引
- 分区表无法使用外键，

### 分区表的原理

通过句柄操作底层表，普通表和分区表从存储引擎角度看都一样。

操作逻辑如下：
- insert
锁定所有底层表，确定哪个分区接手之后，再写入
- update
先锁住所有底层表，然后找到需要更新的记录的分区，读出并更新后，再判断数据应该放在哪个分区，最后再写入并删除原来的数据。
- select
先锁住所有底层表，然后优化器判断是否可以过滤部分分区，最后调用存储引擎接口访问各个分区数据。
- delete
锁住所有底层表，确定分区后，删除指定记录。

### 分区表的类型

分区表达式可以是列，也可以是列的表达式

```mysql
CREATE TABLE sales(
    order_date DATETIME NOT NULL,
    ……
) ENGINE = InnoDB PARTITION BY RANGE(YEAR(order_date))(
    PARTITION p_2010 VALUES LESS THAN (2010),
    PARTITION p_2010 VALUES LESS THAN (2011),
    PARTITION p_2010 VALUES LESS THAN (2012),
    PARTITION p_catchall VALUES LESS THAN MAXVALUE );
  )
```

### 如何使用分区表

假如从一个非常大的表中查询一段时间的记录，表中包含了很多历史数据。如查询几个月数据，大概有10亿数据，

方案：数据量巨大，肯定不能每次查询都扫全表。考虑索引维护的时间和空间的消耗，不希望使用索引。即使用了索引也会大量碎片，最终导致一个插叙导致很多随机IO，程序卡死。
在数据量超大的时候，B+树无法起作用，除非使用覆盖索引，否则也会回表，导致随机IO，响应很慢。同时维护索引代价也非常高。所以可以使用分区，
将数据放在分区内，之后定位分区之后进行全表扫描，并将热点分区进行缓存即可。

### 什么情况有问题

能够快速定位分区且分区本身不消耗很多。

- NULL 导致分区过滤无效

```mysql
# 如果ORDER_DATE是null，那么会把所有null都放在一个独立分区
PARTITION BY RANGE YEAR(order_date) 
```

当查询语句 WHERE order_date BETWEEN '2012-01-01' AND '2012-01-31'的时候就会导致 先去查 2012分区，在查找null分区。

MYSQL5.5已经进行了可以 PARTITION BY RANGE COLUMNS(order_date)解决

- 分区列和索引列不匹配

如果定义的索引列和分区列不匹配，会导致无法进行分区过滤。

- 选择分区成本可能很高

在查找分区数据的时候，尤其是范围分区，服务器查找一批数据分别属于哪些分区，效率不高。建议分区不超过100个。

键分区和哈希分区就没有这样的问题

- 打开并锁住所有底层表的成本可能很高

在过滤前需要锁住所有表是一种额外开销。 可以批量操作或者限制分区个数。

- 维护分区的成本可能很高

在重组分区或者ALERT的时候，需要把数据复制到临时分区后删除原分区。

### 查询优化

可以通过WHERE中加入分区列进行过滤分区，减少扫描的数据。

`EXPLAIN PARTITION`可以观察是否执行了分区过滤。

```mysql
EXPLAIN PARTITIONS SELECT * FROM sales_by_day WHERE day > '2011-01-1'
```

分区列不管在分区的时候使用的是分区列本身还是分区列的函数，在WHERE过滤的时候都只能使用分区列本身，否则不起作用，跟索引一样。
在表关联的时候，如果分区表是第二张表，那么优化器会只对这些匹配的分区中的行进行关联。

### 合并表
合并表和子表结构必须完全一致。子表主键的限制，合并表在全局上不受限制，会出现重复的键VALUE.

```mysql
CREATE TABLE t1(a INT NOT NULL PRIMARY KEY) ENGINE = MyISAM;
CREATE TABLE t2(a INT NOT NULL PRIMARY KEY) ENGINE = MyISAM;
INSERT INTO t1(a) VALUES(1),(2);
INSERT INTO t2(a) VALUES(1),(2);
CREATE TABLE mrg(a INT NOT NULL PRIMARY KEY) 
    ENGINE = MyISAM UNION=(t1,t2) INSERT_METHOD = LAST;
SELECT a FROM mrg;
```
往合并表插入的时候，最后会同步插入到LAST表中，也就是t2中。

删除合并表，不会影响子表；但是删除子表，合并表如果不是打开着就会丢失一部分数据。

查询的时候其实会在扫描所有子表，直到直到数据。

- 一个MyISAM表可以是多个合并表的子表
- 合并表中可以新添加子表，直接修改合并表就可以了
- 可以合并表中只包含感兴趣的数据列，分区表不行
- 可以使用myisampack来压缩所有表。
- 对子表备份、恢复、修改和修复的时候，可以将子表从合并表删除，操作完成后再把子表添加回去。

## 视图

EXPLAIN的select_type 为 ”DERIVED“

创建视图

```mysql
CREATE VIEW Oceania AS SELECT * FROM Country WHERE Continent = 'Oceania'
WITH CHECK OPTION ;
```

### 可更新视图

可更新视图：更新视图的时候更新视图涉及的相关表。如果视图定义中包含了GROUP BY 、UNION 、聚合函数、其他一些特殊情况，就不能被更新了。

```mysql
UPDATE Oceania SET population = population * 1.1 WHERE Name = 'Australia'
```

这里的WITH CHECK OPTION 使得更新和插入都要符合WHERE的限制，不能去变化Continent，

### 视图对性能的影响






# 操作系统和硬件优化


















