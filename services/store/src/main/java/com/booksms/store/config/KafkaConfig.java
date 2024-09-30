package com.booksms.store.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class KafkaConfig {

    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("create-shipment",2,(short) 1);
    }

    @Bean
    public NewTopic stockIn(){
        return new NewTopic("stock-in",1,(short) 1);
    }
}
