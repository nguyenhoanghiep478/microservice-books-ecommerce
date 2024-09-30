package com.booksms.shipment.config;

import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.entity.ShipmentService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Class<ShipmentService> shipmentServiceClass() {
        return ShipmentService.class;
    }

    @Bean
    public Class<ShipmentDetails> shipmentDetailsClass(){
        return ShipmentDetails.class;
    }
}
