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

    String subject = "🎉 Bem-vindo(a) ao Finan Control. 🎉";
    String text = "Olá,\n\n" +
        "Parabéns por escolher o Finan Control para gerenciar seu dinheiro!\n\n" +
        "Por que você vai adorar usar nossa plataforma:\n" +
        "• Visão consolidada de todas as suas contas\n" +
        "• Relatórios automáticos para entender seus gastos\n" +
        "• Alertas e lembretes de pagamento\n\n" +
        "Conte conosco em cada passo da sua jornada financeira!\n\n" +
        "Equipe Finan Control";

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
