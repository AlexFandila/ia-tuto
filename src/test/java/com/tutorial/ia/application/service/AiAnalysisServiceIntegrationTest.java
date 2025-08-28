package com.tutorial.ia.application.service;

import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.in.AiAnalysisUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AiAnalysisServiceIntegrationTest {

    @Autowired
    private AiAnalysisUseCase aiAnalysisUseCase;

    @Test
    void contextLoads() {
        assertNotNull(aiAnalysisUseCase);
    }
}
