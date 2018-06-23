package dao;

import models.Food;
import models.Restaurant;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oFoodDao implements FoodDao{

    private Sql2o sql2o;

    public Sql2oFoodDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Food food) {
        String query = "INSERT INTO foods (name) VALUES (:name)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(query, true)
                    .bind(food)
                    .executeUpdate()
                    .getKey();

            food.setId(id);
        } catch (Sql2oException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Food getById(int id) {
        String query = "SELECT * FROM foods WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Food.class);
        }
    }

    @Override
    public List<Food> getAll() {
        String query = "SELECT * FROM foods";
        try (Connection con = sql2o.open()) {
            List<Food> allFoods = con.createQuery(query)
                    .executeAndFetch(Food.class);
            return allFoods;
        }
    }

    @Override
    public List<Restaurant> getAllRestaurantsForAFoodType(int foodId) {
        List<Restaurant> restaurants = new ArrayList<>();

        String joinQuery = "SELECT restaurantid FROM restaurants_foods WHERE foodId = :foodId";

        try (Connection con = sql2o.open()) {
            List<Integer> allRestaurantIds = con.createQuery(joinQuery)
                    .addParameter("foodId", foodId)
                    .executeAndFetch(Integer.class);

            for (Integer restaurantId : allRestaurantIds) {
                String restaurantQuery = "SELECT * FROM restaurants WHERE id = :restaurantId";
                restaurants.add(
                        con.createQuery(restaurantQuery)
                                .addParameter("restaurantId", restaurantId)
                                .executeAndFetchFirst(Restaurant.class)
                );
            }
        } catch (Sql2oException ex) {
            ex.printStackTrace();
        }

        return restaurants;
    }

    @Override
    public void update(Food food) {

    }

    @Override
    public void addFoodToRestaurant(Food food, Restaurant restaurant) {
        String query = "INSERT INTO restaurants_foods (restaurantid, foodid) VALUES (:restaurantId, :foodId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("restaurantId", restaurant.getId())
                    .addParameter("foodId", food.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM foods WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAll() {

    }
}
