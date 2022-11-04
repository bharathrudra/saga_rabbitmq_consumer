package com.bharath.rabbitmq.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("rabbit-queue")
public class PropertyConfiguration {

    private String orderQueue;
    private String customerQueue;
    private String orderTopic;
}
