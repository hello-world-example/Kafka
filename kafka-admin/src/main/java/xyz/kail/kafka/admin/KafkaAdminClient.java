package xyz.kail.kafka.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.requests.DescribeLogDirsResponse;
import xyz.kail.kafka.tool.KafkaAdminTool;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class KafkaAdminClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final AdminClient adminClient = KafkaAdminTool.defaultAdmin("172.16.2.170:9092", conf -> conf);

        /*
         * 集群
         */
        System.out.println("========= Cluster");
        final DescribeClusterResult describeCluster = adminClient.describeCluster();
        final Collection<Node> nodes = describeCluster.nodes().get();
        nodes.forEach(n -> {
            System.out.println(String.format("id:%s; host:%s;  port:%s; rack:%s; toString:%s;", n.id(), n.host(), n.port(), n.rack(), n.toString()));
        });
        final Node n = describeCluster.controller().get();
        System.out.println(String.format("controller ::: id:%s; host:%s;  port:%s; rack:%s; toString:%s;", n.id(), n.host(), n.port(), n.rack(), n.toString()));

        /*
         * Topic
         */
        System.out.println("========= Topic");
        final Collection<TopicListing> topicListings = adminClient.listTopics().listings().get();
        topicListings.forEach(t -> {
            System.out.println(t.toString());
        });

        /*
         * Describe Topic
         */
        System.out.println("========= Describe Topic");
        final Map<String, TopicDescription> topicDescriptionMap = adminClient.describeTopics(Collections.singletonList("info-log")).all().get();
        topicDescriptionMap.forEach((k, v) -> {
            System.out.println(k);
            System.out.println("    " + v.toString());
            System.out.println("        " + v.name());
            final List<TopicPartitionInfo> partitions = v.partitions();
            partitions.forEach(p -> {
                p.isr().forEach(i -> {
                    System.out.println(i.toString());
                });
                p.replicas().forEach(i -> {
                    System.out.println(i.toString());
                });
                System.out.println(p.leader().toString());
                System.out.println(p.partition());
            });
        });

        /*
         * Describe LogDirs
         */
        System.out.println("========= Describe LogDirs");
        final Map<Integer, Map<String, DescribeLogDirsResponse.LogDirInfo>> logDirs = adminClient.describeLogDirs(Collections.singletonList(1)).all().get();
        logDirs.forEach((k, v) -> {
            System.out.println(k);
            v.forEach((kk, vv) -> {
                System.out.println("    " + kk);
                System.out.println("    error.code:" + vv.error.code());
                System.out.println("    error.message:" + vv.error.message());
                vv.replicaInfos.forEach((kkk, vvv) -> {
                    if (!Objects.equals("__consumer_offsets", kkk.topic())) {
                        System.out.println("        -topic:" + kkk.topic());
                        System.out.println("         partition:" + kkk.partition());
                        System.out.println("         vvv:" + vvv.toString());
                    }
                });
            });
        });


        /*
         * Group
         */
        System.out.println("========= Group");
        final ListConsumerGroupsResult consumerGroups = adminClient.listConsumerGroups();

        final Collection<ConsumerGroupListing> groupListings = consumerGroups.all().get();
        groupListings.forEach(g -> {
            System.out.println(g.groupId());
        });

        /**
         * Describe Groups
         */
        System.out.println("========= Describe Groups");
        final Map<String, ConsumerGroupDescription> groupDescriptionMap = adminClient.describeConsumerGroups(Collections.singletonList("demo")).all().get();
        groupDescriptionMap.forEach((k, v) -> {
            System.out.println(k);
            System.out.println("    " + v.toString());
            System.out.println("        " + v.groupId());
            v.members().forEach(m -> System.out.println("        members:" + m.toString()));
            System.out.println("        state:" + v.state().toString());
            System.out.println("        isSimpleConsumerGroup:" + v.isSimpleConsumerGroup());
            System.out.println("        coordinator:" + v.coordinator().toString());
        });


        /**
         * Metrics
         */
        System.out.println("========= Metrics");
        final Map<MetricName, ? extends Metric> metrics = adminClient.metrics();
        metrics.entrySet().forEach(e -> {
            final MetricName metricName = e.getKey();
            System.out.println(metricName.toString());
            final Metric metric = e.getValue();
            final Object metricValue = metric.metricValue();
            System.out.println("    " + metricValue);
        });

        /**
         *
         */
        adminClient.close();

    }

}
