package com.example.api.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    // TODO: application properties 환경 -> docker 로 이전 혹은 유지
    //    Spring Value annotation Kafka 관련 이슈 조사
//    @Resource
//    private Environment environment;

    private Map<String, Object> producerApplicationConfig() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

//    private Map<String, Object> producerDockerConfig() {
//        Map<String, Object> props = new HashMap<>();
//
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                environment.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
//
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,
//                environment.getProperty(ProducerConfig.BUFFER_MEMORY_CONFIG));
//
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG,
//                environment.getProperty(ProducerConfig.BATCH_SIZE_CONFIG));
//
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                environment.getProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
//
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                environment.getProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
//
//        props.put(ProducerConfig.RETRIES_CONFIG,
//                environment.getProperty(ProducerConfig.RETRIES_CONFIG));
//
//        return props;
//    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = producerApplicationConfig();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
