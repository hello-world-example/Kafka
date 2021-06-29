# Kafka Connect



## 简介

- Kafka Connect 是建立在 Kafka Brocker 之上的数据导入导出工具
- Kafka Connect 与 Kafka 一起，但是需要单独启动，先启动 Kafka，再启动 Kafka Connect，两个是独立的进程
- Kafka Connect 通过 `Source Connect` 获取数据，写入到 Topic，通过 `Sink Connect` 从 Topic 中获取数据，传输到其他地方
- 官方提供的 Connect 不多，但是其本身也是个开发框架（ `org.apache.kafka:connect-api` ），屏蔽了 Kafka 连接、推送、偏移 等细节，可以专注于数据处理；Topic 的发送和订阅，由 Connect Framework 处理
- 分布式部署模式下，提供 RestAPI 接口，用户在线管理 Connect 插件，可以 创建、修改、暂停、恢复、重启 等



## 部署方式

### Standalone mode

- 适用于 **本地环境** 的开发和测试
- 有些情况下着只能使用 Standalone 模式，如：发送本地文件到 Kafka
- 启动 Connector 时，只能使用配置文件启动，无法使用 Rest 接口



### Distributed mode

- Kafka Connect 可以与 Kafka Broker 分开部署，Kafka Connect 在多台实例上启动，连接到同一个 Kafka Broker，实现高可用
- 当然，集群默认启动一个 Connect 节点也是可以的，Connect 节点在集群中的角色可称之为 **Worker**
- 当多个 Kafka Connect Worker 中一个 Down 之后，则 Kafka Connect 会把 Down 掉 Worker 的任务分发给集群中的其他节点
- 因为 Kafka Connect 在 Kafka 集群内存储了连接器配置、状态、偏移信息 等，所以转义过程中是安全的，不会丢失数据
- 集群模式下启动更简单，启动后，可以通过 RestAPI 管理，配合 **Kafka Connect UI** 会更加方便



## Distributed 模式部署

### config/connect-distributed.properties

以下是一些关键配置：

```properties
# Kafka Broker 集群地址，多个用 逗号 分割
bootstrap.servers=localhost:9092

# 集群名称，
group.id=connect-cluster

# 【自动创建 Topic】保存 connector Offset 信息
offset.storage.topic=connect-offsets
# 【自动创建 Topic】保存 Connector 和 Task 的配置信息
config.storage.topic=connect-configs
# 【自动创建 Topic】保存 Connector 状态信息
status.storage.topic=connect-status


# REST API 端口
rest.port=8083

# 自定义 Connect Plugin 的本地扫描路径，多个用 逗号 分割
plugin.path=/opt/websuite/kafka/connects
```



### 启动 Kafka Connect

> - 需要先把 Kafka 启动起来

```bash
./bin/connect-distributed.sh -daemon ./config/connect-distributed.properties
```



### 查看启动结果

```bash
# 查看版本信息
$ curl http://localhost:8083
	{"version":"2.5.0","commit":"66563e712b0b9f84","kafka_cluster_id":"NH6dCKVvRLKY970nFDcFXg"}

#
# 系统自带了 FileStreamConnector
$ curl http://localhost:8083/connector-plugins
  [
      {
          "class":"org.apache.kafka.connect.file.FileStreamSinkConnector",
          "type":"sink",
          "version":"2.5.0"
      },
      {
          "class":"org.apache.kafka.connect.file.FileStreamSourceConnector",
          "type":"source",
          "version":"2.5.0"
      },
      ...
  ]

#
# 查看 Topic 变化
$ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
connect-configs
connect-offsets
connect-status
...

#
# 查看配置信息
$ ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic connect-configs --from-beginning
```



## Kafka Connect UI（Docker）

> - [lensesio/kafka-connect-ui: Web tool for Kafka Connect | (github.com)](https://github.com/lensesio/kafka-connect-ui)

```bash
# 
$ KAFKA_CONNECT_IP=$(ifconfig | grep inet | grep -v 127.0.0.1 | grep -v 0.0.0.0 | awk '{print $2}')

$ docker run -d -p 8000:8000 \
    -e "CONNECT_URL=http://${KAFKA_CONNECT_IP}:8083" \
    --name kafka-connect-ui \
    harbor.ttpai.cn/dev_learn/kafka-connect-ui:0.9.7
```



## 【Demo】FileStreamSourceConnector

```bash
# 创建一个 读取本地文件的 Connect
$ curl -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "FileStreamSourceConnector",
  "config": {
    "connector.class": "org.apache.kafka.connect.file.FileStreamSourceConnector",
    "tasks.max": "1",
    "topic": "connect.file.source.by.kail",
    "file": "connects/test-kail/file.source.txt"
  }
}'

#
#
# 往文件中追加内容
$ echo "333333" >> connects/test-kail/file.source.txt

#
#
# 查看 Topic 内容： 可以收到消息
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
	--topic connect.file.source.by.kail \
	--from-beginning
{"schema":{"type":"string","optional":false},"payload":"333333"}

```



## Read More

- [Apache Kafka Connect](http://kafka.apache.org/25/documentation.html#connect)
- 
- [How to use Kafka Connect - Getting Started | Confluent Documentation](https://docs.confluent.io/platform/current/connect/userguide.html)
- [Kafka Connect Architecture | Confluent Documentation](https://docs.confluent.io/platform/current/connect/design.html)

