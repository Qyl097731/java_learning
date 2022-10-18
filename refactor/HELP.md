# 代码重构的技巧
最近在读《重构改善既有代码设计》,这里我会把所有案例放于此，以及一些笔记。如果有不正确、讲述不到位的请指正。

## 案例开篇

第一个案例是对一个电影票计费系统的重构，第一反应是策略模式，但是作者使用了状态模式，有什么区别呢？

### 策略模式 & 状态模式

#### 策略模式

一般涉及了复杂计算，且情况不同计算的方式也有所不同；不希望对外暴露算法具体实现细节；通过将算法进行抽象出来，然后算法类来实现这个抽象方法，需要调用者对算法有一定了解，进行手动选择计算策略。

#### 状态模式

一个事物的多种状态的表现，状态不同，行为就不同。

#### 区别

- 策略模式需要手动选择策略，状态模式的状态是系统运行过程中自己转换的；
- 策略模式始终都是一个事情的不同实现，本质上做的同一个事情；状态模式是多个状态的不同表现，做的事不同；

### 总结

- 无法方便地添加新特性，就先重构那个程序，使得特性比较容易进行之后添加。
- 重构之前，先准备好所有地测试用例，不然很容易出现bug 

## 如何重构

### 三次法则

#### 添加功能时重构

重构更好理解别人代码，同时能够发现代码的不足之处，能为之后的维护提供便利。

#### 修补时重构

如果出现bug，很大可能是代码不够清晰，可以考虑重构了。

#### 复审代码重构

复审代码时，如果有新的想法就立马看看能不能实现，可以更好地理解别人想法，同时发现更多不足之处，提供更多地修改建议。
- 大的团队建议设计评审
- 只是两个人直接代码评审，一起实现新方案。

### 重构、重写、重载

- 重载：发生在同一个类当中，同名方法，不同的入参个数、入参类型、入参顺序。
- 重写：发生在继承关系中，在返回类型、方法的实现、方法的权限三个方面进行修改
- 重构：只是修改方法的实现

### 总结
- 重构一定程度上解放了软件设计的工作量，良好的设计可以让后续性能优化提供更多时间。

## 何时重构

- 重复代码
- 过长函数
- 需要用到注释说明的时候
- 过大的类
- 过长的参数列表
- 发散式变化
- 散弹式修改
- 依恋情结
- 平行继承体系
- 冗余类
- 夸夸其谈未来性
- 令人迷惑的暂时字段
- 过度耦合的消息链
- 中国人
- 狎昵关系 
- 异曲同工的类
- 不完美的类库
- 纯稚的数据类

.....

### 总结 

把不变、与变化部分的剥离;高度耦合需要解耦（低耦合）;代码逻辑不明确的时候需要抽象出去方便理解;重复冗余的代码需要抽象再设计
;把相关的行为属性放在一起（高内聚）; 复合代替继承；

   
## chapter6

### 内联再重构

如果有多部份函数功能相关，且简单易懂，可以考虑重构这些函数到一个大函数内，然后重新抽取可能会有意想不到的结果！

如存在太多的间接层，但是都只是对另一个函数的简单委托，就应该考虑该重构。如果中间层复杂，且有继承关系，强烈建议放弃该重构。

### 用查询替换临时变量

原因：临时变量过多会大概率造成代码复杂，函数过长。如果临时变量只在函数中赋值一次才建议使用，且事先声明为final进行检查。

如果在一个魂环中，累加好几个数值的情况，把对每一个值的累加抽成一个函数。<u>不要担心性能影响，大概率不会影响且即使真的出现了性能问题也能很好内联回去！</u>

### 不要对入参进行赋值

原因：对按值传递、按引用传递的理解程度

例子：DataTester 调试一下，看看JVM中堆栈变化

### 类的抽取

如果多个函数总是在同时间段出现，可以考虑抽离成新的对象。可以更好的并发编程，是不是锁粒度减小，但是如果要同时锁定两者的时候可能就要充分考虑事务问题了。

### 以函数对象取代函数

当临时变量过多了之后再想抽取函数，会导致当前类存在过多的变量，内聚就会大大降低。

<b>函数对象：</b>把这个函数所涉及的临时变量都放到一个对象中，作为该对象的字段。在初始化的时候对这些字段进行赋值。之后把函数体提取过来，作为该对象的compute()函数。
之后再对这个大的函数进行分解~

