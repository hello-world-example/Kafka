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
# 拉取
$ docker pull solsson/kafka-manager

# 运行
$ docker run -d -p 9000:9000 -e "ZK_HOSTS=192.168.1.7:2181" --restart=always --name kafka-manager solsson/kafka-manager
```
