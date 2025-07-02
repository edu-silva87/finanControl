package com.finan_control.auth_service.producer;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.finan_control.auth_service.model.UserModel;

@Component
public class ValidationEmailProducer extends EmailProducer{

    public ValidationEmailProducer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public void publishMessageEmail(UserModel userModel) {
        
    }

}
