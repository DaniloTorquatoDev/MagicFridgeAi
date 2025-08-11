package dev.torquato.MagicFridgeAi.repository;

import dev.torquato.MagicFridgeAi.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository <FoodItem, Long> {
}
