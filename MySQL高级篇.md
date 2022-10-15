# MySQL高级篇

## Linux安装MySQL及使用

### 虚拟机安装

#### 	虚拟机克隆

- 

![image-20220821234407277](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220821234407277.png)

- 修改mac地址

  ![image-20220821234505004](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220821234505004.png)

-  修改主机名称

  ```bash
  vim /etc/hostname
  ```

- 修改ip地址

  - 查看虚拟网络配置

    ![image-20220822003533173](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220822003533173.png)

    ![image-20220822003626008](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220822003626008.png)

  - 

  ```bash
  vim /etc/sysconfig/network-scripts/ifcfg-ens33
  
  TYPE="Ethernet"
  PROXY_METHOD="none"
  BROWSER_ONLY="no"
  BOOTPROTO="static"
  DEFROUTE="yes"
  IPV4_FAILURE_FATAL="no"
  IPV6INIT="yes"
  IPV6_AUTOCONF="yes"
  IPV6_DEFROUTE="yes"
  IPV6_FAILURE_FATAL="no"
  IPV6_ADDR_GEN_MODE="stable-privacy"
  NAME="ens33"
  UUID="3c1015a7-c7a1-4630-b218-5fad9d37074e"
  DEVICE="ens33"
  ONBOOT="yes"
  # ip address 上述查看的结果进行配置
  IPADDR=192.168.137.130
  NETMASK=255.255.255.0
  # gateway
  GATEWAY=192.168.157.2
  # DNS1
  DNS1=192.168.137.2
  ```

- 修改UUID

- 重启网络

  - 重启

    ```bash
    service network restart 
    ```

  - Redirecting to /bin/systemctl restart network.service
    Failed to restart network.service: Unit network.service not found.

    ```bash
    service network-manager restart
    ```

  - Failed to restart network-manager.service: Unit network-manager.service not

    ```bash
    nmcli c reload
    nmcli networking off
    nmcli networking on
    ```

  - clone之后没有ens33

    ```bash
    ifconfig ens33 主机名
    ```

#### mysql安装

- 官网安装mysql，传送到opt目录下

  ![image-20220822221716841](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220822221716841.png)

- 修改权限

  ![image-20220822221627306](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220822221627306.png)

- sql 初始化

  ```bash
  # 初始化mysql
  mysqld --initialize --user=mysql
  # 查看初始密码
  cat /var/log/mysqld.log 
  # 查看sql状态
  systemctl status mysql
  # 启动mysql
  systemctl start mysqld
  # 查看mysql是否开机启动
  systemctl list-unit-files|grep mysqld.service
  # 设置开机启动
  systemctl enable mysqld.service
  ```

- 连接远程mysql：无法连接

  ![image-20220822230817765](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220822230817765.png)

  - 网络没开启

  - 未开启管道操作：开启Telnet

    ![image-20220822230743982](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220822230743982.png)

    

  - 防火墙未关闭

    ```bash
    # 查看防火墙状态
    systemctl status firewalld
    # 关闭防火墙
    systemctl stop firewolld
    # 开机关闭防火墙
    systemctl disable firewalld
    ```

  - 设置数据库连接允许的ip

    ```bash
    update user set host='%' where user = 'root';
     ALTER USER 'root'@'%' IDENTIFIED BY 'root';
    # 设置密码 不然8.0登不上
    alter user 'root'@'%' identified with mysql_native_password by 'root';
    # 刷新
    flush privileges;
    ```

### MYSQL使用

#### 基础命令

```bash
# 退出
quit
# 登录
mysql -uroot -p
# 刷新
flush privileges;
# 使用某张表 
use database;
# 展示数据库
show databases;
# 修改密码的时候显示安全检验
install plugin validate_password SONAME 'validate_password.so';
# 修改密码
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Qyl12097731';
# 显示字符集
show variables like '%character%';
# 修改字符集
vim etc/my.conf
character_set_server=utf8
# 展示支持的存储引擎
show engines：
```

#### 数据库常用命令

```sql
# 创建数据库
create database user;
# 创建表
create table student(id int,sname varchar(20));
# 显示数据表
show create table user;
# 显示数据表
show create databse user;
# 修改数据库字符集 以及 比较规则
alert database user character set 'utf8' collate ‘utf8_general_ci’;
# 修改表的字符集
alert table user convert to character set 'utf8';
或者
alert table user default character set 'utf8' collate 'utf8_general_ci';
# 查看utf-8字符集的比较规则
show collation like 'utf8%';
# 查看数据库的字符其和比较规则
show variablies like '%_database';
# 查看表的字符集和比较规则
show table status from user like '%emp1';
# 登录ip:port上的database_name的数据库
mysql -h ip -p port -p database_name;
# 展示权限
show grants;
# 查看表的所有列 降序
desc user
```

