# 常用命令



## 基础



### 通用

```bash
# --help 查看帮助
./kafka-topics.sh --help
```



### bootstrap-server OR zookeeper

- `--bootstrap-server` 是趋势，
- `--zookeeper` 直接操作 ZK，跳过权限，不太建议

|                           | --bootstrap-server | --zookeeper |
| ------------------------: | :----------------: | :---------: |
|           kafka-topics.sh |         ✔️          |    ~~✔️~~    |
|          kafka-configs.sh |                    |      ✔️      |
|  kafka-consumer-groups.sh |         ✔️          |             |
| kafka-console-consumer.sh |         ✔️          |             |




## 启动 & 关闭

```bash
# 先启动 Zookeeper
$ nohup bin/zookeeper-server-start.sh config/zookeeper.properties &

# 启动 Kafka
$ nohup bin/kafka-server-start.sh config/server.properties &

# 关闭 Kafka
$ bin/kafka-server-stop.sh 

# 关闭 Zookeeper
$ bin/zookeeper-server-stop.sh
```



## Topic

```bash
# 创建一个 test 主题，包含 1个分区 和 1个副本 --zookeeper localhost:2181
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 \
  --create \
  --partitions 1 \
  --replication-factor 1 \
  --topic topic-test 
  
#> Created topic "topic-test ".

# 查看 topic 列表 --zookeeper localhost:2181
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
#> topic-test 

# 查看 topic 信息
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic topic-test --describe

# 删除 topic
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic topic-test --delete

# 删除主题数据
$ bin/kafka-configs.sh --zookeeper localhost:2181 --alter --entity-type topics --entity-name topic-giraffe --add-config retention.ms=3000
```



## Group

```bash
# Note: This will only show information about consumers that use ZooKeeper (not those using the Java consumer API)
# $ bin/kafka-consumer-groups.sh --zookeeper localhost:2181 --list               
# 
# 查看 group 列表
$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

# 查看 group 信息
$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group test-groupId --describe

TOPIC                          PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG        CONSUMER-ID                                       HOST                           CLIENT-ID
test                           0          23              23              0          consumer-1-4d8a542e-783a-4104-a529-3048f4b4bf76   /192.168.1.8                   consumer-1

# 重设 group 位移
$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --topic test --group test-groupId --reset-offsets --to-earliest --execute

# 删除 group (通过命令行无法删除，只能删除 zk 节点 /consumers/[group_id]，或者等待自动超期)
$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --topic test --group test-groupId2 --delete                                                                                     
Option '[delete]' is only valid with '[zookeeper]'. Note that there's no need to delete group metadata for the new consumer as the group is deleted when the last committed offset for that group expires.


```

> - [Kafka consumer group位移0ffset重设](https://www.cnblogs.com/felixzh/p/8028118.html)



## Producer

```bash
# 发送消息
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

>asd
>123
>


```



## Consumer

```bash
# 从尾部消费
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test

# 头部消费
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

# 从尾部消费 指定的分区
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --offset latest --partition 1

# 查看 __consumer_offsets 消息
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic __consumer_offsets --from-beginning --formatter "kafka.coordinator.group.GroupMetadataManager\$OffsetsMessageFormatter"
```



## Config



### Broker

```bash
# 查看 Broker 配置 ( zookeeper ≈ bootstrap-server)
$ bin/kafka-configs.sh --zookeeper localhost:2181 --entity-type brokers --entity-name 0 --describe
```



### Topic

```bash
# 查看 Topic 配置
> bin/kafka-configs.sh --zookeeper localhost:2181 --entity-type topics --entity-name my-topic --describe


# 覆盖默认属性
$ bin/kafka-configs.sh --zookeeper localhost:2181 --entity-type topics --entity-name my-topic --alter --add-config max.message.bytes=128000


# 删除配置，使用默认配置
> bin/kafka-configs.sh --zookeeper localhost:2181 --entity-type topics --entity-name my-topic --alter --delete-config max.message.bytes
```



