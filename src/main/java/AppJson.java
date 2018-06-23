import com.google.gson.Gson;
import dao.Sql2oFoodDao;
import dao.Sql2oRestaurantDao;
import models.Food;
import models.Restaurant;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;

public class AppJson {

    public static void main(String[] args) {

        Sql2oRestaurantDao restaurantDao;
        Sql2oFoodDao foodDao;
        Connection con;
        Gson gson = new Gson();

        //String connectionString = "jdbc:h2:~/FoodFind.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodDao = new Sql2oFoodDao(sql2o);
        con = sql2o.open();

        //CREATE
        post("/restaurants/new", "application/json", (req, res) -> {
            Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);
            restaurantDao.add(restaurant);
            res.status(201);
            return gson.toJson(restaurant);
        });

        post("/foods/new", "application/json", (req, res) -> {
            Food food = gson.fromJson(req.body(), Food.class);
            foodDao.add(food);
            res.status(201);
            return gson.toJson(food);
        });

        post("restaurants/:restaurantId/food/:foodId", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("restaurantId"));
            int foodId = Integer.parseInt(req.params("foodId"));

            Restaurant restaurant = restaurantDao.getById(restaurantId);
            Food food = foodDao.getById(foodId);

            if (restaurant != null && food != null) {

                foodDao.addFoodToRestaurant(food, restaurant);
                res.status(201);
                return gson.toJson(String.format("Restaurant %s and %s Food have been associated", restaurant.getName(), food.getName()));

            } else {
                return gson.toJson("Restaurant or food not found");
            }
        });

        //READ
        get("restaurant/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Restaurant restaurant = restaurantDao.getById(id);

            if (restaurant == null) {
                return gson.toJson(String.format("Restaurant with id '%d' not found", id));
            }

            return gson.toJson(restaurant);
        });

        get("/restaurants", "application/json", (req, res) -> {
            List<Restaurant> allRestaurants = restaurantDao.getAll();

            if (allRestaurants.size() == 0) {
                return gson.toJson(String.format("There's no restaurants to show. Try adding one!"));
            }

            return gson.toJson(allRestaurants);
        });

        get("/restaurants/:id/foods", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("id"));

            if (restaurantDao.getAllFoodByRestaurant(restaurantId).size() > 0) {
                return gson.toJson(restaurantDao.getAllFoodByRestaurant(restaurantId));
            } else {
                return gson.toJson("{\"message\": \"This restaurant is not serving food yet.\"}");
            }
        });

        get("/foods", "application/json", (req, res) -> {
            List<Food> allFoods = foodDao.getAll();

            if (allFoods.size() == 0) {
                return gson.toJson(String.format("There's no restaurants to show. Try adding one!"));
            }

            return gson.toJson(allFoods);
        });

        //DELETE
        post("restaurant/:id/delete", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            restaurantDao.deleteById(id);

            if (restaurantDao.getById(id) == null) {
                res.status(204);
                return gson.toJson(String.format("Restaurant with id '%d' deleted", id));
            }

            return gson;
        });

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
