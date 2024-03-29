# 前言

主要是去对Spring的回顾，只做简单的记录

# 知识总结

## DIP、OCP、IOC

### DIP

依赖倒置原则(Dependence Inversion Principle)，简称DIP，主要倡导面向抽象编程，面向接口编程，不要面向具体编程，让上层不再依赖下层，下面改动了，  
上面的代码不会受到牵连。这样可以大大降低程序的耦合度，耦合度低了，扩展力就强了，同时代码复用性也会增强。

### OCP

对扩展开放，就修改关闭。简单说就是新增加变化没有问题，但是不应该去影响已有的代码

### IOC

控制反转（Inversion of Control，缩写为IoC），是面向对象编程中的一种设计思想，可以用来降低代码之间的耦合度，符合依赖倒置原则。
控制反转的核心是：将对象的创建权交出去，将对象和对象之间关系的管理权交出去，由第三方容器来负责创建与维护。
控制反转常见的实现方式：依赖注入（Dependency Injection，简称DI），包括两种方式：
- set方法注入
- 构造方法注入

Spring框架就是一个实现了IoC思想的框架。

## Spring

### 核心模块

主要是IOC和AOP：

<b>
- 基于POJO的轻量级和最小侵入性编程
- 通过IOC、AOP实现松耦合
- 基于切面和管理进行声明式编程
- 通过切面和模版减少样板式代码
</b>

1. Spring Core模块
这是Spring框架最基础的部分，它提供了依赖注入（DependencyInjection）特征来实现容器对Bean的管理。核心容器的主要组件是 BeanFactory，BeanFactory是工厂模式的一个实现，是任何Spring应用的核心。它使用IoC将应用配置和依赖从实际的应用代码中分离出来。
2. Spring AOP模块
Spring在它的AOP模块中提供了对面向切面编程的丰富支持，Spring AOP 模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖组件，就可以将声明性事务管理集成到应用程序中，可以自定义拦截器、切点、日志等操作。

### Bean注入简述

1. 通过applicationContext（spring容器）进行获取

    bean 配置文件,其中bean的id是唯一标识
    ```shell script
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        
        <bean id="userBean" class="com.nju.spring6.bean.User"/>
    </beans>
    ```

    程序编写 可以通过类路径、系统文件路径，BeanFactory（上下文的超类）进行实现，
    ```java
        // 初始化Spring容器上下文（解析beans.xml文件，创建所有的bean对象）
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        // 根据id获取bean对象
        Object userBean = applicationContext.getBean("userBean");
        System.out.println(userBean);
        
        // 通过系统文件路径的方式进行获取，并且转换成指定的类型
        ApplicationContext applicationContext2 = new FileSystemXmlApplicationContext("d:/spring6.xml");
        Vip vip = applicationContext2.getBean("vipBean2", Vip.class);
        System.out.println(vip);
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring.xml");
        Object vipBean = beanFactory.getBean("vipBean");
        System.out.println(vipBean);
    ```

   上述过程默认通过 User 的无参构造函数进行对象的创建。是通过反射进行创建的：
   ```java
       // dom4j解析beans.xml文件，从中获取class的全限定类名
        // 通过反射机制调用无参数构造方法创建对象
        Class clazz = Class.forName("com.xxx.xxx.bean.User");
        Object obj = clazz.newInstance();
   ```
2. 创建之后，通过Map<String,Object>存储,后续其实就是通过map进行对象的获取。IOC其实就是通过xml+工厂模式+反射进行实现的
![image](images/image.png)

### 日志框架

1. 引入依赖
    ```xml
    <!--log4j2的依赖-->
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-core</artifactId>
      <version>2.19.0</version>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-slf4j2-impl</artifactId>
      <version>2.19.0</version>
    </dependency>
    ```

