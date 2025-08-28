package com.tutorial.ia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInsight {
    private String emotionalTrend;
    private String topicAnalysis;
    private List<String> recommendations;
}
