<<<<<<<< HEAD:services/shipment/src/main/java/com/booksms/shipment/config/KafkaConfig.java
package com.booksms.shipment.config;
========
package com.booksms.store.config;
>>>>>>>> origin/main:services/store/src/main/java/com/booksms/store/config/KafkaConfig.java

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
    public NewTopic newTopic(){
        return new NewTopic("shipped", 2, (short) 1);
    }
}