2. 在类的根路径下提供log4j2.xml配置文件（文件名固定为：log4j2.xml，文件必须放到类根路径下。）
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    
    <configuration>
    
        <loggers>
            <!--
                level指定日志级别，从低到高的优先级：
                    ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF
            -->
            <root level="DEBUG">
                <appender-ref ref="spring6log"/>
            </root>
        </loggers>
    
        <appenders>
            <!--输出日志信息到控制台-->
            <console name="spring6log" target="SYSTEM_OUT">
                <!--控制日志输出的格式-->
                <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss SSS} [%t] %-3level %logger{1024} - %msg%n"/>
            </console>
        </appenders>
    
    </configuration>
    ```

3. 日志使用
    ```java
    Logger logger = LoggerFactory.getLogger(FirstSpringTest.class);
    logger.info("我是一条日志消息");
    ```

### 依赖注入方式

#### set注入
Spring会利用反射调用相应的set方法，并将注入的参数传递给该方法。这种方式使得Spring能够在运行时动态地将依赖注入到目标对象中。

1. set注入
    ```java
    private UserDao userDao;
    private String[] goods;
    private List<String> names;
    // 一个人有多个住址
    private Map<Integer, String> addrs;
    private UserDao aaa;
    private AccountDao accountDao;

    // 这个set方法非常关键 按名字自动注入 必须要
    public void setAaa(UserDao aaa) {
        this.aaa = aaa;
    }
    
    // 这个set方法非常关键 按类型自动注入 必须要
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setAddrs(Map<Integer, String> addrs) {
        this.addrs = addrs;
    }
    
    public void setNames(List<String> names) {
        this.names = names;
    }
    // 使用set方式注入，必须提供set方法。
    // 反射机制要调用这个方法给属性赋值的。
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public void setGoods(Goods[] goods) {
        this.goods = goods;
    }
    ```

2. xml配置
    ```xml
    <bean id="userDaoBean" class="com.nju.spring6.dao.UserDao"/>
    <!--自动注入-->
    <!--byType表示根据类型自动装配-->
    <bean id="accountService" class="com.nju.spring6.service.AccountService" autowire="byType"/>
    <bean class="com.nju.spring6.dao.AccountDao"/>
    
    <!--byName表示根据类名字自动装配-->
    <bean id="userService" class="com.nju.spring6.service.UserService" autowire="byName"/>
    <bean id="aaa" class="com.nju.spring6.dao.UserDao"/>
    
    <!--手动注入-->
    <bean id="userServiceBean" class="com.nju.spring6.service.UserService">
        <property name="addrs">
            <map>
                <!--如果key不是简单类型，使用 key-ref 属性-->
                <!--如果value不是简单类型，使用 value-ref 属性-->
                <entry key="1" value="北京大兴区"/>
                <entry key="2" value="上海浦东区"/>
                <entry key="3" value="深圳宝安区"/>
            </map>
        </property>
         <property name="names">
            <list>
                <value>铁锤</value>
                <value>张三</value>
                <value>张三</value>
                <value>张三</value>
                <value>狼</value>
            </list>
        </property>
        <property name="userDao" ref="userDaoBean"/>
        <property name="goods">
            <array>
                <!--这里使用ref标签即可-->
                <ref bean="goods1"/>
                <ref bean="goods2"/>
            </array>
        </property>
        
        
    </bean>
    ```

#### 构造注入
1. 构造注入
    ```java
    private OrderDao orderDao;

    // 通过反射机制调用构造方法给属性赋值
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    } 
    ```
2. xml 配置
    ```xml
    <bean id="orderDaoBean" class="com.nju.spring6.dao.OrderDao"/>
    <bean id="orderServiceBean" class="com.nju.spring6.service.OrderService">
      <!--index="0"表示构造方法的第一个参数，将orderDaoBean对象传递给构造方法的第一个参数。-->
      <constructor-arg index="0" name="orderService" ref="orderDaoBean"/>
    </bean>
    ```

#### 通过util进行复用

1. xml配置头部util

    xmlns:util="http://www.springframework.org/schema/util"

    http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">

    ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns:util="http://www.springframework.org/schema/util"
               xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                   http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
        
            <util:properties id="prop">
                <prop key="driver">com.mysql.cj.jdbc.Driver</prop>
                <prop key="url">jdbc:mysql://localhost:3306/spring</prop>
                <prop key="username">root</prop>
                <prop key="password">123456</prop>
            </util:properties>
        
            <bean id="dataSource1" class="com.nju.spring6.beans.MyDataSource1">
                <property name="properties" ref="prop"/>
            </bean>
        
            <bean id="dataSource2" class="com.nju.spring6.beans.MyDataSource2">
                <property name="properties" ref="prop"/>
            </bean>
        </beans> 
    ```

2. 程序编写
    ```java
        public class MyDataSource2 {
            private Properties properties;
        
            public void setProperties(Properties properties) {
                this.properties = properties;
            }
        
            @Override
            public String toString() {
                return "MyDataSource2{" +
                        "properties=" + properties +
                        '}';
            }
        }
        
        public class MyDataSource1 {
            private Properties properties;
        
            public void setProperties(Properties properties) {
                this.properties = properties;
            }
        
            @Override
            public String toString() {
                return "MyDataSource1{" +
                        "properties=" + properties +
                        '}';
            }
        }
    ```

#### 引入外部属性配置文件
1. 程序编写
``` java
  public class MyDataSource implements DataSource {
     private String driver;
     private String url;
     private String username;
     private String password;
   
     public void setDriver(String driver) {
         this.driver = driver;
     }
   
     public void setUrl(String url) {
         this.url = url;
     }
   
     public void setUsername(String username) {
         this.username = username;
     }
   
     public void setPassword(String password) {
         this.password = password;
     }
  }
```

2. properties配置

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/spring
username=root
password=root123
```

3. 数据源配置

```properties
    <context:property-placeholder location="jdbc.properties"/>
    
    <bean id="dataSource" class="com.nju.spring6.beans.MyDataSource">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
    </bean>