```java
/**
 * @Description 修改前
**/
class Account{
    int gamma(int inputVal, int quantity, int yearToDate){
        int importantValue1 = (inputVal * quantity) + delta();
        int importantValue2 = (inputVal * yearToDate) + 100;
        if((yearToDate - importantValue1) > 100) importantValue2 -= 20;
        int importantValue3 = importantValue2 * 7;  
        return importantValue3 - 2 * importantValue1;
    } 
}

/**
 * @Description 修改后
**/
class Gamma{
    // step1
    private final Account account;
    private int inputVal;
    private int quantity;
    private int yearToDate;
    private int importantValue1;
    private int importantValue2;
    private int importantValue3;
    
    // step2
    Gamma(Account account, int inputValue, int quantity, int yearToDate){
        this.account = account;       
        this.inputValue = inputValue;
        this.quantity = quantity;
        this.yearToDate = yearToDate;
    }     
    
    // step3
    // 做完前四步之后就可以对compute函数进行简化抽取了，略
    int compute(){
        int importantValue1 = (inputVal * quantity) + account.delta();
        int importantValue2 = (inputVal * yearToDate) + 100;
        if((yearToDate - importantValue1) > 100) importantValue2 -= 20;
        int importantValue3 = importantValue2 * 7;  
        return importantValue3 - 2 * importantValue1;
    }

}

class Account{
    // step4 委托Gamma来计算
    int gamma(int inputVal,int quantity, int yearToDate){
        return new Gamma(this,inputVal,quantity,yearToDate).compute();
    }
}

```

### 替换算法

如果要替换一个算法，不动就算法，把新算法实现，实现之后通过新旧算法的删除进行对比，正确才替换。

### 总结

就是低耦合，就是类之间尽可能少相关，类内部高的相关性，一个类尽量要符合单一职责。

## chapter7

### 函数搬移

方法：
1. 检查要被搬移的函数中所使用的一切特性，考虑是否一起搬移。
2. 查看是否存在继承关系，是否搬移后会出错
3. 设计函数名符合新的目标类、以及原类如何调用该函数
4. 是否能调试通过

### 字段搬移

例子： 搬移Account利率到AccountType

```java
/**
 * @Description 修改前
**/
class Account{
    private AccountType type;
    private double interest;
    public double  getInterest() {
      return interest;
    }
}
/**
 * @Description 修改后
**/
class Account{
    private AccountType type;

    public double  getInterest() {
      return type.interest;
    }
}

class AccountType{
    private double interest;
    
    public double  getInterest() {
      return interest;
    }
}
```

<b>技巧：</b> 如果出现了很多地方对Account中interest的引用，就可以通过<b>委托</b>来实现。


### 类内联

如果类承担的职责太小，且与另一个类有较大的关联时，可以进行内联。

<b>做法：</b> 先把需要原始类类中的所有public函数移动到目标类，然后把所有对原始类的调用，都变成目标类的调用。

### 隐藏委托关系

<b>简单地说</b> 更好地封装，减少对外暴露具体的实现细节。

```java
/**
 * @Description 修改前
**/
class Person{
    Department department;
    
    public Department getDepartment(){
        return department;
    }

    public void setDepartment(Department department){
        this.department = department;
    }
}

class Department{
    private  String chargeCode;
    private Person manager;
    
    public Department(Person manager){
        this.manager = manager;
    }

    public Person getManager(){
        return manager;
    }
}
```

那么我作为一个调用者，想知道某人的经理是谁，就会如下操作:`manager = stuff.getDepartment().getManager();`
这就暴露了Department的工作原理，直到经理这个信息时保存在Department中。

```java
/**
* @Description 委托函数 隐藏实现细节
**/
class Person{
    Department department;
    
    public void setDepartment(Department department){
        this.department = department;
    }
    public Person getManager(){
        return department.getManager();
    }

}

class Department{
    private  String chargeCode;
    private Person manager;
    
    public Department(Person manager){
        this.manager = manager;
    }

    public Person getManager(){
        return manager;
    }
}
```

### 移除中间人

用了过多的委托函数...

<b>做法:</b> 在当前类建立一个函数获取受托对象，之后Client通过获取该对象直接去操作受托对象就好了。具体过程就是把上述两段代码，进行反转。

### 引入外加函数

临时添加一个函数来实现一个功能，但是无法修改服务类。

就可以在客户端添加一个函数来实现这个功能，把服务类实例作为他第一个参数，注意添加上注释"外加函数，应该在服务类实现"

### 引入本地扩展

临时添加一批函数来实现新功能，但是无法修改服务类。

