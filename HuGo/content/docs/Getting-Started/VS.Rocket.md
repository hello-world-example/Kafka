# Kafka vs RockMQ

## NameSvr vs. Zookeeper

- Kafka 通过 Zookeeper 来进行协调
- RocketMQ 通过自身的 `namesrv` 进行协调。   



- kafka 在具备选举功能，在 Kafka 里面，Master/Slave 的选举，有2步：
  - 第1步，先通过 ZK 在所有机器中，选举出一个 KafkaController；
  - 第2步，再由这个 Controller，决定每个 partition 的 Master 是谁，Slave 是谁。
  - Kafka 某个 partition 的 master 挂了，该 partition 对应的某个slave会升级为主对外提供服务。    
- RocketMQ不具备选举，Master/Slave的角色也是固定的。
  - 当一个Master挂了之后，你可以写到其他Master上，但不能让一个Slave切换成Master。
  - 那么RocketMq是如何实现高可用的呢？
    - rocketMq的所有broker节点的角色都是一样，上面分配的topic和对应的queue的数量也是一样的，MQ 只能保证当一个broker挂了，把原本写到这个broker的请求迁移到其他broker上面，而并不是这个broker对应的slave升级为主。    
  - RocketMQ 在协调节点的设计上显得更加轻量，用了另外一种方式解决高可用的问题，思路也是可以借鉴的。

## 吞吐量

- Kafka 在消息存储过程中会根据 topic 和 partition 的数量创建物理文件，也就是说创建一个 topic 并指定了 3个 partition，那么就会有3个物理文件目录，也就说说 partition 的数量和对应的物理文件是一一对应的
- RocketMQ 在消息存储方式就一个物流问题，也就说传说中的 commitLog，RocketMQ 的 queue 的数量其实是在 consumeQueue 里面体现的，在真正存储消息的 commitLog 其实就只有一个物理文件



- Kafka的多文件并发写入 VS RocketMQ 的单文件写入，性能差异 kafka 完胜可想而知



- kafka的大量文件存储会导致一个问题，也就说在 partition特别多的时候，磁盘的访问会发生很大的瓶颈，毕竟单个文件，看着是append操作，但是多个文件之间必然会导致磁盘的寻道。



## Read More

- 原文来自
  - [RocketMQ 和 Kafka 的架构区别](https://www.jianshu.com/p/c474ca9f9430)
  - [阿里RocketMQ](https://mp.weixin.qq.com/s/KfBruI-tOz-eJuM2fgqyew)
- https://hello-world-example.github.io/RocketMQ/#/vs

