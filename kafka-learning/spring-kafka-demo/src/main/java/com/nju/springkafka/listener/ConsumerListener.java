package com.nju.springkafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author qiuyiliang
 */
@Component
public class ConsumerListener {
    /**
     * 设置消费组，消费指定topic
     *
     * @param record
     * @param ack
     */
    @KafkaListener(topics = "my-replicated-topic", groupId = "MyGroup1")
    public void listenWithoutGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        //手动提交offset
        ack.acknowledge();
    }

    /**
     * 设置消费组、多topic、指定分区、指定偏移量消费及设置消费者个数。
     *
     * @param record
     * @param ack
     */
    @KafkaListener(groupId = "testGroup", topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = {"0", "1"}),
            //concurrency就是同组下的消费者个数，就是并发消费数，建议小于等于分区总数
            @TopicPartition(topic = "topic2", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))}, concurrency = "3")
    public void listenWithGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        //手动提交offset
        ack.acknowledge();
    }
}
