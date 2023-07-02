简单回顾一下kafka的学习

# 简单知识总结

## what
1. Broker：在Kafka中，Broker是指Kafka集群中的一个节点（服务器）或一个实例。每个Broker都是独立运行的Kafka服务器，负责接收、存储和转发消息。它们协同工作以构建一个分布式、高可靠性的消息系统。
2. Controller：Controller是Kafka集群中的一种特殊角色，每个时刻只有一个Broker扮演Controller角色。Controller负责管理整个Kafka集群的元数据信息，包括维护Topic 
、Partition的状态以及监控Broker的健康状态。当发生故障或者新的Broker加入集群时，Controller会负责重新分配Partition的副本。 
3. Partition：Partition是Kafka中对消息进行分区的概念。一个Topic可以被划分为多个Partition，每个Partition是一个有序且持久化存储的消息日志。每条消息都被写入到一个特定的Partition，并且每个Partition可以在不同的Broker上进行复制，以提供高可用性和容错性。 
4. Replication：在Kafka中，消息的副本复制是为了提供高可用性和数据冗余。每个Partition可以配置多个副本，其中一个副本称为Leader，其余的副本称为Follower。Leader负责处理读写请求，而Follower则通过复制Leader的消息来提供备份和容错能力。如果Leader发生故障，Controller将从Follower中选举出新的Leader。 
5. Topic：Topic是Kafka中消息发布和订阅的逻辑名称。它是一个消息的分类或者主题。生产者（Producer）将消息发送到特定的Topic，而消费者（Consumer）则订阅感兴趣的Topic来接收消息。每个Topic可以划分为多个Partition，并且可以在多个Broker上进行复制以实现高可用性。
6. Producer：消息的生产者
7. Consumer：消息的消费者，同一个消费组如果是在生产者单播的情况下，就只能由一个消费者进行消费，防止重复消费

这些概念之间的关系如下：一个Kafka集群由多个Broker组成，每个Broker负责存储和传输消息。集群中有一个Controller负责管理元数据和协调操作。每个Topic可以划分为多个Partition，每个Partition可以在多个Broker上进行复制。这种分区和复制的机制提供了高吞吐量、可扩展性和容错性的特性，使得Kafka成为一种强大的分布式消息系统。

<img src="src/images/1688313247046.jpg"/>

## Why

### 为什么有多个分区

1. 同一个主题有了多个分区，那么我就可以多个生产者并行的写到同一个的主题的不同分区。显然能提高写的吞吐量；
同理消费的时候也可从同一个主题的不同分区并行消费，提高读的吞吐量。
2. 如果同一主题消息过大过多，那么分区可以解决存储文件过大的问题。

### 为什么有副本

防止leader挂了之后，无法提供服务；如果副本（follower）存在就可以成为新的leader提供数据服务。



