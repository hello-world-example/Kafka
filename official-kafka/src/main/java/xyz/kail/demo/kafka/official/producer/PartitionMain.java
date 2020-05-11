package xyz.kail.demo.kafka.official.producer;

import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartitionInfo;
import xyz.kail.demo.kafka.official.R;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic topic-test
 * <p>
 * kafka-topics.sh --bootstrap-server localhost:9092 --topic topic-test --describe
 * Topic:topic-test        PartitionCount:3        ReplicationFactor:1     Configs:segment.bytes=1073741824
 * Topic: topic-test       Partition: 0    Leader: 2       Replicas: 2     Isr: 2
 * Topic: topic-test       Partition: 1    Leader: 2       Replicas: 2     Isr: 2
 * Topic: topic-test       Partition: 2    Leader: 2       Replicas: 2     Isr: 2
 */
public class PartitionMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<PartitionInfo> partitionInfos = R.PRODUCER.partitionsFor("topic-test");
        for (PartitionInfo partitionInfo : partitionInfos) {
            System.out.println(partitionInfo.toString());
            System.out.println("   "+partitionInfo.leader().id());
            System.out.println("   "+partitionInfo.leader().idString());
            System.out.println("   "+partitionInfo.leader().host());
            System.out.println("   "+partitionInfo.leader().port());
            System.out.println("   "+partitionInfo.leader().rack());
            System.out.println("   "+partitionInfo.partition());
        }

        DescribeTopicsResult describeTopics = R.ADMIN.describeTopics(Collections.singleton("topic-test"));
        TopicDescription topicDescription = describeTopics.values().get("topic-test").get();
        List<TopicPartitionInfo> partitions = topicDescription.partitions();
        for (TopicPartitionInfo partitionInfo : partitions) {
            System.out.println(partitionInfo.toString());
        }

    }

}
