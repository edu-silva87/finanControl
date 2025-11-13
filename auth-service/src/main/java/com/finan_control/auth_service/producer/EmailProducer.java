package com.finan_control.auth_service.producer;

import com.finan_control.auth_service.model.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class EmailProducer {

    final RabbitTemplate rabbitTemplate;
    @Value("${broker.queue.email.name}")
    protected String routingKey;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public abstract void publishMessageEmail(UserModel userModel);

}
