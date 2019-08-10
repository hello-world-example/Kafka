package xyz.kail.demo.kafka.official;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import xyz.kail.kafak.tool.KafkaConsumerTool;

import java.util.Collections;

@Slf4j
public class SingleThreadConsumer {

    private static KafkaConsumer<String, String> consumer
            = KafkaConsumerTool.defaultConsumer("", "");


    public static void main(String[] args) {
        consumer.subscribe(Collections.singletonList("my-topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
            }
        }
    }
}