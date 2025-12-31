package com.finanControl.ai_agente.controller;

import com.finanControl.ai_agente.services.AssistantAiService;
import dev.langchain4j.service.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ai-assistant")
public class AssistantController {

    private final AssistantAiService assistantAiService;

    public AssistantController(AssistantAiService assistantAiService) {
        this.assistantAiService = assistantAiService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> askAi(){

        Result<String> result = assistantAiService.handleRequest(UUID.randomUUID());
        return ResponseEntity.ok(Map.of("response", result.content()));
    }
}