通过子类化或者包装来实现新功能，最后把原对象用新对象替换。

## chapter8

### 以对象取代数据值

一个字段需要表现出某些行为，且概念不再单一时，建议抽取成对象。

<b>做法：</b> 新建一个类，声明一个字段（与原类中需要代替字段类型一样的字段），之后在新类中加入这个字段的取值函数，以及以该字段为参数的构造函数。
将原始类中的对该字段的使用替换成对该类的引用。

### 将值对象改为引用对象

```java
/**
 * @Description 未修改前 
**/
class Customer{
    private final String name;
    public Customer(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}

class Order{
    private Customer customer;
    
    public Order(String customerName){
        customer = new Customer(customerName);
    }
    
    public void setCustomer(String customerName){
        customer = new Customer(customerName);   
    }

    public String getCustomerName(){
        return customer.getName();
    }
    private static int numberOfOrdersFor(Collection orders,String customer){
        int  result = 0;
        Iterator iter = orders.iterator();
        while(iter.hasNext()){
            Order each = (Order)iter.next();
            if(each.getCustormerName().equals(customer)){
                result++;
            }
        }
        return result;
    }
} 
```

显然一个用户一个订单，本应该一个多个订单属于同一个用户（Order 应该共享一个 Customer）

```java
/**
 * @Description 修改后
**/

class Customer{
    // ...省略部分代码
    private static Dictionary instances = new Hashtable();

    static void loadCustomers(){ 
        new Customer("Lemon Car Hire").store();
        new Customer("Associated Coffee Machines").store();
        new Customer("Bilston Gasworks").store();
    }
    
    private void store(){
        instances.put(this.getName(),this)
    }    

    private String getName(){
        return name;
    }    

    public static Customer getNamed(String name){
        return (Customer) instances.get(name);
    }
    
    private Customer(String name){ 
       this.name = name;
    }
}

class Order{
    public Order(String customer){
        customer = Customer.create(customer);
    }
}
```

### 将引用对象改为值对象

- 判断是否可以能成为不可变对象。不能不可变就放弃修改
- 建立equals和hasCode
- 考虑删除工厂函数，构造函数声明为public

```java
class Currency{
    private String code;
    public String getCode(){return code;}
    private Currency(String code){this.code = code;}

    public boolean equals(Object object){
        if (!(object instanceof Currency)) return false;
        Currency other = (Currency) object;
        return code.equals(other.code);
    }

    public int hashCode(){return this.code.hashCode();}
}
```

### 复制被监视数据

简单点就是把视图和业务解耦，如果熟悉MVC的应该很好理解。

<b>简述：</b>当视图层改变之后通过观察者模式（把所有要使用该数据的视图都注入到领域模型中），之后某个视图一旦修改了数据，那么所有的视图都会被领域层通知（所有视图都改变）。

### 将单向关联改成双向关联

主要是控制方的选择：选择粒度小的模块作为控制方，可以让控制更为集中。

### 双向关联改成单向关联

双向关联不仅容易错，更重要的一点是占用内存空间，导致无法释放。（强引用尤其突出）

<b>做法：</b>关注关联对象的所有的取值函数，再修改完之后删除所有的赋值函数即可，因为不存在关联关系之后，这个对象字段都是多余了，更不用说赋值了。

### 封装集合

简单说就是把集合使用者的操作粒度缩到最小---获取集合的时候返回的是集合副本；修改的时候只能逐个修改。尽可能暴露集合实现细节。

注意一下，如果赋值函数直接把整个集合赋值给字段，后期用户再修改集合的时候，那字段就会跟着变，显然不安全。

### 以数据类取代记录

试想一下Vo存在的意义就应该懂什么意思了，就是把字段组装成一个对象~只提供数据，不提供行为

### 以类取代类型码

用类取代类型码的时候，要注意是否会出现在Switch中，如果会出现那就不应该实施该重构。

- 首先创建静态类，把所有的静态变量拷贝进去
- 创建所有静态类实例的集合
- 创建返回类型所对应的实例函数、静态变量的获取函数
- 最后把所有对原先的静态变量的使用，都变成对新的静态类的使用，删除所有原先的静态变量。

### 用子类代替类型码

针对的是上一种情况的问题的解决方案，如果类型码会影响宿主类的行为，就创建多种行为对应的子类。

<b>注意：</b>如果类型码会在子类创建后变化或者原来的宿主类已经有了子类，就放弃该重构方法，而应该使用状态或者策略模式。


