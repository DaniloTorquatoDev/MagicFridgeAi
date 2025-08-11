package dev.torquato.MagicFridgeAi.controller;

import dev.torquato.MagicFridgeAi.model.FoodItem;
import dev.torquato.MagicFridgeAi.service.ChatgptService;
import dev.torquato.MagicFridgeAi.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {
    private ChatgptService chatgptService;
    private FoodItemService service;

    public RecipeController(ChatgptService chatgptService, FoodItemService service) {
        this.chatgptService = chatgptService;
        this.service = service;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> genereteRecipe() {
        List<FoodItem> foodItems = service.listProducts();
        return chatgptService.generateRecipe(foodItems)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }


}
