# UI 管理工具

## Kafka Tool

> 官方： http://www.kafkatool.com/download.html
>
> 注：Kafka Tool 仅供个人使用，不得用于商业目的



Kafka Tool 是一个 C/S 工具，下载指定的安装包安装即可。

使用方式详见：http://www.kafkatool.com/features.html



## yahoo/kafka-manager

> 官方： https://github.com/yahoo/kafka-manager
>
> Releases：https://github.com/yahoo/kafka-manager/releases

版本最高支持到 Kafka 0.9.0.1

### Docker 方式安装

```bash
# 搜索 kafka-manager 镜像
$ docker search kafka-manager
NAME                        DESCRIPTION                        STARS     OFFICIAL  AUTOMATED
wurstmeister/kafka          Multi-Broker Apache Kafka Image    849                 [OK]
sheepkiller/kafka-manager   kafka-manager                      159                 [OK]

# 拉取
$ docker pull sheepkiller/kafka-manager

# 运行
$ docker run -d -p 9000:9000 -e "ZK_HOSTS=192.168.1.7:2181" --name kafka-manager sheepkiller/kafka-manager
```



### 手动编译(非常慢)

```bash
# 下载指定的版本
$ wget https://github.com/yahoo/kafka-manager/archive/1.3.3.22.tar.gz

# 解压，解压之后会得到 kafka-manager-1.3.3.22 文件夹
$ tar zxvf 1.3.3.22.tar.gz
$ cd kafka-manager-1.3.3.22

# 编译构建项目，SBT 可以看做是Scala世界的Maven
# 注：编译时间较长，可能会等待一个小时
$ ./sbt clean dist

TODO
```



## 