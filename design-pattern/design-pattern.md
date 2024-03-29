设计模式学习


# 前言

Java学习过程中遇到的一些书中提及的设计模式汇总

# 设计模式

- 创建型（5个）：解决对象创建问题。
  - 单例模式 
  - 工厂方法模式 
  - 抽象工厂模式 
  - 建造者模式 
  - 原型模式
- 结构型（7个）：一些类或对象组合在一起的经典结构。
  - 代理模式
  - 装饰模式 
  - 适配器模式 
  - 组合模式 
  - 享元模式 
  - 外观模式 
  - 桥接模式
- 行为型（11个）：解决类或对象之间的交互问题。 
  - 策略模式 
  - 模板方法模式 
  - 责任链模式 
  - 观察者模式 
  - 迭代子模式
  - 命令模式 
  - 备忘录模式 
  - 状态模式 
  - 访问者模式 
  - 中介者模式 
  - 解释器模式

## 模板模式

## 代理模式

### 角色
- 抽象主题（Subject）：定义了代理和真实主题（原始对象）之间的共同接口。代理对象和真实对象都要实现这个接口，以保证代理对象能够替代真实对象。
- 真实主题（Real Subject）：也称为被代理对象，是具体业务逻辑的实际执行者。
- 代理（Proxy）：代理对象持有对真实主题的引用，并在必要时将客户端的请求转发给真实主题。代理还可以在转发请求前后进行一些额外操作，如权限验证、缓存、延迟加载等。

### 优点
- 远程代理：通过代理对象可以隐藏真实对象存在于远程服务器的事实，使得客户端可以像访问本地对象一样访问远程对象。这对于构建分布式系统或者客户端与服务器之间的网络通信非常有用。
- 虚拟代理：当创建一个对象需要很大开销时，可以使用代理对象暂时代替真实对象，只有在真正需要时才创建和加载真实对象。这样可以提高系统的性能并节省资源。
- 安全代理：代理对象可以控制对真实对象的访问权限，例如在调用真实对象方法前进行身份验证或者权限检查，确保只有符合条件的客户端才能访问真实对象。

### 缺点
- 增加复杂性：引入代理对象会增加系统的复杂性，需要额外的代码来管理代理与真实对象之间的关系。如果过度使用代理模式，可能会导致系统设计变得混乱，并且难以理解和维护。
- 性能损失：由于代理对象需要进行额外的操作，如网络通信、权限验证等，可能会导致性能上的一定损失。

### 具体实现

在代码实现上，包括两种形式：
- 静态代理
- 动态代理

#### 静态代理

假设项目已上线，并且运行正常，只是客户反馈系统有一些地方运行较慢，要求项目组对系统进行优化。于是项目负责人就下达了这个需求。  
首先需要搞清楚是哪些业务方法耗时较长，于是让我们统计每个业务方法所耗费的时长。如果是你，你该怎么做呢？

- 第一种方案：直接修改Java源代码，在每个业务方法中添加统计逻辑，需求可以满足，但显然是违背了OCP开闭原则。这种方案不可取。
- 第二种方案：编写一个子类继承OrderServiceImpl，在子类中重写每个方法，这种方式可以解决，但是存在两个问题：
  - 第一个问题：假设系统中有100个这样的业务类，需要提供100个子类，并且之前写好的创建Service对象的代码，都要修改为创建子类对象。
  - 第二个问题：由于采用了继承的方式，导致代码之间的耦合度较高。
- 采用[代理模式](src/main/java/com/nju/proxy/stati/OrderServiceProxy.java)，符合OCP开闭原则，同时采用的是关联关系，所以程序的耦合度较低。所以这种方案是被推荐的。
  - 如果系统中业务接口很多，一个接口对应一个代理类，显然也是不合理的，会导致类爆炸

#### 动态代理

在程序运行阶段，在内存中动态生成代理类，被称为动态代理，目的是为了减少代理类的数量。解决代码复用的问题。以后程序员只需要关注核心业务的编写了，像这种统计时间的代码根本不需要关注。因为这种统计时间的代码只需要在调用处理器中编写一次即可。

