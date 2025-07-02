package com.finan_control.auth_service.producer;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.finan_control.auth_service.dtos.EmailDto;
import com.finan_control.auth_service.model.UserModel;

@Component
public class WelcomeEmailProducer extends EmailProducer{

    public WelcomeEmailProducer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public void publishMessageEmail(UserModel userModel) {
        EmailDto emailDto = EmailDto.builder()
            .userId(userModel.getId())
            .emailTo(userModel.getEmail())
            .subject("Welcome to Finan Control")
            .text("Welcome to Finan Control")
            .build();
        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

}
