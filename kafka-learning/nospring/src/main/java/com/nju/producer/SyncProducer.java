package com.nju.producer;

import com.alibaba.fastjson.JSON;
import com.nju.domain.Order;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 消息生产者 同步调用
 */
public class SyncProducer {

    private final static String TOPIC_NAME = "my-replicated-topic";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "master:9092,master:9093,master:9094");
        //把发送的key从字符串序列化为字节数组
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //把发送消息value从字符串序列化为字节数组
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(props);
        Order order = new Order(1L, 1);
        // 发送消息到指定的分区
//            ProducerRecord<String, String> producerRecord = new ProducerRecord<> (TOPIC_NAME, 0, order.getId ().toString (), JSON.toJSONString (order));
        //未指定发送分区，具体发送的分区计算公式：hash(key)%partitionNum
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, order.getId().toString(), JSON.toJSONString(order));

        //等待消息发送成功的同步阻塞方法
        RecordMetadata metadata = producer.send(producerRecord).get();
        System.out.println("同步方式发送消息结果：" + "topic-" + metadata.topic() + "|partition-" + metadata.partition() + "|offset-" + metadata.offset());
    }
}