在内存当中动态生成类的技术常见的包括：
- JDK动态代理技术：只能代理接口。
- CGLIB动态代理技术：可以在运行期扩展Java类与实现Java接口。它既可以代理接口，又可以代理类，底层是通过继承的方式实现的。性能比JDK动态代理要好。（底层有一个小而快的字节码处理框架ASM。）

##### JDK动态代理

在动态代理中代理类是可以动态生成的。这个类不需要写。我们直接写[客户端](src/main/java/com/nju/proxy/dynamic/jdk/Client.java)即可。

`OrderService orderServiceProxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces()`, 调用处理器对象);
这行代码做了两件事：
- 第一件事：在内存中生成了代理类的字节码
- 第二件事：创建代理对象
Proxy类全名：java.lang.reflect.Proxy。这是JDK提供的一个类（所以称为JDK动态代理）。主要是通过这个类在内存中生成代理类的字节码。其中newProxyInstance()方法有三个参数：
- 第一个参数：类加载器。在内存中生成了字节码，要想执行这个字节码，也是需要先把这个字节码加载到内存当中的。所以要指定使用哪个类加载器加载。
- 第二个参数：接口类型。代理类和目标类实现相同的接口，所以要通过这个参数告诉JDK动态代理生成的类要实现哪些接口。
- 第三个参数：调用处理器。这是一个JDK动态代理规定的接口，接口全名：java.lang.reflect.InvocationHandler。显然这是一个回调接口，也就是说调用这个接口中方法的程序已经写好了，就差这个接口的实现类了。

##### CGLIB动态代理

[CGLIB](src/main/java/com/nju/proxy/dynamic/cglib/Client.java)既可以代理接口，又可以代理类。底层采用继承的方式实现。所以被代理的目标类不能使用final修饰。高版本需要添加两个启动参数
- --add-opens java.base/java.lang=ALL-UNNAMED
- --add-opens java.base/sun.net.util=ALL-UNNAMED

```xml
<dependency>
  <groupId>cglib</groupId>
  <artifactId>cglib</artifactId>
  <version>3.3.0</version>
</dependency>
```

## 装饰模式

## 工厂模式

参见 factory 模块

### [简单工厂模式](src/main/java/com/nju/factory/simple/WeaponFactory.java)


简单工厂模式的优点：

- 客户端程序不需要关心对象的创建细节，需要哪个对象时，只需要向工厂索要即可，初步实现了责任的分离。客户端只负责“消费”，工厂负责“生产”。生产和消费分离。

简单工厂模式的缺点：
- 缺点1：工厂类集中了所有产品的创造逻辑，形成一个无所不知的全能类，有人把它叫做上帝类。显然工厂类非常关键，不能出问题，一旦出问题，整个系统瘫痪。
- 缺点2：不符合OCP开闭原则，在进行系统扩展时，需要修改工厂类。

Spring中的BeanFactory就使用了简单工厂模式。

### [工厂模式](src/main/java/com/nju/factory/real/AbstractWeaponFactory.java)

如果想扩展一个新的产品，只要新增一个产品类，再新增一个该产品对应的工厂即可，例如新增：匕首。不需要修改之前的源代码，显然工厂方法模式符合OCP原则。

工厂方法模式的优点：
- 一个调用者想创建一个对象，只要知道其名称就可以了。
- 扩展性高，如果想增加一个产品，只要扩展一个工厂类就可以。
- 屏蔽产品的具体实现，调用者只关心产品的接口。
工厂方法模式的缺点：
- 每次增加一个产品时，都需要增加一个具体类和对象实现工厂，使得系统中类的个数成倍增加，在一定程度上增加了系统的复杂度，同时也增加了系统具体类的依赖。这并不是什么好事。


### [抽象工厂模式](src/main/java/com/nju/factory/abstractfactory/WeaponFactory.java)

原来的工厂模式不只能生产单一的商品，无法解决生产多个商品的问题，抽象工厂就是为了解决产品簇问题。

抽象工厂模式的优缺点：
- 优点：当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象。
- 缺点：产品族扩展非常困难，要增加一个系列的某一产品，既要在AbstractFactory里加代码，又要在具体的里面加代码。



## 单例模式

## 命令模式

## 映射器模式
