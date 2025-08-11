package dev.torquato.MagicFridgeAi.service;

import dev.torquato.MagicFridgeAi.model.FoodItem;
import dev.torquato.MagicFridgeAi.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {
    @Autowired
    private FoodItemRepository repository;

    public FoodItem addProduct(FoodItem foodItem) {
        return repository.save(foodItem);
    }

    public List<FoodItem> listProducts() {
        return repository.findAll();
    }

    public Optional<FoodItem> listProductForId(Long id) {
            return repository.findById(id);
    }

    public  FoodItem deleteItemForId(Long id) {
        FoodItem itemForDelete = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe esse id no nosso banco de dados!"));
                repository.deleteById(id);
                return itemForDelete;
    }

    public FoodItem changeProduct(FoodItem foodItem, Long id){
        FoodItem itemForChange = repository.findById(id)
               .orElseThrow(() -> new RuntimeException("Id não encontrado no nosso banco de dados"));

                itemForChange.setName(foodItem.getName());
                itemForChange.setCategory(foodItem.getCategory());

                FoodItem updatedProduct = repository.save(itemForChange);
                return updatedProduct;

    }

}
