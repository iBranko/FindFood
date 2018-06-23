package dao;

import models.Food;
import models.Restaurant;

import java.util.List;

public interface FoodDao {

    //CREATE
    void add(Food food);

    //READ
    Food getById(int id);

    List<Food> getAll();

    List<Restaurant> getAllRestaurantsForAFoodType(int foodtypeId);

    //UPDATE
    void update(Food food);

    void addFoodToRestaurant(Food food, Restaurant restaurant);

    //DELETE
    void deleteById(int id);

    void deleteAll();

}
