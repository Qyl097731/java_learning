//package com.nju.springkafka.config;
//
//import org.apache.kafka.clients.admin.AdminClient;
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaAdmin;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 初始化topic
// * @author qiuyiliang
// */
////@Configuration
//public class KafkaInitialConfiguration {
//
//    private final static String TOPIC_NAME = "my-replicated-topic";
//
//    // 创建TopicName为TOPIC_NAME的Topic并设置分区数为2以及副本数为3
//    // 通过bean创建(bean的名字为initialTopic)
//    @Bean
//    public NewTopic initialTopic() {
//        return new NewTopic("TOPIC_NAME",2, (short) 3 );
//    }
//    /**
//     * 此种@Bean的方式，如果topic的名字相同，那么会覆盖以前的那个
//     * @return
//     */
//
//    // 修改后分区数量会变成11个 注意分区数量只能增加不能减少
//    @Bean
//    public NewTopic initialTopic2() {
//        return new NewTopic("TOPIC_NAME",11, (short) 3);
//    }
//
//    //创建一个kafka管理类，相当于rabbitMQ的管理类rabbitAdmin,没有此bean无法自定义的使用adminClient创建topic
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> props = new HashMap<>();
//        //配置Kafka实例的连接地址                                                                    //kafka的地址，不是zookeeper
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "master:9092");
//        KafkaAdmin admin = new KafkaAdmin(props);
//        return admin;
//    }
//
//    @Bean  //kafka客户端，在spring中创建这个bean之后可以注入并且创建topic
//    public AdminClient adminClient() {
//        return AdminClient.create(kafkaAdmin().getConfigurationProperties());
//    }
//}