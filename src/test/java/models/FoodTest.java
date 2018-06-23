package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void newFoodtype_CorrectlyInstantiated() {
        Food food = new Food("Chinese");
        assertTrue(food instanceof Food);
    }

    @Test
    void setName() {
        Food food = setupChineseFood();
        String newName = "British";
        food.setName(newName);
        assertEquals(newName, food.getName());
    }

    @Test
    void setId() {
        Food food = setupBrazilianFood();
        food.setId(1);
        assertEquals(1, food.getId());
    }

    //Helpers
    Food setupChineseFood() {
        return new Food("Chinese");
    }

    Food setupBrazilianFood() {
        return new Food("Brazilian");
    }

    Food setupThaiFood() {
        return new Food("Thai");
    }


}