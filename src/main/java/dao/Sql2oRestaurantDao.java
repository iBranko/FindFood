package dao;

import models.Food;
import models.Restaurant;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sql2oRestaurantDao implements RestaurantDao {

    private Sql2o sql2o;

    public Sql2oRestaurantDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    //CREATE
    public void add(Restaurant restaurant) {
        String query = "INSERT INTO restaurants (name, address, zipcode, phone, website, email) VALUES (:name, :address, :zipcode, :phone, :website, :email)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(query, true)
                    .bind(restaurant)
                    .executeUpdate()
                    .getKey();

            restaurant.setId(id);
        } catch (Sql2oException ex) {
            ex.printStackTrace();
        }
    }

    //READ
    public Restaurant getById(int id) {
        String query = "SELECT * FROM restaurants WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Restaurant.class);
        }
    }

    @Override
    public List<Restaurant> getByName(String name) {
        String query = "SELECT * FROM restaurants WHERE lower(name) like lower(:name)";
        try (Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .addParameter("name", "%" + name + "%")
                    .executeAndFetch(Restaurant.class);
        }

    }

    public List<Restaurant> getAll() {
        String query = "SELECT * FROM restaurants ORDER BY lower(name)";
        try (Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .executeAndFetch(Restaurant.class);
        }
    }

    @Override
    public List<Food> getAllFoodByRestaurantId(int restaurantId) {
        List<Food> foods = new ArrayList<>();

        String joinQuery = "SELECT foodId FROM restaurants_foods WHERE restaurantid = :restaurantId";

        try (Connection con = sql2o.open()) {
            List<Integer> allFoodIds = con.createQuery(joinQuery)
                    .addParameter("restaurantId", restaurantId)
                    .executeAndFetch(Integer.class);

            for (Integer foodId : allFoodIds) {
                String foodQuery = "SELECT * FROM foods where id = :foodId";
                foods.add(con.createQuery(foodQuery)
                        .addParameter("foodId", foodId)
                        .executeAndFetchFirst(Food.class)
                );
            }

            foods.sort(new Comparator<Food>() {
                @Override
                public int compare(Food o1, Food o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

        } catch (Sql2oException ex) {
            ex.printStackTrace();
        }

        return foods;
    }

    //UPDATE
    public void update(Restaurant restaurant) {
        String query = "UPDATE restaurants SET name = :name, address = :address, zipcode = :zipcode, phone = :phone, website = :website, email = :email WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .bind(restaurant)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            ex.printStackTrace();
        }
    }

    public void addRestaurantToFoodType(Restaurant restaurant, Food food) {
        String query = "INSERT INTO restaurants_foods (restaurantId, foodId) VALUES (:restaurantId, :foodId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("restaurantId", restaurant.getId())
                    .addParameter("foodId", food.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            ex.printStackTrace();
        }
    }

    //DELETE
    public void deleteById(int id) {
        String query = "DELETE FROM restaurants WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public void clearAll() {
        String query = "TRUNCATE TABLE restaurants";
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void clearAllFoodsByRestautantId(int id) {
        String query = "DELETE FROM restaurants_foods WHERE restaurantId = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