#### 字符集和比较规则（了解）

| 后缀 | 描述             |
| ---- | ---------------- |
| _ai  | 不区分重音       |
| _as  | 区分重音         |
| _ci  | 不区分大小写     |
| _cs  | 区分大小写       |
| _bin | 以二进制方式比较 |

#### 请求到响应过程字符集的变化

| character_set_client     | 服务器解码请求使用的字符集                                   |
| ------------------------ | ------------------------------------------------------------ |
| character_set_connection | 服务器处理请求时会把请求字符串从character_set_client转化为character_set_connection |
| character_set_results    | 服务器向客户端返回数据时使用的字符集                         |

#### 大小写规范

```sql
# 查看大小写是否敏感 1 不敏感 0 敏感
show variables like '%lower_case_table_names%';
```

##### window

不敏感，以小写存储在磁盘，通过小写进行查找

##### linux

1. 数据库名、表名、表的别名、变量名都是区分大小写的
2. 关键字、函数名称在SQL中不区分大小写
3. 列名或者列的别名在所有的情况下都忽略大小写

##### 建议

1. 关键字、函数名都用大写
2. 数据库名、表名、列名字、字段名、字段别名都用小写
3. SQL必须以分号结尾

#### sql_mode的合理设置

##### 宽松模式

插入数据即使是错误数据，也可能会被接受

**应用场景：** 数据库**迁移**

##### 严格模式

插入错误数据报错且回滚

##### 模式查看和设置

```sql
select @@session.sql_mode;
或者
show variables like 'sql_mode';

# 设置sql_mode
SET GLOBAL sql_mode = 'names';
```

## MYSQL的数据目录

### MySQL8的主要目录结构

1. 数据库文件的存放路径（数据目录 datadir） /var/lib/mysql/

   ```sql
   show variables like 'datadir';
   ```

2. 数据库命令目录  /usr/bin/ 和 /usr/sbin

3. 配置文件目录 /usr/share/mysql-8.0（命令及配置文件），/etc/mysql （如my.cnf）

### 查看默认数据库

```sql
show databases;
```

1. mysql 

   mysql自带的核心数据库，存放mysql用户账户和权限信息，一些存储过程、事件的定义信息，一些日志、帮助信息和时区信息

2. information_schema

   保存mysql服务器维护的所有其他数据库信息，比如表、视图、触发器、列、索引，提供了innodb_sys 用于表示内部系统表

3. performance_schema 

   监控mysql服务的各类性能指标

4. sys

   通过视图把information_schema和performance_schema 结合起来，进行技术性能的监控。

### 表在文件系统中的表示

- MYSQL5.7

  - db.opt 

    数据库创建时候的一些属性

  - user.frm

    存储表结构

  - user.ibd

    表数据存储在user.ibd或者上层的ibdata1(系统表空间，默认12M)

    ```sql
    # 指定数据存放位置 0 表示系统表空间 1 表示独立表空间
    innod_file_per_table=0 
    ```

    

    ![image-20220823221927844](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220823221927844.png)

  - 非聚簇索引存储

    - user.frm
    - user.MYD + user.MYI ===== user.ibd

- MYSQL8
  - user.ibd 合并了.frm和ibd ，存储表结构和表数据
  - ibdsdi --dump-file=user.txt user.ibd 查看是否.ibd 合并了.frm和ibd 
  - 非聚簇索引
    - user.sdi = user.frm (mysql5.7)
    - user.MYD + user.MYI ===== user.ibd

### 视图在文件系统中的表示

视图：虚拟的表，查询语句的别名 存储在 user_view.frm中

## 用户和权限管理

### 用户管理

分为普通用户和root用户

#### 创建用户

```sql
# 创建用户
create user 'username'@'ip' identified by 'password'
```

#### 修改用户

```sql
update user set user='wang5' where user='lisi' and host = '%';
flush privileges;
```

#### 删除用户

```sql
drop user 'wang5'@`ip`,'lisi'@'ip';
```

#### 设置当前用户密码

```sql
# mysql 5.7
set password = password('123')
# 推荐使用
# 方法1
alert user user() identified by 'root'
# 方法2
set password = 'root'
```

#### 修改其他用户密码

```sql
alert user 'zhang3'@'ip' identified by 'root'
set password for 'zhang3'@'ip' = 'root'
```

#### 密码管理

