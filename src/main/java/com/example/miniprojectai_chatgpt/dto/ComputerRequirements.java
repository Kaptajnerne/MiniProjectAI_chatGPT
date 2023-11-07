package com.example.miniprojectai_chatgpt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComputerRequirements {
    private int id;
    private String primaryUsage;
    private String formFactor;
    private double budget;
    private String popularity;
    private String other;
}
