package com.example.miniprojectai_chatgpt.controller;

import com.example.miniprojectai_chatgpt.dto.ChatRequestFromUser;
import com.example.miniprojectai_chatgpt.dto.Choice;
import com.example.miniprojectai_chatgpt.service.ChatGPTService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
public class ChatGPTRestController {


    final ChatGPTService chatGPTService;

    public ChatGPTRestController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }


    @PostMapping("/chat")
    public List<Choice> chatWithGPT(@RequestBody ChatRequestFromUser chatRequestFromUser) {
        System.out.println(chatRequestFromUser.toString());
        return chatGPTService.fetchChatGPT(chatRequestFromUser);

    }



}