- 密码过期

  默认default_password_lifetime设置为0，表示禁用密码自动过期，正整数N表示每隔N天进行修改

  配置文件中进行维护

  ```mysql
  [mysqld]
  default_password_lifetime=180 # 全局策略，设置密码180就过期一次
  ```

  ```sql
  # 设置密码过期
  ALERT USER 'zhang3'@'ip' PASSWORD EXPIRE; 
  ```

- 密码重用策略

  - password_history 规定密码重用的数量
  - password_reuse_interval 规定密码重用的周期

### 权限管理

#### 权限授予

```mysql
grant select,update on dbtest1.* to 'zhang3'@'%'
# with grant option 允许把自己的权限授予给别人
grant all privileges on *.* to 'zhang3'@'%' [with grant option] 
```

#### 查看权限

```mysql
# 查看当前用户的权限
show grants;
show grants for current_user;
show grants for current_user();
# 查看某用户的全局权限
show grants for 'user'@'port';
```

#### 回收权限

```sql
revoke select,update on dbtest1.emp1 from 'zhang3'@'%'
revoke all privileges on *.* from 'lisi'@'%'
```

### 权限表

通过权限表来控制用户对数据库的访问，最重要的时user表和db表，还有table_priv、procs_priv、columns_priv。mysql启动时把这些表中权限信息的内容读入内存。

### 访问控制

#### 连接核实

验证账号密码

#### 请求核实

验证权限-->库-->表-->列

### 角色管理

#### 创建角色

```sql
create role 'user_manager'@'%','goods_manager'@'%'
```

#### 赋予权限

```sql
grant all privileges on dbtest.emp1 to 'user_manager'@'%';
```

#### 查看角色的权限

```sql
show grants for 'manager'@'%';
```

#### 回收角色权限

```sql
revoke select on dbtest.emp1 from 'user_manager';
```

#### 删除角色

```sql
drop role 'user_manager'
```

#### 给用户赋予角色

```mysql
grant 'user_manager'@'%' to 'wang5'@'%'
```

#### 激活角色

```sql
# 显示当前角色
select current_role()
# 激活角色
# 方法1
set default role  'user_manager'@'%'  to 'wang5'@'%';
# 方法2 对所有角色永久激活
SET GLOBAL activate_all_roles_on_login=ON; 
```

#### 撤销用户角色

```mysql
revoke 'user_manager'@'%' from 'zhang3'@'%';
```

## 逻辑架构

### mysql的查询流程

#### 查询缓存

如果在查询缓存中发现了这条SQL语句，就直接将结果返回给客户端，否则就进入解析器阶段。因为查询缓存命中率不高，所以查询优化的效率不高，已经在8.0之后抛弃了。

同时，如果查询请求包含某些系统函数、用户自定义变量和函数、一些系统表，如MySQL、information_schema、performance_schema数据库中的表，那这个请求就不会呗缓存。以某些系统函数，比如NOW，每次调用都会产生最新的时间，查询请求没变，但是NOW变了，那么在第二次请求的时候就会把第一次查询的结果返回，这是错误的。

此外，如果对表结构或者表数据进行了修改，那么该表所有的高速缓存查询都将变为无效并从高速缓存中删除，对于更新频繁的数据库来说，查询缓存的命中率极低

**总之，查询缓存弊大于利。建议在静态表中使用查询缓存，也就是极少更新的表，如系统配置表、字典表。**

```sql
# 0 关闭缓存 1 开启缓存 2 DEMAND 按需开启，只有sql语句由SQL_CACHE 才开启
query_cache_type = 2
# DEMAND 开启
select SQL_NO_CACHE * from test where ID = 5;
# 查询缓存命中率
show status like '%Qcache%';
```

#### 解析器

没有命中查询缓存，就执行SQL语句。首先进行解析，对SQL进行词法分析，识别MYSQL语句中的各个词的含义；接着语法分析，查看是否SQL语句满足MYSQL语法。如果语法不对，就会报错'you have an error in your SQL syntax';

#### 优化器

确定SQL语句的执行路径，例如选择全表检索还是索引检索。通过优化器来选择最好的执行计划。

优化器可以分为逻辑查询优化和物理查询优化。

- 物理查询优化就是通过索引和表连接方式等技术进行优化。
- 逻辑查询优化就是通过SQL等价变换提升查询效率，就是换一种查询写法来执行

#### 执行器

执行前判断该用户是否具备权限。具备权限的情况下就调用执行引擎API，然后执行引擎来调用底层文件系统。

#### 总结

执行流程：

