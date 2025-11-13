package com.finan_control.auth_service.producer;

import com.finan_control.auth_service.dtos.EmailDto;
import com.finan_control.auth_service.model.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public final class WelcomeEmailProducer extends EmailProducer {

    private final String subject = "🎉 Bem-vindo(a) ao Finan Control. 🎉";
    private final String text = "Olá,\n" +
            "Parabéns por escolher o Finan Control para gerenciar seu dinheiro!\n\n" +
            "Por que você vai adorar usar nossa plataforma:\n" +
            "• Visão consolidada de todas as suas contas\n" +
            "• Relatórios automáticos para entender seus gastos\n" +
            "• Alertas e lembretes de pagamento\n\n" +
            "Conte conosco em cada passo da sua jornada financeira!\n\n" +
            "Equipe Finan Control";
    public WelcomeEmailProducer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public void publishMessageEmail(UserModel userModel) {
        EmailDto emailDto = EmailDto.builder()
                .userId(userModel.getId())
                .emailTo(userModel.getEmail())
                .subject(subject)
                .text(text)
                .build();
        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

}
