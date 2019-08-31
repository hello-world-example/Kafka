package xyz.kail.kafka.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

/**
 * 生产者工具
 */
@Slf4j
public class KafkaAdminTool {


    private static AdminClient adminClient;

    /**
     * 消费者默认配置
     */
    public static synchronized AdminClient defaultAdmin(String servers, Function<Map<String, Object>, Map<String, Object>> func) {
        if (null == adminClient) {

            final Map<String, Object> conf = func.apply(defaultConf(servers));

            adminClient = AdminClient.create(conf);
        }
        return adminClient;
    }


    /**
     * 生产者默认配置
     */
    public static Map<String, Object> defaultConf(String servers) {

        final Map<String, Object> conf = new HashMap<>();
        conf.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        // 等待请求响应的最大时间量。如果超时超时之前没有收到响应，客户端将在必要时重新发送请求，或者在重试耗尽时请求失败
        conf.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 60_000);

        return conf;
    }


    /**
     * 生产者默认配置
     */
    public static synchronized AdminClient propertiesAdmin(String configFile) {
        if (null != adminClient) {
            return adminClient;
        }

        try (final InputStream inStream = ClassLoader.getSystemResourceAsStream(configFile)) {
            final Properties properties = new Properties();
            properties.load(inStream);
            adminClient = AdminClient.create(properties);
        } catch (IOException ex) {
            log.error("读取配置文件 {} 出错", configFile, ex);
        }

        return adminClient;
    }

}
