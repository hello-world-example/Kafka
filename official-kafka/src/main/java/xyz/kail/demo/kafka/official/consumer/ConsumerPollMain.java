package xyz.kail.demo.kafka.official.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import xyz.kail.demo.kafka.official.R;
import xyz.kail.kafka.tool.KafkaConsumerTool;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

public class ConsumerPollMain {

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer =
                KafkaConsumerTool.defaultConsumer(R.SERVER, "group-consumer1",
                        conf -> {
                            // 新创建的 Group 是从哪里开始消费的，
                            // 如果已经存在，还是从上次创建的位置开始
                            conf.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
                            return conf;
                        }
                );

        // 订阅 Topic
        consumer.subscribe(Collections.singleton("topic-test"));

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
