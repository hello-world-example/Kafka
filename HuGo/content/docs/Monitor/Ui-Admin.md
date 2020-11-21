# UI 管理工具

## Kafka Tool

> 官方： http://www.kafkatool.com/download.html
>
> 当前版本 2.0.8，For Kafka version 0.11 and later



Kafka Tool 是一个 C/S 工具，下载指定的安装包安装即可。

使用方式详见：http://www.kafkatool.com/features.html



## ❤ yahoo/CMAK（Kafka Manager）

> Github： https://github.com/yahoo/CMAK
>



### Docker 方式安装

```bash
# 拉取 (支持到 2.0.0.2)
$ docker pull solsson/kafka-manager

# 运行
$ docker run -d -p 9000:9000 -e "ZK_HOSTS=192.168.1.7:2181" --restart=always --name kafka-manager solsson/kafka-manager
```



## xaecbd/KafkaCenter

> Github： https://github.com/xaecbd/KafkaCenter
>
> 依赖： MySQL、Elasticsearch (7.0+)

```bash
# 安装依赖： MySQL
$ docker run -d -p3306:3306 -e MYSQL_ROOT_PASSWORD=123456 --name mysql mysql:5.7
# 使用初始化脚本创建库
https://github.com/xaecbd/KafkaCenter/blob/master/KafkaCenter-Core/sql/table_script.sql

# 安装依赖： ElasticSearch
$ docker run -d -p 9200:9200 \
	-e TZ="Asia/Shanghai" \
	-e discovery.type="single-node" \
	--name es.7.9.3 elasticsearch:7.9.3

# 编译安装 KafkaCenter
$ git clone https://github.com/xaecbd/KafkaCenter.git
$ cd KafkaCenter
$ git tag -l 					# 查看版本
$ git checkout v2.3.0 # 切换到指定的版本
$ mvn clean package -Dmaven.test.skip=true

# 参数详见：https://github.com/xaecbd/KafkaCenter/blob/master/KafkaCenter-Core/src/main/resources/application.properties
$ java -jar KafkaCenter-Core/target/KafkaCenter-Core-2.3.0.jar \
	--spring.security.user.name=admin \
	--spring.security.user.password=admin \
	--spring.datasource.url=jdbc:mysql://localhost:3307/kafka_center \
	--spring.datasource.username=root \
	--spring.datasource.password=123456 \
	--monitor.elasticsearch.hosts=localhost:9200
```



## smartloli/kafka-eagle

> 官网： https://www.kafka-eagle.org/
>
> Github:  https://github.com/smartloli/kafka-eagle

```bash
$ @see https://www.kafka-eagle.org/articles/docs/installation/linux-macos.html

$ wget https://github.com/smartloli/kafka-eagle-bin/archive/v2.0.3.tar.gz

```