```

## Bean的作用域

1. singleton

   Bean对象默认是单例的.默认都是相同的
   
   ```java
   public class SpringBean {
       public SpringBean() {
           System.out.println("SpringBean的无参数构造方法执行。");
       }
   }
   
   @Test
   public void testScope(){
      ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-scope.xml");
   }
   ```
   
   <img src="./images/1688660536460.jpg"/>
   
   通过测试得知，默认情况下，Bean对象的创建是在初始化Spring上下文的时候就完成的。

2. prototype

   在每一次执行getBean()方法的时候创建Bean对象，调用几次则创建几次。
   
   ```properties
   <bean id="sb" class="com.nju.spring6.beans.SpringBean" scope="prototype" />
   ```
   
   ```java
   @Test
   public void testScope(){
      ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-scope.xml");
   }
   ```
   
   这一次在初始化Spring上下文的时候，并没有创建Bean对象。不会打印任何东西。

3. 其他scope

   scope属性的值不止两个，它一共包括8个选项：
   - singleton：默认的，单例。 
   - prototype：原型。每调用一次getBean()方法则获取一个新的Bean对象。或每次注入的时候都是新对象。 
   - request：一个请求对应一个Bean。仅限于在WEB应用中使用。 
   - session：一个会话对应一个Bean。仅限于在WEB应用中使用。 
   - global session：portlet应用中专用的。如果在Servlet的WEB应用中使用global session的话，和session一个效果。（portlet和servlet都是规范。servlet运行在servlet容器中，例如Tomcat。portlet运行在portlet容器中。）
   - application：一个应用对应一个Bean。仅限于在WEB应用中使用。 
   - websocket：一个websocket生命周期对应一个Bean。仅限于在WEB应用中使用。 
     - 自定义scope：很少使用。参见

## Bean实例华方法

Spring为Bean提供了多种实例化方式，通常包括4种方式。（也就是说在Spring中为Bean对象的创建准备了多种方案，目的是：更加灵活）
- 第一种：通过构造方法实例化
- 第二种：通过简单工厂模式实例化
- 第三种：通过factory-bean实例化
- 第四种：通过FactoryBean接口实例化

### 构造方法

```java
public class User {
    public User() {
        System.out.println("User类的无参数构造方法执行。");
    }
}
```

```xml
    <bean id="userBean" class="com.nju.spring6.bean.User"/>
```

### 工厂模式

```java
public class Vip {
}

public class VipFactory {
    public static Vip get(){
        return new Vip();
    }
}

```

需要指定factory-method的方法
```xml
<bean id="vipBean" class="com.nju.spring6.bean.VipFactory" factory-method="get"/>
```


### factory-bean方式

这种方式本质上是：通过工厂方法模式进行实例化。

```java
public class Order {
}
public class OrderFactory {
    public Order get(){
        return new Order();
    }
}
```

在Spring配置文件中指定factory-bean以及factory-method
```xml
<bean id="orderFactory" class="com.nju.spring6.bean.OrderFactory"/>
<bean id="orderBean" factory-bean="orderFactory" factory-method="get"/>
```

### 通过FactoryBean接口实例化

FactoryBean在Spring中是一个接口。被称为“工厂Bean”。“工厂Bean”是一种特殊的Bean。所有的“工厂Bean”都是用来协助Spring框架来创建其他Bean对象的。

```java
public class Person {
}

public class PersonFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        // true表示单例
        // false表示原型
        return true;
    }
}
```

在Spring配置文件中配置FactoryBean。

```xml
<bean id="personBean" class="com.nju.spring6.bean.PersonFactoryBean"/>
```


## BeanFactory和FactoryBean的区别

### BeanFactory

Spring IoC容器的顶级对象，BeanFactory被翻译为“Bean工厂”，在Spring的IoC容器中，“Bean工厂”负责创建Bean对象。
BeanFactory是工厂。

### FactoryBean

它是一个Bean，是一个能够辅助Spring实例化其它Bean对象的一个Bean。在Spring中，Bean可以分为两类：
- 第一类：普通Bean
- 第二类：工厂Bean（记住：工厂Bean也是一种Bean，只不过这种Bean比较特殊，它可以辅助Spring实例化其它Bean对象。）

## Bean的生命周期

[Bean的生命周期](lifecycle/src/main/java/com/nju/spring6)

### 什么是Bean的生命周期

Spring其实就是一个管理Bean对象的工厂。它负责对象的创建，对象的销毁等。所谓的生命周期就是：对象从创建开始到最终销毁的整个过程。

- 什么时候创建Bean对象？
- 创建Bean对象的前后会调用什么方法？
- Bean对象什么时候销毁？
- Bean对象的销毁前后调用什么方法？

### 生命周期五步

Bean生命周期的管理，可以参考Spring的源码：AbstractAutowireCapableBeanFactory类的doCreateBean()方法。Bean生命周期可以粗略的划分为五大步：
- 第一步：实例化Bean
- 第二步：Bean属性赋值
- 第三步：初始化Bean
- 第四步：使用Bean
- 第五步：销毁Bean

```java
public class User {
    private String name;

    public User() {
        System.out.println("1.实例化Bean");
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("2.Bean属性赋值");
    }

    public void initBean(){
        System.out.println("3.初始化Bean");
    }

    public void destroyBean(){
        System.out.println("5.销毁Bean");
    }

}
```

```xml
    <!--
    init-method属性指定初始化方法。
    destroy-method属性指定销毁方法。
    -->
    <bean id="userBean" class="com.nju.spring6.bean.User" init-method="initBean" destroy-method="destroyBean">
        <property name="name" value="zhangsan"/>
    </bean>
```

编写测试代码

```java
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User userBean = applicationContext.getBean("userBean", User.class);
        System.out.println("4.使用Bean");
        // 只有正常关闭spring容器才会执行销毁方法
        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
        context.close();
