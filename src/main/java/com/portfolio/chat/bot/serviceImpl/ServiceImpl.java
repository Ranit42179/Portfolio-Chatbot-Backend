package com.portfolio.chat.bot.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class ServiceImpl {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public ServiceImpl(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public String getAnswer(String question){
        Map<String, Object> requestBody= Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );

        String response=webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type","application/json")
                .header("X-goog-api-key",geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            String text = root
                    .path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text")
                    .asText();

            return text;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }
}
