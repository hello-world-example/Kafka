# 版本信息



## 关注的版本

- 消息格式 v0 
  - ~~0.7：上古版本~~
  - ~~0.8：副本机制~~
  - ~~0.9：2015.11，增加权限和认证，不建议使用 Consumer API~~
- 消息格式 v1
  - ~~0.10：引入 Kafka Stream~~
- 消息格式 v2
  - 0.11：幂等性 Producer API 以及事务（Transaction） API
    - **0.11.0.2**：spring-kafka v1 最新版依赖的最新版本
    - 0.11.0.3：**0.11.*** 最后的版本
  - 1.0：
    - **1.0.1**：当前 Broker 的版本
    - 1.0.2：**1.0.*** 最后的版本
  - 2.0：Stream 的改进



## Kafka 兼容性

> - 从 `0.10.2.0` 开始，在大多数情况下，较新的 Client 可以与较旧的 Broker 通信
> - Broker >= `0.10.x.x`  建议使用 `0.11.x.x` 或更高的 Client 版本，其使用更简单的线程模型，见[KIP-62](https://cwiki.apache.org/confluence/display/KAFKA/KIP-62%3A+Allow+consumer+to+send+heartbeats+from+a+background+thread)
> - 详见 [Kafka 兼容性矩阵](https://cwiki.apache.org/confluence/display/KAFKA/Compatibility+Matrix)



## Spring Kafka 兼容性

> ❤ [Spring Kafka 兼容矩阵](https://spring.io/projects/spring-kafka#kafka-client-compatibility)
>
> - Spring Kafka 1.x 开始 与 Kafka 的首位版本号一致
> - Spring Kafka 2.2 开始 与 Kafka 的 前两位版本号一致

| 更新时间  | Spring Kafka      | Spring             | Kafka Client | Kafka 可升级 |
| --------- | ----------------- | ------------------ | ------------ | ------------ |
| Sep, 2016 | 1.0.4.RELEASE     | 4.2.6.RELEASE      | 0.9.0.1      |              |
| Jan, 2019 | **1.3.9.RELEASE** | **4.3.22.RELEASE** | 0.11.0.2     | - 0.11.0.3   |
|           |                   |                    |              |              |
| Sep, 2017 | 2.0.0.RELEASE     | 5.0.0.RELEASE      | 0.11.0.0     |              |
| Jan, 2019 | 2.1.12.RELEASE    | 5.0.12.RELEASE     | 1.0.2        |              |
| Jun, 2019 | 2.2.7.RELEASE     | 5.1.7.RELEASE      | 2.0.1        |              |



## Read More

- [一文看懂Kafka消息格式的演变](https://blog.csdn.net/u013256816/article/details/80300225)
- [Upgrading From Previous Versions](http://kafka.apache.org/documentation/#upgrade)
- 
- 1.0.1 : https://www.orchome.com/874
- 1.1.0: https://www.orchome.com/1036
- 2.0.1: https://www.orchome.com/1364
- 2.1.0: https://www.orchome.com/1377





