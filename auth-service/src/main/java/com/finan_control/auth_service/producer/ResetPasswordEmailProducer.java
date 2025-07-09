package com.finan_control.auth_service.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.finan_control.auth_service.model.UserModel;

@Component
public class ResetPasswordEmailProducer extends EmailProducer{

    public ResetPasswordEmailProducer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public void publishMessageEmail(UserModel userModel) {
        
    }
}
