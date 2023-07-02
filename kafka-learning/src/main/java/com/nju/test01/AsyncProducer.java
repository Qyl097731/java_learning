package com.nju.test01;

import com.alibaba.fastjson.JSON;
import com.nju.domain.Order;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * 消息生产者 异步发送
 */
public class AsyncProducer {

    private final static String TOPIC_NAME = "my-replicated-topic";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties ();
        props.put (ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "master:9092,master:9093,master:9094");
        //把发送的key从字符串序列化为字节数组
        props.put (ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName ());
        //把发送消息value从字符串序列化为字节数组
        props.put (ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName ());

        Producer<String, String> producer = new KafkaProducer<> (props);
        //要发送 5 条消息
        int msgNum = 5;
        final CountDownLatch countDownLatch = new CountDownLatch (msgNum);
        for (int i = 0; i < msgNum; i++) {
            Order order = new Order ((long) i, i);
            //指定发送分区
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String> (TOPIC_NAME, 0,
                    order.getId ().toString (), JSON.toJSONString (order));
            //异步回调方式发送消息
            producer.send (producerRecord, (metadata, exception) -> {
                if (exception != null) {
                    System.err.println ("发送消息失败：" +
                            exception.getStackTrace ());
                }
                if (metadata != null) {
                    System.out.println ("异步方式发送消息结果：" + "topic-" + metadata.topic () + "|partition-" + metadata.partition () + "|offset-" + metadata.offset ());
                }
                countDownLatch.countDown ();
            });
        }
    }
}

