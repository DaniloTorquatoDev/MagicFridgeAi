package dev.torquato.MagicFridgeAi.service;

import dev.torquato.MagicFridgeAi.model.FoodItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatgptService {
    private final WebClient webClient;
    private final String apiKey = System.getenv("API_KEY");

    public ChatgptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.openai.com/v1")
                .build();
    }

    public Mono<String> generateRecipe(List<FoodItem> foodItem) {
        String alimentos = foodItem.stream()
                .map(item -> String.format("%s - Quantidade: %d, Validade: %s",
                        item.getName(),
                        item.getQuantity(),
                        item.getValidity()))
                .collect(Collectors.joining("\n"));

        String prompt = "Baseado no meu banco de dados, faça uma receita com os seguintes itens:"+alimentos;

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        return webClient.post()
                .uri("/chat/completions")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (List<Map<String, Object>>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return message.get("content").toString();
                    }
                    return "Nenhuma receita foi gerada";
                });
    }}