```

- 第一：只有正常关闭spring容器，bean的销毁方法才会被调用。
- 第二：ClassPathXmlApplicationContext类才有close()方法。
- 第三：配置文件中的init-method指定初始化方法。destroy-method指定销毁方法。

### 生命周期七步

第3步是初始化Bean的时候在初始化前和初始化后添加代码，可以加入“Bean后处理器”。

![img_1](images/img_1.png)

1. 编写一个类实现BeanPostProcessor类，并且重写before和after方法：
```java
public class LogBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Bean后处理器的before方法执行，即将开始初始化");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Bean后处理器的after方法执行，已完成初始化");
        return bean;
    }
}
```
2. 配置xml
```xml
<!--配置Bean后处理器。这个后处理器将作用于当前配置文件中所有的bean。-->
<bean class="com.nju.spring6.bean.LogBeanPostProcessor"/>
```

### 生命周期10步

![img.png](images/截屏2023-07-07%2014.15.19.png)

Aware相关的接口包括：BeanNameAware、BeanClassLoaderAware、BeanFactoryAware
- 当Bean实现了BeanNameAware，Spring会将Bean的名字传递给Bean。
- 当Bean实现了BeanClassLoaderAware，Spring会将加载该Bean的类加载器传递给Bean。
- 当Bean实现了BeanFactoryAware，Spring会将Bean工厂对象传递给Bean。

测试以上10步，可以让User类实现5个接口，并实现所有方法：
- BeanNameAware
- BeanClassLoaderAware
- BeanFactoryAware
- InitializingBean
- DisposableBean

### 不同作用域对应不同的生命周期管理

Spring 根据Bean的作用域来选择管理方式。
- singleton作用域的Bean，Spring 能够精确地知道该Bean何时被创建，何时初始化完成，以及何时被销毁；
- prototype 作用域的 Bean，Spring 只负责创建，当容器创建了 Bean 的实例后，Bean 的实例就交给客户端代码管理，Spring 容器将不再跟踪其生命周期。也就是销毁时候的第9步第10步不会运行


### new的对象交给Spring管理

```java
    // 自己new的对象
    User user = new User();
    System.out.println(user);

    // 创建 默认可列表BeanFactory 对象
    DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
    // 注册Bean
    factory.registerSingleton("userBean", user);
    // 从spring容器中获取bean
    User userBean = factory.getBean("userBean", User.class);
    System.out.println(userBean);
```

## Bean循环依赖


A对象中有B属性。B对象中有A属性。这就是循环依赖

1. 通过测试得知：在singleton + set注入的情况下，循环依赖是没有问题的。Spring可以解决这个问题。在这种模式下，相同于提前曝光自己的地址，后面大家都可以直接用了，因为是单例模式，
所以后面属性赋值之后也能同步。也就是创建之后曝光地址最后赋值。

[RecycleTest.java](recycle/src/main/java/com/nju/spring6/RecycleTest.java)

2. prototype下的set注入产生的循环依赖，需要两个类都是prototype才会出现循环依赖

[RecycleTest.java](recycle/src/main/java/com/nju/spring6/RecycleTest.java)

3. singleton下的构造注入产生的循环依赖

因为构造方法注入会导致实例化对象的过程和对象属性赋值的过程没有分离开，必须在一起完成导致的。


### 解决的机理

![截屏2023-07-07 15.05.57_1](images/截屏2023-07-07%2015.05.57_1.png)

Cache of singleton objects: bean name to bean instance. 单例对象的缓存：key存储bean名称，value存储Bean对象【一级缓存】
Cache of early singleton objects: bean name to bean instance. 早期单例对象的缓存：key存储bean名称，value存储早期的Bean对象【二级缓存】
Cache of singleton factories: bean name to ObjectFactory. 单例工厂缓存：key存储bean名称，value存储该Bean对应的ObjectFactory对象【三级缓存】

从源码中可以看到，spring会先从一级缓存中获取Bean，如果获取不到，则从二级缓存中获取Bean，如果二级缓存还是获取不到，则从三级缓存中获取之前曝光的ObjectFactory对象，  
通过ObjectFactory对象获取Bean实例，这样就解决了循环依赖的问题。

## 手写Spring框架

Spring IoC容器的实现原理：工厂模式 + 解析XML + 反射机制。

[ClassPathXmlApplicationContext](myspring/src/main/java/com/nju/spring6/core/ClassPathXmlApplicationContext.java)

## Spring IoC注解式开发

### 注解回顾

```java
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Component {
    String value();
}
```

Target注解和Retention注解，这两个注解被称为元注解。

Target注解用来设置Component注解可以出现的位置，以上代表表示Component注解只能用在类和接口上。

Retention注解用来设置Component注解的保持性策略，以上代表Component注解可以被反射机制读取。

String value(); 是Component注解中的一个属性。该属性类型String，属性名是value。

#### 通过反射机制读取注解

[注解案例](annotation/src/main/java/com/nju/spring6/Test.java)

### spring中常用的注解 

@Controller、@Service、@Repository这三个注解都是@Component注解的别名。

也就是说：这四个注解的功能都一样。用哪个都可以。
只是为了增强程序的可读性，建议：
- 控制器类上使用：Controller
- service类上使用：Service
- dao类上使用：Repository

他们都是只有一个value属性。value属性用来指定bean的id，也就是bean的名字。

开启bean的注解扫描
```properties
<context:component-scan base-package="com.nju.spring6.bean"/>
```

只想扫描指定的注解
```properties
    <context:component-scan base-package="com.nju.spring6.bean3" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
