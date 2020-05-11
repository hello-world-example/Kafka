package xyz.kail.kafka.tool;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;

import java.util.Objects;

/**
 * @see org.apache.kafka.clients.producer.ProducerRecord
 */
public class KafkaRecordBuilder<K, V> {

    private String topic;
    private Integer partition;
    private Headers headers;
    private K key;
    private V value;
    private Long timestamp;

    private KafkaRecordBuilder(String topic, V value) {
        this.topic = topic;
        this.value = value;
    }

    public static <K, V> KafkaRecordBuilder<K, V> builder(String topic, V value) {
        return new KafkaRecordBuilder<>(topic, value);
    }

    public ProducerRecord<K, V> build() {
        return new ProducerRecord<>(topic, partition, timestamp, key, value, headers);
    }

    public KafkaRecordBuilder<K, V> topic(String topic) {
        this.topic = topic;
        return this;
    }

    public KafkaRecordBuilder<K, V> partition(Integer partition) {
        this.partition = partition;
        return this;
    }

    public KafkaRecordBuilder<K, V> addHeader(Header header) {
        if (Objects.isNull(this.headers)) {
            this.headers = new RecordHeaders();
        }
        this.headers.add(header);
        return this;
    }

    public KafkaRecordBuilder<K, V> key(K key) {
        this.key = key;
        return this;
    }

    public KafkaRecordBuilder<K, V> value(V value) {
        this.value = value;
        return this;
    }

    public KafkaRecordBuilder<K, V> timestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
