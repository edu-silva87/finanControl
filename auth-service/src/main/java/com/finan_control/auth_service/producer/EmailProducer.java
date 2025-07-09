package com.finan_control.auth_service.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.finan_control.auth_service.model.UserModel;

@Component
public abstract class EmailProducer {

    final RabbitTemplate rabbitTemplate;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    protected String routingKey;

   

    public abstract void publishMessageEmail(UserModel userModel);

}
