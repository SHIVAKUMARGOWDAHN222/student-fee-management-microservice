package com.org.feeprocess.config;


import com.org.feeprocess.dto.EnrollmentEventDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EnrollmentEventDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EnrollmentEventDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, EnrollmentEventDTO> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, EnrollmentEventDTO.class);
        configs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new EnrollmentEventDTODeserializer());
    }

    @Bean
    public ContainerProperties containerProperties() {
        ContainerProperties containerProperties = new ContainerProperties("enrollment-events");
        return containerProperties;
    }
}

