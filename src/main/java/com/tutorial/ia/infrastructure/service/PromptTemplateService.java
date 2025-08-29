package com.tutorial.ia.infrastructure.service;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PromptTemplateService {

    private final Map<String, String> templateCache = new ConcurrentHashMap<>();

    public String loadTemplate(String templatePath, Map<String, Object> variables) {
        try {
            // Cargar template con cache
            String template = loadTemplateFromResources(templatePath);

            // Interpolar variables
            return interpolateVariables(template, variables);
        } catch (IOException e) {
            log.error("Error loading template: ", templatePath, e);
            throw new RuntimeException("Failed to load prompt template: " + templatePath, e);
        }
    }

    private String loadTemplateFromResources(String templatePath) throws IOException {
        // Cache check
        String cacheKey = templatePath;
        if (templateCache.containsKey(cacheKey)) {
            return templateCache.get(cacheKey);
        }

        // Load from resources
        String fullPath = "prompts/" + templatePath + ".md";
        ClassPathResource resource = new ClassPathResource(fullPath);

        if (!resource.exists()) {
            throw new IOException("Template not found: " + fullPath);
        }

        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // Cache it
        templateCache.put(cacheKey, content);
        return content;
    }

    private String interpolateVariables(String template, Map<String, Object> variables) {
        String result = template;

        if (variables != null) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                String value = entry.getValue() != null ? entry.getValue().toString() : "";
                result = result.replace(placeholder, value);
            }
        }

        return result;
    }

}