``` 
use-default-filters="false" 表示：不再spring默认实例化规则，即使有Component、Controller、Service、Repository这些注解标注，也不再实例化。
<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/> 表示只有Controller进行实例化。

use-default-filters="true" 表示：使用spring默认的规则，只要有Component、Controller、Service、Repository中的任意一个注解标注，则进行实例化。
```properties
<context:component-scan base-package="com.nju.spring6.bean3">
   <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
   <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
   <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
```

### 负责注入的注解

#### @Value

@Value注解可以出现在属性上、setter方法上、以及构造方法的形参上。
```java
@Component
public class User {
    @Value(value = "zhangsan")
    private String name;
    @Value("20")
    private int age;
}

@Component
public class User {

   private String name;

   private int age;

   @Value("李四")
   public void setName(String name) {
      this.name = name;
   }

   @Value("30")
   public void setAge(int age) {
      this.age = age;
   }
}

@Component
public class User {

   private String name;

   private int age;

   public User(@Value("隔壁老王") String name, @Value("33") int age) {
      this.name = name;
      this.age = age;
   }
}
```

#### @Autowired与@Qualifier

单独使用@Autowired注解，默认根据类型装配。【默认是byType】

```java
    // 在属性上注入
    @Autowired 
    private UserDao userDao;
    // 出现在setter方法上
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    // 构造方法上.当有参数的构造方法只有一个时，@Autowired注解可以省略。
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    // 标注在构造方法的形参上
    public UserService(@Autowired UserDao userDao) {
        this.userDao = userDao;
    }
```

<b>@Autowired注解和@Qualifier注解联合起来才可以根据名称进行装配，在@Qualifier注解中指定Bean名称。</b>

```java
@Autowired
@Qualifier("userDaoForOracle") // 这个是bean的名字。
```

#### @Resource

- @Resource注解是JDK扩展包中的,@Autowired注解是Spring框架自己的。
- @Resource注解默认根据名称装配byName，未指定name时，使用属性名作为name。通过name找不到的话会自动启动通过类型byType装配。
- @Autowired注解默认根据类型装配byType，如果想根据名称装配，需要配合@Qualifier注解一起用。
- @Resource注解用在属性上、setter方法上。
- @Autowired注解用在属性上、setter方法上、构造方法上、构造方法参数上。

@Resource依赖
```properties
<!--spring 6-->
<dependency>
  <groupId>jakarta.annotation</groupId>
  <artifactId>jakarta.annotation-api</artifactId>
  <version>2.1.1</version>
</dependency>

<!--spring 5-->
<dependency>
   <groupId>javax.annotation</groupId>
   <artifactId>javax.annotation-api</artifactId>
   <version>1.3.2</version>
</dependency>
```

```java
    // 默认就是查找userDao，找不到的时候根据UserDao接口类型查找
    @Resource
    private UserDao userDao;

    // 默认找set方法名中的userDao
    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
```

### 全注解开发

写一个配置类来代替配置文件。

```java
   @Configuration
   @ComponentScan({"com.nju.spring6.dao", "com.nju.spring6.service"})
   public class Spring6Configuration {
   }

// 测试类也相应改变
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Spring6Configuration.class);
    UserService userService = applicationContext.getBean("userService", UserService.class);
    userService.save();
```

## JdbcTemplate

pom依赖
```xml
     <!--新增的依赖:mysql驱动-->
     <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>8.0.30</version>
     </dependency>
     <!--新增的依赖：spring jdbc，这个依赖中有JdbcTemplate-->
     <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-jdbc</artifactId>
         <version>6.0.0-M2</version>
     </dependency>
```

spring.xml
```xml
<bean id="myDataSource" class="com.nju.spring6.jdbc.MyDataSource">
   <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
   <property name="url" value="jdbc:mysql://localhost:3306/spring6"/>
   <property name="username" value="root"/>
   <property name="password" value="123456"/>
