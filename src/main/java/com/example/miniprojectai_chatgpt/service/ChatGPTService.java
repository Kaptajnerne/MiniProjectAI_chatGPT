package com.example.miniprojectai_chatgpt.service;

import com.example.miniprojectai_chatgpt.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGPTService {

    // Brug konstanter for beskedroller
    private static final String USER_ROLE = "user";
    private static final String SYSTEM_ROLE = "system";

    @Value("${gpt.model}")
    private String gptModel;

    @Value("${gpt.api.key}")
    private String gptApiKey;

    @Value("${gpt.api.url}")
    private String gptApiUrl;

    private final WebClient.Builder webClientBuilder;

    public ChatGPTService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<Choice> fetchChatGPT(ComputerRequirements computerRequirements) {
        ChatRequest chatRequest = createChatRequest(computerRequirements);
        ChatResponse response = sendChatRequest(chatRequest);
        return response.getChoices();
    }

    // Opret beskeder baseret på brugerinput
    private ChatRequest createChatRequest(ComputerRequirements computerRequirements) {
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel(gptModel);
        List<Message> lstMessages = new ArrayList<>();

        // Construct a message with the computer requirements
        String userMessage = String.format("I'm looking for a computer with the following specifications:\n" +
                        "Primary usage: %s\n" +
                        "Budget: %.2f\n" +
                        "Form factor: %s\n" +
                        "Popularity: %s\n" +
                        "Other: %s",
                computerRequirements.getPrimaryUsage(),
                computerRequirements.getBudget(),
                computerRequirements.getFormFactor(),
                computerRequirements.getPopularity(),
                computerRequirements.getOther());


        lstMessages.add(new Message(SYSTEM_ROLE, "You are a helpful assistant."));
        lstMessages.add(new Message(USER_ROLE, userMessage));
        lstMessages.add(new Message(USER_ROLE, "Can you tell me which computer that fits my requirements and" +
                "try to explain why it suits me," +
                " can you then try to explain what some of the components in the computer does, " +
                "Can you make it as simple as possible" +
                "Can you give a link to a page where I can purchase the computer" +
                "Can you start with saying 'I've found the perfect computer just for you! By SpecSeeker - Kaptajnerne'"));

        chatRequest.setMessages(lstMessages);
        chatRequest.setTemperature(1);
        chatRequest.setMaxTokens(250);
        chatRequest.setStream(false);
        chatRequest.setPresencePenalty(1);

        return chatRequest;
    }


    // Send chatanmodningen ved hjælp af WebClient
    private ChatResponse sendChatRequest(ChatRequest chatRequest) {
        return webClientBuilder.baseUrl(gptApiUrl)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(gptApiKey))
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();
    }

}
