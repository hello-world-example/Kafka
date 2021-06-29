package xyz.kail.demo.kafka.official.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerGroupMetadata;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import xyz.kail.demo.kafka.official.R;
import xyz.kail.kafka.tool.KafkaConsumerTool;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsumerPollMain {

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer =
//                KafkaConsumerTool.defaultConsumer(R.SERVER, "group-consumer1",
                KafkaConsumerTool.defaultConsumer("172.16.2.170:9092", "group-limb-hbase-topic-giraffe",
                        conf -> {
                            // 新创建的 Group 是从哪里开始消费的，
                            // 如果已经存在，还是从上次创建的位置开始
                            conf.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
                            conf.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
                            return conf;
                        }
                );

        // 订阅 Topic
        consumer.subscribe(Collections.singleton("topic-giraffe-1"));

        Map<String, List<PartitionInfo>> stringListMap = consumer.listTopics();
        System.out.println(stringListMap);

        ConsumerGroupMetadata consumerGroupMetadata = consumer.groupMetadata();
        System.out.println(consumerGroupMetadata);

        for (int i = 0; i < 100; i++) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.of(1, ChronoUnit.SECONDS));

            System.out.println("records.count()" + records.count());

            records.forEach(record -> {
                String key = record.key();
                String value = record.value();

                System.out.println(key);
                System.out.println(value);
            });
        }

    }

}
