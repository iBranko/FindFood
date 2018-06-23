package dao;

import models.Food;
import models.Restaurant;

import java.util.List;

public interface RestaurantDao {

    //CREATE
    void add(Restaurant restaurant);

    //READ
    Restaurant getById(int id);

    List<Restaurant> getAll();

    List<Food> getAllFoodByRestaurant(int id);

    //UPDATE
    void update(Restaurant restaurant);

    void addRestaurantToFoodType(Restaurant restaurant, Food food);

    //DELETE
    void deleteById(int id);

    void clearAll();
}