**SQL语句  ------ 查询缓存 ----- 解析器   ----- 优化器    ----- 执行器**

### MYSQL8中的SQL执行原理

#### 确认profiling是否开启

```mysql
# 查看时否开启计划，开启后可以让MYSQL手机SQL执行时所用资源的情况 
# 0 关闭 1 开启
select @@profiling;
show variables like 'profiling';
# 开启 profiling
set profiling = 1;
# 查看运行情况，多条
show profiles;
# 查看最新的SQL详细的运行情况
show profile;
# 查看第七个MYSQL语句运行详细情况
show profile，cpu,block io for query 7;
```

### Oracle的执行流程

Oracle采用共享池来判断SQL语句是否存在缓存和执行计划，以此来选择硬解析还是软解析。

SQL在Oracle中的执行过程：

SQL语句 --- > 语法检查 ---- > 语义检查 ----> 权限检查 ----- > 共享池坚持  -----> 硬解析 ----- > 优化器

​																															     ↓						↓

​																																软解析--------> 执行

- 语法检查：查看是否拼写正确
- 语义检查：哈看访问对象是否存在，表、列等
- 权限检查：查看是否具备数据权限
- 共享池检查：最主要是缓存SQL语句和该语句的执行计划
  - SQL经过hash运算之后，在库缓存中查找到了SQL的执行计划，就直接拿来执行，这就是软解析
  - 如果没有找到SQL语句和执行计划，就要创建解析树进行解析，生成执行计划，进入优化器，这就是硬解析
- 优化器：进行硬解析，生成最优执行计划
- 执行器：把SQL进行执行

**共享池**:包括库缓存、数据字典缓冲区，其中数据字典缓冲区存储Oracle的对象定义，比如表、视图、索引等对象，当对SQL进行解析的时候，如果需要相关数据，就会从数据字典缓冲区提取。

绑定变量：把参数变成变量，如一条SQL语句

```sql
select * from user where uid = '100';
# 绑定变量方式
select * from user where uid = :uid;
```

这样uid变化之后，还是会采用相同的执行计划，这样就减少了硬解析，减少了Oracle的解析工作量。

### 数据库的缓冲池

#### 缓冲池简介

占用内存作为数据缓冲池，把一些最常用的页放在内存中的缓冲池，减少内外存之间的I/O

**预读**：把一些常用数据页的临近页也放入缓冲池

#### 缓冲池大小设置

- MyISAM的缓冲池对应的缓存参数为 `key_buffer_size`

- InnoDB的缓冲池对应的缓存参数为 `innodb_buffer_pool_size`

  ```sql
  # 查看缓冲池大小
  show variables like 'innodb_buffer_pool_size';
  # 设置
  set global innodb_buffer_pool_size = size
  ```

#### 多个Buffer Pool实例

连续的存储内存空间，在多线程情况下，访问Buffer Pool需要枷锁处理，在多线程高并发的情况下 ，可以把Buffer Pool划分成多个小Buffer Pool，每个Buffer Pool都是一个实例，独立运行会不影响，提高并发能力。

设置Buffer Pool的个数

```sql
# 启动服务器时进行设置
[Server]
innodb_buffer_pool_instances = 2
```

## 存储引擎

```sql
# 查看默认的存储引擎
show variables like '%storage_engines%'
# 或
select @@default_storage_engine;
# 修改默认存储引擎
set default_storage_engine = MyISAM;
# 指定表的存储引擎
create table emp2(id int) engine=InnodB;
alert table emp2 engine = InnodB;
```

my.cnf配置文件设置存储引擎,**记得重启**

```sql
default-storage-engine=MyISAM
```

### 引擎介绍

#### InnoDB引擎

- 具备外键支持功能的事务存储引擎
- 默认事务性引擎，被设计用来处理大量的短期事务，可以确保事务的完整提交和回滚
- 除了增加和查询外，还需要更新、删除操作，应该优先选择InnoDB引擎。
- 为了处理巨大数据量的最大性能设计
- 行锁，不影响其他行，适合高并发的操作
- 对内存的要求较高，对写的处理效率较差

#### MyISAM引擎：主要的非事务处理存储引擎

- 提供了全文索引、压缩、空间函数（GIS）等，但是MyISAM不支持事务、行级锁、外键，最致命的是崩溃后无法安全恢复
- 访问速度快，对事务完整性没有要求或者以select、insert为主的应用
- 针对数据统计的由额外的常数存储，所以count(*)查询效率很高
- 数据文件结构
- 适合小型应用，简单业务，占用内存空间小
- 由于表锁，所以并发性差

#### 