</bean>
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <property name="dataSource" ref="myDataSource"/>
</bean>
```

JdbcTemplate中有一个DataSource属性，这个属性是数据源，我们都知道连接数据库需要Connection对象，而生成Connection对象是数据源负责的。
数据源有阿里巴巴的druid连接池，c3p0，dbcp等

### 基础SQL
```java
     // 获取JdbcTemplate对象
     ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
     JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
     // 注意：insert delete update的sql语句，都是执行update方法。

     // 增加
     // 第一个参数：要执行的SQL语句。（SQL语句中可能会有占位符 ? ）
     // 第二个参数：可变长参数，参数的个数可以是0个，也可以是多个。一般是SQL语句中有几个问号，则对应几个参数。
     String sql = "insert into t_user(id,real_name,age) values(?,?,?)";
     int count = jdbcTemplate.update(sql, null, "张三", 30);
     
     // 删除
     String sql = "delete from t_user where id = ?";
     int count = jdbcTemplate.update(sql, 1);
     
     // 改
     String sql = "update t_user set real_name = ?, age = ? where id = ?";
     int count = jdbcTemplate.update(sql, "张三丰", 55, 1);
     
     // 查一个对象
     // 第一个参数：sql语句
     // 第二个参数：Bean属性值和数据库记录行的映射对象。在构造方法中指定映射的对象类型。
     // 第三个参数：可变长参数，给sql语句的占位符问号传值。
     String sql = "select id, real_name, age from t_user where id = ?";
     User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), 2);
     
     // 查多个对象
     String sql = "select id, real_name, age from t_user";
     List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
     
     // 查询一个值
     String sql = "select count(1) from t_user";
     Integer count = jdbcTemplate.queryForObject(sql, int.class);
     
     // 批量添加
     String sql = "insert into t_user(id,real_name,age) values(?,?,?)";
     Object[] objs1 = {null, "小花", 20};
     Object[] objs2 = {null, "小明", 21};
     Object[] objs3 = {null, "小刚", 22};
     List<Object[]> list = new ArrayList<>();
     list.add(objs1);
     list.add(objs2);
     list.add(objs3);
     int[] count = jdbcTemplate.batchUpdate(sql, list);

     // 批量修改
     String sql = "update t_user set real_name = ?, age = ? where id = ?";
     Object[] objs1 = {"小花11", 10, 2};
     Object[] objs2 = {"小明22", 12, 3};
     Object[] objs3 = {"小刚33", 9, 4};
     List<Object[]> list = new ArrayList<>();
     list.add(objs1);
     list.add(objs2);
     list.add(objs3);
     int[] count = jdbcTemplate.batchUpdate(sql, list);

     // 批量删除
     String sql = "delete from t_user where id = ?";
     Object[] objs1 = {2};
     Object[] objs2 = {3};
     Object[] objs3 = {4};
     List<Object[]> list = new ArrayList<>();
     list.add(objs1);
     list.add(objs2);
     list.add(objs3);
     int[] count = jdbcTemplate.batchUpdate(sql, list);

    // 回调函数
   String sql = "select id, real_name, age from t_user where id = ?";
   User user = jdbcTemplate.execute(sql, new PreparedStatementCallback<User>() {
   @Override
   public User doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
      User user = null;
      ps.setInt(1, 5);
      ResultSet rs = ps.executeQuery();
         if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setRealName(rs.getString("real_name"));
            user.setAge(rs.getInt("age"));
      }
      return user;
      }
   });
