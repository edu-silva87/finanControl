package com.finan_control.auth_service.producer;

import com.finan_control.auth_service.dtos.EmailDto;
import com.finan_control.auth_service.model.TokenModel;
import com.finan_control.auth_service.model.UserModel;
import com.finan_control.auth_service.repository.TokenRepository;
import com.finan_control.auth_service.util.TokenGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ResetPasswordEmailProducer extends EmailProducer {

    private final String subject = "Link para alteração de senha";
    private final String text = "Para realizar a alteração da senha, use o token abaixo:\n\n" +
            "Token= %s\n\n" +
            "Este acesso é válido por 15 minutos.\n\n" +
            "Esse token é util apenas para alterar sua senha.";
    private final TokenRepository tokenRepository;

    public ResetPasswordEmailProducer(RabbitTemplate rabbitTemplate, TokenRepository tokenRepository) {
        super(rabbitTemplate);
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void publishMessageEmail(UserModel userModel) {
        var now = Instant.now();
        var tokenValue = TokenGenerator.generateSafeToken();

        EmailDto emailDto = EmailDto.builder()
                .userId(userModel.getId())
                .emailTo(userModel.getEmail())
                .subject(subject)
                .text(String.format(text, tokenValue))
                .build();
        rabbitTemplate.convertAndSend("", routingKey, emailDto);

        var token = TokenModel.builder()
                .createdAt(now)
                .expireAt(now.plusSeconds(900))
                .tokenValue(tokenValue)
                .user(userModel)
                .build();

        tokenRepository.save(token);
    }
}
