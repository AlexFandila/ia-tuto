package com.tutorial.ia.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJournalEntryRequest {
    private String title;
    private String entry;
    private Long userId;
}