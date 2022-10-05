package com.example.mailService.config;

import com.example.core.dto.EmailQueueDirectDto;
import com.example.core.dto.EmailQueueScheduleDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private Map<String, Object> consumerFactoryConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "email");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, EmailQueueDirectDto> emailQueueDirectDtoConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerFactoryConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(EmailQueueDirectDto.class)
        );
    }

    @Bean
    public ConsumerFactory<String, EmailQueueScheduleDto> emailQueueScheduleDtoConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerFactoryConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(EmailQueueScheduleDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailQueueDirectDto> emailQueueDirectDtoContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailQueueDirectDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailQueueDirectDtoConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailQueueScheduleDto> emailQueueScheduleDtoContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailQueueScheduleDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailQueueScheduleDtoConsumerFactory());
        return factory;
    }
}
