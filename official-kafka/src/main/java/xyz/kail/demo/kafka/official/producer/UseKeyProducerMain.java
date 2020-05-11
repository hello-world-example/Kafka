package xyz.kail.demo.kafka.official.producer;

import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartitionInfo;
import xyz.kail.demo.kafka.official.R;
import xyz.kail.kafka.tool.KafkaRecordBuilder;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic topic-test
 * <p>
 * kafka-topics.sh --bootstrap-server localhost:9092 --topic topic-test --describe
 * Topic:topic-test        PartitionCount:3        ReplicationFactor:1     Configs:segment.bytes=1073741824
 * Topic: topic-test       Partition: 0    Leader: 2       Replicas: 2     Isr: 2
 * Topic: topic-test       Partition: 1    Leader: 2       Replicas: 2     Isr: 2
 * Topic: topic-test       Partition: 2    Leader: 2       Replicas: 2     Isr: 2
 */
public class UseKeyProducerMain {

    private static void send(ProducerRecord<String, String> record) throws ExecutionException, InterruptedException {
        Future<RecordMetadata> future = R.PRODUCER.send(record);
        RecordMetadata recordMetadata = future.get();
        System.out.println(recordMetadata.toString());
    }

    /**
     * 相同的 Key 每次都是到同一个分区
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            ProducerRecord<String, String> record = KafkaRecordBuilder
                    .<String, String>builder("topic-test", String.valueOf(i))
                    .key(String.valueOf(i))
                    .build();

            send(record);
        }
    }

}
