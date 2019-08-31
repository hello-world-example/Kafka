# 快速开始

## 安装

```bash 
cd /opt/websuite

# 下载 https://kafka.apache.org/downloads
$ wget https://archive.apache.org/dist/kafka/1.0.1/kafka_2.12-1.0.1.tgz

# 解压
$ tar zxvf kafka_2.12-1.0.1.tgz
$ cd kafka_2.12-1.0.1
```

## 启动 Kafka

```bash
# 先启动 Zookeeper
$ bin/zookeeper-server-start.sh config/zookeeper.properties

# 启动 Kafka
$ bin/kafka-server-start.sh config/server.properties
```

## 创建 Topic

```bash
# 创建一个 test 主题，包含 1个分区 和 1个副本
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
#> Created topic "test".

# 查看 topic 列表
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
#> test
```

## 发送消息

```bash
# 发送消息
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

>asd
>123
>


```

## 接收消息

```bash
# 接收消息
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

asd
123

```



## Read More

- 官方文档 [Quickstart](http://kafka.apache.org/quickstart)
- 中文社区 [Quickstart](http://kafka.apachecn.org/quickstart.html)

