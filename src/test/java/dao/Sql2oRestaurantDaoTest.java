package dao;

import models.Food;
import models.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oRestaurantDaoTest {

    private Connection conn;
    private Sql2oRestaurantDao restaurantDao;
    private Sql2oFoodDao foodDao;

    @BeforeEach
    void setUp() {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodDao = new Sql2oFoodDao(sql2o);
        conn = sql2o.open();
    }

    @AfterEach
    void tearDown() {
        conn.close();
    }

    //CREATE
    @Test
    void add_CorrectlyIdAssociated() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);
        assertEquals(1, restaurant.getId());
    }

    //READ
    @Test
    void getById_CorrectRestaurantReturned() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);
        assertEquals(restaurant, restaurantDao.getById(restaurant.getId()));
    }

    @Test
    void getByName_CorrectRestaurantReturned() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);
        assertEquals(restaurant, restaurantDao.getByName(restaurant.getName()));
    }

    @Test
    void getAll_AllRestaurantsReturned() {
        Restaurant restaurant1 = setupRestaurant1();
        restaurantDao.add(restaurant1);

        Restaurant restaurant2 = setupRestaurant2();
        restaurantDao.add(restaurant2);

        List<Restaurant> allRestaurants = restaurantDao.getAll();
        assertTrue(allRestaurants.contains(restaurant1) && allRestaurants.contains(restaurant2));
    }

    @Test
    void getAllFoodByRestaurantId_AllFoodsForASpecificRestaurantReturned() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);

        Food food1 = setupBrazilianFood();
        foodDao.add(food1);
        restaurantDao.addRestaurantToFoodType(restaurant, food1);

        Food food2 = setupChineseFood();
        foodDao.add(food2);
        restaurantDao.addRestaurantToFoodType(restaurant, food2);

        List<Food> foods = restaurantDao.getAllFoodByRestaurantId(restaurant.getId());
        assertTrue(foods.contains(food1) && foods.contains(food2));
    }

    //UPDATE
    @Test
    void update_InformationsUpdated() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);

        String newName = "Whole Whale";
        restaurant.setName(newName);
        String newAddress = "17 John Av";
        restaurant.setAddress(newAddress);
        String newZipcode = "54321";
        restaurant.setZipcode(newZipcode);
        String newPhone = "123-456-7890";
        restaurant.setPhone(newPhone);
        String newWebsite = "www.wholewhale.ca";
        restaurant.setWebsite(newWebsite);
        String newEmail = "whole@whale.com";
        restaurant.setEmail(newEmail);
        restaurantDao.update(restaurant);

        Restaurant updatedRestaurant = restaurantDao.getById(restaurant.getId());
        assertEquals(restaurant, updatedRestaurant);
    }

    //DELETE
    @Test
    void deleteById_CorrectRestaurantDeleted() {
        Restaurant restaurant = setupRestaurant1();
        restaurantDao.add(restaurant);
        int restaurantId = restaurant.getId();
        restaurantDao.deleteById(restaurantId);
        assertNull(restaurantDao.getById(restaurantId));
    }

    @Test
    void clearAll_AllRestaurantsDeleted() {
        Restaurant restaurant1 = setupRestaurant1();
        restaurantDao.add(restaurant1);

        Restaurant restaurant2 = setupRestaurant2();
        restaurantDao.add(restaurant2);

        restaurantDao.clearAll();

        int restaurantsAfterClear = restaurantDao.getAll().size();

        assertEquals(0, restaurantsAfterClear);
    }

    //Helpers
    private Restaurant setupRestaurant1() {
        return new Restaurant("While Whale", "333 Papa St", "12345", "504-222-3333", "www.whilewhale.com", "while@whale.com");
    }

    private Restaurant setupRestaurant2() {
        return new Restaurant("FishFood", "207 St Avenue", "92844", "402-123-6543", "www.fishfood.com", "fish@food.com");
    }

    private Food setupChineseFood() {
        return new Food("Chinese");
    }

    private Food setupBrazilianFood() {
        return new Food("Brazilian");
    }

}