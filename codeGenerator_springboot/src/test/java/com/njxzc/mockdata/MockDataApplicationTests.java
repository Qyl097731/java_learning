package com.njxzc.mockdata;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.njxzc.mockdata.entity.Goods;
import com.njxzc.mockdata.entity.Order;
import com.njxzc.mockdata.service.GoodsService;
import com.njxzc.mockdata.service.OrderService;
import com.njxzc.mockdata.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;


@SpringBootTest
@Slf4j
class MockDataApplicationTests {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
    }

    @Test
    void generator() {
        codeGenerator ("t_goods");
    }

    @Test
    void codeGenerator(String tableName) {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator ();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig ();
        String projectPath = System.getProperty ("user.dir");
        gc.setOutputDir ("D:/java_learning/codeGenerator_springboot" + "/src/main/java");

        gc.setAuthor ("qyl");
        gc.setOpen (false); //生成后是否打开资源管理器
        gc.setFileOverride (false); //重新生成时文件是否覆盖

        //UserServie
        gc.setServiceName ("%sService");    //去掉Service接口的首字母I

        gc.setIdType (IdType.AUTO); //主键策略
        gc.setDateType (DateType.ONLY_DATE);//定义生成的实体类中日期类型

        mpg.setGlobalConfig (gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig ();
        dsc.setUrl ("jdbc:mysql://localhost:3306/db_mock?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8");
        dsc.setDriverName ("com.mysql.cj.jdbc.Driver");
        dsc.setUsername ("root");
        dsc.setPassword ("097731");
        dsc.setDbType (DbType.MYSQL);
        mpg.setDataSource (dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig ();
        pc.setModuleName ("mockdata"); //模块名
        //包  com.njxzc.mockdata
        pc.setParent ("com.njxzc");
        //包  com.njxzc.mockdata.controller
        pc.setController ("controller");
        pc.setEntity ("entity");
        pc.setService ("service");
        pc.setMapper ("mapper");
        mpg.setPackageInfo (pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig ();

        strategy.setInclude (tableName);

        strategy.setNaming (NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix ("tb" + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming (NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel (true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle (true); //restful api风格控制器
//        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy (strategy);


        // 6、执行
        mpg.execute ();
    }

    @Test
    void mockData() {
        CompletableFuture[] mockFutures = IntStream.range (0, 700).boxed ().map (i ->
                CompletableFuture.runAsync (() -> {
                    ArrayList<Order> orderArrayList = new ArrayList<> ();
                    Snowflake uuidGenerator = new Snowflake ();
                    String gid = uuidGenerator.nextIdStr ();
                    Integer importance = RandomUtil.randomInt (0, 5);
                    String goodsName = RandomUtil.randomString (5);
                    int status = RandomUtil.randomInt (0, 1);
                    Integer num = RandomUtil.randomInt ();
                    int length = RandomUtil.randomInt (0, 1000);
                    for (int j = 0; j < length; j++) {
                        String id = uuidGenerator.nextIdStr ();
                        String address = RandomUtil.randomString (20);
                        BigDecimal goodsPrice = RandomUtil.randomBigDecimal (BigDecimal.ONE, BigDecimal.valueOf (10000));
                        Boolean orderState = RandomUtil.randomBoolean ();
                        DateTime dateTime = RandomUtil.randomDay (-1000, 1000);
                        Date createTime = dateTime.toSqlDate ();
                        Integer year = dateTime.year ();
                        Integer month = dateTime.month ();
                        Integer day = dateTime.dayOfYear ();
                        orderArrayList.add (new Order (id, address, goodsPrice, orderState, createTime, year, month, day, gid));
                    }
                    System.out.println ("finish " + i);
                    goodsService.save (new Goods (gid, importance, goodsName, num, status));
                    orderService.saveBatch (orderArrayList, orderArrayList.size ());
                }, ThreadPoolUtil.getExecutorService ())).toArray (CompletableFuture[]::new);
        CompletableFuture.allOf (mockFutures).join ();
    }
}
