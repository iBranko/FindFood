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
        get("restaurants/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Restaurant> allRestaurants = restaurantDao.getAll();
            model.put("allRestaurants", allRestaurants);
            return new ModelAndView(model, "restaurant-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("restaurants/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String address = req.queryParams("address");
            String zipcode = req.queryParams("zipcode");
            String phone = req.queryParams("phone");
            String website = req.queryParams("website");
            String email = req.queryParams("email");
            Restaurant restaurant = new Restaurant(name, address, zipcode, phone, website, email);
            restaurantDao.add(restaurant);
            model.put("restaurant", restaurant);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //READ
        get("restaurant/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int restaurandId = Integer.parseInt(req.params("id"));
            Restaurant restaurant = restaurantDao.getById(restaurandId);
            model.put("restaurant", restaurant);
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
            List<Restaurant> allRestaurants = restaurantDao.getByName(text);
            model.put("allRestaurants", allRestaurants);
            return new ModelAndView(model, "restaurants.hbs");
        }, new HandlebarsTemplateEngine());

        //UPDATE
        get("/restaurants/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int restaurantId = Integer.parseInt(req.params("id"));
            Restaurant restaurant = restaurantDao.getById(restaurantId);
            model.put("editRestaurant", restaurant);
            return new ModelAndView(model, "restaurant-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/restaurants/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int restaurantId = Integer.parseInt(req.params("id"));
            Restaurant restaurant = restaurantDao.getById(restaurantId);

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

            model.put("restaurant", restaurant);

            return new ModelAndView(model, "restaurant-details.hbs");
        }, new HandlebarsTemplateEngine());

        //DELETE


    }
}
