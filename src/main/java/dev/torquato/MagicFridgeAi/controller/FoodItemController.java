package dev.torquato.MagicFridgeAi.controller;

import dev.torquato.MagicFridgeAi.model.FoodItem;
import dev.torquato.MagicFridgeAi.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @PostMapping("/criar")
    public ResponseEntity<FoodItem> addProduct(@RequestBody FoodItem foodItem) {
        FoodItem newIten = foodItemService.addProduct(foodItem);
        return ResponseEntity.ok(newIten);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FoodItem>> listProduct() {
        return ResponseEntity.ok(foodItemService.listProducts());

    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listProductForId(@PathVariable Long id) {
        Optional<FoodItem> item = foodItemService.listProductForId(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Id não encontrado no nosso banco de dados");
        }
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity <?> deleteItemForId(@PathVariable Long id){
        FoodItem item = foodItemService.deleteItemForId(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/alterar/{id}")
   public ResponseEntity<?> changedProduct(@PathVariable Long id, @RequestBody FoodItem foodItem) {
       FoodItem updatedItem = foodItemService.changeProduct(foodItem, id);
       return ResponseEntity.ok("Item atualizado para: "+ updatedItem);
    }
}


