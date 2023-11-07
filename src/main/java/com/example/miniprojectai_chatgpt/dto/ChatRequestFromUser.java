package com.example.miniprojectai_chatgpt.dto;

import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChatRequestFromUser {
    private int id;
    private String computerLink;

    @OneToOne
    private UserInformation userInformation;
}