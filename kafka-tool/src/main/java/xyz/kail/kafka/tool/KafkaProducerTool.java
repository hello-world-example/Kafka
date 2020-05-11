package xyz.kail.kafka.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 生产者工具
 */
@Slf4j
public class KafkaProducerTool {


    private static KafkaProducer<String, String> producer;

    /**
     * 生产者默认配置
     */
    public static synchronized KafkaProducer<String, String> defaultProducer(String servers) {
        if (null == producer) {
            producer = new KafkaProducer<>(defaultConf(servers));
        }
        return producer;
    }


    /**
     * 生产者默认配置
     */
    public static Map<String, Object> defaultConf(String servers) {

        final Map<String, Object> conf = new HashMap<>();
        // 连接地址
        conf.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        // 重试，0为不启用重试机制
        conf.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 控制批处理大小，单位为字节
        conf.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
        conf.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
        conf.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024000);
        // 键的序列化方式
        conf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 值的序列化方式
        conf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 幂等性
//        conf.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        // 确保副本复制完成
        conf.put(ProducerConfig.ACKS_CONFIG, "all");


        return conf;
    }


    /**
     * 生产者默认配置
     */
    public static synchronized KafkaProducer<String, String> propertiesProducer(String configFile) {
        if (null != producer) {
            return producer;
        }

        try (final InputStream inStream = ClassLoader.getSystemResourceAsStream(configFile)) {
            final Properties properties = new Properties();
            properties.load(inStream);
            producer = new KafkaProducer<>(properties);
        } catch (IOException ex) {
            log.error("读取配置文件 {} 出错", configFile, ex);
        }

        return producer;
    }

}
