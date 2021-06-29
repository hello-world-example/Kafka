package xyz.kail.demo.kafka.official;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import xyz.kail.kafka.tool.KafkaAdminTool;
import xyz.kail.kafka.tool.KafkaConsumerTool;
import xyz.kail.kafka.tool.KafkaProducerTool;

public class R {

    public static final String SERVER = "localhost:9092";

    /**
     * Producer
     */
    public static final KafkaProducer<String, String> PRODUCER = KafkaProducerTool.defaultProducer(SERVER);

    public static final AdminClient ADMIN = KafkaAdminTool.defaultAdmin(SERVER, p -> p);

}
