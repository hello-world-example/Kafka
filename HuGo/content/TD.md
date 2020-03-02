0. 查看集群节点（通过 Zookeeper 查看）
/opt/websuite/zookeeper/bin/zkCli.sh 
ls /brokers/ids
get /brokers/ids/0

1. 如何查看 哪个 brocker 是 master
get /controller

1. topic 列表
bin/kafka-topics.sh --zookeeper localhost:2181 --list

2. 两个关键的 Topic 信息
bin/kafka-topics.sh --zookeeper localhost:2181 --topic info-log --describe
bin/kafka-topics.sh --zookeeper localhost:2181 --topic exception-log --describe

bin/kafka-configs.sh --zookeeper localhost:2181 --entity-type brokers --entity-name 0 --describe

      
{"version":1,"partitions":[{"topic":"exception-log","partition":0,"replicas":[1,0]}]}
bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file /opt/websuite/kafka/replication.json --execute





apache kafka系列之在zookeeper中存储结构： https://blog.csdn.net/lizhitao/article/details/23744675
https://uohzoaix.github.io/studies//2016/01/13/kafka%E9%9B%86%E7%BE%A4%E4%B9%8B%E7%A1%AE%E5%AE%9Aleader/  Kafka 如何手动触发重新选举
https://johng.cn/update-kafka-topic-replicas/
