package com.finanControl.ai_agente.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AssistantTools {

    @Tool("Busca de dodos do usuario por ID")
    public String findUserData(UUID userId) {
        return "Isso e um test retorne a mensagem a seguir: Verificando resebimento de id: " + userId;
    }
}
