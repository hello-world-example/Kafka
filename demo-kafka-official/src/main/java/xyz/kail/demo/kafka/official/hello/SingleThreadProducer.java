package xyz.kail.demo.kafka.official.hello;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import xyz.kail.kafka.tool.KafkaProducerTool;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.System.out;

@Slf4j
public class SingleThreadProducer {

    private static KafkaProducer<String, String> producer = KafkaProducerTool.defaultProducer("localhost:9092");

    private static ProducerRecord<String, String> newRecord(final String topicName, final Object data) {

        final String formatData = String.format("{\"type\":\"test\", \"t\":%s, \"v\":%s}", System.nanoTime(), data);

        return new ProducerRecord<>(topicName, formatData);
    }

    public static void executeNoBlocking(final String topicName) throws ExecutionException, InterruptedException {

        for (int i = 0; i < 100; i++) {

            final ProducerRecord<String, String> producerRecord = newRecord(topicName, i);

            final Future<RecordMetadata> metadataFuture = producer.send(producerRecord);

            final RecordMetadata recordMetadata = metadataFuture.get();

            log.info("send {} message, recordMetadata {} ", i, recordMetadata);
        }


    }

    public static void executeBlocking(final String topicName) throws ExecutionException, InterruptedException {

        for (int i = 0; i < 100; i++) {

            final ProducerRecord<String, String> producerRecord = newRecord(topicName, i);

            Future<RecordMetadata> metadataFuture = producer.send(producerRecord,
                    (metadata, e) -> {
                        final int partition = metadata.partition();
                        final long offset = metadata.offset();
                        out.println("The offset of the record we just sent is: " + partition + "|" + offset);
                    }
            );

            final RecordMetadata recordMetadata = metadataFuture.get();

            log.info("send {} message, recordMetadata {} ", i, recordMetadata);

        }

    }

    /**
     * 事务producer
     *
     * @param topicName topic名称
     */
    public static void tx(final String topicName) {
        producer.initTransactions();

        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>(topicName, Integer.toString(i), Integer.toString(i)));
            }
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Map<MetricName, ? extends Metric> metrics = producer.metrics();
        List<PartitionInfo> topic = producer.partitionsFor("topic");


        executeBlocking("test");

    }
}
