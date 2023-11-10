package com.example.miniprojectai_chatgpt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComputerRequirements {
    private int id;
    private String primaryUsage;
    private String hardware;
    private String buildType;
    private double budget;
    private String other;
}
