package dao;

import models.Food;
import models.Restaurant;

import java.util.List;

public interface RestaurantDao {

    //CREATE
    void add(Restaurant restaurant);

    //READ
    Restaurant getById(int id);

    List<Restaurant> getByName(String name);

    List<Restaurant> getAll();

    List<Food> getAllFoodByRestaurantId(int id);

    //UPDATE

    void update(Restaurant restaurant);

    void addRestaurantToFoodType(Restaurant restaurant, Food food);

    //DELETE

    void deleteById(int id);

    void clearAll();

    void clearAllFoodsByRestautantId(int id);
}
