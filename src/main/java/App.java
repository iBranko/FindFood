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

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        Sql2oRestaurantDao restaurantDao;
        Sql2oFoodDao foodDao;
        Connection con;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/FoodFind.db;INIT=RUNSCRIPT from 'classpath:DB/create.sql'";
        //String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodDao = new Sql2oFoodDao(sql2o);
        con = sql2o.open();

        //CREATE
        get("restaurant/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("type", "restaurant");
            model.put("foods", foodDao.getAll());
            return new ModelAndView(model, "restaurant-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("restaurant/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String address = req.queryParams("address");
            String zipcode = req.queryParams("zipcode");
            String phone = req.queryParams("phone");
            String website = req.queryParams("website");
            String email = req.queryParams("email");
            Restaurant restaurant = new Restaurant(name, address, zipcode, phone, website, email);
            restaurantDao.add(restaurant);
            model.put("addedName", name);
            model.put("allRestaurants", restaurantDao.getAll());
            return new ModelAndView(model, "restaurants.hbs");
        }, new HandlebarsTemplateEngine());

        get("food/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("type", "food");
            return new ModelAndView(model, "food-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("food/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Food food = new Food(name);
            foodDao.add(food);
            model.put("addedName", name);
            model.put("allFoods", foodDao.getAll());
            return new ModelAndView(model, "foods.hbs");
        }, new HandlebarsTemplateEngine());

        //READ
        get("restaurant/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int restaurantId = Integer.parseInt(req.params("id"));
            model.put("object", restaurantDao.getById(restaurantId));
            model.put("type", "restaurant");
            model.put("foods", restaurantDao.getAllFoodByRestaurantId(restaurantId));
            return new ModelAndView(model, "restaurant-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/restaurants", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("allRestaurants", restaurantDao.getAll());
            return new ModelAndView(model, "restaurants.hbs");
        }, new HandlebarsTemplateEngine());

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/results", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String text = req.queryParams("text");
            if (restaurantDao.getByName(text).size() > 0 || foodDao.getByName(text).size() > 0) {
                model.put("found", true);
            }
            model.put("allRestaurants", restaurantDao.getByName(text));
            model.put("allFoods", foodDao.getByName(text));
            return new ModelAndView(model, "results.hbs");
        }, new HandlebarsTemplateEngine());

        get("food/:id/find", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int foodId = Integer.parseInt(req.params("id"));
            if (foodDao.getAllRestaurantsForAFoodType(foodId).size() > 0) {
                model.put("found", true);
            }
            model.put("allRestaurants", foodDao.getAllRestaurantsForAFoodType(foodId));
            return new ModelAndView(model, "results.hbs");
        }, new HandlebarsTemplateEngine());

        get("food/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int foodId = Integer.parseInt(req.params("id"));
            Food food = foodDao.getById(foodId);
            model.put("object", food);
            model.put("type", "food");
            return new ModelAndView(model, "food-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/foods", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("allFoods", foodDao.getAll());
            return new ModelAndView(model, "foods.hbs");
        }, new HandlebarsTemplateEngine());

        //UPDATE
        get("/restaurant/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int restaurantId = Integer.parseInt(req.params("id"));
            model.put("edit", restaurantDao.getById(restaurantId));
            model.put("type", "restaurant");
            model.put("foods", foodDao.getAll());
            return new ModelAndView(model, "restaurant-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/restaurant/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int restaurantId = Integer.parseInt(req.params("id"));
            Restaurant restaurant = restaurantDao.getById(restaurantId);

            restaurantDao.clearAllFoodsByRestautantId(restaurantId);

            for(String s : req.queryParamsValues("food")) {
                restaurantDao.addRestaurantToFoodType(restaurant, foodDao.getById(Integer.parseInt(s)));
            }

            String newName = req.queryParams("name");
            restaurant.setName(newName);
            String newAddress = req.queryParams("address");
            restaurant.setAddress(newAddress);
            String newZipcode = req.queryParams("zipcode");
            restaurant.setZipcode(newZipcode);
            String newPhone = req.queryParams("phone");
            restaurant.setPhone(newPhone);
            String newWebsite = req.queryParams("website");
            restaurant.setWebsite(newWebsite);
            String newEmail = req.queryParams("email");
            restaurant.setEmail(newEmail);
            restaurantDao.update(restaurant);

            model.put("object", restaurant);
            model.put("edited", true);
            model.put("type", "restaurant");

            return new ModelAndView(model, "restaurant-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/food/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int foodId = Integer.parseInt(req.params("id"));
            Food food = foodDao.getById(foodId);
            model.put("edit", food);
            model.put("type", "food");
            return new ModelAndView(model, "food-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/food/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int foodId = Integer.parseInt(req.params("id"));
            Food food = foodDao.getById(foodId);

            String newName = req.queryParams("name");
            food.setName(newName);
            foodDao.update(food);

            model.put("object", food);
            model.put("edited", true);
            model.put("type", "food");

            return new ModelAndView(model, "food-details.hbs");
        }, new HandlebarsTemplateEngine());

        //DELETE
        get("/restaurant/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int restaurantId = Integer.parseInt(req.params("id"));
            String name = restaurantDao.getById(restaurantId).getName();
            model.put("deletedName", name);
            restaurantDao.deleteById(restaurantId);
            model.put("allRestaurants", restaurantDao.getAll());
            return new ModelAndView(model, "restaurants.hbs");
        }, new HandlebarsTemplateEngine());

        get("/food/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int foodId = Integer.parseInt(req.params("id"));
            String name = foodDao.getById(foodId).getName();
            model.put("deletedName", name);
            foodDao.deleteById(foodId);
            model.put("allFoods", foodDao.getAll());
            return new ModelAndView(model, "foods.hbs");
        }, new HandlebarsTemplateEngine());

    }
}
