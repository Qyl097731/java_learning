package com.nju.springkafka.controller;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * @author qiuyiliang
 */
@RestController
@RequestMapping("/msg")
public class MyKafkaController {
    private final static String TOPIC_NAME = "my-replicated-topic";

    @Resource
    private AdminClient adminClient;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/topic/send")
    public void send(@RequestParam String key, @RequestParam String value) {
        kafkaTemplate.send(TOPIC_NAME, 0, key, value);
    }

    @PostMapping("topics")
    public void createTopic(@RequestParam String topicname) {
        NewTopic topic = new NewTopic(TOPIC_NAME, 2, (short) 3);
        adminClient.createTopics(Collections.singletonList(topic));
    }

    @GetMapping("topics")
    public void getAllTopic() throws Exception {
        ListTopicsResult listTopics = adminClient.listTopics();
        Set<String> topics = listTopics.names().get();

        for (String topic : topics) {
            System.out.println(topic);
        }
    }
}