package com.example.api.config;

import com.example.core.dto.EmailQueueDirectDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    // TODO: application properties 환경 -> docker 로 이전 혹은 유지
    //    Spring Value annotation Kafka 관련 이슈 조사

    private Map<String, Object> producerApplicationConfig() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    @Bean
    public ProducerFactory<String, EmailQueueDirectDto> emailQueueDirectDtoProducerFactory() {
        Map<String, Object> configProps = producerApplicationConfig();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, EmailQueueDirectDto> emailQueueDirectDtoKafkaTemplate() {
        return new KafkaTemplate<>(emailQueueDirectDtoProducerFactory());
    }
}
