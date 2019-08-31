package xyz.kail.demo.kafka.official.hello;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import xyz.kail.kafka.tool.KafkaConsumerTool;

import java.util.Collections;
import java.util.Date;

/**
 * auto.commit.interval.ms = 100
 * auto.offset.reset = latest
 * bootstrap.servers = [localhost:2181]
 * check.crcs = true
 * client.id =
 * connections.max.idle.ms = 540000
 * enable.auto.commit = true
 * exclude.internal.topics = true
 * fetch.max.bytes = 52428800
 * fetch.max.wait.ms = 500
 * fetch.min.bytes = 1
 * group.id = test-groupId
 * heartbeat.interval.ms = 3000
 * interceptor.classes = null
 * internal.leave.group.on.close = true
 * isolation.level = read_uncommitted
 * key.deserializer = class org.apache.kafka.common.serialization.StringDeserializer
 * max.partition.fetch.bytes = 1048576
 * max.poll.interval.ms = 300000
 * max.poll.records = 500
 * metadata.max.age.ms = 300000
 * metric.reporters = []
 * metrics.num.samples = 2
 * metrics.recording.level = INFO
 * metrics.sample.window.ms = 30000
 * partition.assignment.strategy = [class org.apache.kafka.clients.consumer.RangeAssignor]
 * receive.buffer.bytes = 65536
 * reconnect.backoff.max.ms = 1000
 * reconnect.backoff.ms = 50
 * request.timeout.ms = 305000
 * retry.backoff.ms = 100
 * sasl.jaas.config = null
 * sasl.kerberos.kinit.cmd = /usr/bin/kinit
 * sasl.kerberos.min.time.before.relogin = 60000
 * sasl.kerberos.service.name = null
 * sasl.kerberos.ticket.renew.jitter = 0.05
 * sasl.kerberos.ticket.renew.window.factor = 0.8
 * sasl.mechanism = GSSAPI
 * security.protocol = PLAINTEXT
 * send.buffer.bytes = 131072
 * session.timeout.ms = 15000
 * ssl.cipher.suites = null
 * ssl.enabled.protocols = [TLSv1.2, TLSv1.1, TLSv1]
 * ssl.endpoint.identification.algorithm = null
 * ssl.key.password = null
 * ssl.keymanager.algorithm = SunX509
 * ssl.keystore.location = null
 * ssl.keystore.password = null
 * ssl.keystore.type = JKS
 * ssl.protocol = TLS
 * ssl.provider = null
 * ssl.secure.random.implementation = null
 * ssl.trustmanager.algorithm = PKIX
 * ssl.truststore.location = null
 * ssl.truststore.password = null
 * ssl.truststore.type = JKS
 * value.deserializer = class org.apache.kafka.common.serialization.StringDeserializer
 */
@Slf4j
public class SingleThreadConsumer {

    private static KafkaConsumer<String, String> consumer
            = KafkaConsumerTool.defaultConsumer("localhost:9092", "test-groupId6",
            conf -> {
                conf.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
                return conf;
            });


    public static void main(String[] args) {
        consumer.subscribe(Collections.singletonList("test"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(new Date());
                System.out.println(record);
            }
        }
    }
}
