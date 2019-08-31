package xyz.kail.kafka.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

/**
 * 消费者 工具
 */
@Slf4j
public class KafkaConsumerTool {

    private static KafkaConsumer<String, String> consumer;

    /**
     * 消费者默认配置
     */
    public static synchronized KafkaConsumer<String, String> defaultConsumer(String servers, String groupId, Function<Map<String, Object>, Map<String, Object>> func) {
        if (null == consumer) {

            final Map<String, Object> conf = func.apply(defaultConf(servers, groupId));

            consumer = new KafkaConsumer<>(conf);
        }
        return consumer;
    }


    /**
     * 消费者默认配置
     */
    public static Map<String, Object> defaultConf(String servers, String groupId) {

        final Map<String, Object> props = new HashMap<>();
        // 连接地址
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        // GroupID
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // 是否自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 自动提交的频率
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
        // Session超时设置
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        // 键的反序列化方式
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 值的反序列化方式
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return props;
    }


    /**
     * 从配置文件读取 消费者配置
     */
    public static synchronized KafkaConsumer<String, String> propertiesConsumer(String configFile) {
        if (null != consumer) {
            return consumer;
        }

        try (final InputStream inStream = ClassLoader.getSystemResourceAsStream(configFile)) {
            final Properties properties = new Properties();
            properties.load(inStream);
            consumer = new KafkaConsumer<>(properties);
        } catch (IOException ex) {
            log.error("读取配置文件 {} 出错", configFile, ex);
        }

        return consumer;
    }

}