```

##  面向切面编程AOP

IoC使软件组件松耦合。AOP让你能够捕捉系统中经常使用的功能，把它转化成组件。

AOP（Aspect Oriented Programming）：面向切面编程，面向方面编程。（AOP是一种编程技术）AOP是对OOP的补充延伸。AOP底层使用的就是动态代理来实现的。

Spring的AOP使用的动态代理是：JDK动态代理 + CGLIB动态代理技术。Spring在这两种动态代理中灵活切换，如果是代理接口，会默认使用JDK动态代理，如果要代理某个类，这个类没有实现接口，就会切换使用CGLIB。当然，你也可以强制通过一些配置让Spring只使用CGLIB。

一般一个系统当中都会有一些系统服务，例如：日志、事务管理、安全等。这些系统服务被称为：交叉业务

如果在每一个业务处理过程当中，都掺杂这些交叉业务代码进去的话，存在两方面问题：
- 第一：交叉业务代码在多个业务流程中反复出现，显然这个交叉业务代码没有得到复用。并且修改这些交叉业务代码的话，需要修改多处。
- 第二：程序员无法专注核心业务代码的编写，在编写核心业务代码的同时还需要处理这些交叉业务。

使用AOP可以很轻松的解决以上问题。

![img_2](images/img_2.png)

用一句话总结AOP：将与核心业务无关的代码独立的抽取出来，形成一个独立的组件，然后以横向交叉的方式应用到业务流程当中的过程被称为AOP。AOP的优点：
- 第一：代码复用性增强。
- 第二：代码易维护。
- 第三：使开发者更关注业务逻辑。

### 切点表达式

切点表达式用来定义通知（Advice）往哪些方法上切入。切入点表达式语法格式：
```xml
execution([访问控制权限修饰符] 返回值类型 [全限定类名]方法名(形式参数列表) [异常])
```

- 访问控制权限修饰符：
  - 可选项。
  - 没写，就是4个权限都包括。
  - 写public就表示只包括公开的方法。
- 返回值类型：
  - 必填项。
  - "*"表示返回值类型任意
- 全限定类名：
  - 可选项。
  - 两个点“..”代表当前包以及子包下的所有类。
  - 省略时表示所有的类。
- 方法名：
  - 必填项。
  - "*"表示所有方法。
  - set*表示所有的set方法。
- 形式参数列表：
  - 必填项
  - () 表示没有参数的方法
  - (..) 参数类型和个数随意的方法
  - (*) 只有一个参数的方法
  - (*, String) 第一个参数类型随意，第二个参数是String的。
- 异常：
  - 可选项。
  - 省略时表示任意异常类型。

理解以下的切点表达式：

```java
// service包下所有的类中以delete开始的所有方法
execution(public * com.nju.mall.service.*.delete*(..))
// mall包下所有的类的所有的方法
execution(* com.nju.mall..*(..))
// 所有类的所有方法
execution(* *(..))
```

### 使用Spring的AOP

简单案例见aop模块

Spring对AOP的实现包括以下3种方式：
- 第一种方式：Spring框架结合AspectJ框架实现的AOP，基于[注解方式](aop/src/main/java/com/nju/spring6/service/Spring6Configuration.java)。
- 第二种方式：Spring框架结合AspectJ框架实现的AOP，基于[XML方式](aop/src/main/resources/spring-aspectj-aop-annotation.xml)。
  - 第三种方式：Spring框架自己实现的AOP，基于XML配置方式。
    ```properties
      <!--纳入spring bean管理-->
      <bean id="vipService" class="com.nju.spring6.service.VipService"/>
      <bean id="timerAspect" class="com.nju.spring6.service.TimerAspect"/>

      <!--aop配置-->
      <aop:config>
          <!--切点表达式-->
          <aop:pointcut id="p" expression="execution(* com.nju.spring6.service.VipService.*(..))"/>
          <!--切面-->
          <aop:aspect ref="timerAspect">
              <!--切面=通知 + 切点-->
              <aop:around method="time" pointcut-ref="p"/>
          </aop:aspect>
      </aop:config>
      ```
  
      ```java
      // 负责计时的切面类
      public class TimerAspect {
    
          public void time(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
              long begin = System.currentTimeMillis();
              //执行目标
              proceedingJoinPoint.proceed();
              long end = System.currentTimeMillis();
              System.out.println("耗时"+(end - begin)+"毫秒");
          }
      }
  
      // 目标类
      public class VipService {
         public void add(){
           System.out.println("保存vip信息。");
         }
      }
      ```

开启自动代理之后，凡事带有@Aspect注解的bean都会生成代理对象。
proxy-target-class="true" 表示采用cglib动态代理。
proxy-target-class="false" 表示采用jdk动态代理。默认值是false。当没有接口的时候，也会自动选择cglib生成代理类。
```properties
<!--开启自动代理-->
<aop:aspectj-autoproxy proxy-target-class="true"/>
```
#### 通知类型
通知类型包括：
- 前置通知：@Before 目标方法执行之前的通知
- 后置通知：@AfterReturning 目标方法执行之后的通知
- 环绕通知：@Around 目标方法之前添加通知，同时目标方法执行之后添加通知。
- 异常通知：@AfterThrowing 发生异常之后执行的通知
- 最终通知：@After 放在finally语句块中的通知

当发生异常之后，最终通知也会执行，因为最终通知@After会出现在finally语句块中。
出现异常之后，后置通知和环绕通知的结束部分不会执行。

#### 切面的先后顺序

业务流程当中不一定只有一个切面，可能有的切面控制事务，有的记录日志，有的进行安全控制，如果多个切面的话，可以使用@Order注解来标识切面类，为@Order注解的value指定一个整数型的数字，数字越小，优先级越高。

<img src="./images/1689003683791.jpg"/>

## Spring对事务的支持

### 事务属性

- 事务传播行为
- 事务隔离级别
- 事务超时
- 只读事务
- 设置出现哪些异常回滚事务
- 设置出现哪些异常不回滚事务

#### 传播行为

```java
@Transactional(propagation = Propagation.REQUIRED)
```
- REQUIRED：支持当前事务，如果不存在就新建一个(默认)【没有就新建，有就加入】
- SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行【有就加入，没有就不管了】
- MANDATORY：必须运行在一个事务中，如果当前没有事务正在发生，将抛出一个异常【有就加入，没有就抛异常】
- REQUIRES_NEW：开启一个新的事务，如果一个事务已经存在，则将这个存在的事务挂起【不管有没有，直接开启一个新事务，开启的新事务和之前的事务不存在嵌套关系，之前事务被挂起】
- NOT_SUPPORTED：以非事务方式运行，如果有事务存在，挂起当前事务【不支持事务，存在就挂起】
- NEVER：以非事务方式运行，如果有事务存在，抛出异常【不支持事务，存在就抛异常】
- NESTED：如果当前正有一个事务在进行中，则该方法应当运行在一个嵌套式事务中。被嵌套的事务可以独立于外层事务进行提交或回滚。如果外层事务不存在，行为就像REQUIRED
  一样。【有事务的话，就在这个事务里再嵌套一个完全独立的事务，嵌套的事务可以独立的提交和回滚。没有事务就和REQUIRED一样。】

#### 事务隔离级别

```java
@Transactional(isolation = Isolation.READ_COMMITTED)
```
数据库中读取数据存在的三大问题：（三大读问题）
- 脏读：读取没有提交的数据，叫做脏读。
- 不可重复读：在同一个事务当中，第一次和第二次读取的数据不一样。
- 幻读：读到的数据量变了。
事务隔离级别包括四个级别：
- 读未提交：READ_UNCOMMITTED，存在脏读问题，能够读取到其它事务未提交的数据。
- 读提交：READ_COMMITTED，解决了脏读问题，存在不可重复读问题。
- 可重复读：REPEATABLE_READ ，解决了不可重复读、脏读，存在幻读问题。
- 序列化：SERIALIZABLE 解决了不可重复读、脏读、幻读，顺序执行，不支持并发。

### 事务超时

```java
// 事务的超时时间为10秒。指的是在当前事务当中，最后一条DML语句执行之前的时间。
@Transactional(timeout = 10)

