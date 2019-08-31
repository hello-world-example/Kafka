# 伪集群安装



> https://github.com/wurstmeister/kafka-docker



## Docker

### Zookeeper

```bash
$ docker pull zookeeper	

$ docker run -d -p 2181:2181 --restart=always --name zookeeper zookeeper:latest
```

### Kafka

```bash
$ docker pull wurstmeister/kafka

# 192.168.1.5 是 宿主机 IP

# Kafka02
$ docker run -d -p 9092:9092 -p 9292:9292 --restart=always \
    --add-host kafka01.kail.xyz:192.168.1.5 \
    --add-host kafka02.kail.xyz:192.168.1.5 \
    --add-host kafka03.kail.xyz:192.168.1.5 \
    -h kafka02.kail.xyz \
    -e "KAFKA_ZOOKEEPER_CONNECT=192.168.1.5:2181/kafka" \
    -e "KAFKA_LISTENERS=PLAINTEXT://:9092" \
    -e "KAFKA_BROKER_ID=2" \
    -e "KAFKA_JMX_OPTS=-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=9292" \
    -e "JMX_PORT=9292" \
    --name Kafka02 wurstmeister/kafka:latest

# Kafka01
$ docker run -d -p 9091:9091 -p 9191:9191 --restart=always \
    --add-host kafka01.kail.xyz:192.168.1.5 \
    --add-host kafka02.kail.xyz:192.168.1.5 \
    --add-host kafka03.kail.xyz:192.168.1.5 \
    -h kafka01.kail.xyz \
    -e "KAFKA_ZOOKEEPER_CONNECT=192.168.1.5:2181/kafka" \
    -e "KAFKA_LISTENERS=PLAINTEXT://:9091" \
    -e "KAFKA_BROKER_ID=1" \
    -e "KAFKA_JMX_OPTS=-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=9191" \
    -e "JMX_PORT=9191" \
    --name Kafka01 wurstmeister/kafka:latest
    
# Kafka03
$ docker run -d -p 9093:9093 -p 9393:9393 --restart=always \
    --add-host kafka01.kail.xyz:192.168.1.5 \
    --add-host kafka02.kail.xyz:192.168.1.5 \
    --add-host kafka03.kail.xyz:192.168.1.5 \
    -h kafka03.kail.xyz \
    -e "KAFKA_ZOOKEEPER_CONNECT=192.168.1.5:2181/kafka" \
    -e "KAFKA_LISTENERS=PLAINTEXT://:9093" \
    -e "KAFKA_BROKER_ID=3" \
    -e "KAFKA_JMX_OPTS=-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=9393" \
    -e "JMX_PORT=9393" \
    --name Kafka03 wurstmeister/kafka:latest
```



## Docker Compose

> `docker-compose -f docker-compose.yml up -d --build`
>
> `docker-compose -f docker-compose.yml down`

```yaml
version: '3'
services:
  zookeeper:
    image: zookeeper
    container_name: Zookeeper
    restart: always
    hostname: zookeeper.kail.xyz
    ports:
      - "2181:2181"

  kafka-manager:
    image: solsson/kafka-manager
    container_name: kafka-manager
    restart: always
    ports:
      - "9000:9000"
    extra_hosts:
      - "zookeeper.kail.xyz:192.168.1.5"
    environment:
      ZK_HOSTS: zookeeper.kail.xyz:2181

  kafka01:
    image: wurstmeister/kafka
    container_name: Kafka01
    restart: always
    ports:
      - "9091:9091"
      - "9191:9191"
    hostname: kafka01.kail.xyz
    extra_hosts:
      - "zookeeper.kail.xyz:192.168.1.5"
      - "kafka01.kail.xyz:192.168.1.5"
      - "kafka02.kail.xyz:192.168.1.5"
      - "kafka03.kail.xyz:192.168.1.5"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper.kail.xyz:2181/kafka
      KAFKA_LISTENERS: PLAINTEXT://:9091
      KAFKA_BROKER_ID: 1
      KAFKA_JMX_OPTS: "-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=9191"
      JMX_PORT: 9191

  kafka02:
    image: wurstmeister/kafka
    container_name: Kafka02
    restart: always
    ports:
      - "9092:9092"
      - "9292:9292"
    hostname: kafka02.kail.xyz
    extra_hosts:
      - "zookeeper.kail.xyz:192.168.1.5"
      - "kafka01.kail.xyz:192.168.1.5"
      - "kafka02.kail.xyz:192.168.1.5"
      - "kafka03.kail.xyz:192.168.1.5"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper.kail.xyz:2181/kafka
      KAFKA_LISTENERS: PLAINTEXT://:9092
      KAFKA_BROKER_ID: 2
      KAFKA_JMX_OPTS: "-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=9292"
      JMX_PORT: 9292

  kafka03:
    image: wurstmeister/kafka
    container_name: Kafka03
    restart: always
    ports:
      - "9093:9093"
      - "9393:9393"
    hostname: kafka03.kail.xyz
    extra_hosts:
      - "zookeeper.kail.xyz:192.168.1.5"
      - "kafka01.kail.xyz:192.168.1.5"
      - "kafka02.kail.xyz:192.168.1.5"
      - "kafka03.kail.xyz:192.168.1.5"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper.kail.xyz:2181/kafka
      KAFKA_LISTENERS: PLAINTEXT://:9093
      KAFKA_BROKER_ID: 3
      KAFKA_JMX_OPTS: "-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=9393"
      JMX_PORT: 9393

```

