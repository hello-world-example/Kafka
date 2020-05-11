package xyz.kail.demo.kafka.official.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import xyz.kail.demo.kafka.official.R;
import xyz.kail.kafka.tool.KafkaConsumerTool;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsumerAdminMain {

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer =
                KafkaConsumerTool.defaultConsumer(R.SERVER, "group-consumer1", conf -> conf);

        Map<String, List<PartitionInfo>> stringListMap = consumer.listTopics();


//        consumer.subscribe(Collections.singleton("topic-test"));
//
//        Set<TopicPartition> topicPartitions = consumer.assignment();
//        for (TopicPartition topicPartition : topicPartitions) {
//            System.out.println(topicPartition.topic());
//            System.out.println("    " + topicPartition.toString());
//        }


    }

}