// 不会记录睡眠时间
@Transactional(timeout = 10) // 设置事务超时时间为10秒。
public void save(Account act) {
    accountDao.insert(act);
    // 睡眠一会
    try {
        Thread.sleep(1000 * 15);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

@Transactional(timeout = 10) // 设置事务超时时间为10秒。
public void save(Account act) {
    // 睡眠一会
    try {
        Thread.sleep(1000 * 15);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    accountDao.insert(act);
}
```

### 只读事务
只允许select语句执行，delete insert update均不可执行。该特性的作用是：启动spring的优化策略。提高select语句执行效率。
```java
@Transactional(readOnly = true)
```

### 异常回滚

```java
// 只有发生RuntimeException异常或该异常的子类异常才回滚
@Transactional(rollbackFor = RuntimeException.class)

// NullPointerException或该异常的子类异常不回滚，其他异常则回滚。
@Transactional(noRollbackFor = NullPointerException.class)

```

### Spring实现事务的两种方式
- 编程式事务
通过编写代码的方式来实现事务的管理。
- 声明式事务
  - 基于注解方式
  - 基于XML配置方式

Spring对事务的管理底层实现方式是基于AOP实现的，对AOP的方式进行了封装。

PlatformTransactionManager接口：spring事务管理器的核心接口。在Spring6中它有两个实现：
- DataSourceTransactionManager：支持JdbcTemplate、MyBatis、Hibernate等事务管理。
- JtaTransactionManager：支持分布式事务管理。
如果要在Spring6中使用JdbcTemplate，就要使用DataSourceTransactionManager来管理事务。（Spring内置写好了，可以直接用。）

#### 声明式事务基于注解

```properties
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
  <property name="dataSource" ref="dataSource"/>
</bean>

<tx:annotation-driven transaction-manager="transactionManager"/>
```

```java
@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

    @Resource(name = "accountDao")
    private AccountDao accountDao;
}
```

#### 全注解事务

```java
@Configuration
@ComponentScan("com.powernode.bank")
@EnableTransactionManagement
public class Spring6Config {

    @Bean
    public DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring6");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}

@Test
public void testNoXml(){
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Spring6Config.class);
    AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
    try {
        accountService.transfer("act-001", "act-002", 10000);
        System.out.println("转账成功");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

#### 声明式事务基于XML

不需要显示@Transaction注解了
```properties
    <context:component-scan base-package="com.powernode.bank"/>

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/spring6"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置通知-->
    <tx:advice id="txAdvice" transaction-manager="txManager">
        <tx:attributes>
            <tx:method name="save*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="del*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="update*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="transfer*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
        </tx:attributes>
    </tx:advice>

    <!--配置切面-->
    <aop:config>
        <aop:pointcut id="txPointcut" expression="execution(* com.powernode.bank.service..*(..))"/>
        <!--切面 = 通知 + 切点-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>

```


## Spring & JUnit

```java
// JUnit 4
// 在单元测试类上使用这两个注解之后，在单元测试类中的属性上可以使用@Autowired。比较方便。
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")

// JUnit 5 
// 在JUnit5当中，可以使用Spring提供的以下两个注解，标注到单元测试类上，这样在类当中就可以使用@Autowired注解了。
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:spring.xml")
```

## Spring中的设计模式

### 简单工厂模式
BeanFactory的getBean()方法，通过唯一标识来获取Bean对象。是典型的简单工厂模式（静态工厂模式）；

### 工厂方法模式
FactoryBean是典型的工厂方法模式。在配置文件中通过factory-method属性来指定工厂方法，该方法是一个实例方法。

### 单例模式

Spring用的是双端锁进行Bean的循环依赖的解决

### 代理模式

Spring的AOP就是使用了动态代理实现的

### 装饰器模式

JavaSE中的IO流是非常典型的装饰器模式。

Spring 中配置 DataSource 的时候，这些dataSource可能是各种不同类型的，比如不同的数据库：Oracle、SQL Server、MySQL等，也可能是不同的数据源：比如apache 提供的org.apache.commons.dbcp.BasicDataSource、spring提供的org.springframework.jndi.JndiObjectFactoryBean等。
这时，能否在尽可能少修改原有类代码下的情况下，做到动态切换不同的数据源？此时就可以用到装饰者模式。

<br>Spring中类名中带有：Decorator和Wrapper单词的类，都是装饰器模式。</br>

### 观察者模式

当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并自动更新。Spring中观察者模式一般用在listener的实现。

Spring中的事件编程模型就是观察者模式的实现。在Spring中定义了一个ApplicationListener接口，用来监听Application的事件，Application其实就是ApplicationContext，ApplicationContext内置了几个事件，其中比较容易理解的是：ContextRefreshedEvent、ContextStartedEvent、ContextStoppedEvent、ContextClosedEvent

### 策略模式

getHandler是HandlerMapping接口中的唯一方法，用于根据请求找到匹配的处理器。
比如我们自己写了AccountDao接口，然后这个接口下有不同的实现类：AccountDaoForMySQL，AccountDaoForOracle。对于service来说不需要关心底层具体的实现，只需要面向AccountDao接口调用，底层可以灵活切换实现，这就是策略模式。


### 模板方法模式

Spring中的JdbcTemplate类就是一个模板类。它就是一个模板方法设计模式的体现。在模板类的模板方法execute中编写核心算法，具体的实现步骤在子类中完成。
